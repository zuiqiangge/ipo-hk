/**
 * ��¼�û������Ϊ��־
 */
$(function() {
	if (typeof operateLogPageCode != 'undefined' && $.trim(operateLogPageCode) != '' && typeof operateLogPageName != 'undefined' && $.trim(operateLogPageName) != '' && typeof operateLogRequestPath != 'undefined') {
		$.ajax({
			url : operateLogRequestPath + '/view/user/operatePageLog.action',
			type : 'POST',
			data : {
				'pageCode' : operateLogPageCode,
				'pageName' : operateLogPageName,
				'extraInfo' : typeof operateLogExtraInfo == 'undefined' ? null : $.trim(operateLogExtraInfo) == '' ? null : operateLogExtraInfo
			},
			success : function(){}
		});
	}
});