/* 폼 전송 */
function submitForm() {

	if (!boardval()) {
		return false;
	}
	const url = window.location.href;
	const str = url.substring(url.lastIndexOf('/') + 1);
	var form;

	if (str == 'townForm') form = document.townForm;
	else if (str == 'proxyWriteForm') form = document.proxyForm;
	else if (str == 'writePost') form = document.shareForm;

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
			$("#files").val(response);
			form.submit();
		},
		error: function(xhr, desc, err) {
			console.error('전송 실패', err);
		}
	});
}

function boardval() {
	const url = window.location.href;
	const str = url.substring(url.lastIndexOf('/') + 1);
	let prefix;

	if (str == 'townForm') prefix = "to_";
	else if (str == 'proxyWriteForm') prefix = "pr_";
	else if (str == 'writePost') prefix = "sh_";

	const title = "#" + prefix + "title"
	const deadLine = "#" + prefix + "deadLine"

	/*카테고리 검증*/
	if ($("#h-input").val() == '') {
		alert("카테고리를 선택해주세요.");
		return false;
	}

	/*제목검증*/
	if ($(title).val() == '') {
		alert("제목을 입력해주세요.");
		return false;
	}
	if ($(title).val().length > 50) {
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


	/*숫자*/
	if ($("#personnelMax").val() == '' || $("#personnelMax").val() < 1) {
		alert("최대 모집 인원은 2명부터 설정 가능합니다.")
		return false;
	}
	if ($("#personnelMax").val() > 100) {
		alert("모집인원을 100명이상으로 설정할 수 없습니다.")
		return false;
	}

	/*날짜검증*/
	if ($(deadLine).val() == '') {
		alert("모집마감일을 지정해주세요.")
		return false;
	} else {
		if (!checkDate($(deadLine).val())) {
			alert("모집마감일은 오늘 날짜보다 이후로 설정해야합니다.")
			return false;
		}
	}

	console.log("총합 : " + $("#price_total").val())

	/*모집가격(총합) / 모집가격(인당)*/
	if ($("#price_total").val() == '' || $("#price_per").val() == '') {
		alert("모집가격은 공란으로 할 수없습니다.")
		return false;
	}
	
	if (($("#pie_tagsOutput").val() == '' && $("#pie_tags_input").val() != '')||($("#pie_tagsOutput").val() != '' && $("#pie_tags_input").val() != '')) {
		insertTag("#pie_tags_input");
	} else if ($("#pie_tagsOutput").val() == '' && $("#pie_tags_input").val() == '') {
		$("#pie_tagsOutput").val("#");
	}

	return true;
}

function checkDate(inputD) {
	let today = new Date();
	let inputDate = new Date(inputD);

	console.log(today > inputDate)
	if (today > inputDate) return false;
	else return true;
}

const url = window.location.href;
const str = url.substring(url.lastIndexOf('/') + 1);

if (str == 'townForm') prefix = "to_";
else if (str == 'proxyWriteForm') prefix = "pr_";
else if (str == 'writePost') prefix = "sh_";

const title = "#" + prefix + "title"
const content = "#" + prefix + "content"
const deadLine = "#" + prefix + "deadLine"

$(deadLine).on({
	change: function() {
		if (!checkDate($(deadLine).val())) {
			alert("모집마감일은 오늘 날짜보다 이후로 설정해야합니다.")
			$(this).val('');
		}
	}
})
