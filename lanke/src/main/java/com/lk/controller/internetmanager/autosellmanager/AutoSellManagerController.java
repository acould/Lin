package com.lk.controller.internetmanager.autosellmanager;

import com.google.common.base.Joiner;
import com.lk.controller.base.BaseController;
import com.lk.entity.LayMessage;
import com.lk.entity.Message2;
import com.lk.entity.Page;
import com.lk.entity.billecenter.SWConsume;
import com.lk.entity.billecenter.SWRecharge;
import com.lk.entity.system.User;
import com.lk.service.billiCenter.consume.ConsumeService;
import com.lk.service.billiCenter.recharge.RechargeService;
import com.lk.service.billiCenter.userInfo.UserInfoService;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.intuser.internetMember.InternetMemberManager;
import com.lk.service.lankeManager.autosellmanager.AutoSellService;
import com.lk.service.system.intenet.IntenetManager;
import com.lk.service.system.myop.MyopManager;
import com.lk.service.system.sysuser.SysUserManager;
import com.lk.service.system.user.UserManager;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.util.*;
import com.lk.wechat.util.TemplateMsgUtil;
import com.lk.wechat.util.WeChatOpenUtil;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping(value = "autosellmanager")
@Controller
public class AutoSellManagerController extends BaseController {

    private  static Logger logger = LoggerFactory.getLogger(AutoSellManagerController.class);
    @Resource
    AutoSellService autoSellService;

    @Resource(name = "internetMemberService")
    private InternetMemberManager internetMemberService;

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private ConsumeService consumeService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AutoReplyService autoReplyService;

    @Resource(name = "intenetService")
    private IntenetManager intenetService;
    @Resource(name = "userService")
    private UserManager userService;
    @Resource(name = "myopService")
    private MyopManager myopService;
    @Resource(name = "terraceService")
    private TerraceManager terraceService;
    @Resource(name = "sysuserService")
    private SysUserManager sysuserService;

    /**
     * 跳转页面
     * @return
     */
    @RequestMapping(value = "list.do")
    public ModelAndView goList(){
        ModelAndView mv = this.getModelAndView();
        mv.addObject("pd", this.getPageData());
        mv.setViewName("internetmanager/autosellmanager/autosellmanager");
        return mv;
    }

    @RequestMapping(value = "listselllist.do")
    public ModelAndView listselllist(){
        ModelAndView mv = this.getModelAndView();
        mv.addObject("pd", this.getPageData());
        mv.setViewName("internetmanager/autosellmanager/autosellinternet");
        return mv;
    }


    /**
     * 展示列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public LayMessage getList() throws Exception {
        Page page = this.getPage();
        PageData pd = this.getPageData();
        LayMessage lms =  autoSellService.list(pd, page);
        return lms;
    }


    @RequestMapping(value = "/getInternetsellList")
    @ResponseBody
    public LayMessage getinternetsellList() throws Exception {
        Page page = this.getPage();
        PageData pd = this.getPageData();
        User user = Jurisdiction.getSessionUser().getUser();
        List<String> stores = user.getStore_ids();
        if(StringUtil.isNotEmpty(stores) && stores.size() > 0){
            pd.put("store_ids", Joiner.on("','").join(user.getStore_ids()));
        }else {
            String internet_id = user.getINTENET_ID();
            pd.put("internet_id",internet_id);
        }
        LayMessage lms =  autoSellService.listInternetsell(pd, page);
        //门店下的角色
        return lms;
    }

    /**
     * 编辑
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goEdit.do")
    public ModelAndView goEdit() throws Exception{
        ModelAndView mv = this.getModelAndView();
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        mv.addObject("pd", this.getPageData());
        mv.setViewName("internetmanager/autosellmanager/autosellmanager_edit");
        List list = autoSellService.getNoByStore_id(pd);
        mv.addObject("data",list);
        mv.addObject("store_id",pd.getString("store_id"));
        return mv;
    }

    /**
     * 参数，store_id 门店id,自助机编号sm_no，删除
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete")
    public JSONObject delete() throws Exception{
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        JSONObject json2 = new JSONObject();
        try{
            autoSellService.delete(pd);
        }catch (Exception e){
            json2.put("code","1");
            json2.put("msg","删除失败");
            return json;
        }
        json2.put("code","0");
        json2.put("msg","删除成功");
        json.put("data",json2);
        return json;
    }

    /**
     * 保存
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/save")
    public JSONObject save() throws Exception{
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        String store_id = pd.getString("store_id");
        List<Map<String,Object>>  list = new ArrayList<>();
        autoSellService.deleteAll(pd);
        String sm_no = pd.getString("sm_no");
        String[] strs = sm_no.split(",");
        JSONObject json2 = new JSONObject();
        for(int i=0;i<strs.length;i++){
            String SM_ID =  StringUtil.get32UUID();
            Map map = new HashMap();
            String sm_mo = strs[i];
            PageData pd2 = autoSellService.check(sm_no);
            if(pd2!=null){
                json2.put("code","3");
                json2.put("msg","有售货机编号已存在");
                json.put("data",json2);
                return json;
            }
            map.put("store_id",store_id);
            map.put("sm_mo",sm_mo);
            map.put("sm_id",SM_ID);
            list.add(map);
        }
        try{
            autoSellService.save(list);
        }catch (Exception e){
            json2.put("code","1");
            json2.put("msg","保存失败");
            return json;
        }
        json2.put("code","0");
        json2.put("msg","保存成功");
        json.put("data",json2);
        return json;
    }


    /**
     * 下订单，返回售货机支付地址
     * @param body
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goPay", method= RequestMethod.POST)
    @ResponseBody
    public JSONObject goDetailShare(@RequestBody String body) throws Exception{
        JSONObject orders = JSONObject.fromObject(body);
        JSONObject json = new JSONObject();
        ModelAndView mv = this.getModelAndView();
        String vm_code = orders.getString("vm_code");//售货机编号
        String out_order_no = orders.getString("out_order_no");//订单号
        String total_fee = orders.getString("total_fee");//订单金额
        Double fee = Double.parseDouble(total_fee)/100.0;
        String detail = orders.getString("detail");//商品名称
        String notify_url = orders.getString("notify_url");//回调地址
        String attach = orders.getString("attach")==null?"":orders.getString("attach");
        String result_url = orders.getString("result_url");
        List<PageData> list = autoSellService.findOrder(out_order_no);//查询订单号是否存在
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//下单时间
        Date d = new Date();
        String date =sdf.format(d);
        Integer uuidHashCode = UUID.randomUUID().toString().hashCode();
        if (uuidHashCode < 0) {
            uuidHashCode = uuidHashCode * (-1);
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("vm_code",vm_code);
        map.put("out_order_no",out_order_no);
        map.put("price",fee);
        map.put("detail",detail);
        map.put("notify_url",notify_url);
        map.put("attach",attach);
        String id = StringUtil.get32UUID();
        map.put("id",id);
        map.put("date",date);
        map.put("result_url",result_url);
        Map map2 = autoSellService.getAppid(map);
        String appid = (String) map2.get("appid");
        String store_id = (String) map2.get("store_id");
        map.put("appid",appid);
        map.put("store_id",store_id);
        map.put("product_count","1");
        map.put("order_state","1");
        //支付链接地址
        String url ="";
        if(list.size()>0){
            String order_state =list.get(0).getString("order_state");
            String lanke_order_no = list.get(0).getString("lanke_order_no");
            id = list.get(0).getString("order_id");
            url = PublicManagerUtil.URL2+"autosellmanager/sureorder.do?"+"appid="+appid+"&id="+id;
            if("1".equals(order_state)){//未支付，可以继续支付
                json.put("result_code","success");
                json.put("result_msg","未完成订单继续下订单");
                json.put("lanke_order_no",lanke_order_no);
                json.put("pay_url",url);
                json.put("pay_state","1");//未支付订单继续下单
            }else{
                json.put("result_code","fail");
                json.put("result_msg","重复订单");
                json.put("lanke_order_no","");
                json.put("pay_url","");
                json.put("pay_state","2");//重复订单
            }
        }else{
            url = PublicManagerUtil.URL2+"autosellmanager/sureorder.do?"+"appid="+appid+"&id="+id;
            String lanke_order_no ="lk-"+date.replaceAll("-", "").substring(0,8)+uuidHashCode;//揽客订单号
            map.put("lanke_order_no",lanke_order_no);
            autoSellService.addOrder(map);//新增一条订单
            json.put("result_code","success");
            json.put("result_msg","准备下单");
            json.put("lanke_order_no",lanke_order_no);
            json.put("pay_url",url);
            json.put("pay_state","0");//新增订单下单
        }
        logger.info("======url======="+url);
        return json;
    }


    /**
     *统一下单
     * vm_code 机器编号,out_order_no苍龙订单号,
     * total_fee 订单金额  notify_url 回调地址
     * detail 商品名称
     * @return
     * @throws Exception
     */
    @RequestMapping("/sureorder")
    public ModelAndView sureOrder() throws Exception{
        PageData pd = this.getPageData();
        String appid = pd.getString("appid");
        String id = pd.getString("id");
        ModelAndView mv = this.getModelAndView();
        List<PageData>   listOrders = autoSellService.getOrderById(id);
        String result_url = listOrders.get(0).getString("result_url");
        Map map = new HashMap();
        map.put("result_url",result_url);
        String b =appid.toString();
        logger.info("appid========="+appid);
        if("null".equals(b)){
            mv.setViewName("internetmanager/autosellmanager/noattention");
            mv.addObject("data",map);
            return mv;
        }
        String share_url = "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=" + appid  +
                "&redirect_uri="+PublicManagerUtil.URL1+"autosellmanager/goPayDetail.do?id=" + id +
                "&response_type=code&scope=snsapi_userinfo" +
                "&state=0" +
                "&component_appid=" + PublicManagerUtil.APPID +
                "#wechat_redirect";
        return new ModelAndView(share_url);
    }

    /**
     * 跳转到确认支付页面
     * @return
     */
    @RequestMapping(value ="/goPayDetail")
    public ModelAndView order() throws Exception{
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        String code = pd.getString("code");
        String id = pd.getString("id");
        List<PageData>   listOrders = autoSellService.getOrderById(id);
        String lanke_order_no = listOrders.get(0).getString("lanke_order_no");
        String detail = listOrders.get(0).getString("detail");
        String price = listOrders.get(0).getString("price");
        String result_url = listOrders.get(0).getString("result_url");
        Map map = new HashMap();
        map.put("code",code);
        map.put("id",id);
        map.put("lanke_order_no",lanke_order_no);
        map.put("detail",detail);
        map.put("price",price);
        map.put("order_state","1");
        map.put("result_url",result_url);
        JSONObject json = new JSONObject();
        json = json.fromObject(map);
        mv.addObject("orders",json);
        mv.setViewName("internetmanager/autosellmanager/sureorder");
        return mv;
    }

    /**
     * 点击确认按钮，得到相应的结果
     * @return
     * @throws Exception
     */
    @RequestMapping(value ="/getPayResult")
    @ResponseBody
    public JSONObject getPayResult() throws Exception {
        logger.info("===============进入支付================");
        ModelAndView mv = this.getModelAndView();
        JSONObject json2 = new JSONObject();
        PageData pdUser = this.getPageData();
        String id = pdUser.getString("id");
        Map map6 = new HashMap();
        map6.put("pay_state","");
        map6.put("result_msg","");
        map6.put("pay_fail_desc","");
        map6.put("order_state","3");
        autoSellService.addState(map6);//添加状态
        List<PageData>   listOrders = autoSellService.getOrderById(id);
        logger.debug("================="+id);
        String sm_no =  listOrders.get(0).getString("sm_no");
        String order_no = listOrders.get(0).getString("lanke_order_no");
        String price2 = listOrders.get(0).getString("price");
        String store_id = listOrders.get(0).getString("store_id");
        String detail = listOrders.get(0).getString("detail");
        Map map3 = new HashMap();
        Map<String,Object> map2 = new HashMap<String,Object>();
        JSONObject json3 = new JSONObject();
        PageData pd2 = autoSellService.checkOrder(order_no);//揽客订单,状态为3
        String order_state = pd2.getString("order_state");
        if("3".equals(order_state)){
            map2.put("result_msg","该订单已支付过");
            json3 = json3.fromObject(map2);
            return json3;
        }else if("4".equals(order_state)){
            map2.put("result_msg","该订单支付失败");
            json3 = json3.fromObject(map2);
            return json3;
        }
        map3.put("vm_code",sm_no);
        Map map5 = autoSellService.getAppid(map3);
        String appid = (String) map5.get("appid");
        Double price =Double.parseDouble(price2);
        PageData pd = new PageData();
        pd.put("appid",appid);
        String code = (String) pdUser.get("code");
        pd.put("code",code);
        logger.info("code========="+code);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//支付时间
        String date =sdf.format(d);
        User user = getUser(pd);//通过微信code得到user
        String open_id = null;
        if(user==null){
            logger.info("不是会员");
            map2.put("pay_state","2");
            map2.put("result_msg","不是会员");
            map2.put("pay_fail_desc","不是会员");
            map2.put("order_state","4");
            json3 = json3.fromObject(map2);
            autoSellService.addState(map2);//添加状态
        }else{
            open_id = user.getOPEN_ID();
            List<PageData> list = autoSellService.getMsgByopen_id(open_id);
            map2.put("id",id);
            if (list.size() != 0) {
                String card_id = list.get(0).getString("CARDID");//卡号
                String  username = list.get(0).getString("NAME");//姓名
                String  user_id = list.get(0).getString("USER_ID");//用户id
                String  carded = list.get(0).getString("CARDED");//身份证
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("card_id",card_id);
                map.put("username",username);
                map.put("user_id",user_id);
                map.put("carded",carded);
                map.put("openid",open_id);
                map.put("date",date);
                map.put("id",id);
                autoSellService.addUserMsg(map);//添加订单信息
                Message2 m2 = userInfoService.getSWUser(store_id, card_id);//调用顺网个人账户信息
                JSONObject json = new JSONObject();
                json = JSONObject.fromObject(m2).getJSONObject("data").getJSONObject("SWUser");
                logger.debug("调用顺网个人信息："+json.toString());
                Double overage = (Double) json.get("overage");//卡余额
                Double reward = (Double) json.get("reward");//奖励
                Double principal = overage - reward;//本金
                SWConsume swConsume = new SWConsume();
                if (price <= overage) {//卡余额足够
                    if (price <= principal) {//本金足够，直接用本金支付
                        swConsume.setReward(0.0);
                        Swconsume(store_id, card_id, price,order_no,open_id,detail,username,date,swConsume);
                        map.put("principal",principal);
                        map.put("award_price","0");
                        map.put("pay_type","1");
                        autoSellService.addUserMsg2(map);
                    } else {//本金不足够，用本金和奖励组合支付
                        reward = price - principal;//奖励支付金额
                        swConsume.setReward(reward);//设置奖励金额
                        Swconsume(store_id, card_id, principal,order_no,open_id,detail,username,date,swConsume);
                        if(principal==0.0){
                            map.put("principal","0");
                            map.put("award_price",reward);
                            map.put("pay_type","2");
                        }else{
                            map.put("principal",principal);
                            map.put("award_price",reward);
                            map.put("pay_type","3");
                        }
                        autoSellService.addUserMsg2(map);
                    }
                    Date d2 = new Date();
                    String date2 =sdf.format(d);
                    logger.debug("揽客-苍龙售货机支付信息:"+swConsume.toString());
                    logger.info("支付成功");
                    map2.put("result_msg","支付成功");
                    map2.put("pay_state","1");
                    map2.put("time_end",date2);
                    map2.put("pay_fail_desc","");
                    map2.put("order_state","3");
                    json3 = json3.fromObject(map2);
                    autoSellService.addState(map2);//添加状态
                    /*ExecutorDemo  executorDemo = new ExecutorDemo(id);
                    Thread thread = new Thread(executorDemo);
                    thread.start();*/
                    new Thread("支付成功线程") {
                        public void run() {
                            try{
                                String result_code =  sendHttpPost(id);
                                if("1".equals(result_code)){
                                    sendHttpPost(id);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    //vm_code售货机编号，out_order_no苍龙订单号，openid，order_no揽客订单号，total_fee金额，detail商品名，attach
                } else {//卡余额不足
                    logger.info("卡余额不足");
                    map2.put("result_msg","卡余额不足");
                    map2.put("pay_state","2");
                    map2.put("pay_fail_desc","卡余额不足");
                    map2.put("order_state","4");
                    map2.put("time_end","");
                    json3 = json3.fromObject(map2);
                    autoSellService.addState(map2);//添加状态
                }
            }else{
                logger.info("不是会员");
                map2.put("pay_state","2");
                map2.put("result_msg","不是会员");
                map2.put("pay_fail_desc","不是会员");
                map2.put("order_state","4");
                map2.put("time_end","");
                json3 = json3.fromObject(map2);
                autoSellService.addState(map2);//添加状态
            }
            //回调售货机出货
        }
//        String open_id = "oHGv95xD9NAMxxbwiJkxgtoVPtR8";
        //查询个人信息

        return json3;
    }

    /**
     * 通知售货机出货
     * @param
     * @throws Exception
     */
    public  String  sendHttpPost(String id) throws Exception{
//        PageData pd = this.getPageData();
//        String id = pd.getString("id");
        HttpClient httpClient = new HttpClient();
        List<PageData> list = autoSellService.getOrderById(id);
        String result_code = "";
        if(list.size()>0) {
            String notify_url = list.get(0).getString("notify_url");
            String vm_code = list.get(0).getString("sm_no");
            String out_order_no = list.get(0).getString("out_order_no");
            String lanke_order_no = list.get(0).getString("lanke_order_no");
            String buyer = list.get(0).getString("username");
            String price = list.get(0).getString("price");
            Double total_fee = Double.parseDouble(price)*100;
            String attach = list.get(0).getString("attach");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("vm_code", vm_code);
            jsonObject.put("out_order_no", out_order_no);
            jsonObject.put("lanke_order_no", lanke_order_no);
            jsonObject.put("buyer", buyer);
            jsonObject.put("total_fee", total_fee);
            jsonObject.put("attach", attach);
            String transJson = jsonObject.toString();
            logger.info("======================支付成功通知================="+transJson.toString());
            String respHtml = "";
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(notify_url);
            logger.info("======notify_url============"+notify_url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000)
                    .setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000)
                    .build();
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");//表示客户端发送给服务器端的数据格式
            //httpPost.setHeader("Accept", "*/*");这样也ok,只不过服务端返回的数据不一定为json
            httpPost.setHeader("Accept", "application/json");                    //表示服务端接口要返回给客户端的数据格式，
            StringEntity entity = new StringEntity(transJson, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            //System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
            System.out.println(statusCode);//200
            HttpEntity respEnt = response.getEntity();
            respHtml = EntityUtils.toString(respEnt, "UTF-8");
            logger.info(respHtml);
            JSONObject jsonObject2 = JSONObject.fromObject(respHtml);
            result_code = jsonObject2.getString("result_code");
        }
        return result_code;
    }

    public User getUser(PageData pd) throws Exception{
        String code = pd.getString("code");
        String openid = pd.getString("openid");
        String appid = pd.getString("appid");

        Session session = Jurisdiction.getSession();
        User user = new User();
        if (StringUtil.isNotEmpty(code)) {
            logger.info("code不为空===================");
            pd.put("APP_ID", appid);
            PageData pdInternet = intenetService.findByappid(pd).get(0);
            PageData pdTerrace = new PageData();
            pdTerrace.put("INTENET_ID", pdInternet.getString("INTENET_ID"));
            pdTerrace = terraceService.findByInternetId(pdTerrace);

            PageData pdMyop = new PageData();
            pdMyop.put("APPID", PublicManagerUtil.APPID);
            pdMyop.put("TOKEN_TIME", Tools.sAddHours(new Date(), -1));
            pdMyop = myopService.findByAppId(pdMyop);

            JSONObject json = WeChatOpenUtil.getOpenAccessToken(pdTerrace.getString("APP_ID"), code,
                    pdMyop.getString("APPID"), pdMyop.getString("COMPONENT_ACCESS_TOKEN"));
            String openId = "";
            if ((json.containsKey("errcode") && json.getString("errcode").equals(PublicManagerUtil.ERRCODE1))||json==null) {
                return null;
            }else{
                openId = json.getString("openid");
                user = sysuserService.getUserByOpenId(openId);
                sysuserService.getByOpenId(openId);//微信用户信息
                sysuserService.getBundUserByOpenId(openId);//绑定的用户信息
            }
        }else{
            return null;
        }
        return user;
    }

    /**
     * 设置支付信息,支付成功，发送模板消息
     * @param store_id
     * @param card_id
     * @param principal
     * @throws Exception
     */
    private void Swconsume(String store_id,String card_id,Double principal,String order_no,String open_id,
                           String detail,String username,String order_time,SWConsume swConsume) throws Exception {
        JSONObject param = new JSONObject();
        swConsume.setCard_id(card_id);//卡号
        swConsume.setOrder_id(order_no);//订单号
        swConsume.setPay_from("lanker");//支付来源
        swConsume.setMemo("揽客-苍龙售货机支付");//备注
        swConsume.setPrincipal(principal);//支付本金
        Message2 result = consumeService.sendConsume(store_id, swConsume);//发送支付
        JSONObject json =JSONObject.fromObject(result);
        String errcode = json.getString("errcode");
        logger.info("支付完成=====================");
        if("0".equals(errcode)){//推送模板消息
            logger.info("==============发送模板");
            SendTemple(store_id,open_id,detail,principal,username,order_time,order_no);
        }
    }

    /**
     * 发送支付成功模板
     * @param store_id
     */
    public void SendTemple(String store_id,String openId,String detail,
                           Double principal,String username,String order_time,String order_no) throws  Exception{

        String internet_id = autoSellService.getInternet(store_id);
        //发送模板
        JSONObject content = new JSONObject();

        PageData pd = autoSellService.getStoreName(store_id);

        String store_name = pd.getString("STORE_NAME");

        logger.info("store_name==========="+store_name);

        String token = autoReplyService.getAuthToken(internet_id);
        String temple_name = "新订单通知";
//        42d593519e6349a48825af3d89272898
//        String template_id = autoSellService.template_Id(internet_id,temple_name);
        String template_id = "oDMsySe9VQmH7LC0jo553m-SIvtdpLbIHT0qu_DdtH0";
        System.out.println(template_id);
        JSONObject jsonFirst = new JSONObject();
        jsonFirst.put("value", "您有一笔新订单，请及时处理");
        // 商品名
        JSONObject jsonKeyword1 = new JSONObject();
        jsonKeyword1.put("value", detail);
        //金额
        JSONObject jsonKeyword2 = new JSONObject();
        jsonKeyword2.put("value", principal+"积分");
        //姓名
        JSONObject jsonKeyword3 = new JSONObject();
        jsonKeyword3.put("value", username);
        //交易时间
        JSONObject jsonKeyword4 = new JSONObject();
        jsonKeyword4.put("value", order_time);
        //流水号
        JSONObject jsonKeyword5 = new JSONObject();
        jsonKeyword5.put("value", order_no);
        JSONObject jsonRemark = new JSONObject();
        jsonRemark.put("value", "感谢你的使用");
        content.put("first",jsonFirst);
        content.put("keyword1",jsonKeyword1);
        content.put("keyword2",jsonKeyword2);
        content.put("keyword3",jsonKeyword3);
        content.put("keyword4",jsonKeyword4);
        content.put("keyword5",jsonKeyword5);
        content.put("remark", jsonRemark);
        String url = PublicManagerUtil.URL2 + "autosellmanager/toDetails.do?detail=" + detail + "&principal="+principal
                + "&order_time="+order_time+ "&lanke_order_no="+order_no+ "&store_name="+store_name;
        logger.info("==============url==========跳转详情页"+url);
        TemplateMsgUtil.sendTemplate(token,openId, template_id,url,null, content);
    }

    @RequestMapping(value = "/toDetails")
    public ModelAndView toDetails(){
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        Map map = new HashMap();
        String detail = pd.getString("detail");
        String principal = pd.getString("principal");
        String order_time = pd.getString("order_time");
        String lanke_order_no = pd.getString("lanke_order_no");
        String store_name = pd.getString("store_name");
        map.put("detail",detail);//商品名
        map.put("principal",principal);//积分数
        map.put("order_time",order_time);//下单时间
        map.put("lanke_order_no",lanke_order_no);//订单号
        map.put("store_name",store_name);//门店
        mv.addObject("data",map);
        mv.setViewName("internetmanager/autosellmanager/todetails");
        return mv;
    }


    /**
     * 查询订单接口
     * @param body
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOrderResult", method= RequestMethod.POST)
    @ResponseBody
    public JSONObject getOrderResult (@RequestBody String body) throws Exception{
         JSONObject json = new JSONObject();
         JSONObject json2 = JSONObject.fromObject(body);
         String out_order_no = json2.getString("out_order_no");
        PageData pd = null;
         try{
              pd= autoSellService.getOrderResult(out_order_no);
         }catch(Exception e){
             json.put("result_code ","fail");
             json.put("result_msg","系统异常");
             return json;
         }

         if(pd==null){
             json.put("result_code ","success");
             json.put("result_msg","没有这条订单");
         }else{
             String pay_state = pd.getString("pay_state");//1.支付成功，2.支付失败
             String openid = pd.getString("openid");
             String detail = pd.getString("detail");//商品名
             String total_fee = pd.getString("price");
             Double price  = (Double.parseDouble(total_fee))*100.0;
             String time_end = pd.getString("time_end");
             String attach = pd.getString("attach");
             String trade_status = pd.getString("order_state");
             if("1".equals(pay_state)){
                 json.put("result_code ","success");
                 json.put("result_msg","支付成功");
             }else if("2".equals(pay_state)){
                 json.put("result_code","success");
                 json.put("result_msg","支付失败");
             }else{
                 json.put("result_code","success");
                 json.put("result_msg","未支付");
             }
             json.put("openid",openid);
             json.put("detail",detail);
             json.put("total_fee",price);
             json.put("time_end",time_end);
             json.put("attach",attach);
             json.put("trade_status",trade_status);
         }
        return json;
    }

    /**
     * 揽客-苍龙售货机退款接口
     * 参数：out_order_no苍龙订单号，total_fee订单金额(分)，refund_fee退款金额，refund_desc退款原因
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/reCharge", method= RequestMethod.POST)
    @ResponseBody
    public JSONObject reCharge(@RequestBody String body) throws Exception{

        JSONObject obj = JSONObject.fromObject(body);
        String out_order_no = obj.get("out_order_no").toString();//订单号
        String total_fee = obj.get("total_fee").toString();//订单金额
        Double refund_fee = Double.parseDouble(obj.get("refund_fee").toString());//退款金额
        String refund_desc = obj.get("refund_desc").toString();//退款原因
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date =sdf.format(d);
        //根据订单号查询门店id,卡号id
        List<PageData>   list = autoSellService.getStoreAndId(out_order_no);

        JSONObject json  = new JSONObject();
        PageData pd = new PageData();
        pd.put("out_order_no",out_order_no);
        pd.put("refund_fee",refund_fee);
        pd.put("refund_desc",refund_desc);
        pd.put("date",date);
        if(list.size()>0){
            String store_id = list.get(0).getString("store_id");
            String card_id = list.get(0).getString("cardid");
            String pay_state = list.get(0).getString("pay_state");
            if(!"1".equals(pay_state)){
                json.put("result_code","fail");
                json.put("result_msg","未支付或支付失败");
                json.put("settlement_refund_fee","0");
                pd.put("refund_state","fail");
                autoSellService.insertRefundMsg(pd);//插入退款信息
                return json;
            }
            //退款
            JSONObject param = new JSONObject();
            SWRecharge swRecharge = new SWRecharge();
            swRecharge.setCard_id(card_id);//卡号
            swRecharge.setOrder_id(out_order_no);//订单号
            swRecharge.setPay_from("lanker");//订单来源
            swRecharge.setMemo("揽客-苍龙售货机退款");//备注
            swRecharge.setAmount(refund_fee);//退还本金金额
            swRecharge.setReward(0.0);//退还奖励金额
            swRecharge.setAllow_reward(0);
            Message2 result = rechargeService.newRecharge(store_id, swRecharge);//发送退款
            JSONObject json2 = JSONObject.fromObject(result);
            String errcode = json2.getString("errcode");
            if("1".equals(errcode)){//支付失败
                json.put("result_code","fail");
                json.put("result_msg","退款失败");
                json.put("settlement_refund_fee","0");
                pd.put("refund_state","fail");
            }else if("0".equals(errcode)){
                json.put("result_code","success");
                json.put("result_msg","退款成功");
                json.put("settlement_refund_fee",refund_fee);
                pd.put("refund_state","success");
                PageData refundMsg = autoSellService.selectRefundMsg(out_order_no);//退款人信息
                String  detail = refundMsg.getString("detail");
                String  price = refundMsg.getString("price");
                String  username = refundMsg.getString("username");
                String  lanke_order_no = refundMsg.getString("lanke_order_no");
                String  pay_time = refundMsg.getString("pay_time");
                String  openid = refundMsg.getString("openid");
                SendTemple2( detail,price,username,lanke_order_no, date,store_id,openid);
            }
        }else{
            json.put("result_code","fail");
            json.put("result_msg","没有这条订单");
            json.put("settlement_refund_fee","0");
            pd.put("refund_state","fail");
        }
        autoSellService.insertRefundMsg(pd);//插入退款信息
        return json;
    }

    /**
     * 商品，积分数，购买人名称，交易流水号，交易时间
     * @throws Exception
     */
    public void SendTemple2(String detail,String price,String username,String lanke_order_no,String pay_time,String store_id,String openid) throws  Exception{

        String internet_id = autoSellService.getInternet(store_id);
        //发送模板
        JSONObject content = new JSONObject();

        String token = autoReplyService.getAuthToken(internet_id);
        String temple_name = "新订单通知";
//        42d593519e6349a48825af3d89272898
//        String template_id = autoSellService.template_Id(internet_id,temple_name);
        String template_id = "061Yhqdy0FR3ve1uNzgy0nUedy0YhqdC";
        System.out.println(template_id);
        JSONObject jsonFirst = new JSONObject();
        jsonFirst.put("value", username+"购买商品，请及时处理");
        // 用户名
        JSONObject jsonKeyword1 = new JSONObject();
        jsonKeyword1.put("value", username);
        //积分数
        JSONObject jsonKeyword2 = new JSONObject();
        jsonKeyword2.put("value", "已经退还");
        //姓名
        JSONObject jsonKeyword3 = new JSONObject();
        jsonKeyword3.put("value", price);
        //流水号
        JSONObject jsonKeyword4 = new JSONObject();
        jsonKeyword4.put("value", pay_time);
        //交易时间
//        JSONObject jsonKeyword5 = new JSONObject();
//        jsonKeyword5.put("value", lanke_order_no);
        JSONObject jsonRemark = new JSONObject();
        jsonRemark.put("value", "感谢你的使用");
        content.put("first",jsonFirst);
        content.put("keyword1",jsonKeyword1);
        content.put("keyword2",jsonKeyword2);
        content.put("keyword3",jsonKeyword3);
        content.put("keyword4",jsonKeyword4);
//        content.put("keyword5",jsonKeyword5);
        content.put("remark", jsonRemark);
        TemplateMsgUtil.sendTemplate(token,openid, template_id,null,null, content);

    }

    /**
     * 获取登录url
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/LoginAutomaten")
    @ResponseBody
    public JSONObject LoginAutomaten() throws Exception{
        JSONObject json = new JSONObject();
        PageData pd = this.getPageData();
        String store_id = (String) pd.get("store_id");
        String loginName = autoSellService.getUsername(store_id);
        String url = "https://op02.aotaki.com/tokenLogin.html?loginName="+loginName+"&token="+getToken().getString("token");
        logger.info("登录售货机后台url:"+url);
        json.put("urll",url);
        return json;
    }

    /**
     * 获取token
     * @return
     */
    public JSONObject getToken(){
        String url = "https://op02.aotaki.com/api/getLoginToken";
        JSONObject json = new JSONObject();
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            HttpResponse res = httpclient.execute(post);
            if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                response = JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }


    @RequestMapping(value = "/test")
    public ModelAndView test(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("internetmanager/autosellmanager/test");
        return mv;
    }

    @RequestMapping(value = "/test2")
    public ModelAndView test2(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("internetmanager/autosellmanager/nobalance");
        return mv;
    }

    @RequestMapping(value = "/test3")
    public ModelAndView test3(){
        ModelAndView mv =this.getModelAndView();
        mv.setViewName("internetmanager/autosellmanager/nomember");
        return mv;
    }

    @RequestMapping(value = "/test4")
    public void test4() throws Exception{
        String open_id = "oHGv95xD9NAMxxbwiJkxgtoVPtR8";
        Map map2 = new HashMap();
        map2.put("id","b2b442166ede45838d11a6500e672b8b");
        map2.put("state","3");
        autoSellService.addState(map2);//添加状态
    }

    @RequestMapping(value = "/test5")
    public ModelAndView test5()throws Exception{
        ModelAndView mv =this.getModelAndView();
        /*String code ="071A92YP1zhUR217zJ0Q1JcdYP1A92YL";
        String id = "aaa8654fb74346888cc431bbbd7c7181";
        Map map3 = new HashMap();
        map3.put("vm_code","2");
        Map map2 = autoSellService.getAppid(map3);
        String appid = (String) map2.get("appid");
        List<PageData>   listOrders = autoSellService.getOrderById(id);
        String lanke_order_no = listOrders.get(0).getString("lanke_order_no");
        String detail = listOrders.get(0).getString("detail");
        String price = listOrders.get(0).getString("price");
        Map map = new HashMap();
        map.put("code",code);
        map.put("id",id);
        map.put("appid",appid);
        map.put("lanke_order_no",lanke_order_no);
        map.put("detail",detail);
        map.put("price",price);
        JSONObject json = new JSONObject();
        json = json.fromObject(map);
        logger.info("===================json===="+json.toString());*/
//        mv.addObject("orders",json);
        mv.setViewName("internetmanager/autosellmanager/sureorder");
        return mv;
    }


    @RequestMapping(value = "/test7")
    public ModelAndView test7(){
        ModelAndView mv = new ModelAndView();
//        PageData pd = this.getPageData();
        Map map = new HashMap();
//        String detail = pd.getString("detail");
//        String principal = pd.getString("principal");
//        String order_time = pd.getString("order_time");
//        String lanke_order_no = pd.getString("lanke_order_no");
//        String store_name = pd.getString("store_name");
        map.put("detail","可乐");
        map.put("principal","10");
        map.put("order_time","2019-08-05");
        map.put("lanke_order_no","lk-20190805");
        map.put("store_name","九堡店");
        mv.addObject("data",map);
        mv.setViewName("internetmanager/autosellmanager/todetails");
        return mv;
    }

    @RequestMapping(value = "/test9")
    public ModelAndView test9() throws Exception{
        ModelAndView mv = new ModelAndView();
        String id = "8b0ae3cc6f9e43138dd1fc3f32ede3aa";
        List<PageData> list = autoSellService.getOrderById(id);
        String result_code = "";
        if(list.size()>0) {
            String notify_url = list.get(0).getString("notify_url");
            String vm_code = list.get(0).getString("sm_no");
            String out_order_no = list.get(0).getString("out_order_no");
            String lanke_order_no = list.get(0).getString("lanke_order_no");
            String buyer = list.get(0).getString("username");
            String price = list.get(0).getString("price");
            Double total_fee = Double.parseDouble(price)*100;
            String attach = list.get(0).getString("attach");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("vm_code", vm_code);
            jsonObject.put("out_order_no", out_order_no);
            jsonObject.put("lanke_order_no", lanke_order_no);
            jsonObject.put("buyer", buyer);
            jsonObject.put("total_fee", total_fee);
            jsonObject.put("attach", attach);
            String transJson = jsonObject.toString();
            String respHtml = "";
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(notify_url);
            logger.info("======notify_url============"+notify_url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000)
                    .setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000)
                    .build();
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");//表示客户端发送给服务器端的数据格式
            //httpPost.setHeader("Accept", "*/*");这样也ok,只不过服务端返回的数据不一定为json
            httpPost.setHeader("Accept", "application/json");                    //表示服务端接口要返回给客户端的数据格式，
            StringEntity entity = new StringEntity(transJson, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            //System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
            System.out.println(statusCode);//200
            HttpEntity respEnt = response.getEntity();
            respHtml = EntityUtils.toString(respEnt, "UTF-8");
            logger.info(respHtml);
            JSONObject jsonObject2 = JSONObject.fromObject(respHtml);
            result_code = jsonObject2.getString("result_code");
        }
        /*ExecutorDemo  executorDemo = new ExecutorDemo(id);
        Thread thread = new Thread(executorDemo);
        thread.start();*/
        /*for(int i=0;i<20;i++){
            sendHttpPost(id);
        }*/
      /*  new Thread("线程一") {
            public void run() {
                for(int i=1;i<=20;i++) {
                    try{
                        sendHttpPost(id);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();*/
        sendHttpPost(id);
        mv.setViewName("internetmanager/autosellmanager/todetails");
        return mv;
    }


}

/*
class ExecutorDemo implements Runnable{
    private int i=0;
    private String id;

    public ExecutorDemo(String id) {
        super();
        this.id = id;
    }
    AutoSellManagerController httpClientUtil = new AutoSellManagerController();
    @Override
    public void run() {
        for(int i=0;i<20;i++) {
            try {
                httpClientUtil.sendHttpPost(id);
                Thread.sleep(200);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }
}
*/
