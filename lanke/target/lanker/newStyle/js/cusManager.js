var moduleUrl = "cusManager/";
var module = "cusManager";
var moduleName = "揽客客户端管理";
var getListUrl = moduleUrl + "listManages";
var getSumUrl = moduleUrl + "getSum";
var goEditUrl = moduleUrl + "toEdit";
var delUrl = moduleUrl + "del";
var delMoreUrl = moduleUrl + "delMore";
var ajaxSave = moduleUrl + "save";
var tableId = "layTable";
var iframeBtn = "laySubmit";

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

layui.define(['layer','table','jquery'],function (exports) {
	var $ = layui.jquery,
		layer = layui.layer,
		table = layui.table,
		laypage = layui.laypage;

	$(top.hangge());//关闭加载状态

	//配置数据表格
	table.render({
		elem:"#"+tableId,
		cols:[[
			{type:'checkbox',field:'id'}
			,{type:'numbers', width:50, title: '序号'}
			,{field:'INTENET_NAME', title: '公众号',  align:'center',width:90}
			,{field:'TELEPHONE', title: '号码', align:'center', width:120}
			,{field:'STORE_NAME', title: '门店',  align:'center',width:162}
			,{field:'lanker_version', align:'center', title: '揽客版本', width:120}
			,{field:'CREATE_TIME',  align:'center',title: '入驻时间', width:184}
			,{field:'EXPIRATION_TIME', align:'center',title: '加V到期时间'}
			,{field:'CHOOSE_PACKAGE',  align:'center',title: '付费状态', templet:function (d) {
					if(d.CHOOSE_PACKAGE == 0){
						return '<span>免费一个月</span>';
					}else if(d.CHOOSE_PACKAGE  == 1){
						return '<span>付费一年</span>';
					}else{
						return '';
					}
				}}
			,{field:'link_state',align:'center',title: '计费链接状态'}
			,{field:'fannums', align:'center',title: '粉丝数'}
			,{field:'storenums', align:'center',title: '会员数' }
			,{field:'totalprice', align:'center',title: '总充值金额' }
		]],//表头
		skin:'row',//表单风格
		even:true,//开启隔行背景
		page:false,//开启分页
		url:getListUrl,
		method:'post',
		request:{
			pageName:'currentPage',
			limitName:'showCount'
		},
		parseData:function (res) {

		}
		,done: function(res, curr, count) {
			//分页
			pageReload(count, res.showCount);
		}

	});

	$("#toSearch").click(function(){
		tableReload(1,10);//重新加载表格
		sumData();//搜索的时候统计数量
	})
	//页面加载的时候统计数量
	sumData();
	//统计数量
	function  sumData(){
		var url = getSumUrl;
		var obj = new Object();
		obj.keywords=$("#keywords").val();
		obj.vesion = $("#vesion").val();
		obj.paystatus = $("#paystatus").val();
		$.post(url, obj, function (res) {
			var sumfans = (res.data[0].sumfans==undefined)?0:res.data[0].sumfans;
			var sumprice = (res.data[0].sumprice==undefined)?0:res.data[0].sumprice;
			var sumstore = (res.data[0].sumstore==undefined)?0:res.data[0].sumstore;
			$("#sumfans").html(sumfans);
			$("#sumstore").html(sumstore);
			$("#sumprice").html(sumprice);
		});
	}
	function pageReload(count,showCount){
		laypage.render({
			elem: 'layPage'
			,count: count  //数据总数量
			, limit: showCount //每页展示的数量
			,limits:[10, 20, 30, 40, 50,60,70,80,90,100]
			,curr: location.hash.replace('#!fenye=', '') //获取起始页
			,hash: 'fenye' //自定义hash值
			,layout:['prev', 'page', 'next','skip','count','limit']
			,theme: '#41a7f0'
			,jump: function(obj, first){
				//首次不执行
				if(!first){
					tableReload(obj.curr, obj.limit);
				}
			}
		});
	}
	function tableReload(curr,limit){
		table.reload(tableId, {
			where: { //设定异步数据接口的额外参数，任意设
				keywords: $("#keywords").val(),
				vesion: $("#vesion").val(),
				paystatus: $("#paystatus").val(),
				currentPage: curr,
				showCount: limit
			}
		});
	}
	//表单中的点击事件
	$('.mail-btn').on('click',function () {
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});
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
			window.location.href = 'cusManager/excel.do?keywords=' + $("#keywords").val() + '&vesion=' + $("#vesion").val() +'&paystatus=' + $("#paystatus").val() + '&str=' + str;
		}
	};
	exports(['cusManager'], {});
});