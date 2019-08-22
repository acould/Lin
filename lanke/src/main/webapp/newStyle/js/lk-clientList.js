$(top.hangge());//关闭加载状态

$("#lk_storeLeft_list li").click(function () {
	$("#lk_storeLeft_list li").removeClass("active");
	$(this).addClass("active")
})

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
    		,theme: '#393D49'
  		});
  		laydate.render({
    		elem: '#endTime'
    		,theme: '#393D49'
  		});
  		var cols =[	 {type:'numbers', width:50, title: '序号'}
					,{title: '操作', align:'center', width: 200, templet:function (d) {
						var detailBtn = '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="detail"><i class="layui-icon layui-icon-detail"></i>详情</a>';
                    	var setBtn = '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="set"><i class="layui-icon layui-icon-detail"></i>设置扫码</a>';
                    	var getOutBtn = '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="getOut"><i class="layui-icon layui-icon-detail"></i>踢出连接</a>';

                    return detailBtn + setBtn + getOutBtn;
					}}
					,{field:'company_name', title: '企业全称'}
					,{field:'store_name', title: '门店名称'}
					,{field:'pubwin_store_id', title: '计费ID'}
					,{field:'store_id',width:100,title: '激活码'}
					,{field:'expiration_time', title: '到期时间'}
					,{field:'telephone', title: '门店电话'}
					,{field:'agent_name',title: '代理商'}
					,{field:'agent_phone',title: '代理商电话'}
					,{field:'online_state',title: '在线状态' }
					,{field:'online_time',title: '上次在线时间'}];
		if($("#state").val() == 1){
	  		cols =[	 {type:'numbers', width:50, title: '序号'}
					,{title: '操作', align:'center', width: 200, templet:function (d) {
                        var detailBtn = '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="detail"><i class="layui-icon layui-icon-detail"></i>详情</a>';
                        var setBtn = '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="set"><i class="layui-icon layui-icon-detail"></i>设置扫码</a>';
                    	var getOutBtn = '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="getOut"><i class="layui-icon layui-icon-detail"></i>踢出连接</a>';

                        return detailBtn + setBtn + getOutBtn;
					 }}
					,{field:'company_name', title: '企业全称'}
					,{field:'store_name', title: '门店名称'}
					,{field:'pubwin_store_id', title: '计费ID'}
					,{field:'store_id',width:100,title: '激活码'}
					,{field:'expiration_time', title: '到期时间'}
					,{field:'telephone', title: '门店电话'}
					,{field:'online_state',title: '在线状态' }
					,{field:'online_time',title: '上次在线时间'}];
		}
  		table.render({
  		  	 id: 'payReload'
    		,elem: "#client_list"
    		,url:getRealPath()+'/client/clientLists'
    		,cellMinWidth: 100
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
							table.reload('payReload', {
								where: { //设定异步数据接口的额外参数，任意设
									online_state: $("#online_state").val(),
									keywords: $("#keywords").val(),
									currentPage:obj.curr,
									showCount:obj.limit
								}
							});
									
						}
					}
				});
			}
			$("#toSearch").click(function(){
				table.reload('payReload', {
					where: { //设定异步数据接口的额外参数，任意设
						online_state: $("#online_state").val(),
						keywords: $("#keywords").val(),
					}
				});
			})


    //表格里的编辑和删除按钮触发
    table.on('tool(pay)', function (obj) {
        if(obj.event == 'detail'){
        	var store_id = obj.data.store_id;
        	var store_name = obj.data.store_name;
            client_detail(store_id,store_name);
        }else if(obj.event == 'set'){
        	var store_id = obj.data.store_id;
            setQrParam(store_id);
		}else if(obj.event == 'getOut'){
			var store_id = obj.data.store_id;
			getOut(store_id);
		}
    });


    //配置数据表格
    table.render({
        elem:"#detailTable",
        cols:[[
            {type:'numbers'},
            {field:'create_time',title:'连接/断开时间',
                templet:function (d) {
                    var span = '<span>'+d.create_time.toString()+'</span>';
                    return span;
                }
            },
            {field:'status',title:'状态',
                templet:function (d) {
                    if(d.status == '2'){
                        return '<span>断开连接</span>';
                    }else if(d.status == '1'){
                        return '<span>连接</span>';
                    }else if(d.status == '3'){
                        return '<span>异常断开连接</span>';
                    }else if(d.status == '4'){
                        return '<span>到期断开连接</span>';
                    }
                }
            }
        ]],//表头
        skin:'row',//表单风格
        even:true,//开启隔行背景
        page:false,//开启分页
        url:'client/loadClientDetail?store_id='+$("#store_id").val(),
        method:'post',
        request:{
            pageName:'currentPage',
            limitName:'showCount'
        },
        parseData:function (res) {
            //接口返回({"code":0,"msg":"ok","count":1000,"data":[{},{}]})
            // res.status;//接口状态
            // res.message;//提示信息
            // res.total;//数据长度
            // res.data.item//数据列表
        }
    });


  });

function client_detail(store_id,store_name) {
    layer.open({
        btn: ['关闭'],
        type: 2,
        title: store_name+"——客户端连接情况",
        shadeClose: false,
        closeBtn: 0,
        shade: 0.8,
        area: ['800px', '500px'],
        fixed: false, //不固定
        content: ['client/clientDetail?store_id='+store_id],
    })
}
//设置扫码参数
function setQrParam(store_id) {
    var obj = new Object();
    obj.store_id = store_id;
	$.post('client/setParam',obj,function (res) {
		if(res.result == "true"){
            layer.msg("设置成功");
		}else{
            layer.msg(res.message);
        }
    })
}

//踢出连接
function getOut(store_id) {
	var obj = new Object();
	obj.store_id = store_id;
	console.log(obj);
	$.post('client/getOut',obj,function (res) {
		console.log(res);
		if(res.result == "true"){
			layer.msg("踢出成功");
		}else{
			layer.msg(res.message);
		}
	})
}

function setAllQrParam() {
    $.post('client/setAllQrParam','',function (res) {
        console.log(res);
    	if(res.result = "true"){
            layer.msg("发送成功");
		}
    })
}