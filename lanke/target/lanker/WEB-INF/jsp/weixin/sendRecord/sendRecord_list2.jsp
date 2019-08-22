<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

	<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"  media="all">
	<link rel="stylesheet" href="<%=basePath%>newStyle/css/textimg.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/awesome-bootstrap-checkbox.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/animate.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/font-awesome.min.css">
    <style type="text/css">
    	#main{
            position: relative;
        }
        .pin{
            padding: 15px 0 0 20px;
            float:left;
        }
    </style>
</head>
<body class="no-skin scroll">

	<div class="bodyBox" style="margin-left:0;">

        <div class="navlist">
            <div class="text-center">
                <div class="paddingSpan span-all"><span class="sreachBj btn-success">全部</span></div>
                <div class="paddingSpan span-drafts"><span class="sreachBj btn-success">草稿箱</span></div>
                <div class="paddingSpan span-history"><span class="sreachBj btn-success">历史记录</span></div>
                <div class="paddingSpan span-addNews"><a class="paddingA btn-warning">新建图文</a></div>
            </div>
        </div>
        
        <form method="post" name="Form" id="Form">
      		<div id="main">

		    </div>

        </form>
    </div>



	<script type="text/javascript" charset="utf-8" src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<%--	<script type="text/javascript" charset="utf-8" src="<%=basePath%>static/ace/js/bootbox.js"></script>--%>
<%--	<script type="text/javascript" charset="utf-8" src="<%=basePath%>newStyle/js/jquery.cookie.js"></script>--%>
<%--	<script type="text/javascript" charset="utf-8" src="<%=basePath%>newStyle/js/bootstrap.min.js"></script>--%>
	<script src="<%=basePath%>newStyle/layuiadmin/layui/layui.js"></script>
	<script>
		var layer ;
		layui.define(['layer'],function () {
			layer = layui.layer
			;
		});
	</script>
	<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
	<script type="text/javascript">
		$(top.hangge());
		var basePath = "<%=basePath%>";

		//初始化列表
		loadRecordNews();

		$(".span-all").click(function () {
			loadRecordNews('all');
		})
		$(".span-drafts").click(function () {
			loadRecordNews('drafts');
		})
		$(".span-history").click(function () {
			loadRecordNews('history');
		})

		//新增图文
		$(".span-addNews").click(function () {
			window.open(basePath + "sendRecord/goAdd.do");
		})


		
		//加载数据
		function loadRecordNews(type){
			var field = new Object();
			if(type == 'drafts'){
				field.state = 1
			}else if(type == 'history'){
				field.state = 2
			}

			$.post(basePath + 'sendRecord/loadRecordNews.do', field, function (res) {
				layer.closeAll();
				console.log(res);
				if(res.errcode == 0){
					var list = res.data.list;
					var htmls = '';
					for (var i = 0; i < list.length; i++) {
						var data = list[i];
						var mList = data.mList;

						var mHtml = '';
						for (var j = 0; j < mList.length; j++) {
							if(j == 0){
								mHtml += '<div class="widthImg">'
										+ '<img src="'+basePath+'uploadFiles/uploadImgs/'+mList[j].PATH+'" alt="">'
										+ '<div class="wordTitle"><p>'+mList[j].TITLE+'</p></div>'
										+ '</div>';
							}else{
								mHtml += '<div class="borBottom">'
										+ '<table width="100%">'
										+ '<tbody>'
										+ '<tr>'
										+ '<td style="vertical-align: top"><p class="tarTileWid">'+mList[j].TITLE+'</p></td>'
										+ '<td style="vertical-align: top;text-align: right"><div class="tarImgWid"><img src="'+basePath+'uploadFiles/uploadImgs/'+mList[j].PATH+'" alt=""></div></td>'
										+ '</tr>'
										+ '</tbody>'
										+ '</table>'
										+ '</div>';
							}
						}

						htmls += '<div class="pin" class="record-edit-div1" id='+data.SENDRECORD_ID+'>'
								+ '<div class="box">'
								+ '<div class="rowBoxCenter">'
								+ '<p class="text-p"><span class="font-12 padding-30">保存于: '+data.CREATE_TIME+'</span><span class="font-12 padding-80 record-edit-div1">'+data.STATE_INFO+'</span></p>'
                                //中间
								+ mHtml
								//底部
								+ '<div class="tarWid">'
								+ '<div class="row">'
								+ '<div class="col-md-6 col-xs-6 record-edit-div" data-id="'+data.SENDRECORD_ID+'">'
								+ '<a class="tarHover"><img src="'+basePath+'newStyle/images/bianji.png" alt="" width="15px"><span  class="tarStyle">编辑</span></a>'
								+ '</div>'
								+ '<div class="col-md-6 col-xs-6 text-right record-del-div" data-id="'+data.SENDRECORD_ID+'">'
								+ '<a class="tarHover"><img src="'+basePath+'newStyle/images/shanchu.png" alt="" width="15px"><span  class="tarStyle">删除</span></a>'
								+ '</div>'
								+ '</div>'
								+ '</div>'
								+ '</div>'
								+ '</div>'
								+ '</div>';
					}

					if(htmls == ''){
						htmls = '<div style="width:100%;text-align: center;margin-top:100px;">暂无数据</div>';
					}

					$("#main").html(htmls);

					//绑定点击事件
					//编辑
					$(".record-edit-div").click(function () {
						var sendRecordId = $(this).data("id");
						// var stateInfo = $(this).find($(".record-edit-div2")).html();
						window.open(basePath + "sendRecord/goAdd.do?sendRecordId="+sendRecordId);
					})
					//删除
					$(".record-del-div").click(function () {
						var sendRecordId = $(this).data("id");
						layer.confirm('确定要删除吗?', {
							btn: ['确定','取消'],
							title: '删除',
						}, function(){
							var url = "<%=basePath%>sendRecord/delRecord.do";
							var field = new Object();
							field.sendrecord_id = sendRecordId
							$.post(url, field, function(res){
								message(res.errmsg);
								var id = "#" + sendRecordId;
								$(id).remove();
								waterfall('main','pin',document.documentElement.clientWidth);
							});
						}, function(){
							return ;
						});
					})

					waterfall('main','pin',document.documentElement.clientWidth);
					// var dataInt={'data':[{'src':'1.png'},{'src':'2.png'},{'src':'1.png'},{'src':'1.png'}]};


				}else{
					message(res.errmsg);
				}
			})
		}

		window.onscroll=function(){
			if(checkscrollside()){
				var oParent=document.getElementById('main');// 父级对象
				for(var i=0;i<dataInt.data.length;i++){
					var oPin=document.createElement('div'); //添加 元素节点
					oPin.className='pin';                   //添加 类名 name属性
					oParent.appendChild(oPin);              //添加 子节点
					var oBox=document.createElement('div');
					oBox.className='box';
					oPin.appendChild(oBox);
					var oImg=document.createElement('img');
					oImg.src='./'+dataInt.data[i].src;
					oBox.appendChild(oImg);
				}
				waterfall('main','pin');
			}
		}


		 //parend 父级id pin 元素id
		function waterfall(parent,pin,width){
		    var oParent=document.getElementById(parent);// 父级对象

			var aPin=getClassObj(oParent,pin);// 获取存储块框pin的数组aPin

			var iPinW=aPin[0].offsetWidth;// 一个块框pin的宽
		    var num=Math.floor((width-110)/iPinW);//每行中能容纳的pin个数【窗口宽度除以一个块框宽度】
		    oParent.style.cssText='width:'+iPinW*num+'px;margin:0 auto;';//设置父级居中样式：定宽+自动水平外边距
		    var pinHArr=[];//用于存储每列中的所有块框相加的高度
		    for(var i=0;i<aPin.length;i++){//遍历数组aPin的每个块框元素
		        var pinH=aPin[i].offsetHeight;
		        if(i<num){
		            pinHArr[i]=pinH; //第一行中的num个块框pin 先添加进数组pinHArr
		        }else{
		            var minH=Math.min.apply(null,pinHArr);//数组pinHArr中的最小值minH
		            var minHIndex=getminHIndex(pinHArr,minH);
		            aPin[i].style.position='absolute';//设置绝对位移
		            aPin[i].style.top=minH+'px';
		            aPin[i].style.left=aPin[minHIndex].offsetLeft+'px';
		            //数组 最小高元素的高 + 添加上的aPin[i]块框高
		            pinHArr[minHIndex]+=aPin[i].offsetHeight;//更新添加了块框后的列高
		        }
		    }
		}


		//通过父级和子元素的class类 获取该同类子元素的数组
		function getClassObj(parent,className){
		    var obj=parent.getElementsByTagName('*');//获取 父级的所有子集

			var pinS=[];//创建一个数组 用于收集子元素
		    for (var i=0;i<obj.length;i++) {//遍历子元素、判断类别、压入数组
		        if (obj[i].className==className){
		            pinS.push(obj[i]);
		        }
		    }
		    return pinS;
		}


		//获取 pin高度 最小值的索引index
		function getminHIndex(arr,minH){
		    for(var i in arr){
		        if(arr[i]==minH){
		            return i;
		        }
		    }
		}

		// 滚动监听
		function checkscrollside(){
		    var oParent=document.getElementById('main');
		    var aPin=getClassObj(oParent,'pin');
		    var lastPinH=aPin[aPin.length-1].offsetTop+Math.floor(aPin[aPin.length-1].offsetHeight/2);//创建【触发添加块框函数waterfall()】的高度：
		    // 最后一个块框的距离网页顶部+自身高的一半(实现未滚到底就开始加载)

		    var scrollTop=document.documentElement.scrollTop||document.body.scrollTop;//注意解决兼容性
		    var documentH=document.documentElement.clientHeight;//页面高度
		    return (lastPinH<scrollTop+documentH)?true:false;//到达指定高度后 返回true，触发waterfall()函数
		}
		
		
	</script>
		
		
	</body>
</html>

