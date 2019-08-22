<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2019/7/18
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>测试</title>
</head>
<body>
        <button id="test">测试</button>
</body>
<script src="<%=basePath%>/js/jquery-3.3.1.min.js"></script>
<script>
    $("#test").on("click",function(){
        $.ajax({
            type: "post",
            url: "/lanker/autosellmanager/goPay.do",
            contentType: "application/json", //必须有
            dataType: "json", //表示返回值类型，不必须
            data:JSON.stringify({"vm_code":"2", "out_order_no":"12345", "total_fee":"10", "notify_url":"wwww", "detail":"可乐"}),
            success: function (data) {
                window.location.href = data.resulturl;
              /* var urll = data.url;
               console.log(urll);*/
              console.log(data.resulturl);
            }
        });
    });
</script>
</html>
