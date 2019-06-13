<#assign static_domain = "${sysStaticUrl!!}">
<#assign static_acedomain = "${sysAceStaticUrl!!}"> 
<#assign static_ztree = "${sysZtreeStaticUrl!!}"> 
<#assign static_version = "20181022">
<#assign threecId = 10000000>
<#assign merchantStatusDesc = {"off":"未提交","audit":"审核中","pass":"审核通过","reject":"审核拒绝"}>
<#assign productAuditStatusDesc = {"audit":"待审核","pass":"审核通过","reject":"审核拒绝"}>
<#assign priceShowType = {"price_month":"月供","price_total":"总价","price_day":"日供"}>
<#assign tradeSource = {"app":"APP","site":"官网","h5","h5","website":"官网"}>
<#--reg-基础授信前;credit-完整授信前;shop1-购买第一单前;loan1-取点花第一单前;-->
<#--<#assign blockUserStatus = {"all":"all","reg":"基础授信前","credit":"完整授信前","shop1":"购买第一单前","loan1":"取点花第一单前"}>-->
<#assign blockUserStatus = {"all":"all","reg":"基础授信前","credit":"完整授信前"}>
<#assign productPromotionStatusDesc = {"off":"等待推广","on":"正在推广"}>
<#assign productTags = ["手机","电脑","平板","数码","其它"]>
<#assign tradeStatusDesc = {"TRADE_NO_CREATE_PAY":"没有创建支付交易","WAIT_BUYER_PAY":"等待买家付款","WAIT_SELLER_SEND_GOODS":"等待卖家发货","WAIT_BUYER_CONFIRM_GOODS":"卖家已发货","TRADE_BUYER_SIGNED":"买家已签收","TRADE_CLOSED":"付款以后用户退款成功","TRADE_CLOSED_BY_USER":"付款前关闭交易"}>
<#assign refundStatusDesc = {"101":"等待卖家同意退款申请","102":"等待卖家同意退款申请","103":"卖家不同意退款申请","104":"客服已介入","105":"卖家已同意退款","106":"已退货,等待卖家确认收货","107":"退款已经关闭","108":"退款受理成功","110":"退款成功"}>
<#assign subjectStatus = {"ONLINE":"上线","OFFLINE":"下线","DELETE":"删除"}>
<#assign refundUserType = {"user":"用户","merchant":"商家","support":"客服","system":"系统"}>
<#assign platformType = {"aiyoumi":"多智啦品牌"}>
<#assign businessType = {"product":"商品交易","qdh":"取点花","byj":"嗨花"}>
<#assign ruleStatus ={"ENABLE":"启用","DISABLE":"停用"}>
<#assign rateActiveStatus ={"AUDIT":"初始化","PASS":"审核通过","REJECT":"审核拒绝"}>
<#assign chectInOutypes = {"purchase":"采购单","reback":"退款单","produce":"生产单"}>

<#assign customerType = {"all": "全部用户", "productNewCustomer": "商品新用户", "productOldCustomer": "商品老用户", "byjNewCustomer": "嗨花新用户", "byjOldCustomer": "嗨花老用户", "loanNewCustomer": "取点花新用户", "loanOldCustomer": "取点花老用户"}>

<#--插入页面 -->
<#macro include page>
<#include "${page}">
</#macro>

<#--插入页登录 头 -->
<#macro webheadLogin>
<@include page="common/top_login.ftl" />
</#macro>

<#--插入页头 -->
<#macro webhead>
<@include page="common/top.ftl" />
</#macro>

<#--插入页尾 -->
<#macro webend>
<@include page="common/end.ftl" />
</#macro>

<#--主体内容头部 -->
<#macro mainContainerTop>
<@include page="common/main_container_top.ftl" />
</#macro>

<#--主体内容底部 -->
<#macro mainContainerBottom>
<@include page="common/main_container_bottom.ftl" />
</#macro>


<#--定义页面内容页导航条 -->
<#macro mainPageContentTop>
<@include page="common/page/mainPageContentTop.ftl" />
</#macro>


<#--插入JS-->
<#macro includeJs jsList>
<#list jsList?split(",") as js>
<script type="text/javascript" charset="utf-8" src="<#if !(js?starts_with('/'))>${jsPash?if_exists}/</#if>${js}"></script>
</#list>
</#macro>

<#--插入datepicker-->
<#macro includeDatepicker url start end>
<div class="time">
    <form action="${url}" method="get">
        <em>自定义日期：</em>
        <input type="text" value="${start}" class="base-input J_Time" name="startTime" /><span>至</span>
        <input type="text" value="${end}" class="base-input J_Time" name="endTime"/><input type="submit" value="筛选" class="btn editor">
    </form>
</div>
</#macro>

<#macro includeEditorJs>
<@model.includeJs jsList="/editor/editor_config.js"/>
<@model.includeJs jsList="/editor/editor_all.js"/>
<link rel="stylesheet" type="text/css" href="/editor/themes/default/ueditor.css"/>
</#macro>
<#-- 标题格式化输出 xxh -->
<#macro printLen infovar size>
    <#assign info = infovar />
    <#if info?exists><#if info?length lte size>${info!!}<#else>${info[0..size]}...</#if></#if>
</#macro>
<#macro includeDateInputJs>
<@model.includeJs jsList="plugins/date_input.js"/>
<link href="/themes/date_input.css" type="text/css" rel="stylesheet" media="screen" />
</#macro>

<#macro includeUploadJs>
<@model.includeJs jsList="upload/fileprogress.js,upload/swfupload.js,upload/swfupload.queue.js,upload/upload.js"/>
<link href="/themes/upload.css" type="text/css" rel="stylesheet" media="screen" />
</#macro>

<#macro cmdSelect obj values names selectedvalue placeholder="请选择" noselect=[] className="">
    <select name="${obj!}" <#if className?? && className?string?length gt 0>class="${className}"</#if>>
        <#if placeholder?? && (placeholder?string != '')>
        <option value="">请选择</option>
        </#if>
        <#list values as v>
            <option value="${v}" <#if (noselect?seq_contains(v))>_noselect="1"</#if>
            <#if (selectedvalue?exists &&selectedvalue==v?string)>selected</#if>>${names[v_index]}</option>
        </#list>
    </select>
</#macro>

<#macro cmdSelectFilter name obj selectedvalue>
    <select id="${name}" name="${name}">
        <option value="">请选择</option>
        <#list obj as v>
            <option value="${v.id}" data-code="${v.code}" <#if (selectedvalue?exists && selectedvalue==(v.id)?string)>selected</#if>>${v.typeName}</option>
        </#list>
    </select>
</#macro>

<#macro cmdSelectOper obj values names selectedvalue noselect=[] >
    <select id="${obj}" name="${obj}">
        <option value="">请选择</option>
    <#list values as v>
        <option value="${v}" <#if (noselect?seq_contains(v))>_noselect="1"</#if> <#if (selectedvalue?exists &&selectedvalue==v?string)>selected</#if>>${names[v_index]}</option>
    </#list>
    </select>
    <button type="button" onclick="return cmd_select_prompt('#${obj}');" class="submitbtn">操 作</button>
</#macro>

<#macro cmdSelectAll obj values names selectedvalue noselect=[] className="">
<select id="${obj}" name="${obj}" <#if className?? && className?string?length gt 0>class="${className}"</#if>>
  <option value="">全部</option>
  <#list values as v>
  <option value="${v}" <#if (noselect?seq_contains(v))>_noselect="1"</#if>
  <#if (selectedvalue?exists &&selectedvalue==v?string)>selected</#if>>${names[v_index]}</option>
  </#list>
</select>
</#macro>

<#-- 显示字符串中的部分，源字符串用逗号分隔 -->
<#function showStringBy(sourceStr,index) >
    <#return vm.getStringByIndex(sourceStr,index) />
</#function>

<#-- 获取资源信息根据类型和 -->
<#function getResourceValue(type,key) >
    <#return vm.getResourceValue(type,key) />
</#function>

<#--显示时间 -->
<#function showDate value=0>
    <#if value?exists>
        <#local format = "yyyy年MM月dd日" />
        <#return vm.dateFormate(value,format) />
    <#else>
        <#return "" />
    </#if>
</#function>

<#function showDateSimple value=0>
    <#if value?exists>
        <#local format = "yyyy-MM-dd" />
        <#return vm.dateFormate(value,format) />
    <#else>
        <#return "" />
    </#if>
</#function>

<#function showTime value=0>
    <#if value?exists>
        <#local format = "yyyy年MM月dd日 HH:mm:ss" />
        <#return vm.dateFormate(value,format) />
    <#else>
        <#return "" />
    </#if>
</#function>

<#function showHHMMTime value=0>
    <#if value?exists>
        <#local format = "yyyy年MM月dd日 HH:mm" />
        <#return vm.dateFormate(value,format) />
    <#else>
        <#return "" />
    </#if>
</#function>

<#function showDateHuman value=0>
    <#if value?exists>
        <#return vm.dateFormateHuman(value) />
    <#else>
        <#return "" />
    </#if>
</#function>

<#function isSelected value select="-1">
    <#if "${value}"=="${select}">
        <#return "selected=\"true\"" />
    <#else>
        <#return "" />
    </#if>
</#function>

<#function isChecked value select="-1">
    <#if "${value}"=="${select}">
        <#return "checked=\"true\"" />
    <#else>
        <#return "" />
    </#if>
</#function>

<#function getValue value showvalue="">
    <#if (value?exists && value?length>0)>
        <#return value />
    <#else>
        <#return showvalue />
    </#if>
</#function>

<#function showTypeName values names value>
    <#list values as v>
        <#if value==v><#return names[v_index] /></#if>
    </#list>
    <#return "" />
</#function>

<#function setTypeChecked values value>
    <#if vm.findArr(value,values,",")><#return "checked=\"checked\"" /></#if>
    <#return "" />
</#function>

<#macro showSearchField field fieldName nowField orderBy>
<a href="#" onclick="<#if (nowField!=field || orderBy=='asc')>searchSubmit('${field}','desc')<#else>searchSubmit('${field}','asc')</#if>;return false;">${fieldName}<#if (nowField==field)><#if (orderBy=="desc")><img src="/images/arrow_down.gif" align="absmiddle" /><#else><img src="/images/arrow_up.gif" align="absmiddle" /></#if></#if></a>
</#macro>

<#macro showSearchSizeSelect searchSize >
<select name="searchSize">
    <#list [10,20,30,50,100] as t><@model.showOption value=t title="${t}条" select="${searchSize?default(20)}" /></#list>
</select>
</#macro>


<#macro showOption value title select="-1" >
<option value="${value?if_exists}" ${isSelected("${value?if_exists}","${select?if_exists}")} >${title?if_exists}</option>
</#macro>

<#macro showSelect name type value="-1">
<#local list = vm.getResourceListByType(type) />
<select name="${name}">
    <option value="">——请选择——</option>
<#list list as res>
    <@showOption value="${res.value?if_exists}" title="${res.title?if_exists}" select="${value?if_exists}" />
</#list>
</select>
</#macro>

<#macro baseOutOption list outValue="-1">
<#list list as res><#if list.id==outValue>
    <option value="${res.id?if_exists}">${res.name?if_exists}</option>
</#if></#list>
</#macro>

<#macro baseOption list value="-1" >
<#list list as res>
    <@showOption value="${res.id?if_exists}" title="${res.name?if_exists}" select="${value?if_exists}" />
</#list>
</#macro>

<#macro showPage url p parEnd startname="start" sizename="size">
<#assign split = "?" />
<#if (url?index_of("?")>0)><#assign split = "&" /></#if>
<#if (!parEnd?starts_with("&"))><#local parEnd = "&"+parEnd /></#if>
<div class="pagination">
    <span>共 ${p.totalRow} 条</span>
    <#if (p.previousPage)?? && (p.previousPage > 0)>
    <a href="${url}${split}${startname}=${p.prePage.startRow}&${sizename}=${p.showNum}${parEnd?if_exists}">上一页</a>
    </#if>
    <#if (p.startPage)?? && (p.startPage!=1)>
    <a href="${url}${split}${startname}=0&${sizename}=${p.showNum}${parEnd?if_exists}">1</a>
    <#if p.startPage gt 2>
    <a>...</a>
    </#if>
    </#if>
    <#list p.getVPages() as pp>
        <#if (pp.page == p.currentPage )>
            <#if p.totalPage gt 1>
            <a href="#" onclick="return false;" class="current">${pp.page}</a>
            </#if>
        <#else>
            <a href="${url}${split}${startname}=${pp.startRow}&${sizename}=${p.showNum}${parEnd?if_exists}">${pp.page}</a>
        </#if>
    </#list>
    <#if (p.totalPage)?? && (p.endPage)?? && (p.totalPage!=p.endPage)>
    <#if (p.endPage)?? && (p.endPage lt p.totalPage - 1)>
    <a>...</a>
    </#if>
    <a href="${url}${split}${startname}=${p.lastPageStartRow}&${sizename}=${p.showNum}${parEnd?if_exists}">${p.totalPage}</a>
    </#if>
    <#if (p.nextPage)?? && (p.nextPage > 0)>
    <a href="${url}${split}${startname}=${p.NPage.startRow}&${sizename}=${p.showNum}${parEnd?if_exists}">下一页</a>
    </#if>
</div>
</#macro>

<#macro pages url="">
<#assign pageNo=page.getPageNo()?default('1') />
<html>
    <head>
        <script language="javascript">
            function filterPageForm_submit(page){
                if(page){
                    document.getElementById("pageNo").value=page;
                }
                var form = document.getElementById("filterPageForm1");
                form.submit();
            }
        </script>
    </head>
<body>
<form id="filterPageForm1" name="filterPageForm1" method="post" action="<#if url=="">${request.getRequestUri()}<#else>${url}</#if>" onsubmit="return checkForm()">
<input name="pageNo" id="pageNo" value="${pageNo}" type="hidden" />
<#list RequestParameters?default({})?keys as list>
<#if list!='pageNo'>
    <input type="hidden" name="${list}" id="${list}" value="${RequestParameters[list]?default('')}"/>
    </#if>
</#list>
</form>

当前页: ${pageNo}/${page.getTotalPages()?default('')} 页，共:${page.getTotalCount()?default('0')}条数据
    &nbsp;&nbsp;
        <a href="javascript:filterPageForm_submit(1);">首页</a>
        <#if (pageNo?number-3>0)>
            ...&nbsp;
        </#if>
        <#if (pageNo?number-2>0)>
            <a href="javascript:filterPageForm_submit(${pageNo-2});">${pageNo?default('0')?number-2}</a>&nbsp;
        </#if>
        <#if (pageNo?number-1>0)>
            <a href="javascript:filterPageForm_submit(${pageNo-1});">${pageNo-1}</a>&nbsp;
        </#if>
        <#if (pageNo?number>0)>
            ${pageNo}&nbsp;
        </#if>
        <#if (page.getTotalPages()?default('0')?number>=pageNo?number+1)>
            <a href="javascript:filterPageForm_submit(${pageNo+1});">${pageNo+1}</a>&nbsp;
        </#if>
        <#if (page.getTotalPages()?default('0')?number>=pageNo?number+2)>
            <a href="javascript:filterPageForm_submit(${pageNo+2});">${pageNo+2}</a>&nbsp;
        </#if>
        <#if (page.getTotalPages()?default('0')?number>=pageNo?number+3)>
            <a href="javascript:filterPageForm_submit(${pageNo+3});">${pageNo+3}</a>&nbsp;
        </#if>
        <#if (page.getTotalPages()?default('0')?number>=pageNo?number+4)>
            <a href="javascript:filterPageForm_submit(${pageNo+4});">${pageNo+4}</a>&nbsp;
        </#if>
        <#if (page.getTotalPages()?default('0')?number>=pageNo?number+5)>
            ...&nbsp;
        </#if>
        <a href="javascript:filterPageForm_submit(${page.getTotalPages()});" >末页</a>
</#macro>
<!---订单状态filter--->
<#macro tradeStatusFilter  status>
    <#if status?exists>
      <#if (status=='TRADE_NO_CREATE_PAY')>
          没有创建支付交易
        <#elseif (status=='WAIT_BUYER_PAY')>
        等待买家付款
        <#elseif (status=='WAIT_PAY_RETURN')>
        支付中
        <#elseif (status=='WAIT_SELLER_SEND_GOODS')>
        买家已付款
        <#elseif (status=='WAIT_BUYER_CONFIRM_GOODS')>
        卖家已发货
        <#elseif (status=='TRADE_BUYER_SIGNED')>
        交易成功
        <#elseif (status=='TRADE_CLOSED')>
        付款后关闭
        <#elseif (status=='TRADE_CLOSED_BY_USER')>
        付款前关闭
        </#if>
    </#if>
</#macro>
<!---授信状态filter--->
<#macro creditStatusFilter status>
      <#if (status)??>
        <#if (status)=='NOT_APPLY'>
            未提交申请
        <#elseif (status)=='WAIT_TAKE'>
            提交申请
        <#elseif (status)=='WAIT_APPOINT'>
            等待面签
        <#elseif (status)=='WAIT_AUTH'>
            提交审核
        <#elseif (status)=='AUTHING'>
            审核中
        <#elseif (status)=='PASS'>
            审核通过
        <#elseif (status)=='REJECT'>
            审核拒绝
        <#elseif (status)=='CLOSE'>
            已关闭
        </#if>
      </#if>
</#macro>

<#macro auditStatFilter status>
    <#if (status)??>
        <#if (status)=='0'>待支付
        <#elseif (status)=='1'>审核通过
        <#elseif (status)=='2'>审核拒绝
        <#elseif (status)=='3'>支付成功
        <#elseif (status)=='4'>支付失败
        <#elseif (status)=='5'>已收货
        <#elseif (status)=='6'>已取消
        <#elseif (status)=='7'>短信通知审核
        <#elseif (status)=='8'>电审
        <#elseif (status)=='9'>面签
        </#if>
    </#if>
</#macro>
<!---退货状态 --->
<#macro refoundTypeFilter type>
    <#if (type)??>
        <#if (type)=='REFUND_GOODS'>退款退货
        <#elseif (type)=='REFUND_MONEY'>仅退款
        </#if>
    </#if>
</#macro>

<!----退款状态---->
 <#macro refoundStatusFilter status>
    <#if (status)?exists>
        <#if (status=='0')>
        无退款
        <#elseif (status=='101')>退款待处理<#elseif (status=='102')>再次等待卖家同意退款申请<#elseif (status=='103')>已拒绝退款<#elseif (status=='104')>客服已介入<#elseif (status=='105')>退款中<#elseif (status=='106')>待卖家收货<#elseif (status=='107')>
        退款关闭
        <#elseif (status=='108')>
        系统处理退款中
        <#elseif (status=='110')>
        退款成功
        <#elseif (status=='202')>
        待卖家同意退货
        <#elseif (status=='203')>
        待买家退货
        <#elseif (status=='204')>
        等待卖家同意退款
        </#if>
    </#if>
</#macro>
<#macro refundMoneyStatusFilter status>
    <#if (status)?exists>
        <#if (status)=='0'>
                     未退款申请
        <#elseif (status)=='1'>
                    退款申请中
        <#elseif (status)=='2'>
                    退款成功
        <#elseif (status)=='3'>
                     退款失败
        </#if>
    </#if>
</#macro>

<!-- crm获取消息模板 -->
<#macro msgCodeStyle caseId sendType msgCode>
    <#if (sendType)?exists>
        <#if (sendType)=='dx'>
            <#if (msgCode)?length gt 10>
                <a style="text-decoration:none;" title=${msgCode}>${msgCode?substring(0,10)}...</a>
            <#else>
                <a>${msgCode}</a>
            </#if>
        <#else>
            <a href="javascript:;" onclick="getMsgCodeTemp('${caseId}','${sendType}','${msgCode}')">${msgCode}</a>
        </#if>
    </#if>
</#macro>

<!--crm红包配置-->
<#macro getCoupon caseId templateId sendType>
    <#if sendType!="dx">
        <#if templateId=="0">
            <a id="caseId_${caseId}" href="javascript:;" onclick="couponOption('${caseId}','${templateId}')">点击配置</a>
        <#else>
            <a id="caseId_${caseId}" href="javascript:;" onclick="couponOption('${caseId}','${templateId}')">${templateId}</a>
        </#if>
    <#else>
        无需红包配置
    </#if>
</#macro>

<!--crm今日是否有数据提供下载-->
<#macro isDownLoad caseId todayTotal lastWeekTotal>
    <#if (todayTotal!="0")>
        ${todayTotal}&nbsp;<a href="javascript:void(0);" onclick="crm_download(${caseId},'today')">今天&#8595;</a>&nbsp;
    </#if>
    <#if (lastWeekTotal!="0")>
        &nbsp;<a href="javascript:void(0);" onclick="crm_download(${caseId},'former')">前一周&#8595;</a>
    </#if>
</#macro>
<!--crm用例状态-->
<#macro crmStatus caseId status type>
    <#if (status=="y")>
        <div id="${type}_case${caseId}" class="statusON">
            <input type="checkbox" onchange="changeStatus(${caseId},'${status}','${type}')" checked/>
            <a class="block"></a>
        </div>
    <#elseif (status="n")>
        <div  id="${type}_case${caseId}" class="statusOFF">
            <input type="checkbox" onchange="changeStatus(${caseId},'${status}','${type}')"/>
            <a class="block"></a>
        </div>
    </#if>
</#macro>

<#function isAiYouMi imgUrl>
    <#return (imgUrl?index_of("aliyuncs.com") > 0 || imgUrl?index_of("img.aixuedai.com") > 0 || imgUrl?index_of("img.aiyoumi.com") > 0)/>
</#function>

<#function getImgUrl imgUrl imgSize="">
    <#if (imgUrl?exists && imgUrl?length>0)>
       <#--判断是否多智啦品牌的图片-->
        <#if (isAiYouMi(imgUrl))>
            <#return "${imgUrl}${imgSize}" />
        <#else>
             <#return "${imgUrl}" />
        </#if>
    <#else>
        <#return "${model.static_domain}/images/default.gif" />
    </#if>
</#function>
