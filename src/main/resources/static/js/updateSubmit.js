/* 폼 전송 */
function submitForm() {
	// 폼데이터 담기

	if (!boardval()) {
		return false;
	}
	const url = window.location.href;
	const str = url.substring(url.lastIndexOf('/') + 1, url.indexOf('?'));
	var form;

	if (str == 'updateTownProductForm') form = document.townUpdateForm;
	else if (str == 'updateProxyForm') form = proxyUpdateForm
	else if (str == 'updateShareBoardForm') form = document.shareUpdateForm;
	else if (str == 'proxyApplyupdateForm') document.proxyApplyUpdateForm;
	else if (str == 'proxyApplyupdateForm') document.proxyApplyUpdateForm;
	else if (str == 'businessApplyUpdateForm') form = document.busApplyUpdate;	
	
	var formData = new FormData(form);
	for (var i = 0; i < filesArr.length; i++) {
		// 삭제되지 않은 파일만 폼데이터에 담기
		if (!filesArr[i].is_delete) {
			formData.append("attach_file", filesArr[i]);
		}
	}
	formData.append("original", originalfileArray)

	$.ajax({
		method: 'POST',
		url: '/imageUpdating',
		data: formData,
		contentType: false,
		processData: false,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success: function(response) {
			console.log('전송 성공', response);
			$("#fileStr").val(response);
			form.submit();
		},
		error: function(xhr, desc, err) {
			console.error('전송 실패', err);
		}
	});
}