/* 폼 전송 */
function submitForm() {

	if (!boardval()) {
		return false;
	}
	var form = document.proxyApplyUpdateForm;

	// 폼데이터 담기

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
			$("#files").val(response);
			form.submit();
		},
		error: function(xhr, desc, err) {
			console.error('전송 실패', err);
		}
	});
}

function boardval() {

	/*카테고리 검증*/
	if ($("#h-input").val() == '') {
		alert("카테고리를 선택해주세요.");
		return false;
	}

	/*제목검증*/
	if ($("#pr_title").val() == '') {
		alert("제목을 입력해주세요.");
		return false;
	}
	if ($("#pr_title").val().length > 50) {
		alert("제목은 50자 이하로 작성해주세요.")
		return false;
	}

	/*내용검증*/
	console.log($("#summernote").val())
	if ($("#summernote").val() == '') {
		alert("내용을 입력해주세요.")
		return false;
	} else {
		if (!fn_chk_byte('#summernote')) {
			return false;
		}
	}

	return true;
}
