// <%
// String path = request.getContextPath();
// String basePath = request.getScheme() + "://"
//     + request.getServerName() + ":" + request.getServerPort()
//     + path + "/";
// %>
console.log(data,"data");
var store=[];
var storeNo=[];
var I = 1;
var storeId = 0;
var Ind=0;
var time='';
var li='';
var INT='';
var WEEK='';
for(var i=0;i<data.length;i++){
 $(".tbody").append(`<tr style="text-align:center">
 <td class='center' style="vertical-align: middle">
        <label class="pos-rel"><input type='checkbox' name='ids' class="ace" /><span class="lbl"></span></label>
    </td>
    <td>
    <input value="${I}" class="i" readonly="readonly">
</td>
    <td>
     ${data[i].store_name}
</td>
<td>
 ${data[i].awardType}
</td>
<td style="display: flex;justify-content: center">
<div class="hidden-sm hidden-xs btn-group">
<a class="btn btn-sm btn-success edit" title="编辑">
<i class="layui-icon" style="padding-right: 4px">&#xe642;</i>编辑
</a>
<a class="btn btn-sm btn-danger del">
<i class="layui-icon" style="padding-right: 4px">&#xe640;</i>删除
</a>
</div>
</td>
</tr>`)
 I++
};
$("body").on("click",".edit",function(){
 var i = $(this).parent().parent().parent().index();
 Ind=1
 li = document.querySelectorAll(".INTEGRAL_SEND");
 $(".mendian").css("display","none")
 storeId =data[$(this).parent().parent().parent().index()].store_id
 $.post("intintegral/edit.do?store_id="+storeId ,function(res){
  console.log(res,"res111");
  console.log(data,"签到")
  if(data[i].awardType=="签到"){
   ind=2;
   $(".two").css("checked","checked");
   $(".five").remove();
   $(".saoma").css("display","none");
   $(".qiandao").css("display","block");
   $(".jifen").val(res.data.data[0].INTEGRAL_SEND);
   if(res.data.data[0].WEEKEND_SEND==-1){
    $(".zhoumo input").prop("checked",false);
   }else{
    $("#WEEKEND_SEND").val(res.data.data[0].WEEKEND_SEND)
   }

   setTimeout(function(){
    $(".tan").css("display","block")
   },500)
  }else if(data[i].awardType=="上机积分"){
   ind=5
   $(".two").remove();
   $(".five").css("checked","checked");
   $(".saoma").css("display","block");
   $(".qiandao").css("display","none");
   setTimeout(function(){
    time=res.data.data[0].sign_time_set;
    time=time.split(",")
    for(var i=0;i<li.length;i++){
     $(li[i]).val(time[i])
     // console.log(time[i])
    }
   },100)
   setTimeout(function(){
    $(".tan").css("display","block")
   },500)
  }else if(data[i].awardType==""){
   $(".tan").css("display","none");
   setTimeout(function(){
    layer.msg("暂无奖励类别");
   },200)
  }else{
   Ind=1;
   $(".two").css("checked","checked");
   $(".saoma").css("display","none");
   $(".qiandao").css("display","block");
   time=res.data.data[1].sign_time_set;
   INT=res.data.data[0].INTEGRAL_SEND;
   WEEK=res.data.data[0].WEEKEND_SEND
   $(".jifen").val(INT);
   if(WEEK==-1){
    $(".zhoumo input").prop("checked",false);
   }else{
    $("#WEEKEND_SEND").val(WEEK)
   }
   setTimeout(function(){
    $(".tan").css("display","block")
   },500)
  };
 })
 // $.post("<%=basePath%>signmanager/sign.do?store_id="+"${result.pdBind.store_id}", function (data) {
 //
 //
 // })
})
$("body").on("click",".sa",function() {//保存
 if (ind == 2) {
  if ($(".jifen").val() == "" || $(".jifen").val() <= 0) {
   layer.tips('请输入有效的奖励积分', '.jifen', {
    tips: 3
   });
   $(".jifen").focus();
   return false;
  }
  if ($(".jifen").val() > 10000) {
   layer.tips('奖励积分最大可设置为10000', '.jifen', {
    tips: 3
   });
   $(".jifen").val(10000);
   $(".jifen").focus();
   return false;
  }

  if (document.getElementById('rdozhoumo').checked == true) {
   if ($("#WEEKEND_SEND").val() == "" || $("#WEEKEND_SEND").val() < 0) {
    layer.tips('请输入有效的周末积分', '#WEEKEND_SEND', {
     tips: 3
    });
    $("#WEEKEND_SEND").focus();
    return false;
   }

   if ($("#WEEKEND_SEND").val() > 10000) {
    layer.tips('周末积分最大可设置为10000', '#WEEKEND_SEND', {
     tips: 3
    });
    $("#WEEKEND_SEND").val(10000);
    $("#WEEKEND_SEND").focus();
    return false;
   }
  }
  var Data = [];
  var Datas='';
  var checked = $('.xuanze input[type=checkbox]:checked');
  var week=-1;
  if($("#WEEKEND_SEND").val()){
   week=$("#WEEKEND_SEND").val()
  }else{
   week=-1;
  }
  if(Ind){
   Datas=[{
    "store_id": storeId,
    "INTEGRAL_SEND": $(".jifen").val(),
    "WEEKEND_SEND": week,
   }]
   var num={"CATEGRORY": "2", "type":"save", "data": Datas};
  }else{
   for (var i = 0; i < checked.length; i++) {
    Data[i] = {
     "store_id": $('.xuanze input[type=checkbox]:checked').eq(i).val(),
     "INTEGRAL_SEND": $(".jifen").val(),
     "WEEKEND_SEND": week,
    }
   }
   var num = {"CATEGRORY": "2", "type":"add", "data": Data};
  }
  console.log(num, 'num')
  $.ajax({
      type: "POST",
      url: 'intintegral/addSign.do',
      data: JSON.stringify(num),
      dataType: 'json',
      contentType: "application/json",
      cache: false,
   success: function (data) {
    console.log(data, 'data')
     layer.msg(data.errmsg);
    if(data.errcode==0){
     location.reload()
    }
   },
   error: function () {
    layer.msg("系统繁忙，请稍后再试！");
    return false;
   }
  });
 } else {
  // var Li =  li = document.querySelectorAll(".INTEGRAL_SEND");
  // for(var i=0;i<Li.length;i++){
  //  var Li=li[i];
  //  if (Li.value() > 10000) {
  //   layer.tips('扫码积分最大可设置为10000', '.INTEGRAL_SEND', {
  //    tips: 3
  //   })
  //   return false;
  //  };
  //  if ($(`${li}`).val() == "" || $(`${li}`).val() < 0) {
  //   layer.tips('请输入有效的扫码积分', '.INTEGRAL_SEND', {
  //    tips: 3
  //   });
  //   return false;
  //  }
  // }
  var Data = [];
  var Datas='';
  var checked = $('.xuanze input[type=checkbox]:checked');
  if(Ind){
   var Li = document.querySelectorAll(".INTEGRAL_SEND")
   for(var i=0;i<Li.length;i++){
    if($(".INTEGRAL_SEND").eq(i).val()>1000){
     layer.tips('扫码积分最大可设置为1000', '.right', {
      tips: 3,

     });
     $(".INTEGRAL_SEND").eq(i).val(1000);
     return false;
    }
    if($(".INTEGRAL_SEND").eq(i).val()<0||$(".INTEGRAL_SEND").eq(i).val()==''){
     layer.tips('扫码积分最小可设置为0', '.right', {
      tips: 3,

     });
     $(".INTEGRAL_SEND").eq(i).val(0);
     return false;
    }
   }
   Datas=[{
    "store_id": storeId,
    "sign_time_set": `${$("#INTEGRAL_SEND0").val()},${$("#INTEGRAL_SEND1").val()},${$("#INTEGRAL_SEND2").val()},${$("#INTEGRAL_SEND3").val()},${$("#INTEGRAL_SEND4").val()},${$("#INTEGRAL_SEND5").val()},${$("#INTEGRAL_SEND6").val()}`
   }];
   var num = {"CATEGRORY": "5", "type":"save", "data": Datas};
  }else{
   for (var i = 0; i < checked.length; i++) {
    Data[i] = {
     "store_id": $('.xuanze input[type=checkbox]:checked').eq(i).val(),
     "sign_time_set": `${$("#INTEGRAL_SEND0").val()},${$("#INTEGRAL_SEND1").val()},${$("#INTEGRAL_SEND2").val()},${$("#INTEGRAL_SEND3").val()},${$("#INTEGRAL_SEND4").val()},${$("#INTEGRAL_SEND5").val()},${$("#INTEGRAL_SEND6").val()}`
    }
   }
   var num = {"CATEGRORY": "5", "type":"add", "data": Data};
  }
  console.log(num, 'num')
  $.ajax({
   type: "POST",
   url: 'intintegral/addSign.do',
   data: JSON.stringify(num),
   dataType: 'json',
   contentType: "application/json",
   cache: false,
   success: function (data) {
    console.log(data, 'data')
    layer.msg(data.errmsg);
    if(data.errcode==0){
     location.reload()
    }
   },
   error: function () {
    layer.msg("系统繁忙，请稍后再试！");
    return false;
   }
  });
 }
});
$("body").on("click",".del",function(){
 storeId =data[$(this).parent().parent().parent().index()].store_id
 if(data[$(this).parent().parent().parent().index()].awardType=="签到"){
  ind=2;
  $(".Right").remove();
  setTimeout(function(){
   $("#del").css("display","block");
  },500)
 }else if(data[$(this).parent().parent().parent().index()].awardType=="上机积分"){
  ind=5;
  $(".Left").remove();
  setTimeout(function(){
   $("#del").css("display","block")
  },500)
 }else if(data[$(this).parent().parent().parent().index()].awardType==""){
  console.log(data[$(this).parent().parent().parent().index()].awardType)
  $("#del").css("display","none");
  setTimeout(function(){
   layer.msg("暂无奖励类别");
  },200)
 }else{
  ind=6
  $("#del").css("display","block");
 }
});

$("body").on("click",".add",function(){//增加
 $(".mendian").css("display","block")
 $.post("intintegral/getStores.do",function(res){
  console.log(res,"res");
  store=res.data;
   for(var i=0;i<res.data.length;i++){
    $('.xuanze').append(`<input type="checkbox" class="${res.data[i].store_id}" title="${res.data[i].store_name}" lay-skin="primary" value="${res.data[i].store_id}">`);
   };
  layui.use('form', function() {
   var form = layui.form;
   form.render();
  });
   setTimeout(function(){
    $(".tan").css("display","block");
    $(".two").css("checked","checked");
    $(".saoma").css("display","none");
    $(".qiandao").css("display","block");
   },500)
 });
 $.post("intintegral/getVStores.do",function (res) {
  console.log(res,"扫码");
  storeNo=res.data;

 });
 console.log(store,storeNo)
});
$("#IsTurnOut").change(function () {
 var ss = $(this).children('option:selected').val();//改变事件
 if (ss == "2") {
  if(Ind){
   $(".saoma").css("display","none");
   $(".qiandao").css("display","block");
   $(".gongneng").css("display","none");
   ind=2;
   $(".jifen").val(INT);
   if(WEEK==-1){
    $(".zhoumo input").prop("checked",false);
   }else{
    $("#WEEKEND_SEND").val(WEEK)
   };
  }else{
   $(".xuanze input").prop("checked",false);
   $(".jifen").val('');
   $("#WEEKEND_SEND").val('');
   ind=2;
   $(".saoma").css("display","none");
   $(".qiandao").css("display","block");
   $(".gongneng").css("display","none");
   for(var i=0;i<storeNo.length;i++){
    var num = "."+storeNo[i].store_id;
    console.log(num);
    $(num).attr("disabled",false);
   };
   layui.use('form', function() {
    var form = layui.form;
    form.render();
   });
  }

 } else if (ss == "5") {
  if(Ind){
   $(".qiandao").css("display","none");
   $(".saoma").css("display","block");
   ind=5;
   console.log(time)
   time=time.split(",")
   for(var i=0;i<li.length;i++){
    $(li[i]).val(time[i])
    // console.log(time[i])
   }
  }else{
   $(".xuanze input").prop("checked",false);
   $(".INTEGRAL_SEND").val('');
   ind=5;
   $(".qiandao").css("display","none");
   $(".saoma").css("display","block");
   $(".gongneng").css("display","block")
   console.log(storeNo)
   for(var i=0;i<storeNo.length;i++){
    var num = "."+storeNo[i].store_id;
    console.log(num);
    $(num).attr("disabled",true);
   }
   layui.use('form', function() {
    var form = layui.form;
    form.render();
   });
  }

 }
});
var inds=0;
$("body").on("click",".shanchu",function(){
 console.log($(".choose input[type=checkbox]:checked").length,"lenght")
 if(ind==2){
  if($(".choose input[type=checkbox]:checked").length){
   $.post("intintegral/deleteSign.do?type="+ind+"&store_id="+storeId,function(res){
    layer.msg(res.errmsg)
    setTimeout(function(){
     if(res.errcode==0){
      location.reload()
     }
    },200)
   })
  }else{
   layer.msg("请选择")
  }
 }else if(ind==5){
  if($(".choose input[type=checkbox]:checked").length){
   $.post("intintegral/deleteSign.do?type="+ind+"&store_id="+storeId,function(res){
    layer.msg(res.errmsg)
    setTimeout(function(){
     if(res.errcode==0){
      location.reload()
     }
    },200)
   })
  }else{
   layer.msg("请选择")
  }
 }else if(ind==6){
  if($(".choose input[type=checkbox]:checked").length==2){
   console.log(222)
   $.post("intintegral/deleteSign.do?type="+2+","+5+"&store_id="+storeId,function(res){
    console.log("localhost/lanker/intintegral/deleteSign.do?type="+2,5+"&store_id="+storeId);
    layer.msg(res.errmsg)
    setTimeout(function(){
     if(res.errcode==0){
      location.reload()
     }
    },200)
   });
   return false;
  }else if($(".choose input[type=checkbox]:checked").length==1){
   console.log(111)
   if($(".Left input[type=checkbox]:checked").length==1){
    $.post("intintegral/deleteSign.do?type="+2+"&store_id="+storeId,function(res){
     layer.msg(res.errmsg)
     setTimeout(function(){
      if(res.errcode==0){
       location.reload()
      }
     },200)
    })
   }else{
    $.post("intintegral/deleteSign.do?type="+5+"&store_id="+storeId,function(res){
     layer.msg(res.errmsg)
     setTimeout(function(){
      if(res.errcode==0){
       location.reload()
      }
     },200)
    })
   }
  }else{
   layer.msg("请选择")
  }
 }
  // if(".Left input[type=checkbox]:checked"&&".Right input[type=checkbox]:checked"){
  //    inds=2,5;
  // }else if(".Left input[type=checkbox]:checked"&&!".Right input[type=checkbox]:checked"){
  //  inds=2
  // }else if(!".Left input[type=checkbox]:checked"&&".Right input[type=checkbox]:checked"){
  //  inds=5;
  // }
})