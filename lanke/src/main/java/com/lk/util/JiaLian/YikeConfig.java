package com.lk.util.JiaLian;

import com.lk.util.PublicManagerUtil;

/**
 * @author myq
 * @description 嘉联商户接口 —— 配置信息
 * @create 2019-04-26 15:23
 */
public interface YikeConfig {

    /**
     * @info 接入地址
     */
    public static final String jl_url = "https://pays.bestcunyun.com";

    /**
     * 支付成功后跳转地址
     */
    public static final String order_return_url = PublicManagerUtil.URL2 + "indexMember/jLReturn";
//    public static final String order_return_url = PublicManagerUtil.myq_local + "indexMember/jLReturn";
    /**
     * 回调地址
     */
    public static final String order_callback_url = PublicManagerUtil.URL2 + "indexMember/jLCallBack";
//    public static final String order_callback_url = PublicManagerUtil.myq_local + "indexMember/jLCallBack";

    /**
     * 提交商户信息，回调url
     */
    public static final String shop_call_back_url = PublicManagerUtil.URL2 + "jialian/recShopAudit.do";
   // public static final String shop_call_back_url = "http://gh.easy.echosite.cn/lanker/jialian/recShopAudit.do";


    /**
     * 代理商编号
     */
    public static final String shop_merchant_no = "461287";//568136





    /**
     * 接入系统终端ip地址
     */
    public static final String client_ip = PublicManagerUtil.domain_ip;




    /**
     * @info 统一使用post方式提交数据
     */
    public static final String method = "post";

    /**
     * @info 统一下单入口的 请求头信息
     */
    public static final String head = "application/json";

    /**
     * @info 嘉联商户上传图片接口
     * @decription 嘉联商户向支付平台上传商户照片信息
     */
    public static final String upload = "/pay/shop/yikeFileUpload";


    /**
     * @info 嘉联商户向支付平台进件接口
     * @decription 嘉联商户向支付平台进件商户信息
     */
    public static final String shop_info = "/pay/shop/insertYikeShopInfo";

    /**
     * @info 商户查询审核状态接口
     * @description 嘉联商户根据商户号查询支付平台审核结果
     */
    public static final String get_audit_status = "/pay/shop/getAuditStatusToYike";

    /**
     * @info 商户修改
     * @description 嘉联商户向支付平台进件商户信息
     */
    public static final String update_shop_info = "/pay/shop/updateYikeShopInfo";


    /**
     * @info 聚合支付-统一下单入口（支持微信和支付宝扫码）
     * @description 通过此接口预下单后，返回支付页面连接地址，接入商户生成此连接二维码，支持用户使用微信或者支付宝扫码，完成支付
     */
    public static final String unified_order = "/pay/unifiedOrder";


    /**
     * @info 订单查询-仅支持支付系统订单编号
     * @description 商户号和订单编号可以查询单笔支付订单的详细信息
     */
    public static final String sys_query_order = "/pay/order/queryOrderInfo";


    /**
     * @info 订单查询-支持接入方订单编号
     * @description 接入方通过提供的商户号和订单编号可以查询单笔支付订单的详细信息
     */
    public static final String lk_query_order = "/pay/order/orderDetail";


    /**
     * @info 订单退款（90天内）
     * @description 支持对90天内的订单发起退款，支持部分退款，一笔订单仅可以发起一次
     */
    public static final String refund_order = "/pay/refundOrder";


    /**
     * 请求成功
     */
    public static final String ok = "R0001";

    /**
     * 发生异常
     */
    public static final String err = "E0001";




    /**
     * 小微商户：小微身份证正面（xwshsfzz），小微身份证背面（xwshsfzb），手持身份证（xwshscsfzzp），银行卡正面（xwshyhkzm）。
     */
    public static final String[] xwsh = {"xwshsfzz","xwshsfzb","xwshscsfzzp","xwshyhkzm"};

    /**
     * 营业执照对公：营业执照（gsyyzz），法人身份证正面（frsfzz），法人身份证背面（frsfzb），商户经营门头照片（shjymtz），商户经营实体产品照（shjystcpz），开户许可证（khxkz）
     */
    public static final String[] yyzzdg = {"gsyyzz","frsfzz","frsfzb","shjymtz","shjystcpz","khxkz"};

    /**
     * 营业执照对私：营业执照（gsyyzz），法人身份证正面（frsfzz），法人身份证背面（frsfzb），商户经营门头照片（shjymtz），商户经营实体产品照（shjystcpz），法人银行卡正面（fryhkzm）
     */
    public static final String[] yyzzds = {"gsyyzz","frsfzz","frsfzb","shjymtz","shjystcpz","fryhkzm"};

    /**
     * 租赁商户：法人身份证正面（xwshsfzz），法人身份证背面（xwshsfzb），手持身份证（xwshscsfzzp），银行卡正面（xwshyhkzm），门头照片（shjymtz），营业环境照片（shjystcpz），商户租赁合同1（xwshzlht1），商户租赁合同2（xwshzlht2）
     */
    public static final String[] zlsh = {"xwshsfzz","xwshsfzb","xwshscsfzzp","xwshyhkzm","shjymtz","shjystcpz","xwshzlht1", "xwshzlht2"};















}
