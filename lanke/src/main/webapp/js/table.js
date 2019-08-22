
    var num=[];

    function addtr() {
        if(data[0]){
            var tableobj = document.getElementById("list");
            var tli =  document.createElement("li");
            var tdobj = document.createElement("div");
            tdobj.setAttribute("class","name");
            var aLi=tableobj.getElementsByTagName('li');
            var length=aLi.length-2;
            tdobj.innerHTML=`自助售货机${length}:`;
            tli.appendChild(tdobj);
            var tdobj = document.createElement("div");
            tdobj.setAttribute("class","sr");
            var inp = document.createElement('input');
            inp.style=`height:90%;width:100%;border:0;padding:0`;
            inp.placeholder=`请输入售货机编号`;
            tdobj.style.width=`400px`;
            tdobj.appendChild(inp);
            tli.appendChild(tdobj);



            j++;
            var tdobj = document.createElement("div");
            tdobj.setAttribute("class","del");
            tdobj.innerHTML = "<div onclick=delecttr(this)>删除</div>";
            tli.appendChild(tdobj);
            tableobj.appendChild(tli);
        }else {
            var tableobj = document.getElementById("list");
            var tli =  document.createElement("li");
            var tdobj = document.createElement("div");
            tdobj.setAttribute("class","name");
            var aLi=tableobj.getElementsByTagName('li');
            var length=aLi.length+1;
            tdobj.innerHTML=`自助售货机${length}:`;
            tli.appendChild(tdobj);
            var tdobj = document.createElement("div");
            tdobj.setAttribute("class","sr");
            var inp = document.createElement('input');
            inp.style=`height:90%;width:100%;border:0;padding:0`;
            inp.placeholder=`请输入售货机编号`;
            tdobj.style.width=`400px`;
            tdobj.appendChild(inp);
            tli.appendChild(tdobj);



            j++;
            var tdobj = document.createElement("div");
            tdobj.setAttribute("class","del");
            tdobj.innerHTML = "<div onclick=delecttr(this)>删除</div>";
            tli.appendChild(tdobj);
            tableobj.appendChild(tli);
        }

    }

    function delecttr(obj) {
        if(data[0]){
            var tr = obj.parentNode.parentNode;
            tr.parentNode.removeChild(tr);
            var tableobj = document.getElementById("list");
            var aLi=tableobj.getElementsByTagName('li');
            for(var i=3;i<aLi.length; i++ ){
                var fDiv=aLi[i].firstChild;
                fDiv.innerHTML=`自助售货机${i-2}:`;
                if(tableobj.getElementsByTagName('input')[i].value!=''){
                    num[num.length]=tableobj.getElementsByTagName('input')[i].value;
                }

            }
        }else{
            var tr = obj.parentNode.parentNode;
            tr.parentNode.removeChild(tr);
            var tableobj = document.getElementById("list");
            var aLi=tableobj.getElementsByTagName('li');
            for(var i=0;i<aLi.length; i++ ){
                var fDiv=aLi[i].firstChild;
                fDiv.innerHTML=`自助售货机${i+1}:`;
                if(tableobj.getElementsByTagName('input')[i].value!=''){
                    num[num.length]=tableobj.getElementsByTagName('input')[i].value;
                }

            }
        }

    }


    function bc(){//保存时发送数据
        var I=2;
                    num=[];
                    var tableobj = document.getElementById("list");
                    var aLi=tableobj.getElementsByTagName('li');
                    for(var i=0;i<aLi.length; i++ ){
                        if( $("li").eq(i).find("input").val()!=''){
                            for(var j=0;j<aLi.length;j++){
                                if($("li").eq(i).find("input").val()!='') {
                                    if($("li").eq(i).find("input").val()==$("li").eq(j).find("input").val()){
                                        if(i!=j){
                                            I=1;
                                            layer.msg("编号有重复");
                                            return false
                                        }
                                    }
                                }
                                }
                            }

                        if(tableobj.getElementsByTagName('input')[i].value!=''){
                            num[num.length]=tableobj.getElementsByTagName('input')[i].value;
                        }
                    }
                    var num = num.join(",")
                    if(num!=''){
                        var Data = {sm_no:num,store_id:id}
                        if(I==2){
                            $.ajax({
                                type: "POST",
                                url: 'save.do',
                                data: Data,
                                dataType:'json',
                                success: function(data){
                                    if(data.data.code==0){
                                        layer.msg(data.data.msg);
                                        // var index = parent.layer.getFrameIndex(window.name);
                                        //     parent.layer.close(index);
                                    }else if(data.data.code==3){
                                        layer.msg(data.data.msg);
                                    }else{
                                        layer.msg("保存失败")
                                    }
                                },
                                error:function(){
                                    // layer.msg("系统繁忙，请稍后再试！");
                                    return false;
                                }
                            });
                        }
                    }else{
                        layer.msg("内容为空");
                        var index = parent.layer.getFrameIndex(window.name);
                        setTimeout(function(){
                            parent.layer.close(index);
                            parent.location.reload();
                        },500)
                    }


                    }

