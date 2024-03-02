function boardval() {
	
	
	/* 제목 */
	if ($(bus_title).val() == '') {
		alert("제목을 입력해주세요");
		return false;
	}

	/* 내용 */
	console.log($("#summernote").val())
	if ($("#summernote").val() == '') {
		alert("내용을 입력해주세요.")
		return false;
	} else {
		if (!fn_chk_byte('#summernote')) {
			return false;
		}
	}
	
	
	/* 제품명 */
	if ($(bus_productName).val() == '') {
		alert("제품명을 입력해주세요");
		return false;
	}


	/* 공급수량 */
	let bus_maxqnt = $('#bus_maxqnt').val();
	if (bus_maxqnt.trim() === '') {
	    alert("최대공급가능수량을 입력해주세요");
	    return false;
	} else if (isNaN(bus_maxqnt)) {
	    alert("최대공급가능수량은 숫자만 입력 가능합니다.");
	    return false;
	}	
	
	/* 단위가격 */
	let bus_unitPrice = $('#bus_unitPrice').val();
	if (bus_unitPrice.trim() === '') {
	    alert("단위가격을 입력해주세요");
	    return false;
	} else if (isNaN(bus_unitPrice)) {
	    alert("단위가격은 숫자만 입력 가능합니다.");
	    return false;
	}	


	/* 회사명 */
	if ($(bus_name).val() == '') {
		alert("회사명을 입력해주세요");
		return false;
	}
	
		
	/* 사업자번호 */
	let bus_num = $('#bus_num');
	
	if (!bus_num.prop('readonly') && bus_num.val().trim() === '') {
	    alert("사업자번호 확인버튼을 눌러 인증완료해주세요");
	    return false;
	} else if (!/^\d{10,12}$/.test(bus_num.val().trim())) {
	    alert("사업자번호 확인버튼을 눌러 인증완료해주세요");
	    return false;
	}
	
	
	/* 주소 */
	if ($(postCode).val() == '') {
		alert("우편번호를 입력해주세요");
		return false;
	}
		

	if ($(address_main).val() == '') {
		alert("주소를 입력해주세요");
		return false;
	}

	if ($(address_sub).val() == '') {
		alert("상세주소를 입력해주세요");
		return false;
	}		
	
	
	
	/* 담당자 */
	if ($(bus_chargePerson).val() == '') {
		alert("담당자를 입력해주세요");
		return false;
	}
		
	/*  전화번호 */
	if ($(bus_phone).val() == '') {
		alert("전화번호를 입력해주세요");
		return false;
	}
	
	/* 이메일 */
	let bus_email = $('#bus_email');
	
	if (!bus_email.val().trim()) {
	    alert("이메일을 입력해주세요");
	    return false;
	} else if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(bus_email.val().trim())) {
	    alert("유효한 이메일 주소를 입력해주세요");
	    return false;
	}
		

	/*  이메일 */
	if ($(bus_password).val() == '') {
		alert("비밀번호를 입력해주세요");
		return false;
	}		
		
	
	return true;
}
