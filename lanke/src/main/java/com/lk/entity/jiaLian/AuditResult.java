package com.lk.entity.jiaLian;

import lombok.Data;

/**
 * @author myq
 * @description 商户审核结果通知类
 * @create 2019-04-26 15:45
 */
@Data
public class AuditResult{

    private String code;//返回状态码 R0001：成功  E0001：失败
    private String result;//返回结果说明
    private AuditData data;//返回对象


    @Data
    public class AuditData{
        private String reason;//审核失败原因
        private String merchNo;//审核完成返回的商户号
        private Integer status;//审核结果：1：成功 ； 0：未通过
        private Integer settleType;//商户结算类型-审核成功则返回(0：T+0 1：T+1 3：D+1)
    }

}
