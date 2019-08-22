$(top.hangge());//关闭加载状态

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
 				 {type:'numbers', width:50, title: '序号'}
 				,{field:'store_name', title: '门店名称'}
 				,{field:'business_number', title: '商户号'}
 				,{field:'store_id', title: '计费ID'}
 				,{field:'user_name',title: '会员姓名'}
 				,{field:'carded', title: '会员卡号'}
 				,{field:'merOrderId', title: '订单号'}
 				,{field:'createtime',title: '充值时间',"templet":"#createtime"}                 	
 				,{field:'memo',title: '充值类型',
                    templet:function (d) {
                        if(d.memo == null){
                            return '<span>揽客充值</span>';
                        }else {
                            return '<span>'+d.memo+'</span>';
                        }
                    }
                }
 				,{field:'rincipal_balance',title: '充值金额'}
 				,{field:'reward_balance',title: '奖励金额' }
 				,{field:'pay_state',title: '充值状态',
                    templet:function (d) {
                        if(d.memo != null && d.memo.indexOf("揽客抵用券") >= 0){
                            return '<span>充值成功</span>'
                        }

                        if(d.pay_state == "a"){
                            return '<span>充值成功</span>'
                        }else
                        if(d.pay_state == "2"){
                            return '<span>支付成功</span>'
                        }else
                        if(d.pay_state == "b"){
                            return '<span>充值处理中</span>'
                        }
                    }
                }
 				,{field:'make',title: '操作', align:'center',fixed: 'right',
                    templet:function (d) {
                        var detailBtn = '<a class="btn btn-sm btn-primary" lay-event="refresh"><i class="layui-icon" style="padding-right: 2px">&#xe669;</i>刷新</a>';
                        if(d.memo != null && d.memo.indexOf("揽客抵用券") >= 0){
                            return ''
                        }
                        if(d.pay_state != "a"){
                            return detailBtn;
                        }else{
                            return '';
                        }
                    }
                }
 			];
		if($("#state").val() == "internet"){
			cols=[
				 {type:'numbers', width:50, title: '序号'}
				,{field:'store_name', title: '门店名称'}
				,{field:'store_id', title: '计费ID'}
				,{field:'user_name',title: '会员姓名'}
				,{field:'carded', title: '会员卡号'}
				,{field:'merOrderId', title: '订单号'}
				,{field:'createtime',title: '充值时间',"templet":"#createtime"}
                ,{field:'memo',title: '充值类型',
                    templet:function (d) {
                        if(d.memo == null){
                            return '<span>揽客充值</span>';
                        }else {
                            return '<span>'+d.memo+'</span>';
                        }
                    }
                }
				,{field:'rincipal_balance',title: '充值金额'}
				,{field:'reward_balance',title: '奖励金额' }
				,{field:'pay_state',title: '充值状态',
                    templet:function (d) {
                        if(d.memo != null && d.memo.indexOf("揽客抵用券") >= 0){
                            return '<span>充值成功</span>'
                        }
                        if(d.pay_state == "a"){
                            return '<span>充值成功</span>'
                        }else{
                            return '<span>充值处理中</span>'
                        }
                    }

                }
				,{field:'make',title: '操作', align:'center', fixed: 'right',
                    templet:function (d) {
                        var detailBtn = '<a class="btn btn-sm btn-primary" lay-event="refresh"><i class="layui-icon" style="padding-right: 2px">&#xe669;</i>刷新</a>';
                        if(d.memo != null && d.memo.indexOf("揽客抵用券") >= 0){
                            return ''
                        }
                        if(d.pay_state != "a" ){
                            return detailBtn;
                        }else{
                            return '';
                        }
                    }
                }
			]
		}
  		table.render({
  		  	 id: 'payReload'
    		,elem: "#rechargeReport_list"
    		,url:getRealPath()+'/rechargeReport/listManages?state='+$("#state").val()
    		,cellMinWidth: 95
			,request:{
				pageName:'currentPage',
				limitName:'showCount'
			}
    		,cols: [cols]
    		,done: function(res, curr, count){
    				//如果是异步请求数据方式，res即为你接口返回的信息。
    				//如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
   					//  console.log(res);
    				//得到当前页码
    				//console.log(curr); 
    				//得到数据总量
    				//console.log(count);
    				

					
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
									keywords: $("#keywords").val(),
									store_id: $("#store_id").val(),
									startTime:$("#startTime").val(),
									endTime:$("#endTime").val(),
									pay_state:$("#pay_state_jsp").val(),
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
						state: $("#state").val(),
						keywords: $("#keywords").val(),
						store_id: $("#store_id").val(),
						startTime:$("#startTime").val(),
						endTime:$("#endTime").val(),
						pay_state:$("#pay_state_jsp").val(),
					}
				});
			})
  });

  
  