<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String webPath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, user-scalable=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-touch-fullscreen" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<meta name="format-detection" content="address=no">
	<meta name="screen-orientation" content="portrait">
	<meta name="x5-orientation" content="portrait">
	<title>${intenetName}</title>
	<link rel="stylesheet" href="<%=basePath%>assets/font/iconfont.css">
    <link rel="stylesheet" href="<%=basePath%>assets/css/animate.min.css">
    <link rel="stylesheet" href="<%=basePath%>assets/css/base.css">
    <script type="text/javascript" src="<%=basePath%>assets/js/zepto.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>assets/js/fastclick.js"></script>
    <link rel="stylesheet" href="<%=basePath%>assets/js/dialog.css">
    <script type="text/javascript" src="<%=basePath%>assets/js/dialog.js"></script>
    <script type="text/javascript" src="<%=basePath%>assets/js/iscroll.js"></script>
    <link rel="stylesheet" href="<%=basePath%>assets/js/swiper-3.2.7.min.css">
    <script type="text/javascript" src="<%=basePath%>assets/js/swiper-3.2.7.jquery.min.js"></script>

    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/index.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/from.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/iconfont.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/swiper.min.css">
    <link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=basePath%>newStyle/weixin/css/lk-layer.css">
</head>
	<style>
		.weui_cell {padding: 13px 10px!important;}
	</style>
<body>
	<div class="bindCard">
        	<input type="hidden" id="STORE_ID" name = "STORE_ID" value="${STORE_ID }">
        	<div class="weui_cells">
               <div class="weui_cell">
                    <div class="weui_cell_hd">所在分店：</div>
                    <div class="weui_cell_bd weui_cell_primary suan" onclick="addstore();">
                    	<c:if test="${STORE_NAME != '' }">
                    		<p style="margin:0">${STORE_NAME}</p>
                    	</c:if>
                    	<c:if test="${STORE_NAME == '' }">
                    		<p style="margin:0">请选择</p>
                    	</c:if>
                    </div>
                </div>
                <div class="weui_cell">
                    <div class="weui_cell_hd">手机号</div>
                    <div class="weui_cell_bd weui_cell_primary"><input class="weui_input" id="phone" value="${phone}" name="phone" type="text" placeholder="请输入手机号" onblur="rule.phone($('#phone'))" data-group-state="false"></div>
                </div>
               <div class="weui_cell" id="getcode">
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" type="text" onblur="rule.custom($(this),'/^[0-9]{6}$/','短信验证码为6位数字');" maxlength="6"
						   id="verificationCode" name="verificationCode" placeholder="请输入验证码" data-group-state="false">
                </div>
                <a class="btn-send" data-url="getcode.do?">获取验证码</a>
            </div>
            </div>
            <div class="weui_cells_title gray">我们不会在任何地方泄漏您的个人信息，仅用于校验会员身份信息。</div>
            <div class="surebtn">
                <div class="btn2" onclick="submit()" id="save">确认更换</div>
            </div>
        <!-- </form> -->
	</div>
	
	<div class="modal" id="bigModal" onclick="modalHide('#bigModal', '')">
        <div class="modal-width position">
            <div class="modal-neirong clearfix">
                <div class="fromWid fromOne">
                    <div class="cityClick">切换城市</div>
                    <div class="nomalText" onclick="change('area','${CITY}','')" id="areaId">${CITY }</div>
                    
                    <!-- 区域列表 -->
                    <c:forEach items="${cityList }" var="var" varStatus="vs">
                    	<c:if test="${CITY == var.CITY }">
                   			<c:forEach items="${var.areaList }" var="aar" varStatus="vs">
                    			<c:if test="${aar.COUNTY == COUNTY }">
		                    		<div class="nomal" onclick="change('area','${var.CITY }','${aar.COUNTY}',this)">${aar.COUNTY }</div>
                    			</c:if>
                    			<c:if test="${aar.COUNTY != COUNTY }">
		                    		<div onclick="change('area','${var.CITY }','${aar.COUNTY}',this)">${aar.COUNTY }</div>
                    			</c:if>
                    		</c:forEach>
                    	</c:if>
                    </c:forEach>
                </div>
                
                <!-- 城市列表 -->
                    <div class="fromWid fromOne cityed" id="cityId">
                        <c:forEach items="${cityList}" var="car" varStatus="vs">
	                    	<div onclick="change('city','${car.CITY}','','')">${car.CITY }</div>
	                	</c:forEach>
                    </div>
                <!-- 门店列表 -->
                <input type="hidden" value="${cityList.size()}" id="cityList-length">
                <div class="fromWid fromTwo" id="storeId">
                	<c:forEach items="${cityList }" var="var" varStatus="vs">
                    	<c:if test="${CITY == var.CITY }">
                    		<c:forEach items="${var.areaList }" var="aar" varStatus="vs">
                    			<c:if test="${COUNTY == aar.COUNTY }">
	                    			<c:forEach items="${aar.storeList }" var="sar" varStatus="vs">
		                    			<div class="clearfix fromLine" onclick="hide(this,'${sar.STORE_ID}')" data-dismiss="modal">
	                    					<c:if test="${STORE_ID == sar.STORE_ID }">
		                    					<p class="fromDianActive" >${sar.STORE_NAME }</p>
		                    					<div class="fromImg">
		                    						<img src="<%=basePath%>newStyle/weixin/images/ture-icon@2x.png" alt="" >
		                    					</div>
	                    					</c:if>
		                    				<c:if test="${STORE_ID != sar.STORE_ID }">
		                    					<p class="fromDian" >${sar.STORE_NAME }</p>
		                    					<div class="fromImg">
		                    						<img src="<%=basePath%>newStyle/weixin/images/false-icon@2x.png" alt="" >
		                    					</div>
		                    				</c:if>
		                    			</div>
	                    			</c:forEach>
                    			</c:if>
                    		</c:forEach>
                    	</c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
    <div class="bodyBj"></div>
    <!-- 模态框区域结束 -->
	<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
	<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/fastclick.js"></script>
	<script src="<%=basePath%>newStyle/weixin/js/swiper.min.js"></script>
	<script src="<%=basePath%>newStyle/weixin/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>newStyle/weixin/js/roomes.js"></script>
	<script src="<%=basePath%>newStyle/layer/mobile/layer.js"></script>
	<script src="<%=basePath%>newStyle/weixin/js/lk-layer.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/mobile.js"></script>
    <script type="text/javascript">
    	$(function() {
    		FastClick.attach(document.body);
  		});
    	$(".bodyBj").hide();
		function addstore(){
			var length=$("#cityList-length").val();
			if(length > 0){
				bodyBj()
				modalShow('#bigModal', '', modalDataInit('test'));
			}else{
				message("暂无可绑门店")
			}
		}
		var STORE_ID = "";
    	var phone = "";
    	var verificationCode = "";
    	function check(){
    		 STORE_ID = $("#STORE_ID").val();
    		 phone = $("#phone").val();
    		 verificationCode = $("#verificationCode").val();
    		 if(STORE_ID == ""){
    			message("请选择分店");
    			return false;
    		}
    		if(phone == ""){
    			message("请输入手机号");
    			return false;
    		}
    		if(verificationCode == ""){
    			message("请输入验证码");
    			return false;
    		}
    		return true;
    	}
    	function submit(){
    		$("#save").attr("onclick"," ");
    		if(check()){
    			$.ajax({
	            	type	: "POST",
	            	url		:'<%=basePath%>intenetmumber/changeCard.do',
	            	data	:{STORE_ID:STORE_ID,phone:phone,verificationCode:verificationCode},
	            	dataType:'json',
	            	beforeSend:beforeSend,
	            	success: function(data) {
	            		layer.closeAll();
	            		$("#save").attr("onclick","submit();");
                		if (data.message == "success"){
                			var html = '<i class="iconfont lk-success-icon">&#xe66a;</i><br>'+data.result;
                    		open_oneBtn(html,'我知道了',function(){
						  		window.location.href = '<%=path%>${backurl}'
					  		})
                		}else if (data.message = "false") {
                    		message(data.result);
                		}
	            	},
	            	error:function(){
	               		layer.closeAll();
	               		message("系统繁忙，请稍后再试");
	               		$("#save").attr("onclick","submit();");
	            	}
	        	});
    		}else{
    			$("#save").attr("onclick","submit();");
    		}
    	}
    	
		function beforeSend(){
			 open_loading("加载中")
		}
    	function change(type,city,area,f){
    		$.ajax({
	            type	: "POST",
	            url		:'<%=basePath%>intenetmumber/change.do',
	            data	:{type:type,city:city,area:area},
	            dataType:'json',
	            success: function(data) {
            		var storeList = data.storeList;
	            	if(type == "city"){
	            		//修改当前城市
	            		$("#areaId").attr("onclick","change('area','"+city+"','',this)");
	            		
	            		//删除原先的地区
	            		$("#areaId").nextAll().remove();
	            		if($("#areaId").nextAll().length == 0){
	            			
		            		//添加该城市的地区
		            		var areaList = data.areaList;
		            		var area = "";
		            		for(var i=0;i<areaList.length;i++){
		            			area += "<div onclick=change('area','"+city+"','"+areaList[i].COUNTY+"',this) class='stop'>"+areaList[i].COUNTY+"</div>";
		            		}
		            		$("#areaId").after(area);
		            		//添加该城市的所有门店
		            		var store = "";
		            		for(var i=0;i<storeList.length;i++){
		            			var storeName = storeList[i].STORE_NAME;
		            			var storeId = storeList[i].STORE_ID;
		            			store += "<div class='clearfix fromLine' onclick=hide(this,'"+storeId+"'); data-dismiss='modal'><p class='fromDian'>"
		            					+storeName+"</p><div class='fromImg'><img src='<%=basePath%>newStyle/weixin/images/false-icon@2x.png' alt='' ></div></div>";
		            			
		            		}
		            		$("#storeId").html(store);
		            		$(".stop").each(function(event){
		            			$(this).click(function(event){
		            				 event.stopPropagation();
		            			})
		            		})
	            		}
	            	}else if(type == "area"){
	            		var store = "";
	            		for(var i=0;i<storeList.length;i++){
	            			var storeName = storeList[i].STORE_NAME;
	            			var storeId = storeList[i].STORE_ID;
	            			store += "<div class='clearfix fromLine' onclick=hide(this,'"+storeId+"'); data-dismiss='modal'><p class='fromDian'>"
	            					+storeName+"</p><div class='fromImg'><img src='<%=basePath%>newStyle/weixin/images/false-icon@2x.png' alt='' ></div></div>";
	            		}
	            		$("#storeId").html(store);
	            		quyuedianji(f);
	            	}
	            },
	            error:function(){
	               message("ajax提交失败");
	               return;
	            }
	        });
    	}
    	
    	function hide(f,storeId){
	    		$(f).children(":first").css("color","#f33a70");
	            var room = $(f).children(":first").text();
	            var nom = $(f).children(":last").children();
	            var sub = $(f).siblings().children("p");
	            var sup = $(f).siblings().children("div").children();
	            nom.attr("src","<%=basePath%>newStyle/weixin/images/ture-icon@2x.png");
	            sub.css("color","#666");
	            sup.attr("src","<%=basePath%>newStyle/weixin/images/false-icon@2x.png");           
	            $(".suan").text(room);
	            $("#STORE_ID").val(storeId);
	            clickFade();            
    			modalHide('#bigModal', '');
    	}
    
    	function quyuedianji(event){
    		 $(event).addClass("nomal");
	         $(event).siblings().removeClass("nomal defaultColor");
	         event.stopPropagation();
    	}
    	
	     $(".fromOne>div").each(function(event){
	        $(this).click(function (event) {
	            $(this).addClass("nomal");
	            $(this).siblings().removeClass("nomal");
	            event.stopPropagation();
	        })
	    })
	
	     
	    $(function (){
	        $(".cityed").hide();
	        $(".cityClick").click(function(event){
	            if(!$(".cityed").is(':visible')){
	                    $(".cityed").show();
	                    $(".cityed").addClass("fadeInLeft   animated");
	                    setTimeout(function(){$(".cityed").removeClass("fadeInLeft   animated");},500);
	            }else{
	                    $(".cityed").addClass("fadeOutLeft   animated");
	                    setTimeout(function(){
	                        $(".cityed").removeClass("fadeOutLeft   animated");
	                        $(".cityed").hide();
	                        },500);
	            }
	            $(".cityed>div").each(function(){
	                $(this).click(function(event){
	                    var cityText = $(this).text();
	                    $(".nomalText").text("全" + cityText);
	                    $(".cityed").addClass("fadeOutLeft   animated");
	                    setTimeout(function(){
	                        $(".cityed").removeClass("fadeOutLeft   animated");
	                        $(".cityed").hide();
	                    },500);
	                    $(".nomalText").addClass("nomal");
	                    $(".nomalText").siblings().removeClass("nomal");
	                    setTimeout(function(){$(".cityed>div").removeClass("nomal");},1000);
	                    event.stopPropagation();
	                })
	            })
	            event.stopPropagation();
	        })
	    })
	
	    function bodyBj(){
	    	if(!$(".bodyBj").is(':visible')){
	            $(".bodyBj").show();
	            $(".bodyBj").addClass("fadeIn   animated");
	            setTimeout(function(){$(".bodyBj").removeClass("fadeIn   animated");},500);
	        }else{
	            clickFade()
	        }
	    }
	    
	    $(".modal-textBor").click(function(){
	        clickFade()
	    })
	    $(".modal").click(function(){
	        clickFade()
	    })
	    var clickFade = function(){
	        $(".bodyBj").addClass("fadeOut   animated");
	        setTimeout(function(){
	            $(".bodyBj").removeClass("fadeOut   animated");
	            $(".bodyBj").hide();
	        },500);
	    }
    </script>
</body>

</html>
