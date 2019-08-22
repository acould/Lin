package com.lk.service.system.payManager.impl;

import java.util.Date;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.system.User;
import com.lk.service.system.payManager.PayManager;
import com.lk.util.Const;
import com.lk.util.PageData;
import com.lk.util.PathUtil;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.util.Tools;

@Service("payService")
public class PayService implements PayManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 保存开通
	 * @param pd（保存数据）
	 * @return
	 * @throws Exception
	 */
	public JSONObject savePay(PageData pd) throws Exception{
		JSONObject result = new JSONObject();
		
		User user = (User) pd.get("user");
		if(StringUtil.isEmpty(user)){
			result.put("result", "false");
			result.put("message", "无效的用户");
			return result;
		}
		if(StringUtil.isEmpty(pd.getString("store_id"))){
			result.put("result", "false");
			result.put("message", "无效的门店");
			return result;
		}
		if(pd.getString("data_type").equals("0")){
			pd.put("state", "2");//2表示待审核
		}
		pd.put("internet_id", user.getINTENET_ID());
		
		if(StringUtil.isEmpty(pd.getString("id"))){
			//新增
			pd.put("id", StringUtil.get32UUID());
			
			if(StringUtil.isEmpty(pd.getString("phone"))){
				pd.put("phone", user.getPHONE());
			}
			pd.put("createtime", Tools.date2Str(new Date()));
			pd.put("updatetime", pd.get("createtime"));
			
			//新增资料图片
			pd.put("img_flag", "add");
			addImg(pd);
			
			//新增网吧资料
			dao.save("InternetDatumMapper.save", pd);
			
			
		}else{
			//修改
			if(StringUtil.isEmpty(pd.getString("phone"))){
				pd.put("phone", user.getPHONE());
			}
			pd.put("updatetime", Tools.date2Str(new Date()));
			
			
			//修改网吧资料图片
			pd.put("img_flag", "edit");
			addImg(pd);
			
			//修改网吧资料
			dao.update("InternetDatumMapper.edit", pd);
		}
		
		result.put("result", "true");
		if(pd.getString("data_type").equals("0")){
			result.put("message", "提交成功");
		}else if(pd.getString("data_type").equals("1")){
			result.put("message", "保存成功");
		}
		result.put("id", pd.getString("id"));
		
		return result;
	}
	
	//新增或修改5中类型图片
	public void addImg(PageData pd) throws Exception{
		PageData pdImg = new PageData();
		pdImg.put("intenetdatum_id", pd.getString("id"));
		
		String[] typeList = PublicManagerUtil.typeList;
		
		for(int i=0;i<typeList.length;i++){
			String type = typeList[i];
			if(StringUtil.isNotEmpty(pd.getString(type)) && pd.getString(type).startsWith("data:image")){
				pdImg.put("type", type);
				pdImg.put("flag", "add");
				
				if(pd.getString("img_flag").equals("add")){
					pdImg.put("sort", "1");
					pdImg.put("createtime", pd.get("updatetime"));
					pdImg = putImg(pd, pdImg);
					dao.save("DatumMapper.save", pdImg);
				}else{
					pdImg = (PageData) dao.findForObject("DatumMapper.findByInternetAdnType", pdImg);
					if(StringUtil.isEmpty(pdImg)){
						pdImg = new PageData();
						pdImg.put("intenetdatum_id", pd.getString("id"));
						pdImg.put("createtime", pd.get("updatetime"));
						pdImg.put("sort", "1");
						pdImg.put("type", type);
						pdImg.put("flag", "add");
						pdImg = putImg(pd, pdImg);
						dao.save("DatumMapper.save", pdImg);
					}else{
						pdImg.put("flag", "edit");
						pdImg = putImg(pd, pdImg);
						dao.update("DatumMapper.editByInternetAdnType", pdImg);
					}
				}
				
			}
		}
	}
	
	public PageData putImg(PageData pd, PageData pdImg) throws Exception{
		if(pdImg.getString("flag").equals("add")){
			pdImg.put("id", StringUtil.get32UUID());
		}
		
		//上传到本地
		String file = pd.getString(pdImg.getString("type"));
		String suffixName = "";
		if(file.contains(PublicManagerUtil.BASE64_JPEG)){
			suffixName = PublicManagerUtil.SUFFIXNAME_JPG;
		}else if(file.contains(PublicManagerUtil.BASE64_PNG)){
			suffixName = PublicManagerUtil.SUFFIXNAME_PNG;
		}else{
			suffixName = PublicManagerUtil.SUFFIXNAME_JPG;
		}
		
		String fileName = new Date().getTime() + suffixName;
		String path = pd.getString("internet_id") + "/" + pd.getString("store_id") + "/" + fileName ;
		String filePath = PathUtil.getClasspath() + Const.FILEPATHIMG + path;
		Tools.pic(file,filePath);
		pdImg.put("url", path);
		
		return pdImg;
	}
	
	
	
	/**
	 * 根据主键id查找
	 * @param pd（）
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("InternetDatumMapper.findById", pd);
	}
	
}
