<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<!-- saved from url=(0030)http://wyq.sina.com/home.shtml -->
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<!-- saved from url=(0014)index:internet -->

		<meta http-equiv="X-UA-Compatible" content="IE=Edge">
		<meta name="renderer" content="webkit">

		<title>鹏越舆情-互联网大数据平台</title>
		<meta name="author" content="">
		<meta name="keywords"
			content="微信舆情监测,微博舆情监控,舆情监测, 舆情APP ,舆情客户端，舆情软件,互联网舆情,视频舆情,海外媒体，舆情APP ,舆情客户端，舆情预警">
		<meta name="description"
			content="微舆情是中国最大的免费舆情服务平台，提供网页、微博、微信、电子报、视频等全媒体舆情监测方案，以及舆情事件分析、舆情简报制作，舆情日周月报自动发送、海外媒体监测、竞争情报服务等">
		<meta name="searchtitle"
			content="舆情监控,舆情监测,微博舆情监控,舆情监控软件,舆情监测系统,互联网舆情监测,舆情监控系统,互联网舆情监控,舆情监测软件,网络舆情监控系统">

		<script type="text/javascript">

var njxImgSrc = '';
var actionBase = '';
var staticResourcePath = 'http://files.wyq.cn/web';
var qrCodeImg = 'http://app.wyq.cn/QRCode/wyq_d_entry.png';
</script>
		<script type="text/javascript"
			src="<%=basePath%>sinaStyle/jquery-1.10.2.min.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>sinaStyle/index.js">
</script>
	 
		<script type="text/javascript"
			src="<%=basePath%>sinaStyle/navigate.js">
</script>
		<link href="<%=basePath%>sinaStyle/style.css" rel="stylesheet"
			type="text/css">
	 
		<link href="<%=basePath%>sinaStyle/font-icon.css" rel="stylesheet"
			type="text/css">
 
		<script src="<%=basePath%>sinaStyle/fingerprint2.min.js"
			type="text/javascript" charset="utf-8">
		</script>
		
		<script src="<%=basePath%>sinaStyle/My97DatePicker/WdatePicker.js"></script>
	 
		<style type="text/css">
a.bshareDiv,#bsPanel,#bsMorePanel,#bshareF {
	border: none;
	background: none;
	padding: 0;
	margin: 0;
	font: 12px Helvetica, Calibri, Tahoma, Arial, 宋体, sans-serif;
	line-height: 14px;
}

#bsPanel div,#bsMorePanel div,#bshareF div {
	display: block;
}

.bsRlogo .bsPopupAwd,.bsRlogoSel .bsPopupAwd,.bsLogo .bsPopupAwd,.bsLogoSel .bsPopupAwd
	{
	line-height: 16px !important;
}

a.bshareDiv div,#bsFloatTab div { *
	display: inline;
	zoom: 1;
	display: inline-block;
}

a.bshareDiv img,a.bshareDiv div,a.bshareDiv span,a.bshareDiv a,#bshareF table,#bshareF tr,#bshareF td
	{
	text-decoration: none;
	background: none;
	margin: 0;
	padding: 0;
	border: none;
	line-height: 1.2
}

a.bshareDiv span {
	display: inline;
	float: none;
}

div.buzzButton {
	cursor: pointer;
	font-weight: bold;
}

.buzzButton .shareCount a {
	color: #333
}

.bsStyle1 .shareCount a {
	color: #fff
}

span.bshareText {
	white-space: nowrap;
}

span.bshareText:hover {
	text-decoration: underline;
}

a.bshareDiv .bsPromo,div.bshare-custom .bsPromo {
	display: none;
	position: absolute;
	z-index: 100;
}

a.bshareDiv .bsPromo.bsPromo1,div.bshare-custom .bsPromo.bsPromo1 {
	width: 51px;
	height: 18px;
	top: -18px;
	left: 0;
	line-height: 16px;
	font-size: 12px !important;
	font-weight: normal !important;
	color: #fff;
	text-align: center;
	background:
		url(http://static.bshare.cn/frame/images/bshare_box_sprite2.gif)
		no-repeat 0 -606px;
}

div.bshare-custom .bsPromo.bsPromo2 {
	background:
		url(http://static.bshare.cn/frame/images/bshare_promo_sprite.gif)
		no-repeat;
	cursor: pointer;
}
</style>
		<style type="text/css">
.bsBox {
	display: none;
	z-index: 100000001;
	font-size: 12px;
	background:
		url(http://static.bshare.cn/frame/images//background-opaque-dark.gif)
		!important;
	padding: 6px !important;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	border-radius: 5px;
}

.bsClose {
	_overflow: hidden;
	cursor: pointer;
	position: absolute;
	z-index: 10000000;
	color: #666;
	font-weight: bold;
	font-family: Helvetica, Arial;
	font-size: 14px;
	line-height: 20px;
}

.bsTop {
	color: #666;
	background: #f2f2f2;
	height: 24px;
	line-height: 24px;
	border-bottom: 1px solid #e8e8e8;
}

.bsTop span {
	float: left;
}

.bsFrameDiv,#bsMorePanel {
	border: none;
	background: #fff;
}

.bsReturn {
	float: right; *
	margin-right: 20px;
	margin-right: 36px;
	text-align: right;
	cursor: pointer;
	line-height: 24px;
	color: #666;
	opacity: 0.5;
}

#bsReturn:hover {
	text-decoration: underline;
	opacity: 1;
}
</style>
	 
		<style type="text/css">
div.bsClear {
	clear: both;
	height: 0;
	line-height: 0;
	overflow: hidden;
	font-size: 0;
}

.bsSearchDiv {
	padding: 5px 15px;
	background-color: #fafafa;
}

.bFind-wrapper-top {
	background: #fff;
	border-color: #ccc #aaa #aaa #ccc;
	border-style: solid;
	border-width: 1px;
	height: 16px;
	padding: 4px;
	margin: 0;
}

.bFind-wrapper-top input {
	padding: 0 !important;
	border: none !important;
	box-shadow: none !important;
	line-height: 16px !important;
}

.bFind-placeholder {
	background: url("http://static.bshare.cn/css/images/search-icon.gif")
		no-repeat;
	display: block;
	float: left;
	height: 16px;
	width: 16px;
}

.bFind {
	background: none;
	border: none;
	float: left;
	font-size: 11px !important;
	height: 16px !important;
	margin-left: 3px;
	outline: none;
	padding: 0;
	width: 400px;
}

.bsPlatDiv {
	height: 322px;
	background: #fff;
	overflow: auto;
	padding: 0 15px;
}

#bsLogoList {
	display: block;
	list-style: none;
	overflow: hidden;
	margin: 0;
	padding: 0;
}

#bsLogoList li {
	float: left;
	display: inline-block;
	width: 71px;
	text-align: center;
	font-size: 12px;
	height: 80px;
	margin: 0 !important;
}

#bsLogoList .bsPlatIcon {
	cursor: pointer;
	display: block !important;
	text-align: center;
}

#bsLogoList .bsPlatImg {
	width: 32px;
	height: 32px;
	border: none !important;
	display: inline-block;
}

#bsLogoList .bsPlatImg:hover {
	-moz-border-radius: 7px;
	-webkit-border-radius: 7px;
	border-radius: 7px;
	box-shadow: 0 0 15px #a7a8ac;
}

#bsLogoList .bsPlatName {
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	text-align: center;
	color: #333 !important;
	margin-top: 2px;
	line-height: 140%; *
	width: 70px;
}

#bsLogoList .bsPromoM {
	text-align: center;
}

.bsFooterDiv {
	height: 24px;
	line-height: 24px;
	padding: 0 15px;
	border-top: 1px solid #e8e8e8;
	background: #f2f2f2;
	text-align: right;
}

a.bsLogoLink {
	color: #666;
}

.bsLogoLink:hover {
	text-decoration: underline;
}

.bsPromoM {
	background:
		url(http://static.bshare.cn/frame/images//bshare_box_sprite2.gif)
		no-repeat top left;
}

.bsNew,.bsHot,.bsRec,.bsAwd {
	background-position: 0 -552px;
	width: 19px;
	margin: 5px auto 1px;
	line-height: 16px;
	height: 18px;
	font-size: 12px;
	color: #fff;
	overflow: hidden;
}

.bsNew {
	background-position: 0 -570px;
}

.bsRec {
	width: 30px;
	background-position: 0 -588px;
}

.bsAwd {
	background:
		url(http://static.bshare.cn/frame/images//promot/promote.gif)
		no-repeat;
}
</style>
		<script src="<%=basePath%>sinaStyle/bshare_view"
			type="text/javascript" charset="utf-8">
</script>
		<script src="<%=basePath%>sinaStyle/bsPlatforms.js"
			type="text/javascript" charset="utf-8">
</script>
	</head>



	<!-- 导航条用户的下拉效果-->

	<!-- 页面右侧的客户端展示的效果-->





	<body class="loginHome">
	
	
	
		<div class="modal fade" id="changeZLM" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h1 class="modal-title" id="myModalLabel">
					改变倾向
				</h1>
			</div>
			<div class="modal-body">
				<div class="radio">
    <label>
      <input type="checkbox">请打勾
    </label>
  </div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary">
					提交更改
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
	
	
		<div id="wx_pic" style="margin: 0 auto; display: none;">
			<img src="<%=basePath%>sinaStyle/share.png">
		</div>

		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="renderer" content="webkit|ie-comp|ie-stand">
		<meta charset="GBK">
		<title>舆情监控</title>
		<meta name="author" content="">
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="searchtitle" content="">
		<script type="text/javascript">
var basePath = '';
<!--选中文字显示按钮效果 end-->


        $(function(){
            $("#noticeLink").on("click",function(){
                $("#notice").addClass('rightIn');
                $("#notice").removeClass('rightOut');

            });
            $("#closeNotice").on("click",function(){
                $("#notice").addClass('rightOut');
                $("#notice").removeClass('rightIn');
            });
        });
</script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>sinaStyle/common.css">
		<link href="<%=basePath%>sinaStyle/home.css" rel="stylesheet"
			type="text/css">
		<link href="<%=basePath%>sinaStyle/jquery-ui-1.9.1.custom.css"
			rel="stylesheet" type="text/css">
	 
		<link href="<%=basePath%>sinaStyle/prettify.css" type="text/css"
			rel="stylesheet">

		<script type="text/javascript"
			src="<%=basePath%>sinaStyle/jquery-1.10.2.min.js">
</script>
		<script type="text/javascript"
			src="<%=basePath%>sinaStyle/jquery-ui-1.9.1.custom.js">
</script>
		<script type="text/javascript" src="<%=basePath%>sinaStyle/index.js">
</script>
		<script src="<%=basePath%>sinaStyle/jquery.JPlaceholder.js">
</script>
 
		<script type="text/javascript"
			src="<%=basePath%>sinaStyle/shouye_v2.js">
</script>
	 

		<script type="text/javascript">
var operateLogPageCode = '1040';
var operateLogPageName = '热度查询';
var operateLogRequestPath = '';
</script>


 
		<link href="<%=basePath%>sinaStyle/openWeiboImg.css" rel="stylesheet"
			type="text/css">
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>sinaStyle/accordion.css">
		<script type="text/javascript"
			src="<%=basePath%>sinaStyle/prettify.js">
</script>
		<script type="text/javascript"
			src="<%=basePath%>sinaStyle/jquery.slimscroll.js">
</script>

	<script type="text/javascript"
		src="<%=basePath%>sinaStyle/index/event.js">
	</script>


		<script>
var jquery2 = $;
var njxBasePath = '';
var baseAction = '';
function logout() {
	window.location.href = "/logout.action";
}
   
    </script>
    
		<!--系统公告 start-->
		<div id="notice" class="topFloat_r">
			<div class="notice">
				<a class="close" id="closeNotice" href="#" style="background: none;">×</a>
				<div>

					<div class="nTit">
						<h1>
							系统公告
						</h1>
					</div>

					<div class="accordion" id="noticeCon">
						<div class="slimScrollDiv"
							style="position: relative; overflow: hidden; width: auto; height: 100%;">
							<ul style="overflow: hidden; width: auto; height: 100%;">

								 


							</ul>
							<div class="slimScrollBar"
								style="width: 7px; position: absolute; top: 0px; opacity: 0.4; display: block; border-radius: 7px; z-index: 99; right: 1px; height: 79.9261px; background: rgb(0, 0, 0);"></div>
							<div class="slimScrollRail"
								style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; opacity: 0.2; z-index: 90; right: 1px; background: rgb(51, 51, 51);"></div>
						</div>
					</div>
				</div>

			</div>
		</div>
		<!--系统公告 end-->

		<form name="buyForm" method="post" action=" ">
			<input type="hidden" name="myProduct.keywordId" id="buyKeywordId"
				value="0">
			<input type="hidden" name="myProduct.keywordPackageNum"
				id="buyKeywordPackageNum" value="1">
			<input type="hidden" name="myProduct.keywordPackagePrice"
				id="buyKeywordPackagePrice" value="0">
		</form>
		<input type="hidden" id="sucaiTotal" value="0">
		<input type="hidden" id="collectTotal" value="0">
		<input type="hidden" id="cartTotal" value="0">
		<input type="hidden" id="userId" value="185482">
		<input type="hidden" id="firstLoginSign" value="0">
		<div id="top"></div>
		<!--head代码 start-->
		<div id="head" class="rel h_line">
			<span class="logon abs"></span>
			<nav class="abs">
			<ul>
 
					<li class="curr">
					<a href="<%=basePath%>search_index">首页</a>
 
				<li>
 				<%@ include file="/main/home/menu2.jsp" %>
				<li>
					<a href="<%=basePath%>report_findReportPage">舆情简报</a>
				</li>
		 		<c:if test="${sessionScope.user.loginId == 'admin'}">
					<li>
						<a href="<%=basePath%>home_queryUser">用户管理</a>
					</li>
				</c:if>

			</ul>

			</nav>

			<!-- headinfo -->
			<%@ include file="/main/home/headinfo.jsp" %>

		</div>
		<!--head代码 end-->


		<script type="text/javascript">
 
function isMobile() {
	var bs = {
		versions : function() {
			var u = navigator.userAgent, app = navigator.appVersion;
			return {
				windowsPhone : u.indexOf('IEMobile') > -1,
				trident : u.indexOf('Trident') > -1,
				presto : u.indexOf('Presto') > -1,
				webKit : u.indexOf('AppleWebKit') > -1,
				gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') === -1,
				mobile : !!u.match(/AppleWebKit.*Mobile.*/)
						|| !!u.match(/AppleWebKit/) || !!u.match(/IEMobile/),
				ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
				android : u.indexOf('Android') > -1
						|| u.indexOf('UCBrowser') > -1,
				iPhone : u.indexOf('iPhone') > -1,
				iPad : u.indexOf('iPad') > -1,
				webApp : u.indexOf('Safari') === -1
			};
		}(),
		language : (navigator.browserLanguage || navigator.language)
				.toLowerCase()
	};
	if (bs.versions.mobile) {
		if (bs.versions.android || bs.versions.iPhone || bs.versions.iPad
				|| bs.versions.windowsPhone) {
					//$('.fenxiang').css('display', 'none');
					//$('#weibo_task_result_star_content_div,#weibo_task_result_line_content_div,#weibo_task_result_fans_div').css('height', '220px');
					location.href = "onclick",
					"http://h5.wyq.cn/register.action";
		}
	} else {
		location.href = "/user/goRegister.shtml";
	}
}

 
</script>












		<meta http-equiv="X-UA-Compatible" content="IE=Edge">
		<title>网络舆情</title>



		<script type="text/javascript">

var njxImgSrc = '';
var actionBase = '';
var staticResourcePath = 'http://files.wyq.cn/web';
</script>
		<script type="text/javascript"
			src="<%=basePath%>sinaStyle/jquery-1.10.2.min.js">
</script>



		<link type="text/css" rel="stylesheet"
			href="<%=basePath%>sinaStyle/tips.css">
		<script type="text/javascript" src="<%=basePath%>sinaStyle/tips.js">
</script>
		<input id="style" name="style" value="0" type="hidden">
		<div id="BgDiv" style="display: none"></div>
		 

		 <div class="page-container2">
			<div class="content">
				
				<!--右侧搜索内容 start-->
				<div class="ksearch mt15">
					 
					<!--条件筛选 start-->
				 <div class="xx_set rel">
				 <div class="row-fluid" >
				<div class="align_c">
					<div class="hotWordsList" id="hotKeyword"
						style=" text-align: center;">
				 
					 
						<span> 
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						检索时间：
						 <input type="text" value="<s:property value="startTime"/>"
								class="input time hasDatepicker" id="starttime" readonly="readonly"
								style="width: 120px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})">
						- <input type="text" value="<s:property value="endTime"/>"
							class="input time hasDatepicker" id="endtime" readonly="readonly"
							style="width: 120px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})">
							
						</span>
					</div>
				</div>
			</div>
				 <div class="row-fluid"  style="margin-top: 70px;">
				<div class="">
					 
					<div class="search-wrapper" style="padding-right: 100px;">
						<form id="searchForm" action="<%=basePath%>search_newsKeyword" method="post">
							<!-- <input type="hidden" name="keyword" id="keyword" value="">
							<input type="hidden" name="filterKeyword" id="filterKeyword" value=""> -->
							
							<input type="hidden" name="queryWord" class="queryWord" value="<s:property value="queryWord"/>"/>
							<input type="hidden" name="parentSourceId" class="parentSourceId" value="<s:property value="parentSourceId"/>"/>
							<input type="hidden" name="currentPage" class="currentPage" value=""/>
							<input type="hidden" name="fenci" class="fenci" value="<s:property value="fenci"/>"/>
							<input type="hidden" class="starttime" name="startTime" value="<s:property value="startTime"/>"/>
							<input type="hidden" class="endtime" name="endTime" value="<s:property value="endTime"/>"/>
							
							<div class="search" style="">
								<span> <input type="text" style="display: none">
									<i class="icon-search"></i> <input type="text" class="keyWord"
										name="title" autocomplete="off" maxlength="20"
										onkeydown="enterClick(event)" oninput="thinkKeywords(this)"
										onmousedown="history()" onfocus="glb_searchTextOnfocus(this);"
										onblur="glb_searchTextOnBlur(this);" id="title"
										placeholder="人名、事件名等关键词"
										value="<s:property value="queryWord"/>" style="color: rgb(170, 170, 170);">
								</span>
								<span>
									<a href="javascript:mtSearch(false)" class="searchBtn">标题检索</a>
								</span>
								<a
									href="javascript:mtSearch(true)"
									class="btn btn-info abs"
									style="top: -1px; right: -121px; padding: 11.5px; font-size: 16px;display:block;">
									<i class="icon-whatshot"></i>全文检索</a>
								<div id="history_search" style="display: none;">
									<ul id="keyWordLogs_ul" class="hotwords"
										style="display: block;">
										<li class="hwtitle">
											<span style="display: block; float: left;">最近搜索</span>
											<span onclick="deleteUserSearch()" class="scjl">删除搜索记录</span>
										</li>
										<li style="cursor: pointer;" onclick="ajaxClickEvl(this)">
											<h1>
												asfd
											</h1>
										</li>
										<li style="cursor: pointer;" onclick="ajaxClickEvl(this)">
											<h1>
												sadf
											</h1>
										</li>
									</ul>
								</div>
								<ul id="hotwords" class="hotwords" style="display: none;">

								</ul>
							</div>
						</form>
					</div>

				</div>
			</div>
			<div class="row-fluid">
				<div class="align_c">
					<div class="hotWordsList" id="hotKeyword"
						style="width: 802px; padding-left: 20px;">
						<s:iterator value="keywordList" id="kw">
							<a href="javascript:hotSearch('<s:property value="#kw.KEYWORD"/>')"><s:property value="#kw.KEYWORD"/></a>
						</s:iterator>
					</div>
				</div>
			</div>
			
 
					</div>
					<!--条件筛选 end-->

					<!--搜索显示内容 start-->
					<div class="ks_con">



						<div id="findListDiv" class="ks_list">


							<!--[if lt IE 10]>
<script type="text/javascript" src="PIE.js"></script>
<![endif]-->
<script type="text/javascript" src="<%=basePath%>layer/layer.js"></script>
<script language="javascript">
$(function() {
	if (window.PIE) {
		$('.rounded').each(function() {
			PIE.attach(this);
		});

	}
});

//设置时间
function settime(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	$(".starttime").val(starttime);
	$(".endtime").val(endtime);
}

//全文搜索
function mtSearch(fenci){
	settime();
	var keyword = $("#title").val();
	$(".queryWord").val(keyword);
	$(".fenci").val(fenci)
	$("#searchForm").submit();
}
//弹出导出报告窗口
function importReportShow(id){
	$("#newsId").val(id);
	var div = $("#tanchu");
	
	//异步请求设置下拉框数据
	setreport();
	setAddressCode();
	
	//弹出div
	layer.open({
		  type: 1,
		  skin: 'layui-layer-rim', //加上边框
		  area: ['700px', '360px'], //宽高
		  content: div,
		  end:closelayer
		});
}
//导出报告
function importReport(){
	var newsId = $("#newsId").val();
	var reportId = $("#reportId").val();
	var addressCode = $("#addressCode").val();
	if(reportId == null || reportId == ""){
		layer.alert("请选择简报！");
		return;
	}
	if(addressCode == null || addressCode == ""){
		layer.alert("请选择板块！");
		return;
	}
	
	var load = layer.load(1, {
		  shade: [0.2,'#fff'] //0.2透明度的白色背景
		});
	//异步请求导出报告
	$.ajax({
		url:"<%=basePath%>news_reportAddr.action",
		type:"POST",
		data:{"newsId":newsId, "record.reportId":reportId, "record.addressCode":addressCode},
		success:function(data){
			layer.close(load);//关闭加载层
			//var json = eval("("+data+")");
			if(data.result == true){
				parent.layer.alert('保存成功',{
				    btn : ['确定'],
				    yes : function(index){
				    	closelayer();
				    }
				});
			}else{
				layer.alert(data.msg);
			}
		},
		error:function(){
			layer.close(load);//关闭加载层
			layer.alert("ajax请求出错！");
		}
	});
}
//关闭弹出层
function closelayer(){
	$("#newsId").val("");
	//$("#reportId option:first").prop("selected", "selected");
	//$("#addressCode option:first").prop("selected", "selected");
	$("#tanchu").hide();
	layer.closeAll();
}

//设置报告下拉框
function setreport(){
	$.ajax({
		url:"<%=basePath%>report_findReportToJson.action",
		type:"POST",
		success:function(data){
			var htm = "<option value=\"\">--请选择--</option>";
			$.each(data, function(i, item) { 
				  htm += "<option value=\""+item.id+"\">"+item.name+"</option>";
			});
			$("#reportId").html(htm);
		}
	});
}
//设置板块下拉框
function setAddressCode(){
	$.ajax({
		url:"<%=basePath%>report_findAllAddressToJson.action",
		type:"POST",
		success:function(data){
			var htm = "<option value=\"\">--请选择--</option>";
			$.each(data, function(i, item) { 
				  htm += "<option value=\""+item.code+"\">"+item.define+"</option>";
			});
			$("#addressCode").html(htm);
		}
	});
}

</script>
 
	<!--条件筛选 start-->
					 
				 <s:iterator value="sourceList" id="source" status="index">
				 	<s:if test="%{#index.index % 7 == 0}">
				 		<div class="xx_list" id="listDiv" style="visibility: visible;">
				 		<span class="list_tab" id="statList" style="display: block">
				 	</s:if>
				 	
				 	<s:if test="%{#source.fid == parentSourceId || (#source.fid == '' && parentSourceId == '')}">
					 	<a class="cur" id="na${index.index}">
				 	</s:if>
				 	<s:else>
					 	<a href="javascript:searchBySource(<s:property value="#source.fid"/>);" class="statLink" id="na${index.index}">
				 	</s:else>
							<s:property value="#source.fname"/>
							<br>
							<span id="all">
								<s:property value="#source.count"/>
							</span> 
					</a>
					<s:if test="%{#index.index % 7 == 6 || #index.count == sourceList.size()}">
						</span>
						</div>
						<div class="xx_set rel"></div>
					</s:if>
				 </s:iterator>
						 
							 
							<div class="xx_table">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="contenttext">
									<tbody>
										<tr>

											<th width="">
												标题
											</th>
											<th width="10%">
												点击量
											</th>
											<th width="15%">
												采集自
											</th>
											<th width="10%">
												时间
											</th>
										</tr>
										
										<s:iterator value="newsList">
											<tr>
												<input type="hidden" name="customFlag1"
													id="flag_21481118901750367682885" value="4">
												<input type="hidden" id="type_21481118901750367682885"
													value="wx">
												<input type="hidden" id="repeatNum_21481118901750367682885"
													value="0">
												<td>
													<div class="zhaiyao">
													
														<s:if test="image != null">
															<img src="<%=basePath%>images/logo/<s:property value="image"/>" class="tx">
														</s:if>
														<s:else>
															<img src="<%=basePath%>sinaStyle/app-more-icon-weixin.jpg" class="tx">
														</s:else>
														
														<div class="tit">
															<a href="<s:property value="url"/>" target="_blank">
																<s:property value="title" escape="false"/>
															</a>
														</div>
														<div class="sx">
															<em class="fm"><s:property value="zlm"/></em>
														</div>
														<div class="summaryDiv" style="">
															<s:property value="describe" escape="false"/>
														</div>
														<div class="tdBottom rel">
															<div class="r_tool r_tool2 r1 modify"
																onmouseover="setSelectIdVal(&#39;21481118901750367682885&#39;)"
																onmouseleave="hideModiTips(this)">
																<ul class="choice">
																	<li>
																		请确认修改属性为
																	</li>
																	<li onclick="flagPositive(1,this);">
																		敏感
																	</li>
																	<li onclick="flagPositive(4,this);">
																		非敏感
																	</li>
																</ul>
															</div>
															<div class="r_tool r_tool2 r4 share"
																onmouseleave="hideShareTips(this)">
																<ul class="choice">
																	<li>
																		请选择分享至
																	</li>
																	<li>
																		微博
																	</li>
																	<li onclick="shareToWeixi(this)">
																		微信
																	</li>
																	<input type="hidden" value="">
																</ul>
															</div>
															<div class="r_tool r_tool2 r3 del"
																onmouseleave="hideDeleteTips(this)">
																<ul class="choice">
																	<li>
																		是否确认为垃圾信息？
																	</li>
																	<li
																		onmouseover="setSelectIdVal(&quot;21481118901750367682885&quot;)"
																		onclick="goDeleteNewsSingle(1,&#39;185482&#39;);">
																		是
																	</li>
																	<li onclick="hideDeleteTips2(this)">
																		否
																	</li>
																</ul>
															</div>
															<div class="handle handle3">
																<ul class="">
																	<li>
																	 
																	</li>
																	<li>
																	</li>
	
																	 
																	<li>
																		<a href="javascript:void(0)" data-toggle="dropdown"
																			onmouseover="setSelectIdVal(&#39;21481118901750367682885&#39;);"
 
																			onclick="importReportShow('<s:property value="id"/>')">
																			<i class="icon-envelope-o"></i><span>导出报告</span> </a>
																		
																	
 
																	</li>
																	<li>
																		<div class="dropdown">
																 			<a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown"
																			onmouseover="setSelectIdVal(&#39;21481118901750367682885&#39;);"
 
																			<%-- onclick="changeZLM('<s:property value="id"/>')" --%>>
																			<i class="icon-class-computer"></i><span>倾向标注</span> </a>
																			<%-- <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"> 
																				<i class="icon-class-computer"></i><span>倾向标注</span>
																			</button> --%>
																			
																			<ul class="dropdown-menu" role="menu">
																				<li><a href="javascript:void(0)" onclick="changeZLM('<s:property value="id"/>','正面')"><i class="icon-class-finance"></i>正面</a></li>
																				<li><a href="javascript:void(0)" onclick="changeZLM('<s:property value="id"/>','中立面')"><i class="icon-class-finance"></i>中立面</a></li>
																				<li><a href="javascript:void(0)" onclick="changeZLM('<s:property value="id"/>','反面')"><i class="icon-class-finance"></i>反面</a></li>
																			</ul>
																		</div>
																	</li>
																</ul>
															</div>
														</div>
													</div>
												</td>
												<td>
													<a href="javascript:goZZNews('O13879954');" class="badge"><s:property value="clickedCounts"/></a>
												</td>
												<td class="website">
													<a href="javascript:void(0);" style="font-size: 14px"
														onclick="goCaptureWebsite(&#39;<s:property value="sourceName"/>&#39;)" title="<s:property value="sourceName"/>">
														<s:property value="sourceName"/>
													</a>
												</td>
												<td class="pdate" id="pdate_1" style="font-size: 14px">
													<s:property value="pubnisihTime"/>
												</td>
											</tr>
										</s:iterator>
									</tbody>
								</table>
								
								<link rel="stylesheet" type="text/css"
									href="<%=basePath%>sinaStyle/bottom_style.css" media="screen">
								<table
									style="border: solid 0px #ededed; border-bottom: 0; font-size: 14px;">
									<tbody>








										<script src="<%=basePath%>sinaStyle/jquery-1.10.2.min(1).js"
											type="text/javascript">
										</script>
										<script src="<%=basePath%>sinaStyle/jquery.paginate.js"
											type="text/javascript">
										</script>


										<style type="text/css">
ul.jPag-pages {
	height: 28px;
	text-align: right;
}
</style>
										<tr bgcolor="#ffffff">

											<td colspan="15" class="page_info"
												style="font-size: 12px; height: 60px;">
												<div style="float: right; padding-right: 0px; width: auto;">
													<div id="Pagination">
													
														<%-- 引入分页页面 --%>
														<%@include file="/main/page.jsp" %>
														
													</div>
												</div>
											</td>
										</tr>
									</tbody>
								</table>


								<br>
							</div>
 
							<link rel="stylesheet" type="text/css"
								href="<%=basePath%>sinaStyle/fly.css">
							<script type="text/javascript"
								src="<%=basePath%>sinaStyle/jquery.fly.min.js">
							</script>
							<script type="text/javascript"
								src="<%=basePath%>sinaStyle/requestAnimationFrame.js">
							</script>
							<script type="text/javascript"
								src="<%=basePath%>sinaStyle/NewsOperate.js">
							</script>

							<script type="text/javascript">
  
 
SetCwinHeight();
setTimeout('getStatList();', 1500);
//限制文字数量//
var arrLinks = $(".website a");
arrLinks.each(function() {
	var s = $.trim($(this).text()), l = $.trim($(this).text()).length, n = 7; //设置字符长度为7个
		$(this).attr("title", s);
		if (l > n) {
			s = $(this).text(s.substring(0, n));
		}
	});
	
//分页查询
function searchPage(page){
	$(".currentPage").val(page);
	$("#searchForm").submit();
}

//按网站查询
function searchBySource(sourceId){
	$(".parentSourceId").val(sourceId);
	$("#searchForm").submit();
}

//点击关键词搜索方法
function hotSearch(keyword){
	settime();
	$(".queryWord").val(keyword);
	$("#searchForm").submit();
}
//预览简报
function reportView(){
	var reportId = $("#reportId").val();
	var addressCode = $("#addressCode").val();
	if(reportId == null || reportId == ""){
		layer.alert("请选择要预览的简报");
		return;
	}
	if(addressCode == null || addressCode == ""){
		var href = "<%=basePath%>report_reportView.action?reportId="+reportId;
	}else{
		var href = "<%=basePath%>report_findRecordByCode.action?reportId="+reportId+"&addresscode="+addressCode;
	}
    var a = $("<a href='"+href+"' target='_Blank'>open</a>").get(0);
    var e = document.createEvent("MouseEvents");
    e.initEvent("click", true, true);
    a.dispatchEvent(e);
}

</script>
						</div>
						<!--搜索显示内容 end-->
					</div>
					<!--右侧搜索内容 end-->
				</div>
			</div>
		</div>
		<div class="hot_frame" id="hot_frame">

			<iframe id="hotIframe" frameborder="no" border="0" src=""
				style="width: 100%; height: 99.6%">

			</iframe>

		</div>

		<!--底部部分代码 start-->

		<div class="h35 clear"></div>
		<%@ include file="/main/home/foot.jsp" %>
		
	<!-- 弹出层 -->
	<div id="tanchu" style="display: none;">
		<%-- <input type="hidden" id="newsId" name="newsId" value="<s:property value="newsId"/>"> --%>
		<input type="hidden" id="newsId" name="newsId" value="">
		<div class="td_title rel">
			<h1>导出简报</h1>
		</div>
		<ul class="frmPopWin2">
			<li>
				<span class="float_l"><font class="f_c2">*</font>选择简报：</span>
				<span>
					<select style="width: 507px;" name="record.reportId" cols="67"
						rows="3" class="inputDH" id="reportId">
						<%-- <option value="">--请选择--</option>
						<s:iterator value="reportList" var="rp">
							<option value="<s:property value="#rp.id"/>"><s:property value="#rp.name"/></option>
						</s:iterator> --%>
					</select>
				</span> 
				<span class="help_outline" style="color: #888;">
				<i class="icon icon-help_outline" 
					onmouseover="$(this).tooltip('show')"
					data-toggle="tooltip" data-placement="top" 
					title="请选择简报"></i>
				</span> <!--信息量过大提示文字 start -->
				<div class="wprompt1" style="display: none;">
					<div>错误提示</div>
				</div> <!--信息量过大提示文字 end -->
			</li>
			<li>
				<span class="float_l"><font class="f_c2">*</font>选择板块：</span>
				<span>
				<select style="width: 507px;" name="record.addressCode" cols="67"
					rows="3" class="inputDH" id="addressCode">
						<%-- <option value="">--请选择--</option>
						<s:iterator value="addrList" var="addr">
							<option value="<s:property value="#addr.code"/>"><s:property value="#addr.define"/></option>
						</s:iterator> --%>
				</select>
				</span> 
				<span class="help_outline" style="color: #888;"><i
						class="icon icon-help_outline" data-toggle="tooltip"
						onmouseover="$(this).tooltip('show')"
						data-placement="top" title="请选择板块"></i>
				</span> <!--信息量过大提示文字 start -->
				<div class="wprompt1" style="display: none;">
					<div>错误提示</div>
				</div> <!--信息量过大提示文字 end -->
			</li>
			<li>
				<span class="float_l">&nbsp;</span> 
				<span class="promptNumber yellow" style="display: none;">
					<i class="icon-notification"></i>
					<span id="tips"></span> 
				</span>
			</li>
		</ul>
		<div class="h10 clear"></div>
		<div class="btn_div" style="">
			<a href="javascript:importReport();" class="submitBtn2 button" 
				id="submit" style="">保存</a> 
			<a href="javascript:reportView();" class="cancelBtn2 button">预览</a>
		</div>
		<div class="h10 clear"></div>
	</div>


 
		<script type="text/javascript"
			src="<%=basePath%>sinaStyle/operatelog.js">
</script>
		<!--底部部分代码 end-->
		<script type="text/javascript"
			src="<%=basePath%>sinaStyle/prettify.js">
</script>
		<script type="text/javascript"
			src="<%=basePath%>sinaStyle/jquery.slimscroll.js">
</script>
<script type="text/javascript">
$(function() {
	$('#sharesList').slimScroll( {
		height : '368'
	});

});

function changeZLM(id,zlm){
	//$('#changeZLM').modal('show')
	$.ajax({
		url:"<%=basePath%>news_changeZLM.action",
		type:"POST",
		data:{"newsId":id,"zlm":zlm},
		success:function(data){
			if(data==1)
				location.reload();
		}
	})
}
</script>



		&gt;
		<script type="text/javascript"
			src="<%=basePath%>sinaStyle/twitter-bootstrap-hover-dropdown.js">
</script>
		<script src="<%=basePath%>sinaStyle/bootstrap.min.js">
</script>
	 
	</body>
</html>