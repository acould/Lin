package com.lk.controller.base;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.lk.entity.Message;
import com.lk.entity.jiaLian.ShopInfo;
import com.lk.entity.system.User;
import com.lk.util.*;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lk.entity.Page;


public class BaseController {
	@InitBinder
	
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd "), true));
	}

	protected org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);


	private static final long serialVersionUID = 6357869213649815390L;
	
	/** new PageData对象
	 * @return
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	public PageData getPageData2(){
		return new PageData(this.getRequest(),null);
	}
	
	/**得到ModelAndView
	 * @return
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**得到request对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/**得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		return UuidUtil.get32UUID();
	}
	
	/**得到分页列表的信息
	 * @return
	 */
	public Page getPage(){
		return new Page();
	}
	
	public static void logBefore(org.slf4j.Logger logger, String interfaceName){
		logger.info("");
		logger.info(PublicManagerUtil.START);
		logger.info(interfaceName);
	}
	
	public static void logAfter(org.slf4j.Logger logger){
		logger.info(PublicManagerUtil.END);
		logger.info("");
	}

	public String getPre(){
		return this.getRequest().getScheme() + "://" + this.getRequest().getServerName() + ":" + this.getRequest().getServerPort() +
				this.getRequest().getContextPath()+ "/";
	}

	public String getUrl(){
        String url = this.getRequest().getScheme() + "://"
                + this.getRequest().getServerName()
                + this.getRequest().getContextPath()
                + this.getRequest().getServletPath();
        if (this.getRequest().getQueryString() != null) {
            url += "?" + this.getRequest().getQueryString();
        }
        return url;
    }

	public JSONObject getErrorJson(){
		JSONObject result = new JSONObject();
		result.put("result", "false");
		return result;
	}

	public JSONObject getOkJson(){
		JSONObject result = new JSONObject();
		result.put("result", "true");
		return result;
	}

    private String[] typeArr = {"user"};
	public Message checkPublic(PageData pd){
	    if(StringUtil.isNotEmpty(pd.getString("user"))){
            User user = this.getUser();//获取用户
            if(user == null){
                return Message.error("用户为空");
            }
        }


        return Message.ok();
    }

	public Message checkPdParam(PageData pd){
	    if(StringUtil.isEmpty(pd)) return Message.error("参数为空");

        Iterator<Map.Entry<String, String>> iterator = pd.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            if(StringUtil.isEmpty(entry.getValue())){
                return Message.error("数据为空").addData("field", entry.getKey());
            }
        }
        return Message.ok();
	}

    /**
     * //获取缓存用户信息
     * @return
     */
    public User getUser(){
		User user = Jurisdiction.getSessionUser() == null ? null : Jurisdiction.getSessionUser().getUser();
		return user;
	}

    public static void main(String[] args) {
        PageData pd = new PageData();
        pd.put("name", "1");
        pd.put("age", 0);
        pd.put("sex", "男");

        System.out.println(StringUtil.isEmpty(pd));
        System.out.println(new BaseController().checkPdParam(pd));

//        pd.put("shop_no", "1111");
        ShopInfo shopInfo = (ShopInfo) JSONObject.toBean(JSONObject.fromObject(pd), ShopInfo.class);

        System.out.println(shopInfo.toString());


    }
	
}
