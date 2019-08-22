$("#pay-userbox").width($(window).width())
	$(window).resize(function() {
  		$("#pay-userbox").width($(window).width())
	});

$("#agreement-icon").unbind("click");
$("#agreement-icon").click(function(){
	$(this).toggleClass("agreement-icon-hide");
})

function active(i){
	var id = "#setMeal-det"+i
	$(".weic-col-33").removeClass("setMeal-active");
	$(id).addClass("setMeal-active");
}

//跳转充值详情页
var flag = true;
function goPay_details(url){
    var internetrule_id = $(".setMeal-active :input[name='internetrule_id']").val();
    var rincipal_balance = $(".setMeal-active :input[name='rincipal_balance']").val();
    var reward_balance = $(".setMeal-active :input[name='reward_balance']").val();
    var agr = $(".setMeal-active").html();
    var agr_icon = $(".agreement-icon-hide").html();
    var store_id = $("#store_id").val();
    var internet_id = $("#internet_id").val();
    var store_name= $("#store_name").val();
    var carded= $("#carded").val();
    var cardid= $("#cardid").val();
    if(agr == undefined){
    	message("请选择充值套餐")
    	return false;
    }
    if(agr_icon != undefined){
    	message("请阅读并同意协议")
    	return false;
    }
    if(flag){
    	flag = false;
    	$.ajax({
    		type: "POST",
    		url: url,
    		data: {rincipal_balance:rincipal_balance,reward_balance:reward_balance,store_id:store_id,
				internetrule_id:internetrule_id,internet_id:internet_id,store_name:store_name,carded:carded,cardid:cardid},
    		async:false,
    		success: function (data) {
    			if(data.result){
    				window.location.href = data.url;
    			}else{
    				message(data.message);
    			    flag = true;
    			}
    		},
    		error: function () {
    			message("系统繁忙,请稍再试")
    			flag = true;
    		}
    	})
    }
}

//加载套餐数据
function agreement(url){
	 var store_id = $("#store_id").val();
	 $.ajax({
       type: "POST",
       url: url,
       beforeSend:open_loading("加载中"),
       data: {storeid:store_id},
       success: function (data){
       	  layer.closeAll();
          if(data.result = "true"){
               var indexList = data.indexList;
               var divs = "";
               if(indexList == '' || indexList == undefined){
               		$("#pay_title").hide();
        			divs +='<div class="weic-noboby" style="top: 3rem;">'+
        						'<div class="weic-nobody-img"></div>'+
        						'<p class="weic-nobody-text">老板太懒了，没设置充值套餐</p>'+
   							'</div>'
    			}else{
    				 $("#pay_title").show();
    				 for(var i =0;i<indexList.length;i++){
            	  		var rincipal_balance = indexList[i].recharge_money;
            	 	 	var reward_balance = indexList[i].reward_money;
            	  		var label_name = indexList[i].label_name;
            	  		var label_color = indexList[i].label_color;
            	  		var internetrule_id = indexList[i].id;
            	  		var lableHtml = "";
            	  		if(label_name != ""){
            	  	 		lableHtml = '<span class="weic-pay-lable '+label_color+'">'+label_name+'</span>'
            	  		}else{
            	  			lableHtml = "";
            	  		}
            	 		 divs += '<div class="weic-col-33 " id="setMeal-det'+i+'" onclick="active('+i+')">'+
            	 		 			   '<div class="setMeal-det">'+
											'<h3>￥<span>'+rincipal_balance+'</span></h3>'+
											'<p>奖励<span>'+reward_balance+'</span>元</p>'+
											'<span class="active-icon"></span>'+
								 			lableHtml+
											'<input type="hidden" value="'+internetrule_id+'" name="internetrule_id">'+
											'<input type="hidden" value="'+rincipal_balance+'" name="rincipal_balance">'+
											'<input type="hidden" value="'+reward_balance+'" name="reward_balance">'+
									    '</div>'+
								'</div>'
               		}
    			}
                
               $("#agreementList").html(divs);
          }else{
        	  alert(data.result);
        	  message("暂无充值套餐")
          }
       },
       error: function () {
       layer.closeAll();
            message("系统繁忙,请稍再试")
       }
    })
}


$.ajaxSetup( {  
	    //设置ajax请求结束后的执行动作  
	    complete : function(XMLHttpRequest, textStatus) {
	        // 通过XMLHttpRequest取得响应头，REDIRECT  
	        var redirect = XMLHttpRequest.getResponseHeader("REDIRECT");//若HEADER中含有REDIRECT说明后端想重定向
	        if (redirect == "REDIRECT") {
	            var win = window;  
	            while (win != win.top){
	                win = win.top;
	            }
	          //将后端重定向的地址取出来,使用win.location.href去实现重定向的要求
	          win.location.href= XMLHttpRequest.getResponseHeader("CONTEXTPATH");  
	        }
	    }  
	});
//支付返回结果
function payResult(url){
    open_loading("加载中")
    $("#pay-btn").attr("onclick", "");
	var id = $("#id").val();
    $.ajax({
       type: "GET",
       url: url,
       data: {id:id},
       success: function (data) {
           layer.closeAll();
           $("#pay-btn").attr("onclick", "payResult(" + url + ")");
//    	   if(data.url=='userNull'){
//    		 window.location.href = data.url;
//    	   }else{
//    		   window.location.href='<%=basePath%>indexMember/'+data.url;
//    	   }

           if(data.errcode == 0 && data.data.third_payment == "2"){
               window.location.href = data.data.url;
           }else if(data.data.third_payment == "2"){
               message(data.errmsg);
           }
       },
       error:function () {
           message("系统繁忙，请稍后再试");
            layer.closeAll();
       }
    })
}
//回跳判断
function init(url,urls,id,mxurl){
	// 初始化内容       
	var status = $("#status").val();
	var errMsg = $("#errMsg").val();
	if(status == "WAIT_BUYER_PAY"){
		message('支付已取消');
	}else if(errMsg != ""){
	 	var html = '<div class="payResult-defeated"></div>'+
			'<p style="color: #666;font-size:12px;">失败原因：'+errMsg+'</p>'
		open_oneBtns(html,'请重新下单',"lk-pay-defeated",function(){
			payResult(urls);
		})
	//支付成功
	}else if(status == "TRADE_SUCCESS"){
		var html = '<div class="payResult-successful"></div>'
		open_oneBtns(html,'返回首页',"lk-pay-successful",function(){
			window.location.href=mxurl;
		})
	}
}

