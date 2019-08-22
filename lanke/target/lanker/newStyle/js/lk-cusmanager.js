$(top.hangge());//关闭遮罩层
var tableId = "cusmanager_list";
function getRealPath(){
	  //获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
	  var curWwwPath=window.document.location.href;
	   //获取主机地址之后的目录，如： myproj/view/my.jsp
	  var pathName=window.document.location.pathname;
	  var pos=curWwwPath.indexOf(pathName);
	  //获取主机地址，如： http://localhost:8083
	  var localhostPaht=curWwwPath.substring(0,pos);
	  //获取带"/"的项目名，如：/myproj
	  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	 //得到了 http://localhost:8083/myproj
	  var realPath=localhostPaht+projectName;
		if(projectName == "/lanker"){
			return realPath
		}else{
			return localhostPaht
		}
	}
layui.use(['table','laypage','form','layer','laydate'], function(){
  		var table = layui.table;
  		var form = layui.form;
  		var laypage = layui.laypage;
  		var laydate = layui.laydate;
  		 //日期范围
  		laydate.render({
    		elem: '#startTime'
			,type: 'datetime'
    		,theme: '#393D49'
  		});
  		laydate.render({
    		elem: '#endTime'
            ,type: 'datetime'
    		,theme: '#393D49'
  		});
  		var cols=[
  			     {type:'checkbox',field:'id'}
 				 ,{type:'numbers', width:50, title: '序号', sort: true, fixed: 'left', totalRowText: '合计：'}
			    ,{field:'INTENET_NAME', title: '公众号'}
 				,{field:'STORE_NAME', title: '门店'}
 				,{field:'lanker_version', title: '揽客版本'}
 				,{field:'CREATE_TIME', title: '入驻时间'}
 				,{field:'EXPIRATION_TIME',title: '加V到期时间'}
 				,{field:'CHOOSE_PACKAGE', title: '付费状态', templet:function (d) {
					if(d.CHOOSE_PACKAGE == 0){
						return '<span>免费一个月</span>';
					}else if(d.CHOOSE_PACKAGE  == 1){
						return '<span>付费一年</span>';
					}else{}
						return '<span>关系延续</span>';
				}}
 				,{field:'link_state', title: '计费链接状态'}
 				,{field:'fannums',title: '粉丝数',totalRow: true}
 				,{field:'storenums',title: '会员数' ,totalRow: true}
 				,{field:'totalprice',title: '总充值金额',totalRow: true }
 			];
  		table.render({
  		  	 id: 'payReload'
    		,elem: "#cusmanager_list"
    		,url:getRealPath()+"/cusManager/listManages"
    		,cellMinWidth: 95
			,totalRow: true //开启合计行
			,request:{
				pageName:'currentPage',
				limitName:'showCount'
			}
    		,cols: [cols]
    		,done: function(res, curr, count){
					pageReload(count,res.showCount)
  			 }
  			,text: {none: '暂无相关数据'}
  		  });

  		 table.on('tool(pay)', function(obj){
				var data = obj.data;
				var layEvent = obj.event;
				var tr = obj.tr;
				if(layEvent == 'refresh'){ //刷新
						$.ajax({
							type: "POST",
							url: getRealPath()+'/indexMember/swrecharge',
							data : {merOrderId:data.merOrderId},
							dataType : 'json',
							async:false,
							beforeSend : loading("刷新中"),
							success : function(data2) {
								layer.closeAll();
								location.reload();//刷新页面
							},
							error:function(){
								layer.closeAll();
								location.reload();//刷新页面
							}
						});
				}
			});

  		  function pageReload(count, showCount){
				laypage.render({
					elem: 'page'
					,count: count  //数据总数量
					,limit:showCount
					,limits:[10, 20, 30, 40, 50,60,70,80,90,100]
					,curr: location.hash.replace('#!fenye=', '') //获取起始页
					,hash: 'fenye' //自定义hash值
					,layout:['prev', 'page', 'next','skip','count','limit']
					,theme: '#41a7f0'
					,jump: function(obj, first){
						//首次不执行
						if(!first){
							//console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
							//console.log(obj.limit); //得到每页显示的条数

							table.reload('payReload', {
								where: { //设定异步数据接口的额外参数，任意设
									state: $("#state").val(),
								}
							});

						}
					}
				});
			}

			$("#toSearch").click(function(){
				table.reload('payReload', {
					where: { //设定异步数据接口的额外参数，任意设
						state: $("#state").val(),
						keywords: $("#keywords").val(),
						store_id: $("#store_id").val(),
						startTime:$("#startTime").val(),
						endTime:$("#endTime").val(),
						pay_state:$("#pay_state_jsp").val(),
					}
				});
			})




	var active = {
		toExcel: function () {
			var checkStatus = table.checkStatus(tableId),
				checkData = checkStatus.data;//获得选中的数据,对象数组

			if (checkData.length == 0)
				return layer.msg("请选择数据");

			var str = "";
			for (var i = 0; i < checkData.length; i++) {
				str += checkData[i].STORE_ID;
				if(i != checkData.length-1){
					str += ",";
				}
			}

			//打印
			window.location.href = 'storeReview/excel.do?keywords=' + $("#keywords").val() + '&STATE=' + $("#STATE").val() + '&str=' + str;
		}
	};

  });


