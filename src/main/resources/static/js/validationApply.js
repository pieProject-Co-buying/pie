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
