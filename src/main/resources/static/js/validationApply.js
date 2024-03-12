function boardval() {

	/*카테고리 검증*/
	if ($("#h-input").val() == '') {
		alert("카테고리를 선택해주세요.");
		return false;
	}
	
	/*제품관련 내용 입력 검증*/
	if ($("#brand").val() == '') {
		alert("제품의 브랜드명을 입력해주세요.");
		return false;
	}
	if ($("#brand").val().length > 50) {
		alert("브랜드명은은 50자 이하로 작성해주세요.")
		return false;
	}
	
	if ($("#productName").val() == '') {
		alert("제품명을 입력해주세요.");
		return false;
	}
	if ($("#productName").val().length > 50) {
		alert("제품명은은 50자 이하로 작성해주세요.")
		return false;
	}

	/*제목검증*/
	if ($("#title").val() == '') {
		alert("제목을 입력해주세요.");
		return false;
	}
	if ($("#title").val().length > 50) {
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
