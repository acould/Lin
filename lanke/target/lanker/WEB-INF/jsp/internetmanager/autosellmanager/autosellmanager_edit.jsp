<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.all.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/layer/layer.js"></script>
<!DOCTYPE html>
<html>
<head>
    <link href="<%=basePath%>css/table.css"  rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">

</head>

<body>
<div id="content">
    <%--<header>自助机管理平台</header>--%>
    <div id="list">
        <li class="none"><div class="name">自助售货机1:</div><div class="sr"><input placeholder="请输入售货机编号"></div><div class="del"><div onclick=delecttr(this)>删除</div></div></li>
        <li class="none"><div class="name">自助售货机2:</div><div class="sr"><input placeholder="请输入售货机编号"></div><div class="del"><div onclick=delecttr(this)>删除</div></div></li>
        <li class="none"><div class="name">自助售货机3:</div><div class="sr"><input placeholder="请输入售货机编号"></div><div class="del"><div onclick=delecttr(this)>删除</div></div></li>
    </div>
<%--    <footer>--%>
<%--        <div onclick="addtr()" class="tj">添加</div>--%>
<%--        <div onclick="bc()" class="bc">保存</div>--%>
<%--    </footer>--%>

</div>
</body>

<script>
    var j = 1;
    var data = "${data}";
    var data = data.slice(1,data.length-1);
    var data = data.split(",")
    var id = "${store_id}"
    var dels='';
 if(data[0]) {//判断有无data，有data循环data，隐藏原始的li
     var none = document.querySelectorAll(".none");
     for (var n = 0; n < none.length; n++) {
         none[n].className = 'Dl'
     }

     for (var i = 0; i < data.length; i++) {
         data[i] = data[i].split("=")[1].slice(0);
         data[i] = data[i].slice(0, data[i].length - 1)
         var tableobj = document.getElementById("list");
         var tli = document.createElement("li");
         tli.setAttribute("class", "bj");
         var tdobj = document.createElement("div");
         tdobj.setAttribute("class", "name");
         tdobj.innerHTML = '自助售货机' + (i + 1) + ':';
         tli.appendChild(tdobj);
         var tdobj = document.createElement("div");
         tdobj.setAttribute("class", "sr");
         var inp = document.createElement('input');
         inp.style = 'height:90%;width:100%;border:0;padding:0';
         inp.value = data[i];
         tdobj.style.width = '400px';
         tdobj.appendChild(inp);
         tli.appendChild(tdobj);
         j++;
         var tdobj = document.createElement("div");
         tdobj.setAttribute("class", "dels");
         tdobj.innerHTML = "<div onclick='delecttr(this)'>删除</div>";
         tli.appendChild(tdobj);
         tableobj.appendChild(tli);




     }
     var ali = document.querySelectorAll('.bj');
     for(var i=0;i<ali.length;i++){
         geti(i)
     }
     function geti(i){
         ali[i].addEventListener('click',function () {
             dels=data[i];
             dels={sm_no:dels,store_id:id};
         })
     }

 }
    $('.dels').on('click',function(){
       setTimeout(function(){
           $.ajax({
               url: 'delete.do',
               type: "get",
               data: dels,
               dataType:'json',
               success: function(data){
                   if(data.data.code==0){
                       layer.msg(data.data.msg);
                   }else{
                       layer.msg('删除失败');
                   }

               },
               error:function(){
                   layer.msg("系统繁忙，请稍后再试！");
                   return false;
               }
           });
       },100)


    })
</script>
<script src="<%=basePath%>js/table.js"></script>


</html>