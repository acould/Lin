<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
					
					<form action="sysanget/${msg }.do" name="Form" id="Form" method="post">
						<input type="hidden" name="ANGET_ID" id="ANGET_ID" value="${pd.ANGET_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">代理商名称:</td>
								<td><input type="text" name="ANGET_NAME" id="ANGET_NAME" value="${pd.ANGET_NAME}" maxlength="40" placeholder="这里输入代理商名称" title="代理商名称" style="width:98%;"/></td>
							</tr>
							<td style="width:75px;text-align: right;padding-top: 13px;">省:</td>
							<td>
							<select name="PROVINCE" id="PROVINCE"  onchange="getcity()" style="width:98%;">
								  <option <c:if test="${pd.PROVINCE =='-1'}">selected="selected"</c:if>>请选择</option>
							      <option value="1" <c:if test="${pd.PROVINCE =='1'}">selected="selected"</c:if>>北京市 </option>
								 <option value="2" <c:if test="${pd.PROVINCE =='2'}">selected="selected"</c:if>>广东省 </option>
								<option value="3" <c:if test="${pd.PROVINCE =='3'}">selected="selected"</c:if>>上海市 </option>
								<option value="4" <c:if test="${pd.PROVINCE =='4'}">selected="selected"</c:if>>天津市 </option>
								<option value="5" <c:if test="${pd.PROVINCE =='5'}">selected="selected"</c:if>>重庆市 </option>
								<option value="6" <c:if test="${pd.PROVINCE =='6'}">selected="selected"</c:if>>辽宁省</option>
								<option value="7" <c:if test="${pd.PROVINCE =='7'}">selected="selected"</c:if>>江苏省</option>
								<option value="8" <c:if test="${pd.PROVINCE =='8'}">selected="selected"</c:if>>湖北省</option>
								<option value="9" <c:if test="${pd.PROVINCE =='9'}">selected="selected"</c:if>>四川省</option>
								<option value="10" <c:if test="${pd.PROVINCE =='10'}">selected="selected"</c:if>>陕西省</option>
								<option value="11" <c:if test="${pd.PROVINCE =='11'}">selected="selected"</c:if>>河北省</option>
								<option value="12" <c:if test="${pd.PROVINCE =='12'}">selected="selected"</c:if>>山西省</option>
								<option value="13" <c:if test="${pd.PROVINCE =='13'}">selected="selected"</c:if>>河南省</option>
								<option value="14" <c:if test="${pd.PROVINCE =='14'}">selected="selected"</c:if>>吉林省</option>
								<option value="15" <c:if test="${pd.PROVINCE =='15'}">selected="selected"</c:if>>黑龙江</option>
								<option value="16" <c:if test="${pd.PROVINCE =='16'}">selected="selected"</c:if>>内蒙古</option>
								<option value="17" <c:if test="${pd.PROVINCE =='17'}">selected="selected"</c:if>>山东省</option>
								<option value="18" <c:if test="${pd.PROVINCE =='18'}">selected="selected"</c:if>>安徽省</option>
								<option value="19" <c:if test="${pd.PROVINCE =='19'}">selected="selected"</c:if>>浙江省</option>
								<option value="20" <c:if test="${pd.PROVINCE =='20'}">selected="selected"</c:if>>福建省</option>
								<option value="21" <c:if test="${pd.PROVINCE =='21'}">selected="selected"</c:if>>湖南省</option>
								<option value="22" <c:if test="${pd.PROVINCE =='22'}">selected="selected"</c:if>>广西省</option>
								<option value="23" <c:if test="${pd.PROVINCE =='23'}">selected="selected"</c:if>>江西省</option>
								<option value="24" <c:if test="${pd.PROVINCE =='24'}">selected="selected"</c:if>>贵州省</option>
								<option value="25" <c:if test="${pd.PROVINCE =='25'}">selected="selected"</c:if>>云南省</option>
								<option value="26" <c:if test="${pd.PROVINCE =='26'}">selected="selected"</c:if>>西藏</option>
								<option value="27" <c:if test="${pd.PROVINCE =='27'}">selected="selected"</c:if>>海南省</option>
								<option value="28" <c:if test="${pd.PROVINCE =='28'}">selected="selected"</c:if>>甘肃省</option>
								<option value="29" <c:if test="${pd.PROVINCE =='29'}">selected="selected"</c:if>>宁夏</option>
								<option value="30" <c:if test="${pd.PROVINCE =='30'}">selected="selected"</c:if>>青海省</option>
								<option value="31" <c:if test="${pd.PROVINCE =='31'}">selected="selected"</c:if>>新疆</option>
								<option value="32" <c:if test="${pd.PROVINCE =='32'}">selected="selected"</c:if>>香港</option>
								<option value="33" <c:if test="${pd.PROVINCE =='33'}">selected="selected"</c:if>>澳门</option>
								<option value="34" <c:if test="${pd.PROVINCE =='34'}">selected="selected"</c:if>>台湾</option>
								<option value="35" <c:if test="${pd.PROVINCE =='35'}">selected="selected"</c:if>>海外</option> 
						   </select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">城市:</td>
								<td>
								<select id="CITY" name="CITY" style="width:98%;" >
					    	<option value="">请选择</option>
					    	<option  if($pd.CITY}) selected="selected" value="${pd.CITY}" end>	
					    	<c:if test="${pd.CITY !='null' }">${pd.CITY}</c:if>		
					    	</select>
					    	</td>
					    	</tr>				
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">联系电话:</td>
								<td><input type="text" name="PHONE" id="PHONE" value="${pd.PHONE}" maxlength="16" placeholder="这里输入联系电话" title="联系电话" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">地址:</td>
								<td><input type="text" name="ADDRESS" id="ADDRESS" value="${pd.ADDRESS}" maxlength="255" placeholder="这里输入地址" title="地址" style="width:98%;"/></td>
							</tr>
							<c:if test="${msg =='save' }">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">登录帐号:</td>
								<td><input  type="text" name="USER_NAME" id="loginname" value="${pd.USER_NAME}" maxlength="100"  onblur="hasU('${pd.USER_NAME }')" placeholder="这里输入登录账号" title="账号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">登录密码:</td>
								<td><input  type="password" name="PASSWORD" id="password" maxlength="100"  placeholder="这里输入密码" title="密码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:79px;text-align: right;padding-top: 13px;">确认密码:</td>
								<td><input type="password" name="chkpwd" id="chkpwd"  maxlength="32" placeholder="确认密码" title="确认密码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">邮箱:</td>
								<td><input type="text" name="EMAIL" id="EMAIL" value="${pd.EMAIL}" maxlength="100" placeholder="这里输入邮箱" onblur="hasE('${pd.USER_NAME }')" title="邮箱" style="width:98%;"/></td>
							</tr>
							</c:if>
							
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						
					</form>
	
					<div id="zhongxin2" class="center" style="display:none"><img src="static/images/jzx.gif" style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#ANGET_NAME").val()==""){
				$("#ANGET_NAME").tips({
					side:3,
		            msg:'请输入代理商名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ANGET_NAME").focus();
			return false;
			}
			if($("#PROVICE").val()==""){
				$("#PROVICE").tips({
					side:3,
		            msg:'请输入省份',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PROVICE").focus();
			return false;
			}
			if($("#CITY").val()==""){
				$("#CITY").tips({
					side:3,
		            msg:'请输入城市',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CITY").focus();
			return false;
			}
			if($("#AREA").val()==""){
				$("#AREA").tips({
					side:3,
		            msg:'请输入区域',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#AREA").focus();
			return false;
			}
			if($("#PHONE").val()==""){
				$("#PHONE").tips({
					side:3,
		            msg:'请输入联系电话',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PHONE").focus();
			return false;
			}
			if($("#STATE").val()==""){
				$("#STATE").tips({
					side:3,
		            msg:'请输入状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATE").focus();
			return false;
			}
			if($("#ADDRESS").val()==""){
				$("#ADDRESS").tips({
					side:3,
		            msg:'请输入地址',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ADDRESS").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		function ismail(mail){
			return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
			}
		
		//判断用户名是否存在
		function hasU(){
			var USERNAME = $.trim($("#loginname").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>user/hasU.do',
		    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" == data.result){
					 }else{
						$("#loginname").css("background-color","#D16E6C");
						setTimeout("$('#loginname').val('此用户名已存在!')",500);
					 }
				}
			});
		}
		
		//判断邮箱是否存在
		function hasE(USERNAME){
			var EMAIL = $.trim($("#EMAIL").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>user/hasE.do',
		    	data: {EMAIL:EMAIL,USERNAME:USERNAME,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" != data.result){
						 $("#EMAIL").tips({
								side:3,
					            msg:'邮箱 '+EMAIL+' 已存在',
					            bg:'#AE81FF',
					            time:3
					        });
						 $("#EMAIL").val('');
					 }
				}
			});
		}
		var city=[
		          ["东城区","西城区", "崇文区", "宣武区","朝阳区","海淀区","丰台区","石景山区","房山区","通州区","顺义区","昌平区",
		          "大兴区","怀柔区","平谷区","门头沟区","密云区","延庆区","其他"],
		          ["广州","深圳", "珠海","汕头","韶关","佛山","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","河源","阳江","清远",
		          "东莞","中山","潮州","揭阳","云浮","其他"],
		          ["黄浦区","卢湾区", "徐汇区","长宁区","静安区","普陀区","闸北区","虹口区","杨浦区","宝山区","闵行区","嘉定区","松江区",
		          "金山区","青浦区","南汇区","奉贤区","浦东新区","崇明区","其他"],
		          ["和平区","河东区", "河西区","南开区","河北区","红桥区","塘沽区","汉沽区","大港区","东丽区","西青区","北辰区","津南区",
		          "武清区","宝坻区","静海县","宁河县","蓟县","其他"],
		          ["渝中区","大渡口区", "江北区","南岸区","北碚区","渝北区","巴南区","长寿区","双桥区","沙坪坝区","万盛区","万州区","涪陵区",
		          "黔江区","永川区","合川区","江津区","九龙坡区","南川区","綦江县","潼南区","荣昌区","璧山区","大足区","铜梁县",
		          "梁平县","开县","忠县","城口县","垫江区","武隆县","丰都县","奉节县","云阳县","巫溪县","巫山县","其他"],
		          ["沈阳","大连","鞍山","抚顺","本溪","丹东","锦州","营口","阜新","辽阳","盘锦","铁岭","朝阳","葫芦岛","其他"],
		          ["南京","苏州","无锡","常州","镇江","南通","泰州","扬州","盐城","连云港","徐州","淮安","宿州","其他"],
		          ["武汉","黄石","十堰","荆州","宜昌","襄樊","鄂州","荆门","孝感","黄冈","咸宁","随州","仙桃","天门","潜江","神农架","其他"],
		          ["成都","自贡","攀枝花","泸州","德阳","绵阳","广元","遂宁","内江","乐山","南充","眉山","宜宾","广安","达州","雅安","巴中","资阳","其他"],
		          ["西安","铜川","宝鸡","咸阳","渭南","延安","汉中","榆林","安康","商洛","其他"],
		          ["石家庄","唐山","秦皇岛","邯郸","邢台","保定","张家口","承德","沧州","廊坊","衡水","其他"],
		          ["太原","大同","阳泉","长治","晋城","朔州","晋中","运城","忻州","临汾","吕梁","其他"],
		          ["郑州","开封","洛阳","平顶山","安阳","鹤壁","新乡","焦作","濮阳","许昌","漯河","三门峡","南阳","商丘","信阳","周口","驻马店","焦作","其他"],
		          ["吉林","四平","辽源","通化","白山","松原","白城","延边朝鲜自治区","其他"],
		          ["哈尔滨","齐齐哈尔","鹤岗","双鸭山","鸡西","大庆","伊春","牡丹江","佳木斯","七台河","黑河","绥远","大兴安岭地区","其他"],
		          ["呼和浩特","包头","乌海","赤峰","通辽","鄂尔多斯","呼伦贝尔","巴彦淖尔","乌兰察布","锡林郭勒盟","兴安盟","阿拉善盟"],
		          ["济南","青岛","淄博","枣庄","东营","烟台","潍坊","济宁","泰安","威海","日照","莱芜","临沂","德州","聊城","滨州","菏泽","其他"],
		          ["合肥","芜湖","蚌埠","淮南","马鞍山","淮北","铜陵","安庆","黄山","滁州","阜阳","宿州","巢湖","六安","亳州","池州","宣城"],
		          ["杭州","宁波","温州","嘉兴","湖州","绍兴","金华","衢州","舟山","台州","丽水","其他"],
		          ["福州","厦门","莆田","三明","泉州","漳州","南平","龙岩","宁德","其他"],
		          ["长沙","株洲","湘潭","衡阳","邵阳","岳阳","常德","张家界","益阳","滨州","永州","怀化","娄底","其他"],
		          ["南宁","柳州","桂林","梧州","北海","防城港","钦州","贵港","玉林","百色","贺州","河池","来宾","崇左","其他"],
		          ["南昌","景德镇","萍乡","九江","新余","鹰潭","赣州","吉安","宜春","抚州","上饶","其他"],
		          ["贵阳","六盘水","遵义","安顺","铜仁","毕节","其他"],
		          ["昆明","曲靖","玉溪","保山","邵通","丽江","普洱","临沧","其他"],
		          ["拉萨","那曲地区","昌都地区","林芝地区","山南区","阿里区","日喀则","其他"],
		          ["海口","三亚","五指山","琼海","儋州","文昌","万宁","东方","澄迈县","定安县","屯昌县","临高县","其他"],
		          ["兰州","嘉峪关","金昌","白银","天水","武威","酒泉","张掖","庆阳","平凉","定西","陇南","临夏","甘南","其他"],
		          ["银川","石嘴山","吴忠","固原","中卫","其他"],
		          ["西宁","海东地区","海北藏族自治区","海南藏族自治区","黄南藏族自治区","果洛藏族自治区","玉树藏族自治州","还西藏族自治区","其他"],
		          ["乌鲁木齐","克拉玛依","吐鲁番地区","哈密地区","和田地区","阿克苏地区","喀什地区","克孜勒苏柯尔克孜","巴音郭楞蒙古自治区",
		          "昌吉回族自治州","博尔塔拉蒙古自治区","石河子","阿拉尔","图木舒克","五家渠","伊犁哈萨克自治区","其他"],
		          ["中西区","湾仔区","东区","南区","深水埗区","油尖旺区","九龙城区","黄大仙区","观塘区","北区","大埔区","沙田区","西贡区",
		          "元朗区","屯门区","荃湾区","葵青区","离岛区","其他"],
		          ["花地玛塘区","圣安多尼塘区","大堂区","望德堂区","风顺堂区","嘉模堂区","圣方济各堂区","路凼","其他"],
		          ["台北市","高雄市","台北县","桃园县","新竹县","苗栗县","台中县","彰化县","南投县","云林县","嘉义县","台南县","高雄县",
		          "屏东县","宜兰县","花莲县","台东县","澎湖县","基隆市","新竹市","台中市","嘉义市","台南市","其他"],
		          ["海外","其他"],
		          ["其他"]];
		      
		      function getcity(){
		      var sltProvince = document.getElementById("PROVINCE") ;        
		      var sltCity = document.getElementById("CITY") ;      
		          
		      var provinceCity = city[sltProvince.selectedIndex - 1];         
		      sltCity.length = 1 ;
		         
		      for ( i = 0 ; i < provinceCity.length ; i++ ) {
		             sltCity[i + 1] = new Option(provinceCity[i], provinceCity[i]) ;
		      }
		      }
		</script>
</body>
</html>