package com.lk.service.weixin.menu.impl;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.lk.dao.DaoSupport;
import com.lk.entity.weixin.Button;
import com.lk.entity.weixin.Button.SUB_BUTTON;
import com.lk.service.internet.terrace.TerraceManager;
import com.lk.service.weixin.autoReply.AutoReplyService;
import com.lk.service.weixin.menu.WxMenuService;
import com.lk.util.PageData;
import com.lk.util.PublicManagerUtil;
import com.lk.util.StringUtil;
import com.lk.wechat.util.MenuUtil;


@Service("wxMenuService")
public class WxMenuImpl implements WxMenuService{
	
	@Resource(name = "daoSupport")
	private DaoSupport baseDao;
	@Resource(name="terraceService")
	private TerraceManager terraceService;
	@Resource(name="autoReplyService")
	private AutoReplyService autoReplyService;
	
	//URL(开发者密码)
	public static final String URL =  PublicManagerUtil.URL1+"intenetmumber/singIn.do?intenetId=";
	public static final String URL1= "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
	public static final String URL2 ="&response_type=code&scope=snsapi_base&state=1&component_appid="+PublicManagerUtil.APPID+"#wechat_redirect";
	public static final String URL3 = PublicManagerUtil.URL1+"intenetmumber/index.do?intenetId=";
	public static final String URL4=  PublicManagerUtil.URL1+"intenetmumber/goToMiniWeb.do?intenetId=";
	public static final String URL5=  PublicManagerUtil.URL1+"intenetmumber/introduction.do?intenetId=";

	private String token = "";
	
	//将一级菜单和二级菜单表示为行和列，一级为行，二级为列，0表示1行或者1列。比如一级菜单的第二个菜单表示为lie=0；hang=1
	
	/**
	 * 新增或修改保存
	 * pd中有INTERNET_ID，APP_ID
	 * request中为ajax传输的数据
	 */
	public JSONObject saveMenu(PageData pd,HttpServletRequest request) throws Exception{
		
		JSONObject json = new JSONObject();
		json.put("result", PublicManagerUtil.SUCCESS);
		json.put("message", "保存成功");
		
		String internetId = pd.getString("INTERNET_ID");
		String appId = pd.getString("APP_ID");
		String lie = request.getParameter("LIE");
		String hang = request.getParameter("HANG");
		PageData pdd = new PageData();
		pdd.put("LIE", lie);
		
		
		PageData pdLie = new PageData();
		pdLie.put("FIRST", lie);
		pdLie.put("APP_ID", appId);
		pdLie = findByOrder(pdLie);
		
		String name = request.getParameter("NAME");
		int len = 0;
		if(StringUtil.isNotEmpty(hang)){
			len = 60;
			if(name.length() == 0){
				name = "子菜单名称";
			}
		}else{
			len = 16;
			if(name.length() == 0){
				name = "菜单名称";
			}
		}
		//菜单名称不超过8字，子菜单不超过30字
		char[] ch = name.toCharArray();
        for (int i = 0; i < ch.length; i++) {
        	if(len == 0){
        		name = name.substring(0,i);
        		break;
        	}
            char c = ch[i];
            if (StringUtil.isChinese(c)) {
            	len -= 2;
            }else{
            	len --;
            }
        }
		
		//hang不为空时，操作的是二级菜单
		if(StringUtil.isNotEmpty(hang)){
			pdd.put("HANG", hang);
			
			PageData pdMenu = new PageData();
			pdMenu.put("FIRST", lie);
			pdMenu.put("SECOND", hang);
			pdMenu.put("APP_ID", appId);
			pdMenu = findByOrder(pdMenu);
			//判断是新增还是修改操作（二级菜单）
			if(pdMenu == null){
				pdMenu = new PageData();
				pdMenu.put("MENU_ID", StringUtil.get32UUID());
				pdMenu.put("PARENT_ID", pdLie.getString("MENU_ID"));//需要查询父id
				pdMenu.put("STATE", 1);//可更改菜单
				pdMenu.put("SEQUENCE", hang);
				pdMenu.put("APP_ID", appId);
				pdMenu.put("INTERNET_ID", internetId);
				
				if(request.getParameter("TYPE").equals("click")){
					if(StringUtil.isNotEmpty(request.getParameter("CARD_ID"))){
						//设置卡券
						pdMenu.put("VALUE", StringUtil.get32UUID());
						pdMenu.put("CARD_ID", request.getParameter("CARD_ID"));
					}else if(StringUtil.isNotEmpty(request.getParameter("CONTENT"))){
						//保存文字信息
						pdMenu.put("VALUE", StringUtil.get32UUID());
						pdMenu.put("CONTENT", request.getParameter("CONTENT"));
					}
				}else if(request.getParameter("TYPE").equals("media_id")){
					pdMenu = updateImgOrTw(pdMenu, internetId, pdd,request);
				}else if(request.getParameter("TYPE").equals("view")){
					if(StringUtil.isNotEmpty(request.getParameter("URL"))){
						pdMenu.put("URL", request.getParameter("URL"));
					}
				}else if(request.getParameter("TYPE").equals("miniprogram")){
					if(StringUtil.isNotEmpty(request.getParameter("MINI_APPID")) && StringUtil.isNotEmpty(request.getParameter("MINI_PAGEPATH"))){
						//保存小程序参数
						pdMenu.put("MINI_APPID", request.getParameter("MINI_APPID"));
						pdMenu.put("MINI_PAGEPATH", request.getParameter("MINI_PAGEPATH"));
						pdMenu.put("URL", request.getParameter("MINI_URL"));
					}
				}
				pdMenu.put("NAME", name);
				pdMenu.put("TYPE", request.getParameter("TYPE"));
				baseDao.save("WechatMenuMapper.save", pdMenu);

				//新增二级菜单的时候，去除父菜单的值
                baseDao.update("WechatMenuMapper.editFu", pdLie);
			}else{
				if(pdMenu.getString("STATE").equals("0")){
					json.put("result", PublicManagerUtil.FALSE);
					json.put("message", "系统菜单不可更改！");
					return json;
				}
				pdMenu.put("NAME", name);
				pdMenu.put("TYPE", request.getParameter("TYPE"));
				String flag = "";
				if(request.getParameter("TYPE").equals("click")){
					if(StringUtil.isNotEmpty(request.getParameter("CARD_ID"))){
						//设置卡券
						if(StringUtil.isNotEmpty(pdMenu.getString("CARD_ID"))){
							pdMenu.put("CARD_ID", request.getParameter("CARD_ID"));
						}else{
							pdMenu.put("VALUE", StringUtil.get32UUID());
							pdMenu.put("CARD_ID", request.getParameter("CARD_ID"));
						}
						pdMenu.remove("CONTENT");
					}else if(StringUtil.isNotEmpty(request.getParameter("CONTENT").trim())){
						//修改或新增文字信息
						if(StringUtil.isNotEmpty(pdMenu.getString("CONTENT"))){
							pdMenu.put("CONTENT", request.getParameter("CONTENT").trim());
						}else{
							pdMenu.put("VALUE", StringUtil.get32UUID());
							pdMenu.put("CONTENT", request.getParameter("CONTENT").trim());
						}
						pdMenu.remove("CARD_ID");
					}else{
						//值不存在时，不修改type和内容
						flag = "1";
					}
				}else if(request.getParameter("TYPE").equals("media_id")){
					//修改图文或者图片信息
					if(StringUtil.isNotEmpty(request.getParameter("MEDIA_ID"))){
						pdMenu = updateImgOrTw(pdMenu, internetId, pdd,request);
					}else{
						//值不存在时，不修改type和内容
						flag = "1";
					}
				}else if(request.getParameter("TYPE").equals("view")){
					if(StringUtil.isNotEmpty(request.getParameter("URL"))){
						pdMenu.put("URL", request.getParameter("URL"));
					}else{
						flag = "1";
					}
				}else if(request.getParameter("TYPE").equals("miniprogram")){
					if(StringUtil.isNotEmpty(request.getParameter("MINI_APPID")) && StringUtil.isNotEmpty(request.getParameter("MINI_PAGEPATH"))){
						//保存小程序参数
						pdMenu.put("MINI_APPID", request.getParameter("MINI_APPID"));
						pdMenu.put("MINI_PAGEPATH", request.getParameter("MINI_PAGEPATH"));
						pdMenu.put("URL", request.getParameter("MINI_URL"));
					}else{
						flag = "1";
					}
				}
				if(StringUtil.isNotEmpty(flag) && flag.equals("1")){
					pdMenu.remove("TYPE");
				}
				baseDao.update("WechatMenuMapper.edit", pdMenu);
			}
		}else{
			//判断是新增还是修改操作（一级菜单）
			if(pdLie == null){
				pdLie = new PageData();
				pdLie.put("MENU_ID", StringUtil.get32UUID());
				pdLie.put("PARENT_ID", 0);//一级菜单
				pdLie.put("STATE", 1);//可更改菜单
				pdLie.put("SEQUENCE", lie);
				pdLie.put("APP_ID", appId);
				pdLie.put("INTERNET_ID", internetId);
				
				if(request.getParameter("TYPE").equals("click")){
					if(StringUtil.isNotEmpty(request.getParameter("CARD_ID"))){
						//设置卡券
						pdLie.put("VALUE", StringUtil.get32UUID());
						pdLie.put("CARD_ID", request.getParameter("CARD_ID"));
					}else if(StringUtil.isNotEmpty(request.getParameter("CONTENT").trim())){
						//保存文字信息
						pdLie.put("VALUE", StringUtil.get32UUID());
						pdLie.put("CONTENT", request.getParameter("CONTENT").trim());
					}
				}else if(request.getParameter("TYPE").equals("media_id")){
					pdLie = updateImgOrTw(pdLie, internetId, pdd,request);
				}else if(request.getParameter("TYPE").equals("view")){
					//保存跳转网页
					if(StringUtil.isNotEmpty(request.getParameter("URL"))){
						pdLie.put("URL", request.getParameter("URL"));
					}
				}else if(request.getParameter("TYPE").equals("miniprogram")){
					//保存小程序参数
					if(StringUtil.isNotEmpty(request.getParameter("MINI_APPID")) && StringUtil.isNotEmpty(request.getParameter("MINI_PAGEPATH"))){
						pdLie.put("MINI_APPID", request.getParameter("MINI_APPID"));
						pdLie.put("MINI_PAGEPATH", request.getParameter("MINI_PAGEPATH"));
						pdLie.put("URL", request.getParameter("MINI_URL"));
					}
				}
				pdLie.put("NAME", name);
				pdLie.put("TYPE", request.getParameter("TYPE"));
				baseDao.save("WechatMenuMapper.save", pdLie);
			}else{
				if(pdLie.getString("STATE").equals("0")){
					json.put("result", PublicManagerUtil.FALSE);
					json.put("message", "系统菜单不可更改！");
					return json;
				}
				pdLie.put("NAME", name);
				//有无子菜单
				if(StringUtil.isNotEmpty(pdLie.getString("TYPE"))){
					pdLie.put("TYPE", request.getParameter("TYPE"));
					//没有子菜单
					String flag = "";//值=1时，表示不修改type和内容
					if(request.getParameter("TYPE").equals("click")){
						if(StringUtil.isNotEmpty(request.getParameter("CARD_ID"))){
							//设置卡券
							if(StringUtil.isNotEmpty(pdLie.getString("CARD_ID"))){
								pdLie.put("CARD_ID", request.getParameter("CARD_ID"));
							}else{
								pdLie.put("VALUE", StringUtil.get32UUID());
								pdLie.put("CARD_ID", request.getParameter("CARD_ID"));
							}
							pdLie.remove("CONTENT");
						}else if(StringUtil.isNotEmpty(request.getParameter("CONTENT").trim())){
							//修改或新增文字信息
							if(StringUtil.isNotEmpty(pdLie.getString("CONTENT"))){
								pdLie.put("CONTENT", request.getParameter("CONTENT").trim());
							}else{
								pdLie.put("VALUE", StringUtil.get32UUID());
								pdLie.put("CONTENT", request.getParameter("CONTENT").trim());
							}
							pdLie.remove("CARD_ID");
						}else{
							flag = "1";
						}
					}else if(request.getParameter("TYPE").equals("media_id")){
						////修改图文或者图片信息
						if(StringUtil.isNotEmpty(request.getParameter("MEDIA_ID"))){
							pdLie = updateImgOrTw(pdLie, internetId, pdd,request);
						}else{
							//值不存在时，不修改type和内容
							flag = "1";
						}
					}else if(request.getParameter("TYPE").equals("view")){
						//保存跳转网页
						if(StringUtil.isNotEmpty(request.getParameter("URL"))){
							pdLie.put("URL", request.getParameter("URL"));
						}else{
							flag = "1";
						}
					}else if(request.getParameter("TYPE").equals("miniprogram")){
						//保存小程序参数
						if(StringUtil.isNotEmpty(request.getParameter("MINI_APPID")) && StringUtil.isNotEmpty(request.getParameter("MINI_PAGEPATH"))){
							pdLie.put("MINI_APPID", request.getParameter("MINI_APPID"));
							pdLie.put("MINI_PAGEPATH", request.getParameter("MINI_PAGEPATH"));
							pdLie.put("URL", request.getParameter("MINI_URL"));
						}else{
							flag = "1";
						}
					}
					pdLie.put("TYPE", request.getParameter("TYPE"));
					if(StringUtil.isNotEmpty(flag) && flag.equals("1")){
						pdLie.remove("TYPE");
					}
				}else{
					pdLie.put("NAME", name);
				}
				baseDao.update("WechatMenuMapper.edit", pdLie);
			}
		}
		return json;
	}
	
	/**
	 * pd中有INTERNET_ID，APP_ID
	 */
	public JSONObject defaultMenu(PageData pd) throws Exception{
		JSONObject json = new JSONObject();
		json.put("result", PublicManagerUtil.SUCCESS);
		json.put("message", "创建默认菜单成功！");
		
		PageData pdButton = new PageData();
		pdButton.put("INTERNET_ID", pd.getString("INTERNET_ID"));
		pdButton.put("APP_ID", pd.getString("APP_ID"));
		pdButton.put("STATE", "1");//0为系统菜单，不可更改
		
		deleteByAppId(pdButton);
		
		List<Button> list = new ArrayList<Button>();
			//第一个：一级菜单
			Button button0 = new Button();
			button0.setName("赢积分");
			String menuId0 = StringUtil.get32UUID();
			pdButton.put("MENU_ID", menuId0);
			pdButton.put("PARENT_ID", "0");
			pdButton.put("NAME", "赢积分");
			pdButton.put("SEQUENCE", "0");
			pdButton.put("STATE", "1");
			baseDao.save("WechatMenuMapper.save", pdButton);
			
			List<SUB_BUTTON> sublist0 = new ArrayList<SUB_BUTTON>();
				SUB_BUTTON subButton0_0 = new Button().new SUB_BUTTON();
				subButton0_0.setName("签到有礼");
				subButton0_0.setType("view");
				String url = URL+pd.getString("INTERNET_ID")+"";
		        url = URLEncoder.encode(url,"utf-8");
		        subButton0_0.setUrl(URL1+pd.getString("APP_ID")+"&redirect_uri="+url+URL2);
		        sublist0.add(subButton0_0);
		        
		        pdButton.put("MENU_ID", StringUtil.get32UUID());
				pdButton.put("PARENT_ID", menuId0);
				pdButton.put("NAME", "签到有礼");
				pdButton.put("TYPE", "view");
				pdButton.put("URL", URLDecoder.decode(url, "UTF-8"));
				pdButton.put("SEQUENCE", "0");
				pdButton.put("STATE", "1");
				baseDao.save("WechatMenuMapper.save", pdButton);
			button0.setSub_button(sublist0);
			
			
			//第二个：一级菜单
			Button button1 = new Button();
			button1.setName("会员中心");
			button1.setType("view");
			url = URL3+pd.getString("INTERNET_ID")+"";
	        url = URLEncoder.encode(url,"utf-8");
			button1.setUrl(URL1+pd.getString("APP_ID")+"&redirect_uri="+url+URL2);
			
			pdButton.put("MENU_ID", StringUtil.get32UUID());
			pdButton.put("PARENT_ID", "0");
			pdButton.put("NAME", "会员中心");
			pdButton.put("TYPE", "view");
			pdButton.put("URL", URLDecoder.decode(url, "UTF-8"));
			pdButton.put("SEQUENCE", "1");
			pdButton.put("STATE", "1");
			baseDao.save("WechatMenuMapper.save", pdButton);
			
			
			//第三个：一级菜单
			Button button2 = new Button();
			button2.setName("看网吧");
			String menuId2 = StringUtil.get32UUID();
			pdButton.put("MENU_ID", menuId2);
			pdButton.put("PARENT_ID", "0");
			pdButton.put("NAME", "看网吧");
			pdButton.put("URL", "");
			pdButton.put("SEQUENCE", "2");
			pdButton.put("STATE", "1");
			pdButton.remove("TYPE");
			baseDao.save("WechatMenuMapper.save", pdButton);
			
			List<SUB_BUTTON> sublist2 = new ArrayList<SUB_BUTTON>();
				//第一个：二级菜单
				SUB_BUTTON subButton2_0 = new Button().new SUB_BUTTON();
				subButton2_0.setName("微官网");
				subButton2_0.setType("view");
				url = URL4+pd.getString("INTERNET_ID")+"";
		        url = URLEncoder.encode(url,"utf-8");
				subButton2_0.setUrl(URL1+pd.getString("APP_ID")+"&redirect_uri="+url+URL2);
				
				pdButton.put("MENU_ID", StringUtil.get32UUID());
				pdButton.put("PARENT_ID", menuId2);
				pdButton.put("NAME", "微官网");
				pdButton.put("TYPE", "view");
				pdButton.put("URL", URLDecoder.decode(url, "UTF-8"));
				pdButton.put("SEQUENCE", "0");
				pdButton.put("STATE", "1");
				baseDao.save("WechatMenuMapper.save", pdButton);
				
				//第二个：二级菜单
				SUB_BUTTON subButton2_1 = new Button().new SUB_BUTTON();
				subButton2_1.setName("找门店");
				subButton2_1.setType("view");
				url = URL5+pd.getString("INTERNET_ID")+"";
		        url = URLEncoder.encode(url,"utf-8");
				subButton2_1.setUrl(URL1+pd.getString("APP_ID")+"&redirect_uri="+url+URL2);
				
				pdButton.put("MENU_ID", StringUtil.get32UUID());
				pdButton.put("PARENT_ID", menuId2);
				pdButton.put("NAME", "找门店");
				pdButton.put("TYPE", "view");
				pdButton.put("URL", URLDecoder.decode(url, "UTF-8"));
				pdButton.put("SEQUENCE", "1");
				pdButton.put("STATE", "1");
				baseDao.save("WechatMenuMapper.save", pdButton);
			sublist2.add(subButton2_0);
			sublist2.add(subButton2_1);
			button2.setSub_button(sublist2);
			
		list.add(button0);
		list.add(button1);
		list.add(button2);
		
		//调用菜单接口，生成菜单
		token = getAuthToken(pd.getString("INTERNET_ID"));
		MenuUtil.deleteMenu(token);
		JSONObject result = MenuUtil.createMenu(token, list, null);
		if(!result.getString("errcode").equals("0")){
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "创建默认菜单失败！");
		}
		return json;
	}

	/**
	 * 删除某个二级菜单（一级菜单不可删除）
	 * pd中有APP_ID，SECOND，FIRST
	 * FIRST表示一级菜单的序号，SECOND表示二级菜单的序号
	 */
	public JSONObject deleteMenuByOrder(PageData pd)throws Exception {
		JSONObject json = new JSONObject();
		
		PageData pdMenu = new PageData();
		pdMenu = findByOrder(pd);
		if(pdMenu.getString("STATE").equals("0")){
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "系统菜单不可删除!");
		}else if(pdMenu.getString("PARENT_ID").equals("0")){
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "一级菜单不可删除!");
		}else{
			baseDao.delete("WechatMenuMapper.deleteByOrder", pd);
			if(pdMenu.getString("TYPE").equals("media_id") && StringUtil.isNotEmpty(pdMenu.getString("PATH"))){
				//如果是已经上传到微信的图片则不删
				pd.put("PICTURE_ID", pdMenu.getString("MEDIA_ID"));
				baseDao.delete("InternetPicturesMapper.delete", pd);
			}
			//当前删除菜单后面的菜单序号往前移
			List<PageData> sonList = findSonByLie(pd);
			for(PageData pdSon : sonList){
				int sequence = Integer.parseInt(pd.getString("SECOND"));
				if(pdSon.getString("SEQUENCE").equals((sequence+1)+"")){
					pdSon.put("SEQUENCE", sequence+"");
					baseDao.update("WechatMenuMapper.edit", pdSon);
				}
				if(pdSon.getString("SEQUENCE").equals((sequence+2)+"")){
					pdSon.put("SEQUENCE", (sequence+1)+"");
					baseDao.update("WechatMenuMapper.edit", pdSon);
				}
				if(pdSon.getString("SEQUENCE").equals((sequence+3)+"")){
					pdSon.put("SEQUENCE", (sequence+2)+"");
					baseDao.update("WechatMenuMapper.edit", pdSon);
				}
				if(pdSon.getString("SEQUENCE").equals((sequence+4)+"")){
					pdSon.put("SEQUENCE", (sequence+3)+"");
					baseDao.update("WechatMenuMapper.edit", pdSon);
				}
			}

			//如果删除的是最后一个子菜单的时候，默认给父菜单赋TYPE = media_id
            if(sonList.size() == 0){
                String parentId = pdMenu.getString("PARENT_ID");
                PageData pdFuMenu = new PageData();
                pdFuMenu.put("MENU_ID", parentId);
                pdFuMenu = (PageData) baseDao.findForObject("WechatMenuMapper.findById", pdFuMenu);
                if(StringUtil.isNotEmpty(pdFuMenu)){
                    pdFuMenu.put("TYPE", "media_id");
                    baseDao.update("WechatMenuMapper.edit", pdFuMenu);
                }
            }




			json.put("result", PublicManagerUtil.SUCCESS);
			json.put("message", "删除成功!");
		}
		return json;
	}

	/**
	 * 移动菜单，自动修改两个之间的序号
	 * pd中有HANG，LIE，FLAG
	 * HANG表示一级菜单，LIE表示二级菜单
	 */
	public JSONObject move(PageData pd) throws Exception {
		JSONObject json = new JSONObject();
		json.put("result", PublicManagerUtil.TRUE);
		
		String flag = pd.getString("FLAG");//up表示上移；down表示下移
		String lie = pd.getString("LIE");
		String hang = pd.getString("HANG");
		int next = 0;
		if(flag.equals("up")){
			next = Integer.parseInt(hang) - 1;
		}else if(flag.equals("down")){
			next = Integer.parseInt(hang) + 1; 
		}
		
		//当前的，改为上一个或下一个
		PageData pdMenu = new PageData();
		pdMenu.put("FIRST", lie+"");
		pdMenu.put("SECOND", hang+"");
		pdMenu.put("APP_ID", pd.getString("APP_ID"));
		pdMenu = findByOrder(pdMenu);
		pdMenu.put("SEQUENCE", next+"");
		//上一个或上一个，改为当前的
		PageData pdMenuNext = new PageData();
		pdMenuNext.put("FIRST", lie+"");
		pdMenuNext.put("SECOND", next+"");
		pdMenuNext.put("APP_ID", pd.getString("APP_ID"));
		pdMenuNext = findByOrder(pdMenuNext);
		pdMenuNext.put("SEQUENCE", hang+"");
		
		if((Integer)baseDao.update("WechatMenuMapper.edit", pdMenu) != 1){
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "移动失败！");
			return json;
		}
		if((Integer)baseDao.update("WechatMenuMapper.edit", pdMenuNext) != 1){
			json.put("result", PublicManagerUtil.FALSE);
			json.put("message", "移动失败！");
			return json;
		}
		
		return json;
	}

	/**
	 * 发布菜单
	 * pd中有INTERNET_ID，APP_ID
	 * 
	 */
	public JSONObject issue(PageData pd) throws Exception{
		JSONObject json = new JSONObject();
		json.put("result", PublicManagerUtil.SUCCESS);
		json.put("message", "发布成功！");
		
		String appId = pd.getString("APP_ID");
		
		List<Button> buttonList = new ArrayList<Button>();
		//查询出该公众号下的所有一级菜单
		pd.put("APP_ID", appId);
		List<PageData> menuList = findByAppId(pd);
		for(int i=0;i<menuList.size();i++){
			Button button = new Button();
			PageData pdMenu = new PageData();
			pdMenu = menuList.get(i);
			if(StringUtil.isNotEmpty(pdMenu.getString("TYPE"))){
				pdMenu.put("APP_ID", appId);
				button = putParamToButton(pdMenu);
				if(!StringUtil.isNotEmpty(button)){
					json.put("result", PublicManagerUtil.FALSE);
					json.put("message", "请输入菜单内容");
					json.put("lie", i);
					return json;
				}
			}else{
				button.setName(pdMenu.getString("NAME"));
				//一级菜单下有二级菜单列表
				List<SUB_BUTTON> subList = new ArrayList<SUB_BUTTON>();
					PageData temp = new PageData();
					temp.put("APP_ID", appId);
					temp.put("FIRST", i);
					List<PageData> pdList = findSonByLie(temp);
					//将二级菜单列表封装到button的List<SUB_BUTTON>中
					for(int j=0;j<pdList.size();j++){
						SUB_BUTTON subButton = new Button().new SUB_BUTTON();
						pdList.get(j).put("APP_ID", appId);
						subButton = putParamToSub_Button(pdList.get(j));
						if(!StringUtil.isNotEmpty(subButton)){
							json.put("result", PublicManagerUtil.FALSE);
							json.put("message", "请输入菜单内容");
							json.put("lie", i);
							json.put("hang", j);
							return json;
						}
						subList.add(subButton);
					}
				button.setSub_button(subList);
			}
			buttonList.add(button);
		}
		//调用微信接口
		token = getAuthToken(pd.getString("INTERNET_ID"));
		MenuUtil.deleteMenu(token);
		JSONObject result = MenuUtil.createMenu(token, buttonList, null);
		if(result.containsKey("errcode")){
			if(result.getString("errcode").equals("40166") && result.getString("errmsg").contains("invalid weapp appid")){
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "无效的小程序APPID!");
				return json;
			}
			
			if(result.getString("errcode").equals("61007")){
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "您还没有将菜单权限授权给我们!");
				return json;
			}
			
			if(!result.getString("errcode").equals("0")){
				json.put("result", PublicManagerUtil.FALSE);
				json.put("message", "调用菜单接口失败!");
				return json;
			}
		}
		return json;
	}

	/**
	 * 将有url的菜单参数进行封装到一级菜单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Button putParamToButton(PageData pd) throws Exception{
		Button button = new Button();
		
		button.setName(pd.getString("NAME"));
		button.setType(pd.getString("TYPE"));
		if(pd.getString("TYPE").equals("view")){
			if(!StringUtil.isNotEmpty(pd.getString("URL"))){
				return null;
			}else if(pd.getString("URL").indexOf("http://") !=0 && pd.getString("URL").indexOf("https://") !=0){
				return null;
			}
			String url = pd.getString("URL");
			if(url.contains(PublicManagerUtil.URL1) || url.contains(PublicManagerUtil.URL2) ){
				url = URL1+pd.getString("APP_ID")+"&redirect_uri="+URLEncoder.encode(pd.getString("URL"),"utf-8")+URL2;
			}
			button.setUrl(url);
		}else if(pd.getString("TYPE").equals("media_id")){
			if(!StringUtil.isNotEmpty(pd.getString("MEDIA_ID"))){
				return null;
			}
			button.setMedia_id(pd.getString("MEDIA_ID"));
		}else if(pd.getString("TYPE").equals("click")){
			if(!StringUtil.isNotEmpty(pd.getString("VALUE"))){
				return null;
			}
			button.setKey(pd.getString("VALUE"));
		}else if(pd.getString("TYPE").equals("miniprogram")){
			if(!StringUtil.isNotEmpty("MINI_APPID") && !StringUtil.isNotEmpty(pd.getString("MINI_PAGEPATH"))){
				return null;
			}
			String url = pd.getString("URL");
			if(url.contains(PublicManagerUtil.URL1) || url.contains(PublicManagerUtil.URL2) ){
				url = URL1+pd.getString("APP_ID")+"&redirect_uri="+URLEncoder.encode(pd.getString("URL"),"utf-8")+URL2;
			}
			button.setUrl(url);
			button.setAppId(pd.getString("MINI_APPID"));
			button.setPagePath(pd.getString("MINI_PAGEPATH"));
		}
		return button;
	}
	
	/**
	 * 将有url的菜单参数进行封装到二级菜单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public SUB_BUTTON putParamToSub_Button(PageData pd) throws Exception{
		SUB_BUTTON button = new Button().new SUB_BUTTON();
		
		button.setName(pd.getString("NAME"));
		button.setType(pd.getString("TYPE"));
		if(pd.getString("TYPE").equals("view")){
			if(!StringUtil.isNotEmpty(pd.getString("URL"))){
				return null;
			}else if(pd.getString("URL").indexOf("http://") !=0 && pd.getString("URL").indexOf("https://") !=0){
				return null;
			}
			String url = pd.getString("URL");
			if(url.contains(PublicManagerUtil.URL1) || url.contains(PublicManagerUtil.URL2) ){
				url = URL1+pd.getString("APP_ID")+"&redirect_uri="+URLEncoder.encode(pd.getString("URL"),"utf-8")+URL2;
			}
			button.setUrl(url);
		}else if(pd.getString("TYPE").equals("media_id")){
			if(!StringUtil.isNotEmpty(pd.getString("MEDIA_ID"))){
				return null;
			}
			button.setMedia_id(pd.getString("MEDIA_ID"));
		}else if(pd.getString("TYPE").equals("click")){
			if(!StringUtil.isNotEmpty(pd.getString("VALUE"))){
				return null;
			}
			button.setKey(pd.getString("VALUE"));
		}else if(pd.getString("TYPE").equals("miniprogram")){
			if(!StringUtil.isNotEmpty("MINI_APPID") && StringUtil.isNotEmpty(pd.getString("MINI_PAGEPATH"))){
				return null;
			}
			String url = pd.getString("URL");
			if(url.contains(PublicManagerUtil.URL1) || url.contains(PublicManagerUtil.URL2) ){
				url = URL1+pd.getString("APP_ID")+"&redirect_uri="+URLEncoder.encode(pd.getString("URL"),"utf-8")+URL2;
			}
			button.setUrl(url);
			button.setAppId(pd.getString("MINI_APPID"));
			button.setPagePath(pd.getString("MINI_PAGEPATH"));
		}
		return button;
	}
	
	/**
	 * 判断是图片或图文的信息，处理其对应的信息
	 * @param pdButton
	 * @param internetId
	 * @param pd
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public PageData updateImgOrTw(PageData pdButton,String internetId,PageData pd,HttpServletRequest request) throws Exception{
		
		if(StringUtil.isNotEmpty(request.getParameter("PATH"))){
			String path = request.getParameter("PATH");
			path = path.substring(path.indexOf("uploadImgs/")+"uploadImgs/".length());
			pdButton.put("PATH", path);
			pdButton.put("MEDIA_ID", request.getParameter("MEDIA_ID"));
		}else{
			//选择历史图文时
			pdButton.put("MEDIA_ID", request.getParameter("MEDIA_ID"));
			pdButton.put("TUWEN", "1");
			pdButton.remove("PATH");
		}
		return pdButton;
	}
	
	/**
	 * 根据序号找菜单
	 * pd中有APP_ID，SECOND，FIRST
	 * FIRST表示一级菜单的序号，SECOND表示二级菜单的序号
	 */
	public PageData findByOrder(PageData pd) throws Exception {
		return (PageData) baseDao.findForObject("WechatMenuMapper.findByOrder",pd);
	}

	/**
	 * 查找一级菜单的所有二级菜单
	 * pd中有APP_ID，FIRST
	 * FIRST表示列的序号，从0开始
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findSonByLie(PageData pd) throws Exception {
		return (List<PageData>) baseDao.findForList("WechatMenuMapper.findSonByLie", pd);
	}

	/**
	 * 查找某个公众的所有的一级菜单
	 * pd中有APP_ID
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findByAppId(PageData pd) throws Exception {
		return (List<PageData>) baseDao.findForList("WechatMenuMapper.findByAppId", pd);
	}

	/**
	 * 删除某个公众号的所有菜单
	 */
	public void deleteByAppId(PageData pd) throws Exception {
		baseDao.delete("WechatMenuMapper.deleteByAppId",pd);
	}
	

	/**
	 * 查询类型为click的菜单（关键词回复）
	 * pd中有APP_ID，VALUE
	 * VALUE表示关键词内容
	 */
	public PageData findByKey(PageData pdMenu) throws Exception {
		return (PageData) baseDao.findForObject("WechatMenuMapper.findByKey",pdMenu);
	}
	
	
	/**
	 * 统一调用自动回复接口中获取token
	 */
	public String getAuthToken(String internetId) throws Exception{
		return autoReplyService.getAuthToken(internetId);
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		WxMenuService ser = new WxMenuImpl();
		PageData pd = new PageData();
		pd.put("APP_ID","wx4d8219c070c78774") ;
		pd.put("INTERNET_ID", "8d78674fe92a4dffb0c3181ed3f8a3cc");
		pd.put("TOKEN", "");
		ser.defaultMenu(pd);
		
	}
	
	
	
	
	
}
