function openCard(title,notice,description,content,id,begin,end,sheng,you,type,store,cardSum_time){
	var time ='';
	if(type == "DATE_TYPE_FIX_TIME_RANGE"){//固定日期
		time='<p class="weic-card-time"><font color="#333">有效期：   </font>'+begin+"至"+end+'</p>'
	}else if(type == "DATE_TYPE_FIX_TERM"){
		var tian = sheng;
		if(sheng == "0"){
			tian = "当";
		}
		time='<p class="weic-card-time"><font color="#333">有效期：   </font>领取后'+tian+"天生效，领取后"+you+'天内有效</p>';
	}
	var html ='<div class="open-card-box"><div class="card-img"></div>'+
			'<p class="weic-card-title">'+title+'</p>'+time+
			'<p class="weic-card-time"><font color="#333">适用门店：</font>'+store+'</p>'+
			'<p class="weic-card-time"><font color="#333">优惠说明：</font>'+description+'</p>'+
			'<p class="weic-card-time"><font color="#333">使用提醒：</font>'+notice+'</p>'+
			'<p class="weic-card-time"><font color="red">领取条件：下机后满足连续上网'+cardSum_time+'小时的条件自动推送 </font></p>'+
			'<div style="padding: 2rem 0 0.5rem 0">'+
			'<div class="weic-recharge-btn weic-btn-gradientOrange" style="width: 75%;" onclick="layer.closeAll()">确定</div>'+
		'</div>'+
	'</div>'
	layer.open({
          content: html
          ,className: 'signIn_box'
          ,style:"background: none;box-shadow: none;width:100%"
          ,end:function(){

          }
      });	
};