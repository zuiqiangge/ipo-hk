function EnterPress(e) {
		var e = e || window.event; 
		if (e.keyCode == 13) {
			goSearchLoad();
			return false;
		}

}
function newEnterPress(e) {
    var e = e || window.event;
    if (e.keyCode == 13) {
        goSearchs();
        return false;
    }

}
function goSearchLoad(){
	var kv = document.getElementById("searchKeyword").value;
    kv = $.trim(kv);
    document.getElementById("searchKeyword").value = kv;
	if(kv==""||kv=="���������������ҵ����Ʒ����Ʊ�����ǵȴ���"){
		showMsgInfo(0,"���������������ҵ����Ʒ����Ʊ�����ǵȴ���",0)
		document.getElementById("searchKeyword").focus();
		document.getElementById("searchKeyword").value = "";
		return ;
	}
    if(kv.length==1){
        showMsgInfo(0,"����������2���ַ�",0)
        document.getElementById("searchKeyword").focus();
        document.getElementById("searchKeyword").value = "";
        return ;
    }
    if(kv=="΢��"||kv=="ԭ΢��"){
        showMsgInfo(0,"�漰"+kv+"�����ݿ������Ż����Ĺؼ�������������",0);
        return;
    }
    var subFlag = 1;
    var arr = kv.split(/ /);
    $.each(arr,function(){
        if(this.length<2){
            subFlag = 0;
            return;
        }
    });
    if(subFlag==0){
        showMsgInfo(0,"ÿ���ؼ������ٰ���2���ַ�",0);
        return;
    }
	var keyTitleV = kv;
	kv =  kv.replace(/\%/g, "%25");
    $('#timeDomain').val(24);
	cscsearch.action = "load.shtml";
	cscsearch.target = "_self";
	cscsearch.submit();
}
function reset(){
    //���ʱ��
    var lenA = 7;
    for ( var i = 1; i <= lenA; i++) {
        if (i != 2) {
            document.getElementById("selectTimeA_"+i).className="";
        } else {
            document.getElementById("selectTimeA_"+i).className="cur";
        }
    }
        var now = new Date().getTime();
        var end = now - 1 * 24 * 3600 * 1000;
        $("#isHourSeach").val(true);
        $("#starttime").val(new Date(end).format("yyyy-MM-dd hh:mm:ss"));
        $("#endtime").val(new Date(now).format("yyyy-MM-dd hh:mm:ss"));

        $("#clickTimeDomain").val(24);
         $("#timeDomain").val(24);

        //��Ϣ����
        var paixuLen = 4;
        for (var i = 1; i <= paixuLen; i++) {
            if (i != 1) {
                document.getElementById("dateTimeA_" + i).className = "";
            } else {
                document.getElementById("dateTimeA_" + i).className = "cur";
            }
        }
        $("#clickPaixu").val(1);
         $("#paixu").val(1);
        //��Ϣ����
        var attributeALen = 3;
        for (var i = 1; i <= attributeALen; i++) {
            if (i != 1) {
                document.getElementById("attributeA_" + i).className = "";

            } else {
                document.getElementById("attributeA_" + i).className = "cur";
            }
        }
            $("#otherAttribute").val(0);
             $("#clickOtherAttribute").val(0);
            //ƥ�䷽ʽ
            var solrType = 3;
            for (var i = 1; i <= solrType; i++) {
                if (i != 3) {
                    document.getElementById("solrType_" + i).className = "";

                } else {
                    document.getElementById("solrType_" + i).className = "cur";

                }
            }
            $("#solrType").val(3);
            //��ͬ����
            var flgCount = 2;
            for (var i = 1; i <= flgCount; i++) {
                if (i != 1) {
                    document.getElementById("combline_flg_" + i).className = "";
                } else {
                    document.getElementById("combline_flg_" + i).className = "cur";
                }
            }
            $("#clickComblineflg").val(1);
             $("#comblineflg").val(1);
            //��ʾ��ʽ
            var zhaiyaoCount = 2;
            for (var i = 1; i <= zhaiyaoCount; i++) {
                if (i != 1) {
                    document.getElementById("zhaiyao_type_" + i).className = "";
                } else {
                    document.getElementById("zhaiyao_type_" + i).className = "cur";
                }
            }
           $("#clickZhaiyaoShow").val(1);
            $("#zhaiyaoShow").val(1);

            //subForm();
        }
function goSearch(){
     /*reset();*/
    $("#page").val(1);//����ҳ��
	var kv = document.getElementById("searchKeyword").value;
    kv = $.trim(kv);
    document.getElementById("searchKeyword").value = kv;
	if(kv==""||kv=="������ؼ���"){
		showMsgInfo(0,"������ؼ���",0)
		document.getElementById("searchKeyword").focus();
		document.getElementById("searchKeyword").value = "";
		return ;
	}
    if(kv.length==1){
        showMsgInfo(0,"����������2���ַ�",0)
        document.getElementById("searchKeyword").focus();
        document.getElementById("searchKeyword").value = "";
        return ;
    }
    if(kv=="΢��"||kv=="ԭ΢��"){
        showMsgInfo(0,"�漰"+kv+"�����ݿ������Ż����Ĺؼ�������������",0);
        return;
    }
    var subFlag = 1;
    var arr = kv.split(/ /);
    $.each(arr,function(){
        if(this.length<2){
            subFlag = 0;
            return;
        }
    });
    if(subFlag==0){
        showMsgInfo(0,"ÿ���ؼ������ٰ���2���ַ�",0);
        return;
    }

	var keyTitleV = kv;
    kv =  kv.replace(/\%/g, "%25");
    if(kv.indexOf("+")!=-1){
        $("#searchPipeiType1").click();
    }
	document.getElementById("searchKeyword2").value = kv;
	
	var otherTimeV = getOtherTime();
	if (otherTimeV==0){
		var clickTimeDomainV = 0;
		document.getElementById("clickTimeDomain").value = clickTimeDomainV;
		var sttime = document.getElementById("starttime").value;
		var endtime = document.getElementById("endtime").value;
		if(sttime==null || sttime == '' || sttime == 'undefined'){
			showMsgInfo(0,"������������ʼʱ��",0);
			return;
		}
		if(endtime==null || endtime == '' || endtime == 'undefined'){
			showMsgInfo(0,"������������ֹʱ��",0);
			return;
		}
		
		document.getElementById("starttime").value = sttime;
		document.getElementById("endtime").value = endtime;
	
	}
	else if(otherTimeV==1){
		var clickTimeDomainV = document.getElementById("timeDomain").value;
		document.getElementById("clickTimeDomain").value = clickTimeDomainV;
	}
		
	var paixuV =  document.getElementById("paixu").value;
	document.getElementById("clickPaixu").value = paixuV;

	var otherAttributeV =  document.getElementById("otherAttribute").value;
	document.getElementById("clickOtherAttribute").value = otherAttributeV;
	/*document.getElementById("searchPipeiType").value= getSearchPipeiType();*/
	
	/*var comblineflag = getComblineFlag();
	 
	document.getElementById("clickComblineflg").value = comblineflag;*/
	
	/*var secondSearchWord = document.getElementById("secondSearchWord").value;
	 if(secondSearchWord=="�ڽ��������"){
		 secondSearchWord="";
	 }*/
	document.getElementById("secondSearchWord2").value = "";
	/*var solrSecondTypes = document.getElementById("solrSecondType").value;*/
	/*if(solrSecondTypes==4 && secondSearchWord==""){
		showMsgInfo(0,"��������������",0);
		return;
	}*/
	document.getElementById("solrSecondType1").value = 3;
	
	$("#search").val(1);
	subForm();
}
function secondsSearch(){
    var secondSearchWord = document.getElementById("secondSearchWord").value;
    if(secondSearchWord=="�ڽ��������"){
        secondSearchWord="";
    }
    var solrSecondTypes = document.getElementById("solrSecondType").value;
    if(solrSecondTypes==4 && secondSearchWord==""){
        showMsgInfo(0,"��������������",0);
        return;
    }else if(solrSecondTypes==3 && secondSearchWord==""){
        showMsgInfo(0,"����������",0);
        return;
    }else{
        goSearch();
    }
}
function getOtherTime(){
	var seType = 0;
		var ras = document.getElementsByName("searchTime");
			for (var i=0; i<ras.length;i++){
				var tmpE = ras[i];
				if (tmpE.checked){
					seType = tmpE.value;
					break;
				}
			}
	return seType;
}

function getSearchPipeiType(){
	var seType = 0;
		var ras = document.getElementsByName("searchPipeiType");
			for (var i=0; i<ras.length;i++){
				var tmpE = ras[i];
				if (tmpE.checked){
					seType = tmpE.value;
					break;
				}
			}
	return seType;
}

 function getComblineFlag(){
	var seType = 1;
	var ras = document.getElementsByName("comblineflag");
		for (var i=0; i<ras.length;i++){
			var tmpE = ras[i];
			if (tmpE.checked){
				seType = 2;
				break;
			}
		}
	return seType;
}
function getOs(){ 
	    var OsObject = ""; 
	   if(navigator.userAgent.indexOf("MSIE")>0) { 
	        return "MSIE"; 
	   } 
	   if(navigator.userAgent.indexOf("Firefox")>0){ 
	        return "Firefox"; 
	   } 
	   if(navigator.userAgent.indexOf("Chrome")>0){ 
	        return "Chrome"; 
	   } 
	   if(navigator.userAgent.indexOf("Safari")>0) { 
	        return "Safari"; 
	   }   
	}  
function SetCwinHeight(){
		var navType= getOs();
		var b_height=0;
		if (navType=="Chrome"||navType=="Safari"){
			b_height = document.documentElement.scrollHeight;
		} 
		else{
			b_height = document.body.scrollHeight;
		}
		b_height = b_height + 100;
		if (b_height < 900){
			b_height = 900;
		}
		var c_iframe1 = parent.document.getElementById('ifrmview');
	 
		if(c_iframe1){
			c_iframe1.height = b_height ;
		}
	}
function remove_loading() { 
	var targelem = document.getElementById('loader_container'); 
	targelem.style.display='none'; 
	targelem.style.visibility='hidden'; 
} 

function display_loading() { 
	var targelem = document.getElementById('loader_container'); 
	targelem.style.display=''; 
	targelem.style.visibility='visible'; 
}