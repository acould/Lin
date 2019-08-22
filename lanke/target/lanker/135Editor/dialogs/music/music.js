function Music() {
    this.init();
}

/* 初始化tab标签 */
    function initTabs(){
        var tabs = $G('tabHeads').children;
        for (var i = 0; i < tabs.length; i++) {
            domUtils.on(tabs[i], "click", function (e) {
                var target = e.target || e.srcElement;
                for (var j = 0; j < tabs.length; j++) {
                    if(tabs[j] == target){
                        tabs[j].className = "focus";
                        var contentId = tabs[j].getAttribute('data-content-id');
                        $G(contentId).style.display = "block";
                        // if(contentId == 'imgManager') {
                        //     initImagePanel();
                        // }
                    }else {
                        tabs[j].className = "";
                        $G(tabs[j].getAttribute('data-content-id')).style.display = "none";
                    }
                }
            });
        }
    }

(function () {
    var pages = [],
        panels = [],
        selectedItem = null;
    Music.prototype = {
        init:function () {

            initTabs();

            var me = this;
            var audio = jQuery(dialog.anchorEl).find('audio').get(0);
            if (audio) {
            	if (!audio.tagName || audio.tagName.toLowerCase() != 'audio' && !audio.getAttribute("src") || !audio.src) return;
            	$G("ImageUrl").value = audio.src;
            	audio.pause();
            }            
        },
        
        exec:function () {
            var me = this;
            if($G("ImageUrl").value) {
            	            	
            	var audio = jQuery(dialog.anchorEl).find('audio').get(0);
                if (parent.current_active_135item != null && audio) {
                	if ((!audio.tagName || audio.tagName.toLowerCase() != 'audio') && (!audio.getAttribute("src") || !audio.src)) return;
                	
                	audio.src = $G("ImageUrl").value;
                	
                	if($('#autoPlay:checked').length>0) {
                		$(audio).attr('autoplay',true);
                	}
                	else{
                		$(audio).removeAttr('autoplay');
                	}
                	if($('#controls:checked').length>0) {
                		$(audio).attr('controls',true);
                	}
                	else{
                		$(audio).removeAttr('controls');
                	}
                	if($('#loop:checked').length>0) {
                		$(audio).attr('loop',true);
                	}
                	else{
                		$(audio).removeAttr('loop');
                	}
                }
                else{
                	var html ="<section class='135editor' style='text-align:center;'><audio "
                		+ ($('#autoPlay:checked').length>0?' autoplay ':'' )
                		+ ($('#controls:checked').length>0?' controls ':'' )
                		+ ($('#loop:checked').length>0?' loop ':'' )
                		+ " src=\""+$G("ImageUrl").value+"\"></audio></section>";
                	editor.execCommand('insertHtml', html);	
                }
            }
        }
    };
})();


function QQMusic() {
    this.init()
}

(function() {
    var a = [],
    b = [],
    c = null,
    mlang={'chapter':"歌曲",'emptyTxt':"未搜索到相关音乐结果，请换一个关键词试试。",'listenTest':"试听",'singer':"歌手",'special':"专辑"};
    QQMusic.prototype = {
        total: 72,
        pageSize: 8,
        dataUrl: "https://auth-external.music.qq.com/open/fcgi-bin/fcg_weixin_music_search.fcg?",
        playUrl: "http://ws.stream.qqmusic.qq.com/",
        init: function() {
            var a = this;
            $("#searchBtn").click(function() {
                a.dosearch()
            })
        },
        callback: function(a) {
            var b = this;
            b.data = a.list,
            setTimeout(function() {
                $("#resultBar").html(function(){
                    return b._renderTemplate(a.list);
                });
            },
            100)
        },
        dosearch: function() {
            a = [],
            b = [],
            c = null;
            var d = this;
            c = null;
            var e = $("#searchName").val();
            if ("" == $.trim(e)) return ! 1;
            e = encodeURIComponent(e),
            d._sent(e)
        },
        doselect: function(a) {
            var b = this;
            "object" == typeof a ? c = a: "number" == typeof a && (c = b.data[a]);
        },
        onpageclick: function(c) {
            for (var d = 1; d <= a.length; d++) $('#page'+d).attr('class', 'pageoff'),
            $('#panel'+ d).attr('class', 'paneloff');
            $("#page" + c).attr('class', 'pageon'),
            $("#panel" + c).attr('class', 'panelon');
        },
        listenTest: function(a) {
            var b = this,
            c = $("#preview"),
            d = "m-try" == a.className;
            e = b._getTryingElem();
            if(e) {
                e.className = "m-try";
                jQuery('#preview').html("");
            }
            if( d ) {
                a.className = "m-trying";
                var html = b._buildMusicHtml(b._getUrl(!0));                
                jQuery('#preview').html(html);
            }
        },
        _sent: function(a) {
            var b = this;
            $("#resultBar").html(function(){
                return '<div class="loading"></div>';
            }),
            $.ajax({
                url: b.dataUrl + "jsonCallback=success_jsonpCallback&remoteplace=txt.weixin.officialaccount&w=" + a + "&platform=weixin&perpage=" + b.total + "&curpage=1",
                dataType: "jsonp",
                jsonp: "callback",
                jsonpCallback: "success_jsonpCallback",
                type: "get",
                success: function(a) {
                    b.callback(a)
                },
                error: function(a, b, c) {
                    console.log(b)
                }
            })
        },
        _removeHtml: function(a) {
            var b = /<\s*\/?\s*[^>]*\s*>/gi;
            return a.replace(b, "")
        },
        _getUrl: function(a) {
            var b = this,
            d = "from=tiebasongwidget&url=&name=" + encodeURIComponent(c.albumname) + "&artist=" + encodeURIComponent(c.singername) + "&extra=" + encodeURIComponent(c.albumname) + "&autoPlay=" + a + "&loop=true";
            return b.playerUrl + "?" + d
        },
        _getTryingElem: function() {
            for (var a = $("#listPanel span"), b = 0; b < a.length; b++) if ("m-trying" == a[b].className) return a[b];
            return null
        },
        _buildMusicHtml: function(a) {
            var b = this,
            d = c.m4a,
            //d = b.playUrl + c.id + ".m4a?fromtag=46",
            e = '<embed class="BDE_try_Music" allowfullscreen="false"';
            e += ' src="' + d + '"',
            e += ' width="1" height="1" style="position:absolute;left:-2000px;"',
            e += ' wmode="transparent" play="true" loop="false"',
            e += ' menu="false" allowscriptaccess="never" scale="noborder">';
            return e;
        },
        _byteLength: function(a) {
            return a.replace(/[^\u0000-\u007f]/g, "aa").length
        },
        _getMaxText: function(a) {
            return this._byteLength(a) > 12 ? a.substring(0, 5) + "...": (a || (a = "&nbsp;"), a)
        },
        _rebuildData: function(a) {
            for (var b, c = this,
            d = [], e = c.pageSize, f = 0; f < a.length; f++)(f + e) % e == 0 && (b = [], d.push(b)),
            b.push(a[f]);
            return d
        },
        _renderTemplate: function(c) {
            var d = this;
            if (0 == c.length) return '<div class="empty">' + mlang.emptyTxt + "</div>";
            c = d._rebuildData(c);
            var e = [],
            f = [],
            g = [];
            e.push('<div id="listPanel" class="listPanel">'),
            f.push('<div class="page">');
            for (var h, i = 0; h = c[i++];) {
                b.push("panel" + i),
                a.push("page" + i),
                1 == i ? (e.push('<div id="panel' + i + '" class="panelon">'), 1 != c.length && g.push('<div id="page' + i + '" onclick="qqmusic.onpageclick(' + i + ')" class="pageon">' + i + "</div>")) : (e.push('<div id="panel' + i + '" class="paneloff">'), g.push('<div id="page' + i + '" onclick="qqmusic.onpageclick(' + i + ')" class="pageoff">' + i + "</div>")),
                e.push('<div class="m-box">'),
                e.push('<div class="m-h"><span class="m-t">' + mlang.chapter + '</span><span class="m-s">' + mlang.singer + '</span><span class="m-z">' + mlang.special + '</span><span class="m-try-t">' + mlang.listenTest + "</span></div>");
                for (var j, k = 0; j = h[k++];) {
                    e.push('<label for="radio-' + i + "-" + k + '" class="m-m">'),
                    e.push('<input type="radio" id="radio-' + i + "-" + k + '" name="musicId" class="m-l" onclick="qqmusic.doselect(' + (d.pageSize * (i - 1) + (k - 1)) + ')"/>'),
                    e.push("<i></i>"),
                    e.push('<span class="m-t" title="'+j.songname+'">' + d._getMaxText(j.songname) + "</span>"),
                    e.push('<span class="m-s" title="'+j.singername+'">' + d._getMaxText(j.singername) + "</span>"),
                    e.push('<span class="m-z" title="'+j.albumname+'">' + d._getMaxText(j.albumname) + "</span>"),
                    e.push('<span class="m-try" onclick="qqmusic.doselect(' + (d.pageSize * (i - 1) + (k - 1)) + ');qqmusic.listenTest(this)"></span>'),                    
                    e.push("</label>");
                }
                e.push("</div>"),
                e.push("</div>")
            }
            return g.reverse(),
            f.push(g.join("")),
            e.push("</div>"),
            f.push("</div>"),
            e.join("") + f.join("")
        },
        exec: function() {
            var a = this;
            if (null != c) {
                $("#preview").innerHTML = "";
                var b = "/cgi-bin/readtemplate?t=tmpl/qqmusic_tmpl&singer=" + encodeURIComponent(c.f.split("|")[3]) + "&music_name=" + encodeURIComponent(c.songname),
                d = c.f.split("|")[22];
                d = "/" + d.slice(d.length - 2, d.length - 1) + "/" + d.slice(d.length - 1, d.length) + "/" + d + ".jpg";
                var e = {
                    error:0,
                    musicid: c.id,
                    mid: c.f.split("|")[20],
                    albumurl: d,
                    audiourl: c.m4a,
                    music_name: c.songname,
                    singer: c.f.split("|")[3],
                    datasrc: b,
                    singername: c.singername,
                    url: a._getUrl(!1)
                };
            }
            else{
                var e ={
                    error:1
                }
            }
            return e
        }
    }
})();


