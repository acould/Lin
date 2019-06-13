$(function(){
    // 时间设置
    $('input[name=startTime],input[name=endTime]').datetimepicker({
        timeFormat: "HH:mm:ss",
        dateFormat: "yy-mm-dd"
    });
});
