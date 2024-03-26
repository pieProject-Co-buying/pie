/* 폼 전송 */
function submitForm() {

	if (!boardval()) {
		return false;
	}
	
	const url = window.location.href;
	const str = url.substring(url.lastIndexOf('/') + 1);
	var form;

	if (str == 'townForm') form = document.boardForm;
	else if (str == 'proxyWriteForm') form = document.boardForm;
	else if (str == 'writePost') form = document.boardForm;
	else if (str == 'proxyApplyForm') form = document.proxyApplyForm;
	else if (str == 'businessApplyForm') form = document.busApply;
	else if (str == 'businessApplyUpdateForm') form = document.busApplyUpdate;
	
	
	// 폼데이터 담기

	var formData = new FormData(form);
	for (var i = 0; i < filesArr.length; i++) {
		// 삭제되지 않은 파일만 폼데이터에 담기
		if (!filesArr[i].is_delete) {
			formData.append("attach_file", filesArr[i]);
		}
	}
	$.ajax({
		method: 'POST',
		url: '/imageUploading',
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