/**

 @Name：layuiAdmin 主页控制台
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：GPL-2
    
 */


layui.define(function(exports){
  
  /*
    下面通过 layui.use 分段加载不同的模块，实现不同区域的同时渲染，从而保证视图的快速呈现
  */
  
  layui.use(['laydate','form'], function(){
	  //日期范围
	  var laydate = layui.laydate
	  	  ,form = layui.form;
	  laydate.render({
	    elem: '#search_time'
	    ,range: true
	  });
	  //门店选择监听
	  form.on('select(store)', function(data){
		  $("#store_id").val(data.value);
		  var name =  $("#modules").find("option:selected").text();
		  $("#store_name").val(name);
	  });      
  })
  
  //区块轮播切换
  layui.use(['admin', 'carousel'], function(){
    var $ = layui.$
    ,admin = layui.admin
    ,carousel = layui.carousel
    ,element = layui.element
    ,device = layui.device();

    //轮播切换
    $('.layadmin-carousel').each(function(){
      var othis = $(this);
      carousel.render({
        elem: this
        ,width: '100%'
        ,arrow: 'none'
        ,interval: othis.data('interval')
        ,autoplay: othis.data('autoplay') === true
        ,trigger: (device.ios || device.android) ? 'click' : 'hover'
        ,anim: othis.data('anim')
      });
    });
    
    element.render('progress');
    
  });

  //数据概览
  layui.use(['carousel', 'echarts'], function(){
    var $ = layui.$
    ,carousel = layui.carousel
    ,echarts = layui.echarts;
    var income_url = $("#income_url").val();
	var user_url = $("#user_url").val();
	var store = $("#store_id").val();
	var list = $("#list").val();
	var time = "";
	
    //顶部搜索
    $("#toSearch").click(function(){
    	store = $("#store_id").val(); //下拉框选择门店id
    	var time = $("#search_time").val(); //搜索框选择搜索时间
    	income(income_url,time,store,list);
		user(user_url,time,store,list);
		$("#data_store").html($("#store_name").val())
    })
    
    var income_echarts = '', options =
      //收入图表设置
      {
        title: {
          text: '',
          x: 'center',
          textStyle: {
            fontSize: 14
          }
        },
        tooltip : {
          trigger: 'axis'
        },
        xAxis : [{
          type : 'category',
          boundaryGap : false,
          data: []
        }],
        yAxis : [{
          type : 'value'
        }],
        series : [{
          name:'收入',
          type:'line',
          smooth:true,
          itemStyle: {normal: {areaStyle: {type: 'default'}}},
          data: []
        }]
      }

    ,elemDataView = $('#LAY-index-dataview').children('div')
    ,renderDataView = function(index){
      income_echarts = echarts.init(elemDataView[index], layui.echartsTheme);
      income(income_url,time,store,list);// 初始化收入数据
      window.onresize = income_echarts.resize;
    };
    //没找到DOM，终止执行
    if(!elemDataView[0]) return;
    renderDataView(0);
    

    //请求收入数据
    function income(url,time,store,list){
    	$.ajax({
		    type: "POST",
			url: url,
			data: {time:time,store:store,list:list},
			dataType:'json',
			beforeSend:beforeSend(),
			success: function(data){
				layer.closeAll();
				options.title.text = data.chart_title;
				options.xAxis[0].data = data.categories;
				options.series[0].data = data.data;
				income_echarts.setOption(options,true);

				$("#income_num").html(data.income)
				$("#income_title").html(data.income_title)
			},
		    error:function(){
		    	 layer.closeAll();
				 layer.msg("系统繁忙，请稍后再试！");
                 return false;
			}
		});

//    	$.get()
    }
   
    //时间搜索(日/周/月/年/总)
    $(".layui-btn-xs").click(function(){
    	store = $("#store_id").val();
    	time = $(this).data("type");
    	$(".layui-btn-xs").removeClass("layui-btn-normal");
    	$(".layui-btn-xs").addClass("layui-btn-primary");
    	$(this).removeClass("layui-btn-primary");
    	$(this).addClass("layui-btn-normal");
    	if(time == "day" || time == "week" || time == "month" || time == "year"){
    		income(income_url,time,store,list);
    	}else if(time == "sum"){//总收入
    		income(income_url,time,store,list);
    	}else if(time == "user_sum"){//总粉丝/总会员
    		user(user_url,time,store,list);
    	} else {
    		user(user_url,time,store,list);
    	}
    })
    
    var user_echarts = '', options2 =
      //用户图表设置
      {
        title: {
          text: '最近一周新增的用户量',
          x: 'right',
          textStyle: {
            fontSize: 14
          }
        },
        calculable : true,
        tooltip : { //提示框
          trigger: 'axis',
        },
        legend: {
          show :true,
          type :'plain',
          selectedMode :true,
          data:['会员数','粉丝数'],
        },
        xAxis : [{ //X轴
          type : 'category',
          data : []
        }],
        yAxis : [
        	{  //Y轴
          		type : 'value',
          		name : '会员数',
        	},
        	{  //Y轴
          		type : 'value',
          		name : '粉丝数',
        	}
        ],
        series : [
        	{ //内容
          		type: 'line',
          		name : '会员数',
          		data:[],
        	},
        	{ //内容
          		type: 'line',
          		name : '粉丝数',
          		yAxisIndex: 1,
          		data:[],
        	}
        ]
      }

    ,elemDataView2 = $('#LAY-index-user').children('div')
    ,renderDataView2 = function(index){
      user_echarts = echarts.init(elemDataView2[index], layui.echartsTheme);
      user(user_url,time,store,list);
      window.onresize = user_echarts.resize;
    };
    //没找到DOM，终止执行
    if(!elemDataView2[0]) return;
    renderDataView2(0);
    
    //请求用户数据
    function user(url,time,store,list){
    	$.ajax({
		    type: "POST",
			url: url,
			data: {time:time,store:store,list:list},
			dataType:'json',
			beforeSend:beforeSend(),
			success: function(data){
				 layer.closeAll();
                 options2.title.text = data.chart_title;
                 options2.xAxis[0].data = data.categories;
                 options2.series[0].data = data.member;
                 options2.series[1].data = data.fans;
                 user_echarts.setOption(options2,true)

				 $("#member_num").html(data.member_num);
				 $("#member_title").html(data.member_title);
				 $("#fans_num").html(data.fans_num);
				 $("#fans_title").html(data.fans_title);
			},
		    error:function(){
		    	 layer.closeAll();
				 layer.msg("系统繁忙，请稍后再试！");
                 return false;
			}
		});
    }
    
    function beforeSend(){
    	layer.msg('加载中', {
    		  icon: 16
    		  ,shade: 0.01
    	});
    }
    //监听数据概览轮播
    var carouselIndex = 0;
    carousel.on('change(LAY-index-dataview)', function(obj){
      renderDataView(carouselIndex = obj.index);
    });
    
    //监听侧边伸缩
    layui.admin.on('side', function(){
      setTimeout(function(){
        renderDataView(carouselIndex);
        renderDataView2(carouselIndex);
      }, 300);
    });
    
    //监听路由
    layui.admin.on('hash(tab)', function(){
      layui.router().path.join('') || renderDataView(carouselIndex);
    });
  });

  
  exports('console', {})
});