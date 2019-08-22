package com.lk.entity.system;

import java.util.List;

import com.lk.entity.Page;
import com.lk.wechat.response.WechatUser;
import lombok.Data;


/**
 * 类名称：用户
 * 类描述：
 *
 * @author 洪智鹏
 * 作者单位：
 * 联系方式：
 * 创建时间：2014年6月28日
 * @version 1.0
 */

@Data
public class User {
    private String USER_ID;        //用户id
    private String USERNAME;        //用户名
    private String PASSWORD;    //密码
    private String NAME;        //姓名
    private String RIGHTS;        //权限
    private String ROLE_ID;        //角色id
    private String LAST_LOGIN;    //最后登录时间
    private String IP;            //用户登录ip地址
    private String STATUS;        //状态
    private String BZ;        //备注
    private String SKIN;        //皮肤
    private String INTENET_ID;  //网吧id
    private String ANGET_ID;    //代理商id
    private String PHONE;       //手机号
    private Integer INTEGRAL;   //积分

    private String IMGURL;
    private String OPEN_ID;
    private List<String> store_ids; //用户管理的门店id列表


    private Page page;            //分页对象
    private Role role;            //角色对象

    public Page getPage() {
        if (page == null)
            page = new Page();
        return page;
    }


}
