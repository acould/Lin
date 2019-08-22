/**
 * Created with JetBrains PhpStorm.
 * User: xuheng
 * Date: 12-9-26
 * Time: 下午1:09
 * To change this template use File | Settings | File Templates.
 */
var charsContent = [
    { name:"tsfh", title:lang.tsfh, content:toArray("♔,♕,♖,♚,♛,♜,❄,❅,❆,❖,★,☆,✦,✪,✫,✿,❀,❁,ღ,✔,✘,✓,✕,☑,☒,☋,☊,ㄨ,♂,♀,❧,✍,✎,✑,✄,❂,卍,❦,♘,♞,♣,♥,♡,☀,☂,♫,☎,☺,<hr>,·,ˉ,ˇ,¨,〃,々,—,～,‖,…,‘’,“”,〔〕,〈〉,《》,「」,『』,〖〗,【】,±,×,÷,∶,∧,∨,∑,∏,∪,∩,∈,∷,√,⊥,∥,∠,⌒,⊙,∫,∮,≡,≌,≈,∽,∝,≠,≮,≯,≤,≥,∞,∵,∴,♂,♀,°,′,″,¤,￠,￡,§,<hr>,○,●,©,◎,◇,◆,※,〓,〡,〢,〣,〤,〥,〦,〧,〨,〩,囍,㊣,㊤,㊦,㊥,㊧,㊨,㊐,㊊,㊌,㊍,㊎,㊏,㊛,㊚,<hr>,㎎,㎏,㎜,㎝,㎞,㎡,㏄,㏎,㏑,㏒,㏕,‰,℅,℉,℃,＄,№")},
    //罗马数字 { name:"lmsz", title:lang.lmsz, content:toArray("ⅰ,ⅱ,ⅲ,ⅳ,ⅴ,ⅵ,ⅶ,ⅷ,ⅸ,ⅹ,Ⅰ,Ⅱ,Ⅲ,Ⅳ,Ⅴ,Ⅵ,Ⅶ,Ⅷ,Ⅸ,Ⅹ,Ⅺ,Ⅻ")},
    { name:"szfh", title:lang.szfh, content:toArray("⒈,⒉,⒊,⒋,⒌,⒍,⒎,⒏,⒐,⒑,⒒,⒓,⒔,⒕,⒖,⒗,⒘,⒙,⒚,⒛,<hr>,⑴,⑵,⑶,⑷,⑸,⑹,⑺,⑻,⑼,⑽,⑾,⑿,⒀,⒁,⒂,⒃,⒄,⒅,⒆,⒇,<hr>,①,②,③,④,⑤,⑥,⑦,⑧,⑨,⑩,⑪,⑫,⑬,⑭,⑮,⑯​,<hr>,<span style='font-size:20px;'>❶</span>&nbsp;,<span style='font-size:20px;'>❷</span>&nbsp;,<span style='font-size:20px;'>❸</span>&nbsp;,<span style='font-size:20px;'>❹</span>&nbsp;,<span style='font-size:20px;'>❺</span>&nbsp;,<span style='font-size:20px;'>❻</span>&nbsp;,<span style='font-size:20px;'>❼</span>&nbsp;,<span style='font-size:20px;'>❽</span>&nbsp;,<span style='font-size:20px;'>❾</span>&nbsp;,<span style='font-size:20px;'>➓</span>&nbsp;,<hr>,㈠,㈡,㈢,㈣,㈤,㈥,㈦,㈧,㈨,㈩,<hr>,ⅰ,ⅱ,ⅲ,ⅳ,ⅴ,ⅵ,ⅶ,ⅷ,ⅸ,ⅹ,<hr>,Ⅰ,Ⅱ,Ⅲ,Ⅳ,Ⅴ,Ⅵ,Ⅶ,Ⅷ,Ⅸ,Ⅹ,Ⅺ,Ⅻ")},
     {name:"jtfh", title:"箭头/形状", content:toArray("☝,☟,☜,☞,←,↑,→,↓,↙,↘,↖,↗,➝,⇒,⇔,↔,↕,➫,➬,➩,➪,➭,➮,➯,➱,➜,➡,➥,➦,➧,➨,➷,➸,➻,➼,➽,➸,➹,➳,➤,➟,➲,➢,➣,➞,⇪,➚,➘,➙,➛,➺,♐,➴,➵,➶,↨,<hr>,❏,❐,❑,❒,▏,▐,░,▒,▓,▔,▕,■,□,▢,▣,▤,▥,▦,▧,▨,▩,❖,◆,◇,◈,<hr>,⊙,●,○,◎,¤,❂,✪,Θ,⊕,◉,◌,◐,◑,⊗,◯,❍,<hr>,◤,◥,►,▶,◀,◣,◢,▲,▼,△,▽,▷,◁,⊿,∆,∇")},
   { name:"yyyb", title:"拼音/音标", content:toArray("ā,á,ǎ,à,ē,é,ě,è,ī,í,ǐ,ì,ō,ó,ǒ,ò,ū,ú,ǔ,ù,ǖ,ǘ,ǚ,ǜ,ü,<hr>,i:,i,e,æ,ʌ,ə:,ə,u:,u,ɔ:,ɔ,a:,ei,ai,ɔi,əu,au,iə,εə,uə,p,t,k,b,d,g,f,s,ʃ,θ,h,v,z,ʒ,ð,tʃ,tr,ts,dʒ,dr,dz,m,n,ŋ,l,r,w,j,")},
    
    { name:"rwfh", title:lang.rwfh, content:toArray("ぁ,あ,ぃ,い,ぅ,う,ぇ,え,ぉ,お,か,が,き,ぎ,く,ぐ,け,げ,こ,ご,さ,ざ,し,じ,す,ず,せ,ぜ,そ,ぞ,た,だ,ち,ぢ,っ,つ,づ,て,で,と,ど,な,に,ぬ,ね,の,は,ば,ぱ,ひ,び,ぴ,ふ,ぶ,ぷ,へ,べ,ぺ,ほ,ぼ,ぽ,ま,み,む,め,も,ゃ,や,ゅ,ゆ,ょ,よ,ら,り,る,れ,ろ,ゎ,わ,ゐ,ゑ,を,ん,ァ,ア,ィ,イ,ゥ,ウ,ェ,エ,ォ,オ,カ,ガ,キ,ギ,ク,グ,ケ,ゲ,コ,ゴ,サ,ザ,シ,ジ,ス,ズ,セ,ゼ,ソ,ゾ,タ,ダ,チ,ヂ,ッ,ツ,ヅ,テ,デ,ト,ド,ナ,ニ,ヌ,ネ,ノ,ハ,バ,パ,ヒ,ビ,ピ,フ,ブ,プ,ヘ,ベ,ペ,ホ,ボ,ポ,マ,ミ,ム,メ,モ,ャ,ヤ,ュ,ユ,ョ,ヨ,ラ,リ,ル,レ,ロ,ヮ,ワ,ヰ,ヱ,ヲ,ン,ヴ,ヵ,ヶ")},
    { name:"xlzm", title:"希腊/俄文", content:toArray("Α,Β,Γ,Δ,Ε,Ζ,Η,Θ,Ι,Κ,Λ,Μ,Ν,Ξ,Ο,Π,Ρ,Σ,Τ,Υ,Φ,Χ,Ψ,Ω,<hr>,α,β,γ,δ,ε,ζ,η,θ,ι,κ,λ,μ,ν,ξ,ο,π,ρ,σ,τ,υ,φ,χ,ψ,ω,<hr>,А,Б,В,Г,Д,Е,Ё,Ж,З,И,Й,К,Л,М,Н,О,П,Р,С,Т,У,Ф,Х,Ц,Ч,Ш,Щ,Ъ,Ы,Ь,Э,Ю,Я,<hr>,а,б,в,г,д,е,ё,ж,з,и,й,к,л,м,н,о,п,р,с,т,у,ф,х,ц,ч,ш,щ,ъ,ы,ь,э,ю,я")},
    // 俄文 { name:"ewzm", title:lang.ewzm, content:toArray("А,Б,В,Г,Д,Е,Ё,Ж,З,И,Й,К,Л,М,Н,О,П,Р,С,Т,У,Ф,Х,Ц,Ч,Ш,Щ,Ъ,Ы,Ь,Э,Ю,Я,а,б,в,г,д,е,ё,ж,з,и,й,к,л,м,н,о,п,р,с,т,у,ф,х,ц,ч,ш,щ,ъ,ы,ь,э,ю,я")},
    { name:"hw", title:"韩文", content:toArray("ㄱ,ㄲ,ㄳ,ㄴ,ㄵ,ㄶ,ㄷ,ㄸ,ㄹ,ㄺ,ㄻ,ㄼ,ㄽ,ㄾ,ㄿ,ㅀ,ㅁ,ㅂ,ㅃ,ㅄ,ㅅ,ㅆ,ㅇ,ㅈ,ㅉ,ㅊ,ㅋ,ㅌ,ㅍ,ㅎ,ㅏ,ㅐ,ㅑ,ㅒ,ㅓ,ㅔ,ㅕ,ㅖ,ㅗ,ㅘ,ㅙ,ㅚ,ㅛ,ㅜ,ㅝ,ㅞ,ㅟ,ㅠ,ㅡ,ㅢ,ㅥ,ㅦ,ㅧ,ㅨ,ㅩ,ㅪ,ㅫ,ㅬ,ㅭ,ㅮ,ㅯ,ㅰ,ㅱ,ㅲ,ㅳ,ㅴ,ㅵ,ㅶ,ㅷ,ㅸ,ㅹ,ㅺ,ㅻ,ㅼ,ㅽ,ㅾ,ㅿ,ㆀ,ㆁ,ㆂ,ㆃ,ㆄ,ㆅ,ㆆ,ㆇ,ㆈ,ㆉ,ㆊ")},
    
    //{ name:"ewzm", title:lang.ewzm, content:toArray("")},
    //{ name:"ewzm", title:lang.ewzm, content:toArray("")},
    { name:"zyzf", title:lang.zyzf, content:toArray("ㄅ,ㄆ,ㄇ,ㄈ,ㄉ,ㄊ,ㄋ,ㄌ,ㄍ,ㄎ,ㄏ,ㄐ,ㄑ,ㄒ,ㄓ,ㄔ,ㄕ,ㄖ,ㄗ,ㄘ,ㄙ,ㄚ,ㄛ,ㄜ,ㄝ,ㄞ,ㄟ,ㄠ,ㄡ,ㄢ,ㄣ,ㄤ,ㄥ,ㄦ,ㄧ,ㄨ,<hr>,︰,￢,￤,℡,ˊ,ˋ,˙,–,―,‥,‵,∕,∟,∣,≒,≦,≧,⊿,═,║,╒,╓,╔,╕,╖,╗,╘,╙,╚,╛,╜,╝,╞,╟,╠,╡,╢,╣,╤,╥,╦,╧,╨,╩,╪,╫,╬,╭,╮,╯,╰,╱,╲,╳,▁,▂,▃,▄,▅,▆,▇,�,█,▉,▊,▋,▌,▍,▎,▏,▓,▔,▕,〒,〝,〞")}
];
(function createTab(content) {
    for (var i = 0, ci; ci = content[i++];) {
        var span = document.createElement("a");
        span.setAttribute("tabSrc", ci.name);
        span.setAttribute("href", '#'+ci.name);
        span.innerHTML = ci.title;
        if (i == 1)span.className = "focus";
        domUtils.on(span, "click", function () {
            var tmps = $G("tabHeads").children;
            for (var k = 0, sk; sk = tmps[k++];) {
                sk.className = "";
            }
            tmps = $G("tabBodys").children;
            for (var k = 0, sk; sk = tmps[k++];) {
                sk.style.display = "none";
            }
            this.className = "focus";
            $G(this.getAttribute("tabSrc")).style.display = "";
        });
        $G("tabHeads").appendChild(span);
        domUtils.insertAfter(span, document.createTextNode("\n"));
        var div = document.createElement("div");
        div.id = ci.name;
        div.style.display = (i == 1) ? "" : "none";
        var cons = ci.content;
        for (var j = 0, con; con = cons[j++];) {
            if(con == "" || con =="<hr>") {
                var charSpan = document.createElement("hr");
                div.appendChild(charSpan);
                continue;
            }
            var charSpan = document.createElement("span");
            charSpan.innerHTML = con;
            domUtils.on(charSpan, "click", function (evt) {
                editor.execCommand("insertHTML", this.innerHTML);
                if ( !evt.ctrlKey && !evt.metaKey) { // ctrl键可同时输入多个
                    if( dialog.popup ) {
                        dialog.popup.hide();
                    }
                    else{
                        dialog.close();
                    }
                }
            });
            div.appendChild(charSpan);
        }
        $G("tabBodys").appendChild(div);
    }
})(charsContent);
function toArray(str) {
    return str.split(",");
}
