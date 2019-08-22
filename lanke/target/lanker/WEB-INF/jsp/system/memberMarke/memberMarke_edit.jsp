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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="<%=basePath%>newStyle/layui/css/layui.css"
	media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/lanke.css" media="all">
<link rel="stylesheet" href="<%=basePath%>newStyle/css/custom.css">
<link rel="icon" type="text/css"
	href="<%=basePath%>newStyle/images/lk-icon.png">
<title>网吧管理后台</title>
<style>
body {background-color: #f6f8f9;min-width: 600px;overflow: auto;}
.borPadding {padding: 20px 14px;}
.layui-this:after {border-bottom: none !important;}
.layui-tab-title li {padding: 0 !important;margin: 0 10px;line-height: 39px;}
.inner, .cardDiv {height: 220px;overflow: auto;}
#adrss{
	text-overflow: -o-ellipsis-lastline;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;
}
.Color010 {color: #63b359;}
.Color020 {color: #2c9f67;}
.Color030 {color: #509fc9;}
.Color040 {color: #5885cf;}
.Color050 {color: #9062c0;}
.Color060 {color: #d09a45;}
.Color070 {color: #e4b138;}
.Color080 {color: #ee903c;}
.Color090 {color: #dd6549;}
.Color100 {color: #cc463d;}
.store-style{padding: 6px 10px; display: inline-block;border: 1px solid #e6e6e6; margin: 6px; border-radius: 20px;}
.media_cover {width: 42%;}
.layui-laydate-content td.laydate-selected {background-color: #ecf4ff;}
</style>
</head>
<body class="scroll">
	<div class="card-header">
		<div class="card-header-msg">
			<img src="<%=basePath%>newStyle/images/logo2.png"
				class="card-lankeIcon"> <span class="card-header-title">网吧管理后台</span>
			<div class="card-header-right">
				<img alt="" src="<%=basePath%>static/ace/avatars/user.jpg" class="">
				<span class="card-userName layui-elip"> <small>Welcome</small><br>
					${pd.user_name}
				</span>
			</div>
		</div>
	</div>
	<div class="card-width" style="min-width: 1000px;">
		<h1>新增群发消息</h1>
		<div class="lanke-pbMsg">
			<div class="layui-clear lk-declare-box">
				<div class="lk-declare-item">
					<div class="declare-img"></div>
					<div class="declare-bg"></div>
					<span class="btn-example" onclick="example()">查看示例</span>
				</div>
				<div class="lk-declare-item">
					<div class="declare-text">
						<h1 class="declare-title">群发营销、骚扰的提醒和免责声明</h1>
						<p>微信平台对发送内容、频率有严格限制，不当的营销内容、过高的发送频率均会被判定为过度营销骚扰用户，轻则导致封闭群发接口的后果、重则导致封闭微信公众号的后果，如果用户群发过程中，发生了违规被微信处罚等后果...</p>
						<span class="declare-btn" onclick="declare()">详情</span>
					</div>
				</div>
			</div>
			<div class="lanke-pbMsgSide">
				<form class="layui-form layui-form-pane layui-clear" name="Form" id="Form" method="post">
					<input type="hidden" id="mass_object" name="mass_object" value="${pd.mass_object}"> 
					<input type="hidden" id="store_id" name="store_id" value="${pd.storeList}"> 
					<input type="hidden" id="state" name="state" value="${pd.state}">
					<c:if test="${pd.mass_object == 'member'}">
						<div class="lk-store-set" style="margin-bottom:16px;">
							<p class="msg-title">已选群发门店：</p>
							<div class="msg-content" style="padding: 10px 20px;">
								<div id="store_name" style="color: #666;">
									<c:forEach items="${list}" var="var" varStatus="vs">
										<span value="${var.STORE_ID}" class="store-style">${var.STORE_NAME}</span>
									</c:forEach>
								</div>
								<input type="hidden" id="store_id" name="store_id" value="${pd.storeList}">
							</div>
						</div>
					</c:if>
					<c:if test="${pd.mass_object == 'fans'}">
						<div class="lk-store-set" style="margin-bottom:16px;">
							<p class="msg-title">群发门店：</p>
							<div class="msg-content" style="padding: 10px 20px;">
								<div id="store_name" style="color: #666;">
									<span style="padding: 6px 10px; display: inline-block;">公众号全部粉丝</span>
								</div>
							</div>
						</div>
					</c:if>
					<div class="lk-msg-set">
						<p class="msg-title">消息内容设置：</p>
						<div class="msg-content" style="height: 710px">
							<div class="layui-form-item">
								<label class="layui-form-label"><span class="lanke-sign">*</span>消息标题：</label>
								<div class="layui-input-block">
									<input type="text" name="title" class="layui-input"
										placeholder="" id="title" value="${pd.title}" maxlength="20"
										<c:if test="${pd.state == 'view'}">readonly="readonly"</c:if>>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label"><span class="lanke-sign">*</span>活动类型：</label>
								<div class="layui-input-block">
									<input type="text" name="activtyType" class="layui-input"
										placeholder="" id="activtyType" value="${pd.activtyType}" maxlength="20"
										<c:if test="${pd.state == 'view'}">readonly="readonly"</c:if>>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label"><span class="lanke-sign">*</span>活动内容：</label>
								<div class="layui-input-block">
									<input type="text" name="activtyContent" class="layui-input"
										placeholder="" id="activtyContent"
										value="${pd.activtyContent}" maxlength="1000"
										<c:if test="${pd.state == 'view'}">readonly="readonly"</c:if>>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label"><span class="lanke-sign">*</span>活动时间：</label>
								<div class="layui-input-block">
									<input type="text" name="createtime" class="layui-input"
										placeholder="" id="createtime" value="${pd.createtime}" maxlength="30"
										<c:if test="${pd.state == 'view'}">readonly="readonly"</c:if>>
								</div>
							</div>
							<div class="layui-form-item">
								<label class="layui-form-label">备注(选填)：</label>
								<div class="layui-input-block">
									<input type="text" name="remark" class="layui-input"
										placeholder="" id="remark" value="${pd.remark}" maxlength="50"
										<c:if test="${pd.state == 'view'}">readonly="readonly"</c:if>>
								</div>
							</div>
							<div class="layui-form-item layui-form-text"
								style="margin-bottom: 4px;">
								<label class="layui-form-label">活动详情(选填)：</label>
							</div>
							<div class="layui-form-item">
								<div class="msg_sender">
									<div class="msg_tab" style="height: 362px;">
										<div class="tab_navs_panel">
											<div class="tab_navs_wrp">
												<input id="type" name="type" type="hidden" value="${pd.type}"> 
												<input id="object_id" name="object_id" type="hidden" value="${pd.object_id}">
												<div class="layui-tab layui-tab-brief" lay-filter="keywordReply">
													<ul class="tab_navs layui-tab-title">
														<c:if test="${pd.type == 'card' }">
															<li class="tab_nav <c:if test="${pd.type == 'card' }">layui-this actived</c:if>" id="card">
															<a href="javascript:void(0);"> 
															<i class="layui-icon tab_icon3">&#xe65e;</i> 
															<span class="tab_span">卡券</span>
															</a>
															</li>
														</c:if>
														<c:if test="${pd.type != 'card' }">
															<li class="tab_nav <c:if test="${pd.type == 'material' }">layui-this actived</c:if>"
																id="material"><a href="javascript:void(0);"> <i
																	class="layui-icon tab_icon1">&#xe62a;</i> <span
																	class="tab_span">图文消息</span>
															</a></li>
															<li
																class="tab_nav <c:if test="${pd.type == 'image' }">layui-this actived</c:if>"
																id="image"><a href="javascript:void(0);"> <i
																	class="layui-icon tab_icon2">&#xe64a;</i> <span
																	class="tab_span">图片</span>
															</a></li>
															<li
																class="tab_nav <c:if test="${pd.type == 'text' }">layui-this actived</c:if>"
																id="text"><a href="javascript:void(0);"> <i
																	class="layui-icon tab_icon3" style="font-size: 14px">&#xe648;</i>
																	<span class="tab_span">文字</span>
															</a></li>

															<li
																class="tab_nav <c:if test="${pd.type == 'link' }">layui-this actived</c:if>"
																id="link"><a href="javascript:void(0);"> <i
																	class="layui-icon tab_icon2" style="font-size: 14px;">&#xe64c;</i>
																	<span class="tab_span">链接</span>
															</a></li>
														</c:if>
													</ul>
													<div class="layui-tab-content">
														<c:if test="${pd.type == 'card' }">
															<!-- 卡券内容 -->
															<div
																class="layui-tab-item <c:if test="${pd.type == 'card' }">layui-show</c:if>"
																id="kaquanItem">
																<div class="cardDiv">
																	<div id="jsCard">
																		<input id="CARD_ID" type="hidden" value="${card.CARD_ID }">
																		<div class="cardBox" style="width: 344px;box-shadow: 0px 4px 16px #f6f8f9;background: #fff;margin-left:20px;">
																			<h1 style="font-size: 16px; font-weight: 600; margin-bottom: 15px;padding: 14px 15px;border-bottom: 1px solid #eee;" id="card-title" class="<c:if test="${card.COLOR == 'Color010' }">Color010</c:if><c:if test="${card.COLOR == 'Color020' }">Color020</c:if><c:if test="${card.COLOR == 'Color030' }">Color030</c:if><c:if test="${card.COLOR == 'Color040' }">Color040</c:if><c:if test="${card.COLOR == 'Color050' }">Color050</c:if><c:if test="${card.COLOR == 'Color060' }">Color060</c:if><c:if test="${card.COLOR == 'Color070' }">Color070</c:if><c:if test="${card.COLOR == 'Color080' }">Color080</c:if><c:if test="${card.COLOR == 'Color090' }">Color090</c:if><c:if test="${card.COLOR == 'Color100' }">Color100</c:if> title" >${card.SUB_TITLE }</h1>
																			<p id="card-time">${card.AVAILABLE_TIME }</p>
																			<p id="adrss" title="${card.STORE_NAME}">${card.STORE_NAME}</p>
																			<p style="padding-bottom: 20px" id="xiangqing">${card.DESCRIPTION }</p>
																		</div>
																	</div>
																</div>
															</div>
														</c:if>
														
														<c:if test="${pd.type != 'card' }">
															<!-- 图文内容 -->
															<div
																class="layui-tab-item <c:if test="${pd.type == 'material'}">layui-show</c:if>">
																<div class="inner">
																	<div class="tab_cont_cover" id="newImg">
																		<div class="media_cover" id="opende"
																			onclick="opende('<%=basePath%>wxMenu/tuwenList.do')">
																			<span class="create_access"> <a
																				href="javascript:void(0);"> <i
																					class="layui-icon tab_icona">&#xe654;</i> <strong>从素材库中选择</strong>
																			</a>
																			</span>
																		</div>
																	</div>
																	<div id="textImg">
																		<div class="rowBoxCenter">
																			<p>
																				<span class="font-12 padding-30">保存于：${material.CREATE_TIME }</span>
																			</p>
																			<div class="widthImg">
																				<img src="" alt="">
																				<div class="wordTitle">
																					<p>${material.mList[0].TITLE }</p>
																				</div>
																				<div class="imgyulan" onclick="previewTuWen(0)">
																					<p class="text-center">预览</p>
																				</div>
																			</div>
																			<%-- <div class="borBottom">
	                                                          				<table width="100%" >
	                                                              				<tbody >
	                                                              					<tr>
	                                                                  					<td style="vertical-align: top"><p class="tarTileWid">${var.TITLE }</p></td>
	                                                                  					<td style="vertical-align: top;text-align: right"><div class="tarImgWid"><img src="<%=basePath%>uploadFiles/uploadImgs/${var.PATH }" alt=""></div></td>
	                                                              					</tr>
	                                                              				</tbody>
	                                                          				</table>
	                                                          				<div class="imgyulan" >
	                                                              				<p class="text-center" id="tuwen_${vs.index}">预览</p>
	                                                          				</div>
	                                                     		 		 </div> --%>
																		</div>
																		<input id="TUWEN_MEDIA_ID" type="hidden" value="${material.MEDIA_ID }"> 
																		<input id="TUWEN_SENDRECORD_ID" type="hidden" value="${material.SENDRECORD_ID }">
																		<c:if test="${pd.state != 'view'}">
																		<p id="shanchu" class="shanchu" onclick="deleteTuwen()">删除</p>
																		</c:if>
																	</div>
																</div>
															</div>
															<!-- 图片内容 -->
															<div class="layui-tab-item <c:if test="${pd.type == 'image' }">layui-show</c:if>"
																id="tupianItem">
																<div class="flieImg">
																	<div class="tab_cont_cover">
																		<div class="media_cover" id="beforeImg"
																			onclick="openImg('<%=basePath%>wxMenu/tupianList.do')">
																			<span class="create_access"> <a
																				href="javascript:void(0);"> <i
																					class="layui-icon tab_icona">&#xe654;</i> <strong>选择历史图片</strong>
																			</a>
																			</span>
																		</div>
																		<div class="media_cover" id="flieImg">
																			<input type="file" class="upFlie" id="upFlie"
																				name="upFile"> <span class="create_access">
																				<a href="javascript:void(0);"> <i
																					class="layui-icon tab_icona">&#xe654;</i> <strong>选择本地上传</strong>
																			</a>
																			</span>
																		</div>
																		<div id="divImg">
																			<div class="divImg ">
																				<img
																					src="<%=basePath%>uploadFiles/uploadImgs/${image.PATH }"
																					width="100%" id="successImg">
																				<div class="imgyulan" id="imgyulan"
																					onclick="yulanTuPian()">
																					<p class="text-center">预览</p>
																				</div>
																			</div>
																			<input id="IMG_MEDIA_ID" type="hidden" value="${image.MEDIA_ID }"> 
																			<c:if test="${pd.state != 'view'}">
																				<span id="deleteImg" class="shanchu" onclick="deleteImg()">删除</span>
																			</c:if>
																		</div>
																	</div>
																</div>
															</div>

															<!-- 文字内容 -->
															<div
																class="layui-tab-item <c:if test="${pd.type == 'text' }">layui-show</c:if>"
																id="wenziItem">
																<div class="layui-form-item layui-form-text"
																	style="margin-top: 30px;">
																	<div class="layui-input-block">
																		<textarea placeholder="请输入文字内容" class="layui-textarea" style="width: 100%" id="CONTENT" rows="10"
																			onkeyup="checkNum()" maxlength="300" <c:if test="${pd.state == 'view'}">readonly="readonly"</c:if>>${text.CONTENT}</textarea>
																		<p style="text-align: right">
																			还可以输入<span id="num">300</span>字，按下Enter键换行
																		</p>
																	</div>
																	<input id="TEXTMSG_ID" type="hidden" value="${text.TEXTMSG_ID }">
																</div>
															</div>

															<!-- 链接内容 -->
															<div class="layui-tab-item <c:if test="${pd.type == 'link' }">layui-show</c:if>"
																id="kaquanItem">
																<div class="borPadding">
																	<div class="layui-form-item">
																		<label class="layui-form-label"
																			style="background: #fff">跳转链接：</label>
																		<div class="layui-input-block">
																			<input type="text" id="linkContent" name="linkContent" lay-verify="title" autocomplete="off" placeholder="请输入开头以http或者https的完整链接"
																				class="layui-input" value="${link.content}" <c:if test="${pd.state == 'view'}">readonly="readonly"</c:if>>
																		</div>
																	</div>
																</div>
															</div>
														</c:if>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="lk-obj-box">
						<div class="lk-store-set">
							<p class="msg-title">群发对象设置：</p>
							<div class="msg-content" style="padding: 20px;height: 700px;position: relative;">
								<div class="layui-form-item">
									<label class="layui-form-label">性别：</label>
									<div class="layui-input-inline">
										<select name="sex">
											<option value="0" selected=""
												<c:if test="${pd.sex == ''}">selected="selected"</c:if>
												<c:if test="${pd.state == 'view'}">disabled</c:if>>全部</option>
											<option value="1"
												<c:if test="${pd.sex == 1}">selected="selected"</c:if>
												<c:if test="${pd.state == 'view'}">disabled</c:if>>男</option>
											<option value="2"
												<c:if test="${pd.sex == 2}">selected="selected"</c:if>
												<c:if test="${pd.state == 'view'}">disabled</c:if>>女</option>
										</select>
									</div>
								</div>
								<c:if test="${pd.mass_object == 'member'}">
									<div class="layui-form-item layui-form-text"
										style="margin-bottom: 6px;">
										<label class="layui-form-label">发送会员类型</label>
									</div>
									<div class="layui-form-item">
										<div class="layui-input-block" style="margin-left: 0;">
											<input type="radio" name="member" value="全部会员" title="全部会员"
												checked="" lay-filter="member"
												<c:if test="${pd.member == '全部会员'}">checked=""</c:if>
												<c:if test="${pd.state == 'view'}">disabled</c:if>>
											<input type="radio" name="member" value="按消费能力" title="按消费能力"
												data-type="consumeId" lay-filter="member"
												<c:if test="${pd.member == '按消费能力'}">checked=""</c:if>
												<c:if test="${pd.state == 'view'}">disabled</c:if>>
										</div>
										<div class="layui-input-block" style="margin-left: 0;">
											<input type="radio" name="member" value="按活跃度" title="按活跃度"
												data-type="liveId" lay-filter="member"
												<c:if test="${pd.member == '按活跃度'}">checked=""</c:if>
												<c:if test="${pd.state == 'view'}">disabled</c:if>>
											<input type="radio" name="member" value="按卡余额" title="按卡余额"
												data-type="amountId" lay-filter="member"
												<c:if test="${pd.member == '按卡余额'}">checked=""</c:if>
												<c:if test="${pd.state == 'view'}">disabled</c:if>>
										</div>
									</div>
									<c:if test="${pd.state != 'add'}">
										<c:if test="${pd.mass_object != 'member'}">
											<div id="tips-value" style="display: none" name="">
										</c:if>
										<c:if test="${pd.mass_object == 'member'}">
											<div id="tips-value" name="<c:if test="${pd.member == '按消费能力'}">consumeId</c:if><c:if test="${pd.member == '按活跃度'}">liveId</c:if><c:if test="${pd.member == '按卡余额'}">amountId</c:if>">
										</c:if>
									</c:if>
									<c:if test="${pd.state == 'add'}">
										<div id="tips-value" style="display: none" name="">
									</c:if>
									<c:if test="${pd.member != '全部会员'}">
										<div class="layui-form-item">
											<label class="layui-form-label" id="tip-lable">消费能力：</label>
											<div class="layui-input-inline">
												<select name="quiz2">
													<option value="高" selected=""
														<c:if test="${pd.quiz2 == '高'}">selected="selected"</c:if>
														<c:if test="${pd.state == 'view'}">disabled</c:if>>高</option>
													<option value="中"
														<c:if test="${pd.quiz2 == '中'}">selected="selected"</c:if>
														<c:if test="${pd.state == 'view'}">disabled</c:if>>中</option>
													<option value="低"
														<c:if test="${pd.quiz2 == '低'}">selected="selected"</c:if>
														<c:if test="${pd.state == 'view'}">disabled</c:if>>低</option>
												</select>
											</div>
										</div>
										<div class="see_memberLable">
<%--											<p class="title">查看门店会员标签：</p>--%>
											<div class="set-tip">
												<div class="layui-form-item">
													<div class="layui-input-inline store_select">
														<select name="storeName" id="storeName" lay-filter="storeTips">
															<c:forEach items="${list}" var="var" varStatus="vs">
																<option value="${var.STORE_ID}">${var.STORE_NAME}</option>
															</c:forEach>
														</select>
													</div>
													<font class="tips_set">当前的标签设置：</font>
												</div>
											</div>
										</div>
									</c:if>
									<div class="set-tip-box">
										<c:if test="${pd.member == '按消费能力'}">
											<p class="set-tip">消费能力高： 平均消费金额高于${pd.consume_high}</p>
											<p class="set-tip">消费能力中：
												平均消费金额在${pd.consume_high}-${pd.consume_low}之间</p>
											<p class="set-tip">消费能力低： 平均消费金额低于${pd.consume_low}</p>
										</c:if>
										<c:if test="${pd.member == '按活跃度'}">
											<p class="set-tip">活跃度高： 平均间隔时间小于${pd.live_high}</p>
											<p class="set-tip">活跃度中：
												平均间隔时间在${pd.live_high}-${pd.live_low}之间</p>
											<p class="set-tip">消活跃度低： 平均消费金额低于${pd.live_low}</p>
										</c:if>
										<c:if test="${pd.member == '按卡余额'}">
											<p class="set-tip">余额高：当前余额高于${pd.balance_high}</p>
											<p class="set-tip">余额中：
												当前余额在${pd.balance_high}-${pd.balance_low}之间</p>
											<p class="set-tip">余额高：当前余额低于${pd.balance_low}</p>
										</c:if>
									</div>
								</div>
							</c:if>
											<c:if test="${pd.type != 'card'}">
								<div class="layui-form-item" pane="">
									<label class="layui-form-label" style="width: 120px;">会员下机推送</label>
									<div class="layui-input-block">
										<input type="checkbox" name="send_type" value="user_down" lay-skin="switch"
										<c:if test="${pd.send_type == 'user_down'}">checked</c:if>
											   lay-filter="switchTest" lay-text="关闭|开启">
									</div>
								</div>
											<input type="hidden" name="markingcontext_id" id="markingcontext_id" value="${pd.markingcontext_id}">
								<div id="push_time" <c:if test="${pd.send_type != 'user_down'}">style="display: none"</c:if>>
									<div class="layui-form-item" style="color: #666">选择推送时间段:</div>
									<div class="layui-form-item">
										<div class="layui-inline" style="margin-right: 0">
											<div class="layui-input-inline" style="width: 133px">
												<input type="text" name="start_time" placeholder="开始时间" autocomplete="off" value="${pd.start_time}"
													   class="layui-input" id="start_time">
											</div>
											<div class="layui-form-mid" style="margin: 0 5px;">-</div>
											<div class="layui-input-inline" style="width: 134px">
												<input type="text" name="end_time" placeholder="结束时间" autocomplete="off" value="${pd.end_time}"
													   class="layui-input" id="end_time">
											</div>
										</div>
									</div>
								</div>
											</c:if>
							<c:if test="${pd.state == 'add'}">
								<div class="marke_btnBox">
									<span class="layui-btn layui-btn-normal" onclick="return toMarke()" style="width: 88px;padding:0 10px;">确定发送</span>
								</div>
							</c:if>
							<c:if test="${pd.state == 'view' && pd.type != 'card'}">
								<div class="marke_btnBox">
									<span class="layui-btn layui-btn-normal" onclick="return updateSendType()" style="width: 88px;padding:0 10px;">确定修改</span>
								</div>
							</c:if>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<div id="declareContent" style="display: none;">
		<h1>免责声明</h1>
		<p>尊敬的用户：</p>
		<p class="text-indent">您好：</p>
		<p class="text-indent">本公司的揽客平台具有微信群发接口申请和技术对接的功能。对接完毕后，网吧经营者或者该软件使用者（以下简称“用户”）可以通过此技术进行群发功能。本公司对此技术特此声明：</p>
		<p class="text-indent"><strong>1、本公司仅为技术提供者，发送内容由用户自行编辑，发送时间、发送方式等由用户自行决定，与本公司无关，所有内容均不反应本公司之意见。本公司无需也没有能力对发送内容、时间、方式等进行审核、监管，对内容的真实性、准确性、合法性概不负责，亦不承担任何法律责任。</strong></p>
		<p class="text-indent"><strong>2、用户使用本技术进行群发功能时不得违反国家法律法规政策、不得侵犯他人权利（包括但不限于骚扰、诽谤、知识产权侵权等行为）、不得群发垃圾信息、骚扰信息等，用户自行承担上述行为可能导致严重法律后果，本公司不承担任何法律责任，由此给本公司造成损失的，用户应当赔偿本公司的损失。</strong></p>
		<p class="text-indent"><strong>3、用户使用本技术进行群发功能时应当遵守微信群发规则，微信平台对发送内容、频率有严格限制，不当的营销内容、过高的发送频率均会被判定为过度营销骚扰用户，轻则导致封闭群发接口的后果、重则导致封闭微信公众号的后果。如果用户群发过程中，发生了违规被微信处罚等后果均由用户自行承担，本公司不承担任何责任。</strong></p>
		<p class="text-indent"><strong>凡是使用本技术的用户均应仔细阅读本声明，凡是用户使用本技术的行为将被视为对本声明全部内容的认可。</strong></p>
		<p class="foot">杭州网晶科技有限公司</p>
	</div>
</body>
<script src="<%=basePath%>newStyle/js/jquery-1.11.1.min.js"></script>
<script src="<%=basePath%>newStyle/layui/layui.js"></script>
<!-- 业务JS -->
<script src="<%=basePath%>newStyle/js/lk-public.js"></script>
<script src="<%=basePath%>newStyle/js/lk-message.js"></script>
<script src="<%=basePath%>newStyle/js/lk-memberMake.js"></script>
<script type="text/javascript">
	//页面校正
	function marke_check() {
		var title = $("#title").val();                 //消息标题
		var activtyType = $("#activtyType").val();     //活动类型
		var content = $("#activtyContent").val();      //活动内容
		var createtime = $("#createtime").val();       //活动时间
		var remark = $("#remark").val();               //备注(选填)
		var activityType = $("#activityType").val();   //活动详情选填类型
		var sex =$(".numbers option:selected").val();  //性别
		var TUWEN_MEDIA_ID=$("#TUWEN_MEDIA_ID").val(); //图文消息
		var IMG_MEDIA_ID=$("#IMG_MEDIA_ID").val();     //图片
		var CONTENT=$("#CONTENT").val();               //文字
		var CARD_ID=$("#CARD_ID").val();               //卡券
		var LINK=$("#linkContent").val();              //链接
		var type = $(".layui-this").attr("id");        //选择的类型
		if (title == "") {
			layer.tips('请填写消息标题', '#title', {
				tips : 3
			});
			$("#title").focus();
			return false;
		}
		if (activtyType == "") {
			layer.tips('请填写活动类型', '#type', {
				tips : 3
			});
			$("#activtyType").focus();
			return false;
		}
		if (activtyContent == "") {
			layer.tips('请填写活动内容', '#activtyContent', {
				tips : 3
			});
			$("#activtyContent").focus();
			return false;
		}
		if (createtime == "") {
			layer.tips('请填写活动时间', '#createtime', {
				tips : 3
			});
			$("#createtime").focus();
			return false;
		}
		console.log(LINK);
		if(type == "material"){
			$("#object_id").val(TUWEN_MEDIA_ID);
			$("#type").val('material');
		}else if(type == "image"){
			$("#object_id").val(IMG_MEDIA_ID);
			$("#type").val('image');
		}else if(type == "text"){
			$("#object_id").val(CONTENT);
			$("#type").val('text');
		}else if(type == "card"){
			$("#object_id").val(CARD_ID);
			$("#type").val('card');
		}else if(type == "link"){
			$("#object_id").val(LINK);
			$("#type").val('link');
		}
		return true;
	}

	//服务器校正保存方法
	function toMarke(){
		if (!marke_check()) {
			return false;
		}
		$(".marke_btnBox").attr("onclick","");
		$.ajax({
			type : "POST",
			url : '<%=basePath%>/memberMarke/toMarke.do',
			data : $('#Form').serialize(),
			dataType : 'json',
			beforeSend : loading("提交中"),
			success : function(data) {
				layer.closeAll();
                if(data.result == "false"){
                    if(data.err_type == 'start_time_err'){
                    	layer.alert("开始时间与已创建的" + data.message +"该时间段重复");
					}else if(data.result == "false" && data.err_type == 'end_time_err'){
                    	layer.alert("结束时间与已创建的" + data.message +"该时间段重复");
					}else{
                        message(data.message);
					}
                }else{
                    if ("success" == data.result) {
                        message(data.message);
                        setTimeout(function(){
                            //关闭当前页面
                            window.opener = null;
                            window.open("", "_self");
                            window.close();
                        }, 700);
                        opener.location.reload();//刷新父页面
                    }else{
                        $(".marke_btnBox").attr("onclick","return toMarke()");
                    }
				}


			},
			error : function() {
				layer.closeAll();
				layer.msg("系统繁忙，请稍后再试！");
				$(".marke_btnBox").show();
				$(".marke_btnBox").attr("onclick","return toMarke()");
			}
		});
	}
	function updateSendType() {

	    if($("#send_type").val() != ''){
	        if($("#start_time").val() == ''){
	            layer.msg("请输入开始时间");
	            return false;
			}
            if($("#end_time").val() == ''){
                layer.msg("请输入结束时间");
                return false;
            }
            if($("#start_time").val() > $("#end_time").val()){
                layer.msg("开始时间必须大于结束时间");
                return false;
			}
		}

        var data = $('#Form').serialize();
		$.post('<%=basePath%>memberMarke/updateSendType.do', data, function (res) {
            console.log(res);
            if(res.result == "false" && res.err_type == 'start_time_err'){
                layer.alert("开始时间与已创建的" + res.message +"该时间段重复");
			}
            if(res.result == "false" && res.err_type == 'end_time_err'){
                layer.alert("结束时间与已创建的" + res.message +"该时间段重复");
            }
            if(res.result == "true"){
                layer.msg(res.message);
			}
        })
    }
	
</script>
</html>