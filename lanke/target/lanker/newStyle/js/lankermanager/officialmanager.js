var moduleUrl = "officialmanager/";
var module = "officialmanager";
var moduleName = "公众号管理";
var getListUrl = moduleUrl + "getList";
var updateTokenUrl = moduleUrl + "getUpdateToken";
var getUpdateUrl = moduleUrl + "toUpdate";
var tableId = "layTable";

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
			,{field:'USERNAME', title: '用户名', width:100}
			,{field:'NAME', title: '昵称', width:100}
			,{field:'INTENET_NAME', title: '公众号'}
			,{field:'WECHAT_ID', title: '公众号appid'}
			,{field:'INTENET_ID',title: '公众号的原始ID'}
			,{field:'STATE', title: '状态',width:100,templet:function (d) {
					if(d.STATE == 0){
						return '<span>注册</span>';
					}else if(d.STATE  == 2){
						return '<span>正式</span>';
					}else if(d.STATE  == 3){
						return '<span>禁用</span>';
					}else if(d.STATE  ==4){
						return '<span>流失</span>';
					}else if(d.STATE  ==5){
						return '<span>订阅号</span>';
					}else{
						return '';
					}
				}}
			,{field:'PHONE', title: '电话号码'}
			/*,{field: 'HEAD_IMG',title: '产品图片',templet:'<div><img src="{{ d.HEAD_IMG }}"></div>'}*/
			,{field:'HEAD_IMG',width:80,title:'头像',templet:'<div><img width="30px" src="{{ d.HEAD_IMG}}" ></div>'}
			,{field:'QRCODE_URL',width:120,title:'二维码',templet:'<div><img src="{{ d.QRCODE_URL}}"></div>'}
			,{field:'CREATE_TIME',title: '创建时间' }
			,{field:'make',title: '操作', align:'center', toolbar: '#barDemo',fixed: 'right', width:200}
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

	table.on('tool(layTable)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
		var url = getUpdateUrl;
		var url2 = updateTokenUrl;
		var data = obj.data ;//获得当前行数据
		var layEvent = obj.event; //获得 lay-event 对应的值
        console.log(layEvent);
        if(layEvent=="edit"){
			$.post(url, data, function (res) {
				if(res.message){
					layer.msg('更新成功');
					tableReload(1,10);
				}else{
					layer.msg("更新失败");
				}
			});
		}else if(layEvent=="edit2"){
			$.post(url2, data, function (res) {
				if(res.message){
					layer.msg('更新成功');
					tableReload(1,10);
				}else{
					layer.msg("更新失败");
				}
			});
		}

	});

	$("#toSearch").click(function(){
		tableReload(1,10);//重新加载表格
	})

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
                state: $("#state").val(),
				currentPage: curr,
				showCount: limit
			}
		});
	}

	exports(['officialmanager'], {});//提供外部访问接口
});