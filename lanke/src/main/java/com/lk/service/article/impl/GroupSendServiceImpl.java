package com.lk.service.article.impl;

import com.lk.communicate.server.util.Tools2;
import com.lk.dao.DaoSupport;
import com.lk.entity.Page;
import com.lk.entity.system.User;
import com.lk.service.article.GroupSendService;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.internetPictures.InternetPicturesManager;
import com.lk.service.system.internetmaterial.InternetMaterialManager;
import com.lk.service.system.wechatuser.WeChatUserManager;
import com.lk.service.wechat.WeixinService;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.recordMaterial.RecordMaterialManager;
import com.lk.service.weixin.template.TemplateManager;
import com.lk.util.*;
import com.lk.util.qrcode.QrcodeUtil;
import com.lk.wechat.util.TemplateMsgUtil;
import com.lk.wechat.util.WechatMaterialUtil;
import com.lk.wechat.util.WechatMessageUtil;
import com.lk.wechat.util.WechatUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author myq
 * @description 公众号群发业务处理
 * @create 2019-01-11 15:19
 */
@Service("GroupSendService")
public class GroupSendServiceImpl implements GroupSendService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource(name = "recordMaterialService")
    private RecordMaterialManager recordMaterialService;
    @Resource(name = "intenetService")
    private IntenetManager intenetService;
    @Resource(name="internetmaterialService")
    private InternetMaterialManager internetmaterialService;
    @Resource(name="internetPicturesService")
    private InternetPicturesManager internetPicturesService;
    @Resource(name="autoReplyService")
    private AutoReplyService autoReplyService;
    @Resource(name = "wechatuserService")
    private WeChatUserManager wechatuserService;

    @Resource(name = "weixinService")
    private WeixinService weixinService;


    @Override
    public JSONObject loadIndex(PageData pd, Page page) throws Exception{
        JSONObject result = initJSON();

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        pd.put("INTERNET_ID", user.getINTENET_ID());

        page.setPd(pd);
        List<PageData> varList = (List<PageData>) dao.findForList("SendRecordMapper.listIndex", page);

        // 查询该记录的图文数据
        for (int i = 0; i < varList.size(); i++) {
            PageData pdMaterial = new PageData();
            pdMaterial.put("SENDRECORD_ID", varList.get(i).getString("SENDRECORD_ID"));
            List<PageData> materialList = recordMaterialService.findBySendRecordId(pdMaterial);
            varList.get(i).put("mList", materialList);
        }

        result.put("varList", varList);
        return result;
    }


    @Override
    public JSONObject loadNews(PageData pd) throws Exception{
        JSONObject result = initJSON();

        User user = Jurisdiction.getSessionUser().getUser();//得到用户

        //加载网吧信息
        PageData pdInternet = new PageData();
        pdInternet.put("INTENET_ID", user.getINTENET_ID());
        pdInternet = intenetService.findById(pdInternet);
        result.put("internet", pdInternet);

        //编辑图文，取数据
        System.out.println(pd.toString());

        if(StringUtil.isNotEmpty(pd.getString("id"))){
            pd.put("SENDRECORD_ID", pd.getString("id"));
            List<PageData> mList = recordMaterialService.findBySendRecordId(pd);
            result.put("mList", mList);
        }else{
            result.put("mList", "");
        }

        return result;
    }


    @Override
    public JSONObject delNews(PageData pd) throws Exception{
        JSONObject result = initJSON();

        PageData pdRecord = new PageData();
        pdRecord.put("SENDRECORD_ID", pd.getString("id"));
        pdRecord = (PageData) dao.findForObject("SendRecordMapper.findById", pdRecord);
        if (!StringUtil.isNotEmpty(pdRecord)) {
            result.put("errcode", 1001);
            result.put("errmsg", "参数错误，找不到数据");
            return result;
        } else if (pdRecord.getString("STATE").equals("2")) {
            result.put("errcode", 1002);
            result.put("errmsg", "图文已群发，不能删除！");
            return result;
        }

        //删除素材
        dao.delete("SendRecordMapper.deleteMaterialByRecord",pdRecord);

        //删除关系表
        dao.delete("SendRecordMapper.deleteRelationByRecord",pdRecord);

        //删除记录表
        dao.delete("SendRecordMapper.delete", pdRecord);


        return result;
    }


    @Override
    public JSONObject saveNews(PageData pd) throws Exception{
        JSONObject result = initJSON();

        User user = Jurisdiction.getSessionUser().getUser();//得到用户

        PageData pdRecord = new PageData();
        if(StringUtil.isEmpty(pd.getString("id"))){
            //保存
            pdRecord.put("SENDRECORD_ID", StringUtil.get32UUID());
            pdRecord.put("INTERNET_ID", user.getINTENET_ID());
            pdRecord.put("CREATE_TIME", Tools.date2Str(new Date()));
            pdRecord.put("STATE", "1");//未发（草稿）
            dao.save("SendRecordMapper.save",pdRecord);//保存记录

        }else{
            //修改
            String sendRecordId = pd.getString("id");

            pdRecord.put("SENDRECORD_ID", sendRecordId);
            pdRecord = (PageData) dao.findForObject("SendRecordMapper.findById", pdRecord);
            if (StringUtil.isEmpty(pdRecord)) {
                result.put("errcode", 1001);
                result.put("errmsg", "参数错误，找不到数据");
                return result;
            }else if(pdRecord.getString("STATE").equals("2")){
                result.put("errcode", 1003);
                result.put("errmsg", "图文已经群发，不能再修改保存");
                return result;
            }
        }
        result.put("sendRecordId", pdRecord.getString("SENDRECORD_ID"));
        List<PageData> recordList = (List<PageData>) dao.findForList("SendRecordMapper.findRecordBySendId", pdRecord);

        //保存图文内容（item：SEQUENCE,TITLE,CREATE_USER,CONTENT_SOURCE_URL,CONTENT,DIGEST,PATH）
        JSONArray array = JSONArray.fromObject(pd.getString("item"));

        //前端传递的数量 《 已保存的数量，则需要删除以保存中多余的图文
        if(array.size() < recordList.size()){
            int num = array.size();
            for (int i = num; i < recordList.size(); i++) {
                PageData pdd = new PageData();
                pdd.put("SENDRECORD_ID", recordList.get(num).getString("sendrecord_id"));
                pdd.put("SEQUENCE", i);
                //删除素材
                dao.delete("SendRecordMapper.deleteMaterialByRecord",pdd);

                //删除关系表
                dao.delete("SendRecordMapper.deleteRelationByRecord",pdd);
            }
        }
        for (int i = 0; i < array.size(); i++) {
            JSONObject news =  array.getJSONObject(i);

            String path = news.getString("PATH");
            if(path.startsWith("/")){
                path = path.substring(1);
                if(path.startsWith("uploadFiles/uploadImgs/")){
                    path = path.split("uploadFiles/uploadImgs/")[1];
                }
            }

            PageData pdNews = new PageData();
            pdNews.put("TITLE", news.getString("TITLE"));
            pdNews.put("CREATE_USER", news.getString("CREATE_USER"));
            pdNews.put("CONTENT_SOURCE_URL", news.getString("CONTENT_SOURCE_URL"));
            pdNews.put("CONTENT", news.getString("CONTENT").replaceAll("&#39;", "'"));
            pdNews.put("DIGEST", news.getString("DIGEST"));
            pdNews.put("THUMB_MEDIA_ID", "");//封边图片的素材id

            PageData pdRecordNews = new PageData();
            pdRecordNews.put("SENDRECORD_ID", pdRecord.getString("SENDRECORD_ID"));
            pdRecordNews.put("SEQUENCE", i);

            PageData pdImg = new PageData();
            pdImg.put("NAME", path.split("/")[1]);
            pdImg.put("PATH", path);
            if(StringUtil.isEmpty(pd.getString("id")) || i > (recordList.size()-1)){
                System.out.println("add-------------i"+i);
                //新增图文
                pdNews.put("INTERNETMATERIAL_ID", StringUtil.get32UUID());
                pdNews.put("INTERNET_ID", user.getINTENET_ID());
                pdNews.put("CREATE_TIME", Tools.date2Str(new Date()));
                internetmaterialService.save(pdNews);

                //新增图片
                pdImg.put("PICTURE_ID", StringUtil.get32UUID());
                pdImg.put("INTERNET_ID", user.getINTENET_ID());
                pdImg.put("CREATE_TIME", Tools.date2Str(new Date()));
                internetPicturesService.save(pdImg);

                //新增关联表
                pdRecordNews.put("RECORDMATERIAL_ID", StringUtil.get32UUID());
                pdRecordNews.put("INTERNETMATERIAL_ID", pdNews.getString("INTERNETMATERIAL_ID"));
                pdRecordNews.put("PICTURE_ID", pdImg.getString("PICTURE_ID"));//图片id
                recordMaterialService.save(pdRecordNews);
            }else{
                //修改图文
                pdNews.put("INTERNETMATERIAL_ID", recordList.get(i).getString("internetmaterial_id"));
                internetmaterialService.edit(pdNews);

                //修改图片
                pdImg.put("PICTURE_ID", recordList.get(i).getString("picture_id"));
                if(!path.equals(recordList.get(i).getString("PATH"))){
                    internetPicturesService.edit(pdImg);
                }

                //修改关联表
                pdRecordNews.put("RECORDMATERIAL_ID", recordList.get(i).getString("recordmaterial_id"));
                pdRecordNews.put("INTERNETMATERIAL_ID", pdNews.getString("INTERNETMATERIAL_ID"));
                pdRecordNews.put("PICTURE_ID", pdImg.getString("PICTURE_ID"));//图片id
                recordMaterialService.edit(pdRecordNews);
            }

        }

        return result;
    }


    @Override
    public JSONObject saveImgs(String base64Img) throws Exception{
        JSONObject result = initJSON();

        if(StringUtil.isEmpty(base64Img)){
            result.put("errcode", 1);
            result.put("errmsg", "非法参数，数据为空");
            return result;
        }else if(!base64Img.startsWith("data:image/")){
            result.put("errcode", 2);
            result.put("errmsg", "非法参数，格式错误");
            return result;
        }

        User user = Jurisdiction.getSessionUser().getUser();//得到用户

        String fileName = new Date().getTime() + ".jpg";
        String path = user.getINTENET_ID() + "/" + fileName ;

        //上传到本地
        String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + path;
        Tools.pic(base64Img, filePath);

        String picture_url = Const.FILEPATHIMG + path;
        result.put("picture_url", picture_url);
        return result;
    }


    @Override
    public JSONObject preview(PageData pd) throws Exception{
        JSONObject result = initJSON();

        result = saveNews(pd);
        if(!result.get("errcode").toString().equals("0")){
            return result;
        }
        //通过网吧易购买家（测试）、揽客使用的公众号（正式）来实现预览功能
        String internet_id = "";
        if(PublicManagerUtil.URL1.contains("wkbar.cc")){
            internet_id = "0e25b7bd3c38483cb3ea34c2ae52602b";
        }else  if(PublicManagerUtil.URL1.contains("lanke8.cc")){
            internet_id = "066e25efa74043bfb92339d8f46c3858";
        }
        String token = autoReplyService.getAuthToken(internet_id);

        //获取该图文信息
        PageData pdMaterial = new PageData();
        pdMaterial.put("SENDRECORD_ID", pd.getString("id"));
        pdMaterial.put("SEQUENCE", pd.getString("sequence"));
        pdMaterial = recordMaterialService.findByMediaIdAndSequence(pdMaterial);
        String content = WechatUtil.saveImgOrVideoToWx(pdMaterial, token, pd.getString("pre"));
        pdMaterial.put("CONTENT", content);

        //上传封面图片
        PageData pdPic = new PageData();
        pdMaterial.put("SENDRECORD_ID", pd.getString("id"));
        pdMaterial.put("SEQUENCE", pd.getString("sequence"));
        pdPic = (PageData) dao.findForObject("SendRecordMapper.findPicBySendAndSequence", pdMaterial);
        String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + pdPic.getString("path");
        JSONObject pic_result = WechatMaterialUtil.addMaterialEver(token, filePath, "image");
        pdMaterial.put("THUMB_MEDIA_ID", pic_result.getString("media_id"));


        //上传图文消息，
        JSONObject postResult = new JSONObject();
        String media_id = "";
        if(pdMaterial.get("MEDIA_ID") != null){
            //修改
            pdMaterial.put("INTERNETMATERIAL_ID", pdMaterial.get("MEDIA_ID"));
            postResult = WechatMaterialUtil.updateNew(pdMaterial, token, "0");

            media_id = pdMaterial.get("MEDIA_ID").toString();
        }else{
            //新增
            postResult =WechatMaterialUtil.postNew(pdMaterial, token);
            media_id = postResult.getString("media_id");
        }

        //将图文连接生成二维码，传给页面，用户扫描二维码查看图文消息
        if((postResult.containsKey("errcode") &&
                postResult.getString("errcode").equals("0")) || postResult.containsKey("media_id")){
            JSONObject materialResult = WechatMaterialUtil.getVedioMaterial(media_id, token);
            JSONArray array = materialResult.getJSONArray("news_item");
            String url = array.getJSONObject(0).getString("url");

            String img_base64 = QrcodeUtil.putBase64(url, 360, 360);
            result.put("img_base64",img_base64);
        }else{
            result.put("errcode", 1004);
            result.put("errmsg", "上传图文信息失败");
        }
        return result;
    }


    @Override
    public JSONObject getInternetList(PageData pd) throws Exception{
        JSONObject result = initJSON();

        pd.put("state", 2);//正式用户（已经授权的）
        List<PageData> list = intenetService.findByState(pd);
        result.put("list", list);
        return result;
    }

    @Override
    public JSONObject groupSend(PageData pd) throws Exception{
        JSONObject result = initJSON();

        if(StringUtil.isEmpty(pd.get("id")) || StringUtil.isEmpty(pd.get("internetList"))){
            result.put("errcode", 1);
            result.put("errmsg", "非法参数，请求参数为空");
            return result;
        }

        pd.put("SENDRECORD_ID", pd.getString("id"));
        List<PageData> mList = recordMaterialService.findBySendRecordId(pd);
        if(mList.size() == 0){
            result.put("errcode", 3);
            result.put("errmsg", "非法参数，参数错误");
            return result;
        }

        String[] internetIdList = (String[]) pd.get("internetList");
        GroupSendThread thread = new GroupSendThread(pd, internetIdList, mList);
        new Thread(thread).start();

        return result;
    }

    /**
     * 线程--群发图文
     */
    class GroupSendThread implements Runnable {
        private PageData pd;
        private String[] internetIdList;
        private List<PageData> list = new ArrayList<>();
        public GroupSendThread(PageData pd, String[] internetIdList, List<PageData> list) {
            this.pd = pd;
            this.internetIdList = internetIdList;
            this.list = list;
        }
        public void run() {
            try {
                groupSend(pd, internetIdList, list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //群发线程任务
    private void groupSend(PageData pd, String[] internetIdList, List<PageData> list) throws Exception{
        String pre = pd.getString("pre");
        boolean flag = true;
        for (int i = 0; i < internetIdList.length; i++) {
            String internet_id = internetIdList[i];
            String token = autoReplyService.getAuthToken(internet_id);

            for (int j = 0; j < list.size(); j++) {
                PageData pdMaterial = list.get(j);

                //上传图文的封面图片
                String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + pdMaterial.getString("PATH");
                JSONObject pic_result = WechatMaterialUtil.addMaterialEver(token, filePath, "image");
                if(!pic_result.containsKey("media_id")){
                    //上传图片失败
                    flag = false;
                    break;
                }
                pdMaterial.put("THUMB_MEDIA_ID", pic_result.getString("media_id"));
                pdMaterial.put("IMG_URL", pic_result.getString("url"));

                //上传图文中的图片和视频
                pdMaterial.put("CONTENT_OLD", pdMaterial.getString("CONTENT"));
                String content = WechatUtil.saveImgOrVideoToWx(pdMaterial, token, pre);
                pdMaterial.put("CONTENT", content);
            }

            if(!flag){
                //上传封面图片失败

                //保存公众号群发记录表internet_group_send
                PageData pdSend = new PageData();
                pdSend.put("id", StringUtil.get32UUID());
                pdSend.put("send_type", 1);//图文信息
                pdSend.put("foreign_id", pd.getString("id"));//记录id
                pdSend.put("create_time", Tools.date2Str(new Date()));
                pdSend.put("status", 0);//发送失败
                pdSend.put("internet_id", internet_id);
                pdSend.put("errmsg", "上传封面图片失败");
                dao.save("InternetGroupSendMapper.save", pdSend);
            }

            //上传图文素材
            JSONObject jsonResult = WechatMaterialUtil.postNews(list, token);

            //选择所有的粉丝，群发图文
            if(jsonResult.containsKey("media_id")){
                //上传成功

                pd.put("media_id", jsonResult.getString("media_id"));
                JSONObject json = WechatMessageUtil.sendNewsByTag(token, "mpnews", pd);

                //保存公众号群发记录表internet_group_send
                PageData pdSend = new PageData();
                pdSend.put("id", StringUtil.get32UUID());
                pdSend.put("send_type", 1);//图文信息
                pdSend.put("foreign_id", pd.getString("id"));//记录id
                pdSend.put("create_time", Tools.date2Str(new Date()));
                pdSend.put("status", 2);//发送中
                pdSend.put("internet_id", internet_id);
                dao.save("InternetGroupSendMapper.save", pdSend);
                if(json.getString("errcode").equals("0")){
                    //正确返回：{"errcode":0, "errmsg":"send job submission success", "msg_id":34182, "msg_data_id": 206227730}

                    //修改公众号群发记录表internet_group_send
                    pdSend.put("status", 1);//发送成功
                    pdSend.put("update_time", Tools.date2Str(new Date()));
                    pdSend.put("errmsg", json.get("errmsg"));
                    pdSend.put("msg_data_id", json.getString("msg_id") + ";" +json.getString("msg_data_id"));
                    dao.update("InternetGroupSendMapper.edit", pdSend);

                    //发送成功，保存对应网吧的发送记录、关系表、图文表和图片表
                    saveNewsAfterSend(internet_id, jsonResult.getString("media_id"), list, pd.getString("id"));
                }else{
                    //修改公众号群发记录表internet_group_send
                    pdSend.put("status", 0);//发送失败
                    pdSend.put("update_time", Tools.date2Str(new Date()));
                    pdSend.put("errmsg", json.get("errmsg"));
                    String msg_id = "";
                    if(StringUtil.isNotEmpty(json.get("msg_id"))){
                        msg_id += json.getString("msg_id");
                    }
                    if(StringUtil.isNotEmpty(json.get("msg_data_id"))){
                        msg_id = msg_id + ";" +json.getString("msg_data_id");
                    }
                    pdSend.put("msg_data_id", msg_id);
                    dao.update("InternetGroupSendMapper.edit", pdSend);
                }
            }else{
                //上传图文失败

                //保存公众号群发记录表internet_group_send
                PageData pdSend = new PageData();
                pdSend.put("id", StringUtil.get32UUID());
                pdSend.put("send_type", 1);//图文信息
                pdSend.put("foreign_id", pd.getString("id"));//记录id
                pdSend.put("create_time", Tools.date2Str(new Date()));
                pdSend.put("status", 0);//发送失败
                pdSend.put("internet_id", internet_id);
                String errmsg = "上传图文失败";
                if(StringUtil.isNotEmpty(jsonResult.get("errmsg"))){
                    errmsg += jsonResult.getString("errmsg");
                }
                pdSend.put("errmsg", errmsg);
                dao.save("InternetGroupSendMapper.save", pdSend);
            }
        }
    }


    //群发成功后，保存网吧数据
    private void saveNewsAfterSend(String internet_id, String media_id, List<PageData> list, String id) throws Exception{
        //把本图文记录更改为已发送
        PageData pdSendRecord = new PageData();
        pdSendRecord.put("SENDRECORD_ID", id);
        pdSendRecord.put("STATE", "2");//已发送
        pdSendRecord.put("SEND_TIME", Tools.date2Str(new Date()));
        dao.update("SendRecordMapper.edit", pdSendRecord);

        //保存记录
        PageData pdRecord = new PageData();
        pdRecord.put("SENDRECORD_ID", StringUtil.get32UUID());
        pdRecord.put("INTERNET_ID", internet_id);
        pdRecord.put("CREATE_TIME", Tools.date2Str(new Date()));
        pdRecord.put("SEND_TIME", Tools.date2Str(new Date()));

        pdRecord.put("MEDIA_ID", media_id);
        pdRecord.put("STATE", "2");//已发送
        dao.save("SendRecordMapper.save",pdRecord);

        //保存关系表、图文表
        for (int i = 0; i < list.size(); i++) {
            PageData news = list.get(i);
            //新增图文
            PageData pdNews = new PageData();
            pdNews.put("TITLE", news.getString("TITLE"));
            pdNews.put("CREATE_USER", news.getString("CREATE_USER"));
            pdNews.put("CONTENT_SOURCE_URL", news.getString("CONTENT_SOURCE_URL"));
            pdNews.put("CONTENT", news.getString("CONTENT_OLD"));
            pdNews.put("DIGEST", news.getString("DIGEST"));
            pdNews.put("THUMB_MEDIA_ID", news.getString("THUMB_MEDIA_ID"));//封边图片的素材id

            pdNews.put("INTERNETMATERIAL_ID", StringUtil.get32UUID());
            pdNews.put("INTERNET_ID", internet_id);
            pdNews.put("CREATE_TIME", Tools.date2Str(new Date()));
            internetmaterialService.save(pdNews);

            //新增图片
            PageData pdImg = new PageData();
            pdImg.put("NAME", news.getString("PATH").split("/")[1]);
            pdImg.put("PATH", news.getString("PATH"));
            pdImg.put("MEDIA_ID", news.getString("THUMB_MEDIA_ID"));
            pdImg.put("URL", news.getString("IMG_URL"));

            pdImg.put("PICTURE_ID", StringUtil.get32UUID());
            pdImg.put("INTERNET_ID", internet_id);
            pdImg.put("CREATE_TIME", Tools.date2Str(new Date()));
            internetPicturesService.save(pdImg);

            //新增关联表
            PageData pdRecordNews = new PageData();
            pdRecordNews.put("SENDRECORD_ID", pdRecord.getString("SENDRECORD_ID"));
            pdRecordNews.put("SEQUENCE", i);

            pdRecordNews.put("RECORDMATERIAL_ID", StringUtil.get32UUID());
            pdRecordNews.put("INTERNETMATERIAL_ID", pdNews.getString("INTERNETMATERIAL_ID"));
            pdRecordNews.put("PICTURE_ID", pdImg.getString("PICTURE_ID"));//图片id
            recordMaterialService.save(pdRecordNews);
        }

    }



    /****************************************图文消息--分割线****************************************/



    @Override
    public JSONObject loadMessageIndex(PageData pd, Page page) throws Exception{
        JSONObject result = initJSON();

        User user = Jurisdiction.getSessionUser().getUser();//得到用户
        pd.put("internet_id", user.getINTENET_ID());

        page.setPd(pd);
        List<PageData> varList = (List<PageData>) dao.findForList("InternetTemplateMessageMapper.listIndex", page);

        // 模板消息数据
        for (int i = 0; i < varList.size(); i++) {

        }

        result.put("varList", varList);
        return result;
    }


    @Override
    public JSONObject findByMessageId(PageData pd) throws Exception{
        JSONObject result = initJSON();

        pd = (PageData) dao.findForObject("InternetTemplateMessageMapper.findById", pd);

        result.put("pd", pd);
        return result;
    }


    @Override
    public JSONObject saveMessage(PageData pd) throws Exception{
        JSONObject result = initJSON();

        User user = Jurisdiction.getSessionUser().getUser();//得到用户

        PageData pdMessage = pd;
        pd.put("internet_id", user.getINTENET_ID());
        pd.put("update_time", Tools.date2Str(new Date()));

        JSONObject keyword = new JSONObject();
        keyword.put("keyword1", pd.getString("keyword1"));
        keyword.put("keyword2", pd.getString("keyword2"));
        keyword.put("keyword3", pd.getString("keyword3"));
        pd.put("keyword_data", keyword.toString());

        if(StringUtil.isEmpty(pd.getString("id"))){
            pdMessage.put("id", StringUtil.get32UUID());
            pd.put("create_time", Tools.date2Str(new Date()));

            dao.save("InternetTemplateMessageMapper.save", pdMessage);

        }else{

            dao.update("InternetTemplateMessageMapper.edit", pdMessage);
        }
        result.put("id", pdMessage.getString("id"));

        return result;
    }


    @Override
    public JSONObject delMessage(PageData pd) throws Exception{
        JSONObject result = initJSON();


        dao.delete("InternetTemplateMessageMapper.delete", pd);

        return result;
    }


    @Override
    public JSONObject sendMessage(PageData pd) throws Exception{
        JSONObject result = initJSON();

        if(StringUtil.isEmpty(pd.get("id")) || StringUtil.isEmpty(pd.get("internetList"))){
            result.put("errcode", 1);
            result.put("errmsg", "非法参数，请求参数为空");
            return result;
        }

        PageData pdMessage = (PageData) dao.findForObject("InternetTemplateMessageMapper.findById", pd);
        if(StringUtil.isEmpty(pdMessage)){
            result.put("errcode", 3);
            result.put("errmsg", "非法参数，参数错误");
            return result;
        }

        String[] internetIdList = (String[]) pd.get("internetList");
        SendMessageThread thread = new SendMessageThread(pdMessage, internetIdList);
        new Thread(thread).start();

        //删除本模板消息
        dao.delete("InternetTemplateMessageMapper.delete", pdMessage);

        return result;
    }


    /**
     * 线程--群发模板消息
     */
    class SendMessageThread implements Runnable {
        private PageData pd;
        private String[] internetIdList;
        public SendMessageThread(PageData pd, String[] internetIdList) {
            this.pd = pd;
            this.internetIdList = internetIdList;
        }
        public void run() {
            try {
                for (int i = 0; i < internetIdList.length; i++) {
                    String internet_id = internetIdList[i];
                    String token = autoReplyService.getAuthToken(internet_id);


                    //发送模板消息前，处理模板设置(服务器维护通知)
                    JSONObject tempJson = weixinService.preHandleTemplate(internet_id, "server_guard", TemplateMsgUtil.server_guard_short);

                    //修改模板id
                    pd.put("template_id", TemplateMsgUtil.server_guard_short);
                    dao.update("InternetTemplateMessageMapper.edit", pd);

                    //模板推送（点击跳转到微信的上网记录中）
                    String url = pd.getString("url");

                    pd.put("keyword1", JSONObject.fromObject(pd.getString("keyword_data")).getString("keyword1"));
                    pd.put("keyword2", JSONObject.fromObject(pd.getString("keyword_data")).getString("keyword2"));
                    pd.put("keyword3", JSONObject.fromObject(pd.getString("keyword_data")).getString("keyword3"));
                    JSONObject jsonData = TemplateMsgUtil.serverGuard(pd);

                    //获取用户列表
                    PageData pdd = new PageData();
                    pdd.put("internet_id", internet_id);
                    pdd.put("state", "1");//已关注的人
                    List<PageData> personList = wechatuserService.findByCondition(pdd);
                    for (int j = 0; j < personList.size(); j++) {
                        TemplateMsgUtil.sendTemplate(token, personList.get(j).getString("OPEN_ID"), tempJson.getString("template_id"), url, null, jsonData);
                    }

                    PageData pdMessage = pd;
                    pdMessage.put("id", StringUtil.get32UUID());
                    pdMessage.put("internet_id", internet_id);
                    pdMessage.put("update_time", Tools.date2Str(new Date()));
                    pdMessage.put("create_time", Tools.date2Str(new Date()));

                    dao.save("InternetTemplateMessageMapper.save", pdMessage);


                    //保存公众号群发记录表internet_group_send
                    PageData pdSend = new PageData();
                    pdSend.put("id", StringUtil.get32UUID());
                    pdSend.put("send_type", 2);//模板信息
                    pdSend.put("foreign_id", pdMessage.getString("id"));//模板消息id
                    pdSend.put("create_time", Tools.date2Str(new Date()));
                    pdSend.put("update_time", Tools.date2Str(new Date()));
                    pdSend.put("status", 1);//发送成功
                    pdSend.put("internet_id", internet_id);
                    dao.save("InternetGroupSendMapper.save", pdSend);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    /****************************************群发消息--分割线****************************************/


    @Override
    public JSONObject loadRecordIndex(PageData pd, Page page) throws Exception{
        JSONObject result = initJSON();

        page.setPd(pd);
        List<PageData> varList = (List<PageData>) dao.findForList("InternetGroupSendMapper.datalistPage", page);

        // 模板消息数据
        for (int i = 0; i < varList.size(); i++) {
            if(StringUtil.isNotEmpty(varList.get(i).get("create_time"))){
                varList.get(i).put("create_time", varList.get(i).get("create_time").toString().substring(0,19));
            }
            if(varList.get(i).get("send_type").toString().equals("1")){
                varList.get(i).put("type_name", "图文");
            }else if(varList.get(i).get("send_type").toString().equals("2")){
                varList.get(i).put("type_name", "模板消息");
            }
        }

        result.put("data", varList);
        result.put("count", page.getTotalResult());
        result.put("msg", "查询成功");
        result.put("showCount", page.getShowCount());
        result.put("code", "0");
        return result;
    }





    private JSONObject initJSON(){
        JSONObject result = new JSONObject();
        result.put("errcode", 0);
        result.put("errmsg", "success");
        return result;
    }

}
