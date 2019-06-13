$(function(){
	var string = "baidu.com";
	$(".J_TwoCode").qrcode({ 
        render: "table", //table,canvas方式 
        width: 150, //宽度 
        height:150, //高度 
        text: toUtf8(string) //任意内容 
        // text: toUtf8("钓鱼岛是中国的！")
    });
	
    // 职别中文 所有输入的内容都加上toUtf8(内容)
    function toUtf8(str) {    
        var out, i, len, c;    
        out = "";    
        len = str.length;    
        for(i = 0; i < len; i++) {    
            c = str.charCodeAt(i);    
            if ((c >= 0x0001) && (c <= 0x007F)) {    
                out += str.charAt(i);    
            } else if (c > 0x07FF) {    
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));    
                out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));    
                out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
            } else {    
                out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));    
                out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
            }    
        }    
        return out;    
    }
})