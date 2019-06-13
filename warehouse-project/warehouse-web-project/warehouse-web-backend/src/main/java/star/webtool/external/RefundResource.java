package star.webtool.external;


/**
 * Title:账户绑定关联的资源
 *
 * @author wangjh
 * @created 2015年7月1日  下午1:58:58
 */
public enum RefundResource {
   getResourceList("强制退款", "ExternalService", "external/cashier/refund/bill/create");

    private String serviceName; // 服务名
    private String serviceClass; // 服务类名
    private String serviceURL;  // 外部安信服务URL

    private RefundResource(String serviceName, String serviceClass, String serviceURL)
    {
        this.serviceName = serviceName;
        this.serviceClass = serviceClass;
        this.serviceURL = serviceURL;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String getServiceURL()
    {
        return serviceURL;
    }

    public void setServiceURL(String serviceURL)
    {
        this.serviceURL = serviceURL;
    }

    public String getServiceClass()
    {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass)
    {
        this.serviceClass = serviceClass;
    }

}
