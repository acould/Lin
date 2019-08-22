function getRealPath() {
	// 获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： myproj/view/my.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/myproj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	// 得到了 http://localhost:8083/myproj
	var realPath = localhostPaht + projectName;
	if (projectName == "/lanker") {
		return realPath
	} else {
		return localhostPaht
	}
}

// 选择群发对象
function memberMake_select(url, src) {
	layer.open({
		btn : [ '确定', '关闭' ],
		btn1 : function(index, layero) {
			// 按钮的回调
			var res = window["layui-layer-iframe" + index].go_edit(src);
			if (res == 'warn') {
				message("员工不能进行该操作")
			} else if (res == false) {

			} else {
				window.open(res);
				layer.close(layer.index);
			}
		},
		type : 2,
		title : '选择群发对象',
		shadeClose : false,
		shade : 0.8,
		area : [ '530px', '436px' ],
		content : url,
	})
}
$(".lk-massObject-item-card").click(function() {
	$(".lk-massObject-item-card").removeClass("active");
	$(this).addClass("active");
})
$(".lk-massObject-item").click(function() {
	$(".lk-massObject-item").removeClass("active");
	$(this).addClass("active");
	var type = $(this).data("type")
	if (type == "member") {
		$(".lk-massObject-box").hide();
		$(".lk-chooseStore-box").show();
	}
})
// 上一步
$("#come-back").click(function() {
	$(".lk-massObject-box").show();
	$(".lk-chooseStore-box").hide();
})

// 去新建群发消息
function go_edit(src) {
	var checked = [];
	var checked1 = [];
	$("input:checkbox[name='store_id']:checked").each(function(i) {
		checked[i] = $(this).val();
		checked1[i] = $(this).attr("title");
	})
	$("#store_list").val(checked); // id
	$("#store_name").val(checked1);// name
	var storeList = $("#store_list").val();
	var storeName = $("#store_name").val();
	var type = $(".active").data("type");
	if (type == "" || type == undefined) {
		message("请选择群发的对象");
		return false;
	}
	if (storeList == "" && type == "member") {
		message("请选择群发的门店");
		return false;
	}
	$.ajax({
		type : "POST",
		url : src,
		data : {
			mass_object : type
		},
		dataType : 'json',
		async : false,
		success : function(data) {
			if (data.result == "success") {
				var url = getRealPath() + data.url + '?mass_object=' + type
						+ '&storeList=' + storeList + '&store_name='
						+ storeName;
				$("#url").val(url);
			} else {
				var url = 'warn';
				$("#url").val(url);
			}
		},
		error : function() {
			message("系统繁忙，请稍后再试！");
		}
	});
	var url = $("#url").val();
	if (url != '') {
		return url;
	} else {
		return false;
	}
}

layui.use([ "form", "layer", 'element', 'laydate' ],function() {
					var form = layui.form, layer = layui.layer, element = layui.element, laydate = layui.laydate;
					laydate.render({
						elem : '#createtime',
						range : true,
					});
					//开始时间
					laydate.render({
						elem: '#start_time'
					});
					//结束时间
					laydate.render({
						elem: '#end_time'
					});
					form.render("select");
						//开关监听
					form.on('switch(switchTest)', function(data){
						// console.log(data.elem); //得到checkbox原始DOM对象
						// console.log(data.elem.checked); //开关是否开启，true或者false
						// console.log(data.value); //开关value值，也可以通过data.elem.value得到
						// console.log(data.othis); //得到美化后的DOM对象
						if(data.elem.checked){
							$("#push_time").show();
						}else {
							$("#push_time").hide();
						}
					});

	// 全选监听
					form.on('checkbox(tufure)', function(data) {
						if (data.elem.checked == true) {
							$(data.othis).prevAll(".layui-form-checkbox")
									.addClass("layui-form-checked")
							$(data.othis).prevAll("input").prop("checked",
									"checked");
						} else {
							$(data.othis).prevAll(".layui-form-checkbox")
									.removeClass("layui-form-checked")
							$(data.othis).prevAll("input")
									.removeAttr("checked");
						}
					});
					// 复选框监听
					form.on('checkbox(store)', function(data) {
						var stores = document.getElementsByName("store_id");
						for (var i = 0; i < stores.length; i++) {
							if (stores[i].checked == false) {
								$("#allChecked").next().removeClass(
										"layui-form-checked")
								$("#allChecked").removeAttr("checked");
								return;
							}
							if (i == stores.length - 1) {
								$("#allChecked").next().addClass(
										"layui-form-checked")
								$("#allChecked").prop("checked", "checked");
								return;
							}
						}
					});

					// 单选框监听(会员类型)
					form
							.on(
									'radio(member)',
									function(data) {
										var memberId = $(data.elem)
												.data("type");
										var store_id = $("#storeName").val();
										$
												.ajax({
													async : false,
													type : "POST",
													url : getRealPath()
															+ '/memberMarke/selectTips.do',
													data : {
														storeid : store_id
													},
													dataType : 'json',
													success : function(data) {
														if ("success" == data.result) {
															if (memberId == "consumeId") {
																$("#tips-value")
																		.show();
																$("#tips-value")
																		.attr(
																				"name",
																				"consumeId");
																$("#tip-lable")
																		.html(
																				"消费能力：");
																var divs = '<p class="set-tip">消费能力高: 平均消费金额高于'
																		+ data.pd1.consume_high
																		+ '</p>'
																		+ '<p class="set-tip">消费能力中: 平均消费金额在'
																		+ data.pd1.consume_high
																		+ '-'
																		+ data.pd1.consume_low
																		+ '</p>'
																		+ '<p class="set-tip">消费能力低: 平均消费金额低于'
																		+ data.pd1.consume_low
																		+ '</p>'
																		+ '<p class="set-tip"><span class="set-tip-btn" onclick=amend_tips("'
																		+ store_id
																		+ '","'
																		+ memberId
																		+ '")>修改</span></p>'
																$(
																		".set-tip-box")
																		.html(
																				divs);
															} else if (memberId == "liveId") {
																$("#tips-value")
																		.show();
																$("#tips-value")
																		.attr(
																				"name",
																				"liveId");
																$("#tip-lable")
																		.html(
																				"活跃度：");
																var divs = '<p class="set-tip">活跃度高: 平均间隔时间小于'
																		+ data.pd1.live_high
																		+ '</p>'
																		+ '<p class="set-tip">活跃度中: 平均间隔时间在'
																		+ data.pd1.live_high
																		+ '-'
																		+ data.pd1.live_low
																		+ '之间</p>'
																		+ '<p class="set-tip">消活跃度低: 平均消费金额低于'
																		+ data.pd1.live_low
																		+ '</p>'
																		+ '<p class="set-tip"><span class="set-tip-btn" onclick=amend_tips("'
																		+ store_id
																		+ '","'
																		+ memberId
																		+ '")>修改</span></p>'
																$(
																		".set-tip-box")
																		.html(
																				divs);
															} else if (memberId == "amountId") {
																$("#tips-value")
																		.show();
																$("#tips-value")
																		.attr(
																				"name",
																				"amountId");
																$("#tip-lable")
																		.html(
																				"卡余额：");
																var divs = '<p class="set-tip">余额高: 当前余额高于'
																		+ data.pd1.balance_high
																		+ '</p>'
																		+ '<p class="set-tip">余额中: 当前余额在'
																		+ data.pd1.balance_high
																		+ '-'
																		+ data.pd1.balance_low
																		+ '之间</p>'
																		+ '<p class="set-tip">余额高: 当前余额低于'
																		+ data.pd1.balance_low
																		+ '</p>'
																		+ '<p class="set-tip"><span class="set-tip-btn" onclick=amend_tips("'
																		+ store_id
																		+ '","'
																		+ memberId
																		+ '")>修改</span></p>'
																$(
																		".set-tip-box")
																		.html(
																				divs);
															} else {
																$("#tips-value")
																		.hide();
															}
														}
													},
													error : function() {
														layer
																.msg("系统繁忙，请稍后再试！");
													}
												});
									});
					// 下拉框监听(门店变化)
					form
							.on(
									'select(storeTips)',
									function(data) {
										var memberId = $("#tips-value").attr(
												"name");
										var state = $("#state").val();
										var store_id = data.value;
										if (state != 'view') {// 新增时
											$
													.ajax({
														async : false,
														type : "POST",
														url : getRealPath()
																+ '/memberMarke/selectTips.do',
														data : {
															storeid : store_id
														},
														dataType : 'json',
														success : function(data) {
															if ("success" == data.result) {
																$(
																		".set-tip-box")
																		.html(
																				"");
																if (memberId == "consumeId") {
																	var divs = '<p class="set-tip">消费能力高: 平均消费金额高于'
																			+ data.pd1.consume_high
																			+ '</p>'
																			+ '<p class="set-tip">消费能力中: 平均消费金额在'
																			+ data.pd1.consume_high
																			+ '-'
																			+ data.pd1.consume_low
																			+ '</p>'
																			+ '<p class="set-tip">消费能力低: 平均消费金额低于'
																			+ data.pd1.consume_low
																			+ '</p>'
																			+ '<p class="set-tip"><span class="set-tip-btn" onclick=amend_tips("'
																			+ store_id
																			+ '","'
																			+ memberId
																			+ '")>修改</span></p>'
																	$(
																			".set-tip-box")
																			.html(
																					divs);
																} else if (memberId == "liveId") {
																	var divs = '<p class="set-tip">活跃度高: 平均间隔时间小于'
																			+ data.pd1.live_high
																			+ '</p>'
																			+ '<p class="set-tip">活跃度中: 平均间隔时间在'
																			+ data.pd1.live_high
																			+ '-'
																			+ data.pd1.live_low
																			+ '之间</p>'
																			+ '<p class="set-tip">消活跃度低: 平均消费金额低于'
																			+ data.pd1.live_low
																			+ '</p>'
																			+ '<p class="set-tip"><span class="set-tip-btn" onclick=amend_tips("'
																			+ store_id
																			+ '","'
																			+ memberId
																			+ '")>修改</span></p>'
																	$(
																			".set-tip-box")
																			.html(
																					divs);
																} else if (memberId == "amountId") {
																	var divs = '<p class="set-tip">余额高: 当前余额高于'
																			+ data.pd1.balance_high
																			+ '</p>'
																			+ '<p class="set-tip">余额中: 当前余额在'
																			+ data.pd1.balance_high
																			+ '-'
																			+ data.pd1.balance_low
																			+ '之间</p>'
																			+ '<p class="set-tip">余额高: 当前余额低于'
																			+ data.pd1.balance_low
																			+ '</p>'
																			+ '<p class="set-tip"><span class="set-tip-btn" onclick=amend_tips("'
																			+ store_id
																			+ '","'
																			+ memberId
																			+ '")>修改</span></p>'
																	$(
																			".set-tip-box")
																			.html(
																					divs);
																} else {
																	$(
																			"#tips-value")
																			.hide();
																}
															}
														},
														error : function() {
															layer
																	.msg("系统繁忙，请稍后再试！");
														}
													});
										} else {// 查看时
											$
													.ajax({
														async : false,
														type : "POST",
														url : getRealPath()
																+ '/memberMarke/selectTips.do',
														data : {
															storeid : store_id
														},
														dataType : 'json',
														success : function(data) {
															if ("success" == data.result) {
																$(
																		".set-tip-box")
																		.html(
																				"");
																if (memberId == "consumeId") {
																	var divs = '<p class="set-tip">消费能力高: 平均消费金额高于'
																			+ data.pd1.consume_high
																			+ '</p>'
																			+ '<p class="set-tip">消费能力中: 平均消费金额在'
																			+ data.pd1.consume_high
																			+ '-'
																			+ data.pd1.consume_low
																			+ '</p>'
																			+ '<p class="set-tip">消费能力低: 平均消费金额低于'
																			+ data.pd1.consume_low
																			+ '</p>'
																	$(
																			".set-tip-box")
																			.html(
																					divs);
																} else if (memberId == "liveId") {
																	var divs = '<p class="set-tip">活跃度高: 平均间隔时间小于'
																			+ data.pd1.live_high
																			+ '</p>'
																			+ '<p class="set-tip">活跃度中: 平均间隔时间在'
																			+ data.pd1.live_high
																			+ '-'
																			+ data.pd1.live_low
																			+ '之间</p>'
																			+ '<p class="set-tip">消活跃度低: 平均消费金额低于'
																			+ data.pd1.live_low
																			+ '</p>'
																	$(
																			".set-tip-box")
																			.html(
																					divs);
																} else if (memberId == "amountId") {
																	var divs = '<p class="set-tip">余额高: 当前余额高于'
																			+ data.pd1.balance_high
																			+ '</p>'
																			+ '<p class="set-tip">余额中: 当前余额在'
																			+ data.pd1.balance_high
																			+ '-'
																			+ data.pd1.balance_low
																			+ '之间</p>'
																			+ '<p class="set-tip">余额高: 当前余额低于'
																			+ data.pd1.balance_low
																			+ '</p>'
																	$(
																			".set-tip-box")
																			.html(
																					divs);
																} else {
																	$(
																			"#tips-value")
																			.hide();
																}
															}
														},
														error : function() {
															layer
																	.msg("系统繁忙，请稍后再试！");
														}
													});
										}
									});

					element.on('tab(keywordReply)', function(data) {
						/*
						 * var id = $(".layui-this").attr("id"); if(id ==
						 * "material"){ deleteImg(); deleteWenzi();
						 * deleteCard(); }else if(id == "image"){ deleteTuwen();
						 * deleteWenzi(); deleteCard(); }else if(id == "text"){
						 * deleteTuwen(); deleteImg(); deleteCard(); }else if(id =
						 * "card"){ deleteTuwen(); deleteImg(); deleteWenzi(); }
						 */
					})

					// 点击选中
					$(".tab_navs").children(".tab_nav").click(function() {
						$(this).addClass("actived");
						$(this).siblings().removeClass("actived");
					})
				})
// 修改会员标签
function amend_tips(ID, type) {
	var memberId = $("#tips-value").attr("name");
	layer
			.open({
				btn : [ '保存', '关闭' ],
				btn1 : function(index, layero) {
					// 按钮的回调
					var res = window["layui-layer-iframe" + index].save();
					if (res.msg) {
						$
								.ajax({
									async : false,
									type : "POST",
									url : getRealPath()
											+ '/memberMarke/selectTips.do',
									data : {
										storeid : ID
									},
									dataType : 'json',
									success : function(data) {
										if ("success" == data.result) {
											$(".set-tip-box").html("");
											if (memberId == "consumeId") {
												var divs = '<p class="set-tip">消费能力高: 平均消费金额高于'
														+ data.pd1.consume_high
														+ '</p>'
														+ '<p class="set-tip">消费能力中: 平均消费金额在'
														+ data.pd1.consume_high
														+ '-'
														+ data.pd1.consume_low
														+ '</p>'
														+ '<p class="set-tip">消费能力低: 平均消费金额低于'
														+ data.pd1.consume_low
														+ '</p>'
														+ '<p class="set-tip"><span class="set-tip-btn" onclick=amend_tips("'
														+ ID
														+ '","'
														+ memberId
														+ '")>修改</span></p>'
												$(".set-tip-box").html(divs);
											} else if (memberId == "liveId") {
												var divs = '<p class="set-tip">活跃度高: 平均间隔时间小于'
														+ data.pd1.live_high
														+ '</p>'
														+ '<p class="set-tip">活跃度中: 平均间隔时间在'
														+ data.pd1.live_high
														+ '-'
														+ data.pd1.live_low
														+ '之间</p>'
														+ '<p class="set-tip">消活跃度低: 平均消费金额低于'
														+ data.pd1.live_low
														+ '</p>'
														+ '<p class="set-tip"><span class="set-tip-btn" onclick=amend_tips("'
														+ ID
														+ '","'
														+ memberId
														+ '")>修改</span></p>'
												$(".set-tip-box").html(divs);
											} else if (memberId == "amountId") {
												var divs = '<p class="set-tip">余额高: 当前余额高于'
														+ data.pd1.balance_high
														+ '</p>'
														+ '<p class="set-tip">余额中: 当前余额在'
														+ data.pd1.balance_high
														+ '-'
														+ data.pd1.balance_low
														+ '之间</p>'
														+ '<p class="set-tip">余额高: 当前余额低于'
														+ data.pd1.balance_low
														+ '</p>'
														+ '<p class="set-tip"><span class="set-tip-btn" onclick=amend_tips("'
														+ ID
														+ '","'
														+ memberId
														+ '")>修改</span></p>'
												$(".set-tip-box").html(divs);
											} else {
												$("#tips-value").hide();
											}
										}
									},
									error : function() {
										layer.msg("系统繁忙，请稍后再试！");
									}
								});
					}
				},
				skin : 'demo-class',
				btnAlign : 'c',
				type : 2,
				title : '编辑会员标签',
				shadeClose : false,
				shade : 0.6,
				area : [ '580px', '486px' ],
				content : [ getRealPath()
						+ '/storeShow/goTipsEdit.do?STORE_ID=' + ID
						+ '&memberId=' + type ],
			});
}

// 选择图文消息
function opende(url) {
	layer
			.open({
				btn : [ '确定', '关闭' ],
				btn1 : function(index, layero) {
					// 按钮【按钮一】的回调
					var res = window["layui-layer-iframe" + index].callTuWen();
					console.log(res)
					$(".padding-30").html(res.time);
					$(".wordTitle").children(":first-child").html(res.ftitle);
					$(".widthImg").children(":first-child").attr("src",
							res.fimg);
					$("#TUWEN_MEDIA_ID").val(res.media_id);
					$("#TUWEN_SENDRECORD_ID").val(res.sendRecord_id);

					if (parseInt(res.length) > 0) {
						var all = "";
						for (var i = 0; i < res.length; i++) {
							var titleName = "title" + i;
							var imgName = "img" + i;
							var title = JSON.parse(res.last)[titleName];
							var img = JSON.parse(res.last)[imgName];
							var div = '<div class="borBottom">'
									+ '<table width="100%" >'
									+ '<tbody >'
									+ '<tr>'
									+ '<td style="vertical-align: top"><p class="tarTileWid">'
									+ title
									+ '</p></td>'
									+ '<td style="vertical-align: top;text-align: right"><div class="tarImgWid"><img src="'
									+ img
									+ '" alt=""></div></td>'
									+ '</tr>'
									+ '</tbody>'
									+ '</table>'
									+ '<div class="imgyulan" onclick="previewTuWen(i+1)">'
									+ '<p class="text-center">预览</p>'
									+ '</div>' + '</div>';
							all += div;
						}
						$(".widthImg").after(all);
					}

					$("#newImg").hide();
					$("#textImg").show();
					layer.close(index)
				},
				skin : '',
				btnAlign : 'c',
				type : 2,
				title : '添加图文消息',
				shadeClose : false,
				shade : 0.8,
				area : [ '55%', '70%' ],
				content : [ url ], // iframe的url

			});
}
// 预览图文
function previewTuWen(i) {
	var SENDRECORD_ID = $("#TUWEN_SENDRECORD_ID").val();
	layer.open({
		btn : [ '关闭' ],
		btn1 : function(index, layero) {
			layer.close(index);
		},
		skin : '',
		btnAlign : 'c',
		type : 2,
		title : '预览图文',
		shadeClose : false,
		shade : 0.8,
		area : [ '1074px', '94%' ],
		content : [ getRealPath() + '/wxMenu/preview.do?SENDRECORD_ID='
				+ SENDRECORD_ID + '&SEQUENCE=' + i ]
	});

}

// 选择历史图片
function openImg(url) {
	layer.open({
		btn : [ '确定', '关闭' ],
		btn1 : function(index, layero) {
			// 按钮的回调
			var res = window["layui-layer-iframe" + index].callbackdata();
			$("#successImg").attr("src", res.url);
			$("#IMG_MEDIA_ID").val(res.media_id);
			flieImgsuccess();
			layer.close(index);
		},
		skin : '',
		btnAlign : 'c',
		type : 2,
		title : '选择历史图片',
		shadeClose : false,
		shade : 0.8,
		area : [ '780px', '60%' ],
		content : url,
	});
}
function flieImgsuccess() {
	$("#divImg").show()
	$("#flieImg").hide()
	$("#beforeImg").hide()
}
// 上传图片
function uploadImg(f) {
	for (var i = 0; i < f.length; i++) {
		var reader = new FileReader();
		reader.readAsDataURL(f[i]);
		var tt = f[i].type;
		var size = f[i].size;
		reader.onload = function(e) {
			var srces = e.target.result;
			var rule = /^(?:image\/jpeg|image\/png)$/i;
			if (rule.test(tt)) {
				if (size > (2 * 1024 * 1024)) {
					layer.msg("图片大小不能超过2M", {
						time : 1500
					});
				} else {
					$.ajax({
						type : "POST",
						url : getRealPath() + '/wxAutoReply/uploadImg.do',
						data : {
							upFile : srces
						},
						dataType : 'json',
						beforeSend : beforeSend,
						success : function(data) {
							layer.closeAll('loading');
							layer.msg(data.message, {
								time : 1000
							});
							if (data.result == "true") {
								$("#IMG_MEDIA_ID").val(data.MEDIA_ID);

								$("#successImg").attr("src", srces);
								flieImgsuccess();
							}
						},
						error : function() {
							layer.closeAll('loading');
							layer.msg("系统繁忙，请稍后再试！");
							return false;
						}
					});
				}
			} else {
				layer.msg("请上传jpg或者png格式的图片", {
					time : 1500
				});
			}
		}
	}
}
function beforeSend() {
	layer.load(2);
}
// 预览图片
function yulanTuPian() {
	console.log()
	parent.layer.open({
		type : 1,
		title : false,
		closeBtn : 0,
		area : '30%',
		closeBtn : 1,
		skin : '', // 没有背景色
		shadeClose : true,
		content : $(".divImg").html()
	});
}
// 初始化
$(function() {
	if ($("#TUWEN_MEDIA_ID").val() == "") {
		$("#newImg").show();
		$("#textImg").hide();
	} else {
		$("#newImg").hide();
		$("#textImg").show();
	}

	if ($("#IMG_MEDIA_ID").val() == "") {
		$("#flieImg").show();
		$("#beforeImg").show();
		$("#divImg").hide();
	} else {
		$("#flieImg").hide();
		$("#beforeImg").hide();
		$("#divImg").show();
	}

	if ($("#CARD_ID").val() == "") {
		$("#jsCard").hide();
		$("#xzCard").show();
	} else {
		$("#jsCard").show();
		$("#xzCard").hide();
	}
	checkNum();
})

// 文字检测
function checkNum() {
    if($("#CONTENT").val() != null){
        var length = $("#CONTENT").val().length;
        $("#num").html(300 - length);
    }
}

// 绑定事件
$("body").on("change", "#upFlie", function() {
	var files = this.files
	uploadImg(files);
});
// 删除图文
function deleteTuwen() {
	$("#textImg").hide();
	$("#newImg").show();
	$("#TUWEN_MEDIA_ID").val("");
	$("#TUWEN_SENDRECORD_ID").val("");
	$(".borBottom").remove();
}
// 删除图片
var deleteImg = function() {
	$("#flieImg").show();
	$("#beforeImg").show();
	$("#divImg").hide();
	$("#divImg").children().children().attr("src", "");
	$("#upFlie").val("");
	$("#IMG_MEDIA_ID").val("");
}
// 删除卡券
var deleteCard = function() {
	$("#jsCard").hide();
	$("#xzCard").show();
	$("#card-title").html("");
	$("#card-title").attr("class", "");
	$("#card-time").html("");
	$("#adrss").html("");
	$("#xiangqing").html("");
	$("#CARD_ID").val("");
}
// 删除文字
var deleteWenzi = function() {
	$("#CONTENT").val("");
	$("#TEXTMSG_ID").val("");
}

/** *****群发卡券******* */

// 选择历史卡券
function select_card(url, src, role, card_id, store_id, is_all, state,cardType) {
	if (card_id == '') {// 如果card_id为空,则为会员营销群发卡券
		var url1 = url + "?card_id=" + card_id + "&store_id=" + store_id;
		// 先选对象,再选卡券,最后前往群发页面
		layer.open({
			btn : [ '确定', '关闭' ],
			btn1 : function(index, layero) {
				// 按钮【按钮一】的回调
				var type = $("#massObject .active").data("type");
				if (type == "" || type == undefined) {
					message("请选择群发对象");
				} else if (type == 'fans' && role == 'staff') {// 员工不能群发粉丝
					message("员工不能进行该操作");
				} else if (type == 'fans' && role == 'boss' && cardType == 'TERM') {// 员工不能群发粉丝
					message("该类卡券只能发给会员");
				}else {
					layer.close(index);
					layer.open({
						btn : [ '确定', '关闭' ],
						btn1 : function(index, layero) {
							var res = window["layui-layer-iframe" + index].callCard();
							var cardId = res.cardId;
							var store_id = res.store_id;
							if (cardId == "" || cardId == undefined) {
								message("请选择卡券");
							} else {
								window.open(src + "?cardId=" + cardId + "&role=" + role + "&store_id="+ store_id + "&mass_object=" + type);
								layer.close(index);
							}
						},
						btnAlign : 'c',
						type : 2,
						title : '请选择群发的卡券',
						shadeClose : false,
						shade : 0.8,
						area : [ '704px', '80%' ],
						content : url1 + "&type=" + type,
					})
				}
			},
			btnAlign : 'c',
			type : 1,
			title : '请选择卡券群发的对象',
			shadeClose : false,
			shade : 0.8,
			area : [ '530px', '436px' ],
			content : $("#massObject"),
		});
	} else {// 不为空,则为卡券设置,群发指定卡券
		layer.open({
			btn : [ '确定', '关闭' ],
			btn1 : function(index, layero) {
				// 按钮【按钮一】的回调
				var type = $("#massObject .active").data("type");
				if (type == "" || type == undefined) {
					message("请选择群发对象");
				} else {
					$(".lk-massObject-box").hide();
					if(type == 'fans' && role == 'staff'){//员工选择粉丝
						message("员工不能进行该操作");
					}else if(type == 'fans' && role == 'boss'){//老板选择粉丝
						if(cardType == 'TERM'){
							message("该类卡券只能发给会员");
						}else if (is_all == '1') {
							window.open(src + "?cardId=" + card_id + "&role=" + role + "&store_id=" + store_id + "&mass_object=" + type);
							layer.close(index);
						} else {
							layer.close(index);
							layer.alert("该卡券不通用所有门店，不能发放给粉丝")
						}
					}else if(type == 'member'){// 对象为会员
						if (state == '1') {
							window.open(src + "?cardId=" + card_id + "&role=" + role + "&store_id=" + store_id + "&mass_object=" + type);
							layer.close(index);
						} else {
							layer.close(index);
							layer.alert("该卡券适用门店均未绑定计费系统")
						}
					}
				}
			},
			btn2 : function(index, layero) {
				$(".lk-massObject-box").hide();
			},
			btnAlign : 'c',
			type : 1,
			title : '请选择卡券群发的对象',
			shadeClose : false,
			shade : 0.8,
			area : [ '530px', '436px' ],
			content : $("#massObject"),
		});
	}
}

// 免责声明
function declare() {
	layer.open({
		btn : [ '关闭' ],
		btn1 : function(index, layero) {
			layer.close(index)
		},
		btnAlign : 'c',
		type : 1,
		title : false,
		shadeClose : false,
		shade : 0.8,
		closeBtn : 0,
		area : [ '570px', '90%' ],
		content : $("#declareContent")
	})
}

// 查看消息示例

function example() {
	layer.open({
		btnAlign : 'c',
		type : 1,
		title : false,
		closeBtn : 0,
		area : '520px',
		skin : 'layui-layer-nobg', // 没有背景色
		shadeClose : true,
		shade : 0.8,
		closeBtn : 0,
		content : '<img src="' + getRealPath()
				+ '/newStyle/images/newerGuide/example.jpg" width="100%">'
	})
}
