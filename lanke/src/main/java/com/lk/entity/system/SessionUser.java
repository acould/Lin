package com.lk.entity.system;

import com.lk.wechat.response.WechatUser;
import lombok.Data;

/**
 * @author myq
 * @description 系统SessiionUser
 * @create 2019-05-27 15:55
 */
@Data
public class SessionUser {

    private User user;//系统用户
    private WechatUser wechatUser;//微信用户

    private BundUser bundUser;//微信绑定用户


}
