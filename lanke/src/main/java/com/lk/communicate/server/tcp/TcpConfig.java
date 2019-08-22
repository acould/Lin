package com.lk.communicate.server.tcp;

public interface TcpConfig {
    String charsetName = "utf-8";//字符集编码
    Integer headLength = 4;//包头长度
    String loginContent = "{\"company_token\":\"HZwjkj\"}";//登录验证内容
    String desKey = "898730ED";//加密key
    String handshakeKey = "7pC-Wrm-u2B-WNJ";//握手加密key

    String public_key = "bK54-c0H6-4e9T-5e5Y";//揽客的公钥
}
