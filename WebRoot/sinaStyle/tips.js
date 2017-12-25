var timer;
var clickFlag = 0;
var keysetArray= new Array();
function init(){
    var clientWidth = document.body.clientWidth;
    var clientHeight = document.body.clientHeight;
    var offsetWidth = document.body.offsetWidth;
    var offsetHeight = document.body.offsetHeight;
    var scrollWidth = document.body.scrollWidth;
    var scrollHeight = document.body.scrollHeight;
    var scrillTop = document.body.scrollTop;
    var scrollLeft = document.body.scrollLeft;
    var screenTop = window.screenTop;
    var screenLeft = window.screenLeft;
    var screenHeight = window.screen.height;
    var screenWidth =  window.screen.width;
    var screenAvailHeight = window.screen.availHeight;
    var screenAvailWidth = window.screen.availWidth;
}
window.onresize = function(){
    /*if($("#BgDiv").css("display")=="block"){
     showBgDiv();
     }*/
    if($("#SystemSetDiv").css("display")=="block"){
        showSystemSet();
    }
    if($("#InfoDiv").css("display")=="block"){
        var msg = $("#msg").text();
        var style = $("#style").val();
        showInfoDiv(msg,style,null,0);
    }
    if($("#SmsDiv").css("display")=="block"){
        //hideSmsDiv();
    }
}
function showBgDiv(){
    var scrollWidth = document.body.scrollWidth;
    var scrollHeight = document.body.scrollHeight;
    $("#BgDiv").css({
        height:scrollHeight,
        width:scrollWidth,
        display:"block",
        zIndex:50
    });
}
function hideBgDiv(){
    $("#BgDiv").css({
        display:"none"
    });
}
function showSystemSet(){
    window.search_set.src="searchset.action?actionInfo='search'";
    window.search_set.location="searchset.action?actionInfo='search'";
    showBgDiv();
    var screenWidth =  window.screen.width;
    var screenAvailWidth = window.screen.availWidth;
    var divH = $("#SystemSetDiv").height();
    var reWidth = (screenWidth - divH)/2;
    $("#SystemSetDiv").css({
        display:"block",
        top:"200px",
        left:reWidth,
        zIndex:50,
        position:"absolute",
        backgroundColor:"#FFF",
        width:700
    });

}
function hideSystemSet(){
    hideBgDiv();
    $("#SystemSetDiv").css({
        display:"none"
    });
    window.search_set.src="";
}

function jump(count,url) {
    timer = window.setTimeout(function(){
        count--;
        if(count > 0) {

            $("#submitBtn").html("<span style='font-size:12px;'> "+count+" </span>�����ת");
            jump(count,url);
        } else {
            if(url!=null){
                window.location.href=url;
            }else{
                $("#submitBtn").html("ȷ��");
                hideInfoDiv();
            }
        }
    }, 1000);
}
function showInfoDiv(msg,style,src,type){
    showBgDiv();
    var screenWidth =  window.screen.width;
    var clientWidth = document.body.clientWidth;
    var screenAvailWidth = window.screen.availWidth;
    var divH = $("#InfoDiv").height();
    var divW = $("#InfoDiv").width();
    var reWidth = (clientWidth - divW)/2;
    var clientHeight = document.body.clientHeight;
    var topH = (clientHeight - divH)/2
    $(".cancelBtn").css({
        display:"",
        margin:"0",
        marginLeft:"5px"
    });
    $(".submitBtn").css({
        display:"",
        margin:"0",
        marginRight:"5px"
    });
    $('.submitBtn').unbind("click"); //�Ƴ�click
    if(style == 0){
        //ֻ��ʾȷ����ť
        $(".cancelBtn").css({
            display:"none"
        });
        $(".submitBtn").css({
            margin:"5px 75px"
        });
        $('.submitBtn').click(function(){
            hideInfoDiv();
        });
    }
    else if(style==2){
        // ����������ת
        $(".cancelBtn").css({
            display:"",
            margin:"0",
            marginLeft:"5px"
        });
        $(".submitBtn").css({
            display:"",
            margin:"0",
            marginRight:"5px"
        });
        $('.submitBtn').html("<span style='font-size:12px;'>3</span>�����ת");
        if(type==0){
            $('.cancelBtn').html("����");

            $('.cancelBtn').click(function(){
                $('.submitBtn').html("ȷ��");
                clearTimeout(timer);
                hideInfoDiv();
            });


        }else if(type==1){
            $('.cancelBtn').html("ȥ�ղؼ�");
            $('.cancelBtn').click(function(){
                window.location.href="collectNewsList.action";
            });


        }else if(type==2){
            $('.cancelBtn').html("ȥ�زĿ�");
            $('.cancelBtn').click(function(){
                window.location.href="briefListSearch.action?flag=3";
            });
        }


        jump(3,src);
        $('.submitBtn').click(function(){
            hideInfoDiv();
        });
    }else if(style == 1){
        // ����������ת
        $(".cancelBtn").css({
            display:"",
            margin:"0",
            marginLeft:"5px"
        });
        $(".submitBtn").css({
            display:"",
            margin:"0",
            marginRight:"5px"
        });
        if(type==1){
            $('.submitBtn').html("ȥ����");
            $('.submitBtn').click(function(){
                window.location.href=src;
            });
        }else if(type==2){
            $('.submitBtn').click(function(){
                window.location.href=src;
            });
        }
    }
    $('.info_close,.cancelBtn').click(function(){
        hideInfoDiv();
    });
    $("#InfoDiv").css({
        display:"block",
        top:topH,
        left:reWidth
    });
    $("#msg").html(msg);
    $("#style").val(style);
}
//������Ϣ������
function hideInfoDiv(){
    hideBgDiv();
    $("#InfoDiv").css({
        display:"none"
    });
}

//��ת��ĳһ��action
function showMsgInfoToAction(msg_type,msg,style,src){
    if(msg_type==0){
        showInfoDiv(msg,style,src,0);
    }
}

//��ת���زĻ����ղؼ��б� typeΪ1ʱ�����ղؼ��б� 2ʱ�������ز��б�
function showMsgInfoByType(msg_type,msg,style,src,type){
    if(msg_type==0){
        showInfoDiv(msg,style,src,type);
    }
}

//����ʾ������
/*
 *msg_type = 0 ��ͨ��Ϣ
 *
 *stype = 1 ;ȷ����ȡ��
 *
 *stype = 0 ;ȷ��
 *
 *importType =1 �زĿ�
 *importType =2 �ղؼ�
 */
function showMsgInfo(msg_type,msg,style){
    if(msg_type==0){
        showInfoDiv(msg,style,null,0);
    }
}
//�ص�JS
function CallBack(result) {
    /**
     if (null != result && result == 1) {
		showMsgInfoByType(0,"�ѳɹ������ղؼ�!",2,null,1);
	}else if (null != result && result == 2) {
		showMsgInfoByType(0,"�ѳɹ�������زģ�������������Ŀ�д������ļ򱨣�",2,null,2);
	}else{
		showMsgInfo(0,"����ʧ�ܣ������ԣ�",2);
	}*/
}
//��ǳɹ�״̬
function CallBackSenior(result) {
    //alert(result)
    /*if (null != result && result == 1) {
     showMsgInfo(0,"�ѳɹ������ղؼ�!",0);
     }else if (null != result && result == 2) {
     showMsgInfo(0,"�ѳɹ�������زģ�������������Ŀ�д������ļ򱨣�",0);
     }else{
     showMsgInfo(0,"����ʧ�ܣ������ԣ�",0);
     }*/
    var res;
    clickFlag = 0;
    if(null==result||""==result){
        showMsgInfo(0,"�벻Ҫ�ظ���ӣ�",0);
        return;
    }else{
        res = eval('(' + result + ')');
    }
    var count = res.count;
    var type = res.type;
    var flag = res.flag;
    var ids = res.ids;
    ids = ids.substring(0,ids.length-1);
    var idstr = ids.split(",");
    var silderRightDiv = $("#izl_rmenu",parent.window.document);
    if(parent.document.getElementById("izl_rmenu")==null){
        silderRightDiv = $("#izl_rmenu",parent.parent.window.document);
    }
    var countSucaiObj = $(silderRightDiv).find("a").eq(0).find("div").find("span");  //��ȡ�Ҳ��ز� ����
    var countCollectObj = $(silderRightDiv).find("div[class='btn btn-scj rel']").find("span");  //��ȡ�Ҳ��ղ� ����
    if(null!=type && type == 1){
        //showMsgInfoByType(0,"�ѳɹ������ղؼ�!",2,null,1);
        //var ids = res.ids;
        //var idstr = ids.split(",");
        $(countCollectObj).css({'visibility':'visible'});
        var collectNum = $(countCollectObj).text()==""?0:$(countCollectObj).text();
        $(countCollectObj).html(parseInt(collectNum)+parseInt(count));

        $('#addC').css('display', 'none');
        $('#clearC').css('display', 'inline-block');

        if(document.getElementById("addC")==null){
            for(var i=0;i<idstr.length;i++){
                var id = idstr[i];
                var cl = "iclt_"+id;
                var ce = "icltok_"+id;
                //var topF = window.frames["top_ifrmview"];
                //if(!topF){
                //	topF = window.frames["ifrmview"];
                //}else{
                //topF = topF.frames["iframe"];
                //	topF = topF.frames["ifrmview"];
                //}
                var iclt ;
                var icltok ;
                //if(topF && topF!='undifend'){
                //	iclt = topF.document.getElementById(cl);
                //	icltok = topF.document.getElementById(ce);
                //}else{
                //	iclt = document.getElementById(cl);
                //	icltok = document.getElementById(ce);
                //}
                iclt = document.getElementById(cl);
                if(iclt==null){
                    iclt = window.frames["ifrmview"].document.getElementById(cl);
                }
                icltok = document.getElementById(ce);
                if(icltok==null){
                    icltok = window.frames["ifrmview"].document.getElementById(ce);
                }
                //var iclt = window.frames["top_ifrmview"].frames["ifrmview"].document.getElementById(cl);
                //var icltok = window.frames["top_ifrmview"].frames["ifrmview"].document.getElementById(ce);
                if(iclt && icltok){
                    if(iclt.style.display==""){
                        iclt.style.display = "none";
                        icltok.style.display = "";
                    }
                }
            }
        }

        $(".addCollect").bind('click', addCollect);

    }else if(null!=type && type == 2){
        //showMsgInfoByType(0,"�ѳɹ�������زģ�������������Ŀ�д������ļ򱨣�",2,null,2);
        var sucaiNum = $(".sucaiNum").html();
        if(flag==0){
            $(".sucaiNum").html(parseInt(sucaiNum)+parseInt(count));
        }else if(flag==1){
            $(".sucaiNum").html(parseInt(sucaiNum)-parseInt(count));
        }
        if($(".tipfloat").height()==0){
            $(".tipfloat").css("height","48px");
            $(".tipfloat").animate({height:"show"},400);
        }
        if($(".sucaiNum").html()<0){
            $(".sucaiNum").html(0);
        }
        for(var i=0;i<idstr.length;i++){
            var id = idstr[i];
            var cl = "sc_"+id;
            var ce = "scok_"+id;

            var iclt = document.getElementById(cl);
            var icltok = document.getElementById(ce);

            if(iclt && icltok){
                if(iclt.style.display==""){
                    iclt.style.display = "none";
                    icltok.style.display = "";

                }else if(iclt.style.display=="none"){
                    iclt.style.display = "";
                    icltok.style.display = "none";
                }
            }
            /*var cl = "sc_"+id;
             var ce = "scok_"+id;
             var topF = window.frames["top_ifrmview"];
             if(!topF){
             topF = window.frames["ifrmview"];
             }else{
             //topF = topF.frames["iframe"];
             topF = topF.frames["ifrmview"];
             }
             var iclt ;
             var icltok ;
             if(topF && topF!='undifend'){
             iclt = topF.document.getElementById(cl);
             icltok = topF.document.getElementById(ce);
             }else{
             iclt = document.getElementById(cl);
             icltok = document.getElementById(ce);
             }

             //var iclt = window.frames["top_ifrmview"].frames["ifrmview"].document.getElementById(cl);
             //var icltok = window.frames["top_ifrmview"].frames["ifrmview"].document.getElementById(ce);
             if(iclt && icltok){
             iclt.style.display = "none";
             icltok.style.display = "";
             }*/
        }
    }

}
function operateNews(opt_type,checkedIds, importType, account,timeDomain,starttime,endtime,keywordId,keyword){
//$('.submitBtn').unbind("click"); //�Ƴ�click

    //NewsOperate.operateNews(opt_type,checkedIds, importType, account,timeDomain,starttime,endtime,
    //		CallBack);
    //�߼���
	
    if(clickFlag==0){
        clickFlag=1;
        NewsOperate.operateNewsSenior(opt_type,checkedIds, importType, account,timeDomain,starttime,endtime,keywordId,keyword,
            CallBackSenior);
    }

}
//���ݶ��������ղؼл��زĿ⣨�£�
function importNewsSenior(importNewType, folderId, account) {
    if (importNewType == 2) {
        importToName = "���ز�";
        importType = 2;

    } else if (importNewType == 1) {
        importToName = "�ղؼ�";
        importType = 1;
    }
    //var checkedMenu = getCheckIDsNum();
    //var checkedIds = getCheckIDs();

    var elements = document.getElementsByTagName("input");
    var checkedIds = "";
    var repeatNum = "";
    for ( var i = 0; i < elements.length; i++) {
        var e = elements[i];
        if (e.id == "menuCheckId") {
            if (e.checked == true) {
                // checkedMenu = checkedMenu + 1;
                repeatNum = $("#repeatNum_"+e.value).val();
                console.log(repeatNum)
                checkedIds = checkedIds + e.value + "_"+repeatNum+",";
            }
        }
    }
    var timeDomain = document.getElementById("timeDomain").value;
    var starttime = document.getElementById("starttime").value;
    var endtime = document.getElementById("endtime").value;
    var silderRightDiv = $("#izl_rmenu",parent.window.document);
    if(parent.document.getElementById("izl_rmenu")==null){
        silderRightDiv = $("#izl_rmenu",parent.parent.window.document);
    }
    var countCollectObj = $(silderRightDiv).find("div[class='btn btn-scj rel']").find("span");  //��ȡ�Ҳ��ղ� ����
    var collectNum = $(countCollectObj).text()==""?0:$(countCollectObj).text();
    var checkNum = checkedIds.split(",").length;
    // alert(collectNum);
    //alert(checkNum);
    if(importNewType==1&&(parseInt(collectNum)+checkNum)>1001){
        $("#cancelBtn").html("ȷ��");
        $("#submitBtn").html("ȥ�ղؼ�");

        showMsgInfo(0,"ʧ�������ղؼ�ֻ�ṩ1000����Ϣ����Ŷ��",1);
        $("#submitBtn").click(function(){
            location.href =njxBasePath+ "/collect.shtml";
        });
        return;
    }

    /**if (checkedMenu == "0") {
		showMsgInfo(0,"��ѡ����Ҫ���뵽" + importToName + "������!",0);
		return;
	}*/
    var keywordId=null;
	var keyword=null;
	if(document.getElementById("kw.keywordId")){
		var keywordId = document.getElementById("kw.keywordId").value;
	}
	if(keywordId==null||keywordId==""){
		if(document.getElementById("searchKeyword")){
			keyword=document.getElementById("searchKeyword").value;
		}
	}
    operateNews("insert",checkedIds, importType, account,timeDomain,starttime,endtime,keywordId,keyword);

}
//�ҵ��ղض���������ز�



//���ݵ��������ղؼл��زĿ�(��)2
function importNewsSingleSenior(importNewType, folderId, account) {
    if (importNewType == 2) {
        importToName = "���ز�";
        importType = 2;
    } else if (importNewType == 1) {
        importToName = "�ղؼ�";
        importType = 1;
    }
    var checkedId = document.getElementById("selectedId").value;


    var repeatNum = document.getElementById("repeatNum_"+checkedId).value;

    var checkedIds = checkedId + "_" +repeatNum +",";
    var timeDomain = 0;
    if(document.getElementById("timeDomain")!=null){
        timeDomain = document.getElementById("timeDomain").value;
    }
    var starttime = endtime = "";
    if(document.getElementById("starttime")!=null){
        starttime = document.getElementById("starttime").value;
    }
    if(document.getElementById("endtime")!=null){
        endtime = document.getElementById("endtime").value;
    }
    var silderRightDiv = $("#izl_rmenu",parent.window.document);
    if(parent.document.getElementById("izl_rmenu")==null){
        silderRightDiv = $("#izl_rmenu",parent.parent.window.document);
    }
    var countCollectObj = $(silderRightDiv).find("div[class='btn btn-scj rel']").find("span");  //��ȡ�Ҳ��ղ� ����
    var collectNum = $(countCollectObj).text()==""?0:$(countCollectObj).text();
    var checkNum = checkedIds.split(",").length;
    //alert(collectNum);
    // alert(checkNum);
    if(importNewType==1&&(parseInt(collectNum)+checkNum)>1001){
        $("#cancelBtn").html("ȷ��");
        $("#submitBtn").html("ȥ�ղؼ�");
        showMsgInfo(0,"ʧ�������ղؼ�ֻ�ṩ1000����Ϣ����Ŷ��",1);
        $("#submitBtn").click(function(){
            location.href = njxBasePath + "/collect.shtml";
        });
        return;
    }
    var keywordId=null;
    var keyword=null;
    if(document.getElementById("kw.keywordId")){
    	keywordId = document.getElementById("kw.keywordId").value;
    }
    if(keywordId==""||keywordId==null){
    	if(document.getElementById("searchKeyword")){    			
    		var searchKeyword=document.getElementById("searchKeyword").value;
    		if(searchKeyword!=""||searchKeyword!=null){
    			keyword = searchKeyword;
    		}
    	}
    }
    operateNews("insert",checkedIds, importType, account,timeDomain,starttime,endtime,keywordId,keyword);
}


//���ŷ���
function showSmsDiv(src){
    window.send_sms_frame.src=src;
    window.send_sms_frame.location=src;
    showBgDiv();
    var screenWidth =  window.screen.width;
    var clientWidth = document.body.clientWidth;
    var screenAvailWidth = window.screen.availWidth;
    var divH = $("#SmsDiv").height();
    var divW = $("#SmsDiv").width();
    var reWidth = (clientWidth - divW)/2;
    var clientHeight = document.body.clientHeight;
    var topH = (clientHeight - divH)/2
    $("#SmsDiv").css({
        display:"block",
        left:reWidth,
        top:topH,
        zIndex:100,
        position: "fixed",
        backgroundColor:"#FFF"
    });
}
function hideSmsDiv(){
    hideBgDiv();
    $("#SmsDiv").css({
        display:"none"
    });
}
function hideMailFrame(){
    hideBgDiv();
    $("#InfoDiv2").fadeOut("slow");
    $("body").css({overflow:"auto"});    //���ù�����
}
function showEditBSDiv(src){
    window.edit_bs_frame.src=src;
    window.edit_bs_frame.location=src;
    showBgDiv();
    var screenWidth =  window.screen.width;
    var screenAvailWidth = window.screen.availWidth;
    var divH = $("#EditBSDiv").height();
    var divW = $("#EditBSDiv").width();
    var reWidth = (screenWidth - divW)/2;
    var clientHeight = document.body.clientHeight;
    var topH = (clientHeight - divH)/2
    $("#EditBSDiv").css({
        display:"block",
        left:reWidth,
        top:topH,
        zIndex:100,
        position: "fixed",
        backgroundColor:"#FFF"
    });
}
function hideEditBSDiv(){
    hideBgDiv();
    $("#EditBSDiv").css({
        display:"none"
    });
}

//��㵯���� style Ϊ�ڼ��㵯����
function showInfoDiv2(msg,style){
    showBgDiv();
    var screenWidth =  window.screen.width;
    var screenAvailWidth = window.screen.availWidth;
    var divH = $("#InfoDiv").height();
    var divW = $("#InfoDiv").width();
    var reWidth = (screenWidth - divW)/3;
    var clientHeight = document.body.clientHeight;
    var topH = (clientHeight - divH)/3;
    $("#InfoDiv").css({
        display:"block",
        left:reWidth,
        top:topH,
        zIndex:100,
        position: "fixed",
        backgroundColor:"#FFF"
    });

    if(style == 2){
        //ֻ��ʾȷ����ť
        $("#InfoDiv .submitBtn").css({
            margin:"5px 75px"
        });
        $("#InfoDiv .cancelBtn").css({
            display:'none'
        })
        $('.submitBtn,.info_close').click(function(){
            hideInfoDiv();
        });
    }
    $("#msg").html(msg);
}