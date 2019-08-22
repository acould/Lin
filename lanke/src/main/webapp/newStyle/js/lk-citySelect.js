/** 省市区三级代码查询 **/
var DISTRICTS;
var provinceCode = '',
    cityCode = '',
    districtCode = '';
//筛选的地区
var province_selector = $('#province_selector'),
    city_selector = $('#city_selector'),
    district_selector = $('#district_selector');
var htm = '';
var form ="";
var layer = "";

layui.use(["form","layer"],function () {
     form = layui.form;
     layer = layui.layer;

    // 选择省、渲染市
    form.on('select(province)', function(data){
        var code = data.value;
        var name  = province_selector.find('option:selected').html();
        var j = province_selector.find('option:selected').data("type");
        if(j != undefined){
            htm = '<option value="">请选择市</option>';
            for (var i = 0; i < DISTRICTS[j].cityList.length; i++) {
                htm += '<option value="'+DISTRICTS[j].cityList[i].code+'" data-type="'+i+'">'+DISTRICTS[j].cityList[i].name+'</option>';
            }
            city_selector.html(htm);
        }else {
            city_selector.html("");
        }
        district_selector.html('');
        form.render('select','cityBox');
        form.render('select','districtBox');
    });

    // 选择市、渲染区
    form.on('select(city)', function(data){
        var code = data.value;
        var name  = city_selector.find('option:selected').html();
        var j = province_selector.find('option:selected').data("type");
        var d = city_selector.find('option:selected').data("type");
        if(j != undefined && d != undefined){
            htm = '<option value="">请选择区/县</option>';
            for (var i = 0; i < DISTRICTS[j].cityList[d].areaList.length; i++){
                htm += '<option value="'+DISTRICTS[j].cityList[d].areaList[i].code+'" data-type="'+i+'">'+DISTRICTS[j].cityList[d].areaList[i].name+'</option>';
            }
            district_selector.html(htm);
        }else {
            district_selector.html('');
        }
        form.render('select','districtBox');
    });

    // 选择区
    form.on('select(district)', function(data){
        var code = data.value;
        var name  = district_selector.find('option:selected').html();

    });
})

function citySelect(province,city,district){
    $.ajax({
        type: 'GET',
        url: 'newStyle/js/districts.js',
        dataType: 'json'
    }).done(function (res){
        DISTRICTS = res;
        htm = '<option value="">请选择省</option>';
        if(province == "" || city == "" || district == ""){
            for (var i = 0; i < DISTRICTS.length; i++){
                htm += '<option value="'+DISTRICTS[i].code+'" data-type="'+i+'">'+DISTRICTS[i].name+'</option>';
            }
            province_selector.html(htm);
            city_selector.html("");
            district_selector.html("");
        }else {
            var active = "",province_html = '<option value="">请选择省</option>',city_html = '<option value="">请选择市</option>',district_html = '<option value="">请选择区或县</option>',p = "",c = "";
            //找到默认省
            for (var i = 0; i < DISTRICTS.length; i++){
                if(DISTRICTS[i].name == province){ active = "selected"; p = i;}else { active = "";}
                if(DISTRICTS[i].code == province){ active = "selected"; p = i;}else { active = "";}
                province_html += '<option value="'+DISTRICTS[i].code+'" data-type="'+i+'" '+active+'>'+DISTRICTS[i].name+'</option>';
            }
            //找到默认市
            for (var a = 0; a < DISTRICTS[p].cityList.length; a++){
                if(DISTRICTS[p].cityList[a].name == city){ active = "selected"; c = a; }else {active = "";}
                if(DISTRICTS[p].cityList[a].code == city){ active = "selected"; c = a; }else {active = "";}
                city_html += '<option value="'+DISTRICTS[p].cityList[a].code+'" data-type="'+a+'" '+active+'>'+DISTRICTS[p].cityList[a].name+'</option>';
            }
            //找到默认区
            for (var b = 0; b < DISTRICTS[p].cityList[c].areaList.length; b++){
                if(DISTRICTS[p].cityList[c].areaList[b].name == district){ active = "selected";}else { active = "";}
                if(DISTRICTS[p].cityList[c].areaList[b].code == district){ active = "selected";}else { active = "";}
                district_html += '<option value="'+DISTRICTS[p].cityList[c].areaList[b].code+'" data-type="'+b+'" '+active+'>'+DISTRICTS[p].cityList[c].areaList[b].name+'</option>';
            }
            province_selector.html(province_html);
            city_selector.html(city_html);
            district_selector.html(district_html);
        }
        form.render('select');
    }).fail(function (){
        console.log('获取地区json数据失败');
    });
}


function getRealPath(){
    var curWwwPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    var localhostPaht=curWwwPath.substring(0,pos);
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    var realPath=localhostPaht+projectName;
    if(projectName == "/lanker"){
        return realPath
    }else{
        return localhostPaht
    }
}