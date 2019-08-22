<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/index/top.jsp"%>
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="<%=basePath%>newStyle/css/custom.css">
  </head>
<style type="text/css">
    body {color: #000;padding: 12px;background: #f3f3f3;}
    .lk-pages-main {background: #fff;height: calc(100vh - 24px);min-height: 650px;padding: 0;}
    .layui-form-item {margin-bottom: 25px;}
    .layui-form-switch i {top:4px;}
    .layui-form-switch em {font-size: 14px;}
    .layui-form-switch {height: 26px;line-height: 26px;margin-top: 4px;}
    .labletx label { width: 100px!important;margin-bottom: 0;color: black;font-weight: 600!important;}
    #adrss{overflow: hidden;text-overflow: ellipsis;display: -webkit-box;-webkit-line-clamp: 2;-webkit-box-orient: vertical;}
    .closeCard { padding-left: 20px;color: #1883E2;position: absolute;bottom: 0;cursor: pointer;}
    .closeCard:hover {color: red;}
    .cardBox {width: 320px;box-shadow: 0px 4px 16px #f6f8f9;background: #fff;}
    .cardBox h1{font-size: 16px; font-weight: 600; margin-bottom: 15px;padding: 14px 15px;border-bottom: 1px solid #eee;}
    .layui-form-checkbox[lay-skin=primary] {margin: 5px}
</style>
<body>
    <div class="" id="main-container">
        <div class="lk-pages-main">
            <div class="lk-item-title font_size15">在线申请会员</div>
            <div class="lk-card-flud">
                <form class="layui-form">
                    <div class="layui-form-item labletx">
                        <div class="layui-inline lk-relative ">
                            <div class="state_lable" id="sdsd">劲爆</div>
                            <label class="layui-form-label">0元办会员</label>
                            <div class="layui-input-block">
                                <input type="checkbox" lay-skin="switch" lay-text="关闭|开启" ${pd.typeo=='1'?"checked":""} lay-filter="switchTest" id="type0">
                            </div>
                        </div>
                    </div>
                    <div id="zero"  data-id="zero" >
                        <div class="layui-row layui-col-space30" style="max-width: 1200px;padding: 20px;">
                            <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                                <div class="apply_msg">
                                    <p class="apply_title layui-form-item"><font color="red">*</font>设置基本信息：</p>
                                    <div class="layui-form-item">参与此活动的门店：
                                        <span class="choose_store" onclick="chooseStore()">选择</span>
                                    </div>
                                    <div class="layui-form-item" id="storeF">
									<c:forEach items="${addedStoreList}" var="var" varStatus="vs">
										<span class='show_storeName'>${var.store_name}</span>
									</c:forEach>
                                    </div>
                                    <div class="layui-form-item lable-beyond" style="margin-top: 20px;">
                                        <label class="layui-form-label">编辑广告词：</label>
                                        <div class="layui-input-block">
                                            <input type="text" id="member0" placeholder="示例：0元办会员再送一张10元上网券，超值!" class="layui-input" maxlength="18" onkeyup=bannerText(this,"zero") value="${pd.advertisement}">
                                            <span><font color="red" id="num0">${fn:length(pd.advertisement)==0? "18":18-fn:length(pd.advertisement)}</font>/18</span>
                                        </div>
                                    </div>
                                    <div class="layui-form-item">
                                        <span class="choose_store" style="margin: 0px;" onclick="xcard('<%=basePath%>','0')">送券</span><span class="lk-textTip" style="font-size: 14px">申请会员成功自动推送“申请会员福利券”</span>
                                    </div>
                                    <div class="layui-form-item lk-relative">
                                        <div id="jsCard0">
										<c:if test="${!empty pd.couponid}">
											<div class='cardBox'>
												 <h1  id="card-title" style="font-size: 16px;border-bottom: 1px solid #eee;font-weight: 600;padding: 14px 15px" class="title <c:if test="${pdInternet.COLOR == 'Color010' }">Color010</c:if><c:if test="${pdInternet.COLOR == 'Color020' }">Color020</c:if><c:if test="${pdInternet.COLOR == 'Color030' }">Color030</c:if><c:if test="${pdInternet.COLOR == 'Color040' }">Color040</c:if><c:if test="${pdInternet.COLOR == 'Color050' }">Color050</c:if><c:if test="${pdInternet.COLOR == 'Color060' }">Color060</c:if><c:if test="${pdInternet.COLOR == 'Color070' }">Color070</c:if><c:if test="${pdInternet.COLOR == 'Color080' }">Color080</c:if><c:if test="${pdInternet.COLOR == 'Color090' }">Color090</c:if><c:if test="${pdInternet.COLOR == 'Color100' }">Color100</c:if>">${pdInternet.SUB_TITLE }</h1>
												 <p id="card-time">有效期：${pdInternet.AVAILABLE_TIME }</p>
												 <p id="adrss" title="${card.STORE_NAME}">使用门店：${pdInternet.STORE_NAME }</p>
												 <p style="padding-bottom: 20px" id="xiangqing">使用说明：${pdInternet.DESCRIPTION }</p>
											</div>
											<span class='closeCard' onclick='closeCard("0")'>删除</span>
										</c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="layui-col-xs6 layui-col-sm6 layui-col-md6">
                                <div class="apply_banner">
                                    <p class="apply_title layui-form-item">首页广告展示：</p>
                                    <div class="layui-form-item">
                                        <div class="apply_img">
                                            <h1 id="bannerText0">${pd.advertisement==''? "0元办会员再送一张10元上网券，超值!":pd.advertisement}</h1>
                                        </div>
                                        <p class="lk-textTip" style="padding-left: 0;">会员端活动广告示例：暂不支持换底图</p>
                                    </div>
                                    <div class="layui-form-item">
                                        <span class="layui-btn layui-btn-normal"  onclick="tj('<%=basePath%>','0')" id="saveMemberShip">保存设置</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
	<form class="layui-form" id="store_form0" style="display:none" target="_blank" method="post">
		<div class="lk-chooseStore-box" style="padding-top: 0;">
			<div class="choosable-box" id="openBox0">
				<h2>可选门店（已开通计费系统）</h2>
				<div class="choose-store" id="openStoreList0">
					<input type="checkbox"  title="全选" lay-skin="primary" checked lay-filter="tufure" id="allChecked0" >
				</div>
			</div>
			<%--<div class="choosable-box" id="notOpenBox0">--%>
				<%--<h2>不可选门店</h2>--%>
				<%--<div class="choose-store" id="notOpenStoreList0">--%>
					<%--<p class="openUp-action">以上门店因未开通计费系统，无法实现在线申请会员功能--%>
						<%--<span onclick="siMenu('z234','lm154','消息通知','newerGuide/list.do')">去开通</span>--%>
					<%--</p> --%>
				<%--</div>--%>
			<%--</div>--%>
			<div class="choosable-box" id="cardBox0">
				<h2>无会员级别门店</h2>
				<div class="choose-store" id="cardStoreList0">
					<%--<p class="openUp-action">以上门店没有设置会员级别，无法实现在线申请会员功能
					    <span onclick="siMenu('z90','lm115','门店管理','storeShow/list.do')">去设置</span>
					</p>--%>
				</div>
			</div>
		</div>
	</form>
		<input type="hidden" name="memberId" id="memberId" value="${pd.memberId}">
		<input type="hidden" name="nameList" id="nameList">
		<input type="hidden" name="idList0" id="idList0" value="${idList}">
		<input type="hidden" name="idList" id="idList" value="${idList}">
		<input type="hidden" name="banner" id="banner" value="${pd.advertisement}">
		<input type="hidden" name="banner0" id="banner0" value="${pd.advertisement}">
		<input type="hidden" name="storefalg" id="storefalg" >
		<input type="hidden" name="storefalg0" id="storefalg0">
		<input type="hidden" name="type00" id="type00" value="${pd.typeo}">
		<input type="hidden" name="type11" id="type11" value="${pd.typet}">
		<input type="hidden" name="couponid0" id="couponid0" value="${pd.couponid}">
		<input type="hidden" name="couponid" id="couponid" value="${pd.couponid}">
</body>
<%@ include file="../../system/index/foot.jsp"%>
<!-- 业务JS -->
<script src="<%=basePath%>newStyle/weixin/jquery-weui/js/jquery-2.1.4.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/myjs/head.js"></script>
<script type="text/javascript">
    var basePath = '<%=basePath%>';

    layui.use(['form', 'layer','jquery'], function(){
        var form = layui.form
            ,layer = layui.layer
            ,$ = layui.jquery;
        //监听指定开关
        form.on('switch(switchTest)', function(data){
            if(data.elem.checked){
                var id = $(data.elem).attr("id");
                if(id == "type0"){
                    layer.msg("已开启0元办会员",{icon:1,offset: '50px'})
                    $("#type1").removeAttr("checked");
					$("#type00").val("1");
					$("#type11").val("2");
                    var member0=$("#member0");
                    bannerText(member0,"zero");
                }else {
                    layer.msg("已开启充值办会员",{icon:1,offset: '50px'})
                    $("#type0").removeAttr("checked");
					$("#type00").val("2");
					$("#type11").val("1");
               
                    var member=$("#member");
                    bannerText(member,"no_zero");

                }
                form.render();
            }else {
				layer.msg("已关闭办会员",{icon:2,offset: '50px'})
			
				$("#type00").val("2");
				$("#type11").val("2");
            }
			//修改 开启状态
			if($("#type00").val()!=""){
				$.ajax({
					type: "POST",
					url: '<%=basePath%>membership/editType.do',
					data : {typeo:$("#type00").val(),typet:$("#type11").val(),memberId:$("#memberId").val()},
					dataType : 'json',
					async:false,
					success : function(data) {
						
					},
					error:function(){
						message("系统繁忙，请稍后再试！");
					}
				})
			}
				
        });
		
	//全选
	form.on('checkbox(tufure0)', function (data) {
        var a = data.elem.checked;
        if (a == true) {
            $(".storeId").prop("checked", true);
            form.render('checkbox');
        }else {
            $(".storeId").prop("checked", false);
            form.render('checkbox');
        }
    });

    //有一个未选中全选取消选中
    form.on('checkbox(store0)', function (data) {
        var item = $(".storeId");
        for (var i = 0; i < item.length; i++) {
            if (item[i].checked == false) {
                $("#allChecked0").prop("checked", false);
                form.render('checkbox');
                break;
            }
        }
        //如果都勾选了  勾上全选
        var  all=item.length;
        for (var i = 0; i < item.length; i++) {
            if (item[i].checked == true) {
                all--;
            }
        }
        if(all==0){
            $("#allChecked0").prop("checked", true);
            form.render('checkbox');
        }
    });
    })


    var regs = /^[0-9]\d*$/;
    var reg = /^[1-9]\d*$/;
    //金额监听
    function textKeyup(o) {
        var othis = $(o),type = othis.data("type"),val = othis.val();
        if(type == "recharge_money"){
            if(!reg.test(val)){
                error("请输入大于0的整数金额");
                othis.val("")
            }
        }else {
            if(!regs.test(val)){
                error("请输入不于0的整数金额");
                othis.val("")
            }
        }
    }

    //删除卡券
    function closeCard(falg) {
        layer.confirm('确定要删除该卡券？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $("#jsCard"+falg).html("");
			$("#couponid"+falg).val("");
            layer.closeAll()
        }, function(){
            layer.closeAll()
        });
    }

    // 广告文字监听
    function bannerText(o,type) {
        var othis = $(o),val = othis.val(),le = val.length;
        var remainLenght =  18 - le;
        
        if(val == ""){
            if(type == "zero"){
                $("#bannerText0").html("0元办会员再送一张10元上网券，超值!")
            }else {
                $("#bannerText").html("充30办会员送30再送一张10元上网券!")
            }
        }else {
            $("#bannerText0").html(val);
            $("#bannerText").html(val);
			if(type == "zero"){
                $("#banner0").val(val);
				$("#num0").html(remainLenght);
            }else {
                $("#banner").val(val);
				$("#num").html(remainLenght);
            }
        }
    }



    function chooseStore() {
        var field = new Object();
        $.post('<%=basePath%>membership/getStoreList.do', field, function (res) {
            if(res.errcode == 0){
                var list = res.data.list;
                var pdMenu = res.data.pdMenu;
                var addedStoreList = res.data.addedStoreList;

                var htmls = '';
                var open_htmls = '';//已加v
                var no_open_htmls = '';//已加v 未设置会员级别
                for (var i = 0; i < list.length; i++) {
                    var store = list[i];
                    if(store.member_level != '' && store.member_level != 'undefined'){
                        open_htmls += '<input class="storeId" type="checkbox" title="'+store.STORE_NAME+'" lay-skin="primary" '+
                            ' lay-filter="store0" name="open_store_id0" value="'+store.STORE_ID+'">'
                    }else{
                        no_open_htmls += '<input type="checkbox" title="' + store.STORE_NAME + '" lay-skin="primary" disabled>';
                    }

                }
                open_htmls += '<input type="checkbox"  title="全选" lay-skin="primary" lay-filter="tufure0" id="allChecked0">';
                $("#openStoreList0").html(open_htmls);
                // no_open_htmls += '<p class="openUp-action">以上门店因未开通计费系统或者未开通在线支付，无法实现<br>充值功能' +
                //     '<span onclick=siMenu("z'+pdMenu.MENU_ID+'","lm'+pdMenu.PARENT_ID+'","'+pdMenu.MENU_NAME+'","'+pdMenu.MENU_URL+'")>去开通</span></p>';
                $("#cardStoreList0").html(no_open_htmls);

                for (var i = 0; i < addedStoreList.length; i++) {
                    var addedStore = addedStoreList[i];
                    $("input[name='open_store_id0']").each(function () {
                        if(addedStore.storeid == $(this).val()){
                            $(this).attr("checked", true);//默认选中的门店
                        }
                    })

                }
                layui.form.render();
                layer.open({
                    btn : [ "确定", "取消" ],
                    yes : function(index, layero) {
                        var length = $("input:checkbox[name='open_store_id0']:checked").length;
                        $("#jsCard0").html("");
                        $("#couponid0").val("");
                        if(length == 0){
                            message("请选择门店");
                            return false;
                        }else{
                            var nameList = '';
                            var idList = '';
                            $("input:checkbox[name='open_store_id0']:checked").each(function (i) {
                                nameList += "<span class='show_storeName'>"+$(this).attr("title") + "</span>";
                                idList += $(this).attr("value") + ",";
                            })
                            $("#storeF").html(nameList);

                            $("#idList0").val(idList);
                            layer.closeAll();
                        }
                    },
                    type : 1,
                    shade : 0.6,
                    title : "选择门店",
                    area: ['530px', '436px'],
                    content : $('#store_form0'),
                })
            }else{
                message(res.errmsg);
            }
        })
    }

    //选择门店
    function go_rechargeEdit(falg){
            var data={typeo:'${pd.typeo}',typet:'${pd.typet}',falg:falg};
            if(falg==''){
                data = {typeo:'${pd.typeo}',typet:'${pd.typet}',pay_state:1,falg:falg};
            };
			$("#allChecked"+falg).html("");
			$("#notAllChecked"+falg).html("");
			var openStoreList='1';



			$.ajax({
				type: "POST",
				url: basePath+'membership/getStoreList.do',
				data : data,
				dataType : 'json',
				async:false,
				success : function(data) {
					if(data.result == "true"){
						var pdMenu = data.pdMenu;
						if($("#storefalg"+falg).val().length==0){
                            //可选门店
                            openStoreList = data.openStoreList;
                            var addedStoreList = data.addedStoreList;
                            var openInputs = '';
                            var checked = "";
                            for(var i=0;i<openStoreList.length;i++){
                                openInputs += '<input class="storeId" type="checkbox" title="'+openStoreList[i].store_name+'" lay-skin="primary" ';
                                if(openStoreList[i].checked==true){
                                    openInputs += ' checked ' ;
                                }
                                if(addedStoreList.length <1){
                                    openInputs += ' checked ';
                                }
                                openInputs +=' lay-filter="store'+falg+'" name="open_store_id'+falg+'" value="'+openStoreList[i].store_id+'">';
                            }
                            if(openInputs == ""){
                                $("#openBox"+falg).remove();
                            }else{
                                openInputs += '<input type="checkbox"  title="全选" lay-skin="primary" ';

                                if(addedStoreList.length==openStoreList.length){
                                    openInputs += ' checked ';
                                }
                                if(addedStoreList.length <1){
                                 openInputs += ' checked ' ;
                                }
                                openInputs += ' lay-filter="tufure'+falg+'" id="allChecked'+falg+'">';
                                $("#openStoreList"+falg).html(openInputs);
                            }

                            //不可选门店
                            var notOpenStoreList = data.notOpenStoreList;
                            var notOpenInputs = '';
                            for(var i=0;i<notOpenStoreList.length;i++){
                                notOpenInputs += '<input type="checkbox" title="'+notOpenStoreList[i].store_name+'" lay-skin="primary" disabled>';
                            }
                            if(notOpenInputs == ""){
                                $("#notOpenBox"+falg).remove();
                            }else{
                                notOpenInputs += '<p class="openUp-action">以上门店因未开通计费系统或者未开通在线支付，无法实现<br>充值功能<span  onclick=siMenu("z'+pdMenu.MENU_ID+'","lm'+pdMenu.PARENT_ID+'","'+pdMenu.MENU_NAME+'","'+pdMenu.MENU_URL+'")>去开通</span></p>';
                                $("#notOpenStoreList"+falg).html(notOpenInputs);
                            }

                            //没这只会员级别不可选门店
                            var cardStoreList = data.cardStoreList;
                            var cardStoreInputs = '';
                            for(var i=0;i<cardStoreList.length;i++){
                                cardStoreInputs += '<input type="checkbox" title="'+cardStoreList[i].store_name+'" lay-skin="primary" disabled>';
                            }
                            if(cardStoreInputs == ""){
                                $("#cardBox"+falg).remove();
                            }else{
                                cardStoreInputs += '<p class="openUp-action">以上门店没有设置会员级别，无法实现在线申请会员功能<span onclick=siMenu("z90","lm115","门店管理","storeShow/list.do")>去设置</span></p>';
                                $("#cardStoreList"+falg).html(cardStoreInputs);
                            }
						}

                        layui.form.render();
                        layer.open({
                            btn : [ "确定", "取消" ],
                            yes : function(index, layero) {
                                var length = $("input:checkbox[name='open_store_id"+falg+"']:checked").length;
                                $("#jsCard"+falg).html("");
                                $("#couponid"+falg).val("");
                                if(length == 0){
                                    message("请选择门店");
                                    return false;
                                }else{
                                    var nameList = '';
                                    var idList = '';
                                    $("input:checkbox[name='open_store_id"+falg+"']:checked").each(function (i) {
                                        nameList += "<span class='show_storeName'>"+$(this).attr("title") + "</span>";
                                        idList += $(this).attr("value") + ",";
                                    })
                                    if(falg=='0'){
                                        $("#storeF").html(nameList);
                                    }else{
                                        $("#storeT").html(nameList);
                                    }
                                    $("#idList"+falg).val(idList);
                                    $("#storefalg"+falg).val(openStoreList);
                                    layer.closeAll();
                                }
                            },
                            type : 1,
                            shade : 0.6,
                            title : "选择门店",
                            area: ['530px', '436px'],
                            content : $('#store_form'+falg),
                        })
					}else{
						message(data.message);
					}
				},
				error:function(){
					message("系统繁忙，请稍后再试！");
				}
			});

	  }







//保存设置  F表示0元，T表示在线支付  
function tj(basePath,falg){
	var idList=$("#idList"+falg).val(); //选中门店ID
	var banner=$("#banner"+falg).val(); //广告内容
	var rincipal_balance=$("#rincipal_balance").val(); //充值金额
	var reward_balance=$("#reward_balance").val();  // 奖励金额
	var couponid=$("#couponid"+falg).val(); //卡劵id
	var type0=$("#type00").val(); //卡劵id
	var type1=$("#type11").val(); //卡劵id
	var memberId=$("#memberId").val(); //主键id
	var member_level=$("#member_level").val();
	if(idList == ""){
		message("请选择门店");
		return false;
	}
	if(banner == ""){
		message("请填写广告词");
		return false;
	}
	if(falg!='0'){
	if(rincipal_balance == ""){
		message("请填写充值金额");
		return false;
	}
	}
    $("#saveMemberShip").attr("onclick", "");

	$.ajax({
        type: "POST",
        url: basePath+'membership/save.do',
        data : {memberId:memberId,storeids:idList,advertisement:banner,typeo:type0,typet:type1,rincipal_balance:rincipal_balance,reward_balance:reward_balance,couponid:couponid,member_level:member_level},
        dataType : 'json',
        async:false,
        success : function(data) {
            if(data.result == "true"){
                layer.msg("保存成功!",{icon:1})
            }else{
                message("系统繁忙，请稍后再试1！");
            }
            $("#saveMemberShip").attr("onclick", "tj('<%=basePath%>','0')");
        },
        error:function(){
            message("系统繁忙，请稍后再试！");
        }
    })
}

function xcard(basePath,falg){
	var idList=$("#idList"+falg).val();
	layer.open({
		btn : [ '确定', '关闭' ],
		btn1 : function(index, layero) {
			var res = window["layui-layer-iframe" + index].callCard();
			var cardxx="";
			cardxx+="<input id='couponid"+falg+"' type='hidden' value='"+res.cardId+"'>";
            cardxx+="<div class='cardBox'>";
            cardxx+=res.hh+res.carddet;
            cardxx+="</div>";
            cardxx+="<span class='closeCard' onclick=closeCard('"+falg+"')>删除</span>";
			$("#jsCard"+falg).html(cardxx);
			$("#couponid"+falg).val(res.cardId);
			layer.close(index);
		},
		btnAlign : 'c',
		type : 2,
		title : '请选择卡券',
		shadeClose : false,
		shade : 0.8,
		area : [ '704px', '80%' ],
		content : basePath+'membership/cardList?store_id='+idList,
	})
};




</script>
</html>
