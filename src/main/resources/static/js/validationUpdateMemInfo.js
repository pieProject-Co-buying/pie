const inputedname = $("#name").val();
const inputednickname = $("#nickname").val();
const inputedphone = $("#phone").val();
const inputedemail = $("#emailCom").val();

let emailSelect = $("#email2");
let emailInput = $("#emailAddressInput");
let email = $("#emailCom");
let emailId = $("#email1");
let emailAlert = document.querySelector("#email1");
let emailIdVal;

emailInput.hide();

(function() {
	'use strict';
	window.addEventListener('load',
		function() {
			document.querySelector("#name").addEventListener("blur", function() {

				// 입력한 value 값을 읽어온다.
				let input = this.value.trim();

				if (input != inputedname) {
					// 유효성 검증을 위한 정규식 패턴
					let pattern = /^[a-zA-Z_-]{1,51}$|^[\uAC00-\uD7A3]{1,17}$/;

					// 유효하다면 input 요소에 is-valid 클래스 추가, 아니라면 is-invalid 클래스 추가
					if (input == '') {
						this.setCustomValidity("이름을 입력해주세요.");
					} else if (!pattern.test(input)) {
						this.setCustomValidity("한글로 최대 17자(자/모음 불가) / 영문이름은 최대 51자 입력가능합니다.");
					} else {
						this.setCustomValidity("");
					}

					setErrorMessage(findAncestorWithClass(this, 'form-group'), this.validationMessage);
					checkGroup(this);
				}
			});

			document.querySelector("#nickname").addEventListener("blur", function() {
				let thisId = this;
				// 입력한 value 값을 읽어온다.
				let input = this.value.trim();
				// 유효성 검증을 위한 정규식 패턴
				if (input != inputednickname) {
					let pattern = /^[a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ0-9]{1,8}$/

					// 유효하다면 input 요소에 is-valid 클래스 추가, 아니라면 is-invalid 클래스 추가
					if (input == '') {
						this.setCustomValidity("닉네임을 입력해주세요.");
					} else if (!pattern.test(input)) {
						this.setCustomValidity("최대 8자로 입력가능합니다.(특수문자 불가)");
					} else {
						console.log(input);
						$.ajax({
							url: "checkNickName",
							type: 'POST',
							data: {
								"chkName": input
							},
							cache: false,
							beforeSend: function(xhr) {
								xhr.setRequestHeader(header, token);
							},
							success: function(response) {
								if (response) {
									thisId.setCustomValidity("\'" + input + "\'" + "는 사용중인 닉네임 입니다.")
									setErrorMessage(findAncestorWithClass(thisId, 'form-group'), thisId.validationMessage);
								} else {
									thisId.setCustomValidity("");
								}
							},
							error: function(xhr, status, error) {
								console.log('error:' + error);
							}
						});
					}

					setErrorMessage(findAncestorWithClass(this, 'form-group'), this.validationMessage);
					checkGroup(this);
				}
			});

			document.querySelector("#phone").addEventListener("blur", function() {
				let thisphone = this;
				// 입력한 value 값을 읽어온다.
				let input = this.value.trim();
				// 유효성 검증을 위한 정규식 패턴
				if (input != inputednickname) {
					let pattern = /^01[0-9]{1}-?[0-9]{3,4}-?[0-9]{4}$/;

					// 유효하다면 input 요소에 is-valid 클래스 추가, 아니라면 is-invalid 클래스 추가
					if (input == '') {
						this.setCustomValidity("전화번호를 입력해주세요.");
					} else if (!pattern.test(input)) {
						this.setCustomValidity("올바른 전화번호인지 확인해주세요.");
					} else {
						let phone = $("#phone");
						let inputPhone = input
						inputPhone = phoneForm(inputPhone);
						phone.val(inputPhone);
						$.ajax({
							url: "checkPhone",
							type: 'POST',
							data: {
								"chkPhone": inputPhone
							},
							cache: false,
							beforeSend: function(xhr) {
								xhr.setRequestHeader(header, token);
							},
							success: function(response) {
								if (response) {
									thisphone.setCustomValidity("동일한 회원정보로 가입한 내역이 존재합니다.")
									setErrorMessage(findAncestorWithClass(thisphone, 'form-group'), thisphone.validationMessage);
								} else {
									thisphone.setCustomValidity("");
								}
							},
							error: function(xhr, status, error) {
								console.log('error:' + error);
							}
						});
					}
					setErrorMessage(findAncestorWithClass(this, 'form-group'), this.validationMessage);
					checkGroup(this);
				}
			});

			document.querySelector("#email1").addEventListener("blur", function() {
				// 입력한 value 값을 읽어온다.
				let input = this.value.trim();
				let pattern = /^[a-zA-Z0-9._%+-]+$/;

				if (input == '') {
					this.setCustomValidity("이메일 id를 입력해주세요.");
				} else if (!pattern.test(input)) {
					this.setCustomValidity("이메일 id 형식을 확인해주세요.");
				} else {
					this.setCustomValidity("");
				}
				setErrorMessage(findAncestorWithClass(this, 'form-group'), this.validationMessage);
				checkGroup(this);
			});


			document.querySelector("#emailAddressInput").addEventListener("blur", function() {
				// 입력한 value 값을 읽어온다.
				let input = this.value.trim();
				// 유효성 검증을 위한 정규식 패턴
				let pattern = /[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

				// 유효하다면 input 요소에 is-valid 클래스 추가, 아니라면 is-invalid 클래스 추가
				if (input == '') {
					this.setCustomValidity("이메일 주소를 입력해주세요.");
				} else if (!pattern.test(input)) {
					this.setCustomValidity("이메일 주소를 확인해주세요.");
				} else {
					this.setCustomValidity("");
				}

				setErrorMessage(findAncestorWithClass(this, 'form-group'), this.validationMessage);
				checkGroup(this);
			});

			// Fetch all the forms we want to apply custom Bootstrap validation styles to
			var forms = document
				.getElementsByClassName('needs-validation');
			// Loop over them and prevent submission
			var validation = Array.prototype.filter.call(forms,
				function(form) {
					form.addEventListener('submit', function(
						event) {


						if (!form.checkValidity()) {
							event.preventDefault()
							event.stopPropagation()

							var inputs = form.querySelectorAll('input, select, textarea');
							inputs.forEach(function(input) {
								input.dispatchEvent(new Event('blur', { bubbles: true }));
							});


						}

						form.classList.add('was-validated')
					}, false);
				});
		}, false);
})();

$(function() {
	console.log($("#email2value").val() + "확인!")
	setComboEmailValue($("#email2value").val())
})

function setComboEmailValue(val) {
	var selectEmail = document.getElementById("email2");
	let chk = false;
	for (var i = 0, j = selectEmail.options.length; i < j; i++) {
		if (selectEmail.options[i].value == val) {
			chk = true;
			selectEmail.options[i].selected = true;
			console.log(selectEmail.options[i].value)
			console.log(val)
			console.log(chk)
			break;
		}
	}
	if (!chk) {
		$("#email2").hide();
		$("#emailAddressInput").show();
		$("#emailAddressInput").prop("readonly", false);
		$("#emailAddressInput").val(val);
	}
}


emailSelect.change(function() {
	emailIdVal = emailId.val();
	if (emailSelect.val() === "") {
		$(this).hide();
		emailInput.show();
		emailInput.attr("readonly", false);
	} else {
		$(this).show();
		emailInput.hide();
		emailInput.attr("readonly", true);
	}
	email.val(emailIdVal + "@" + emailSelect.val());

	if (inputedemail != email.val()) {
		checkEmailAjax(emailIdVal + "@" + emailSelect.val())
	}
})

emailInput.blur(function() {
	emailIdVal = emailId.val();
	if (emailInput.val() != "") {
		email.val(emailIdVal + "@" + emailInput.val());
		if (inputedemail != email.val()) {
			checkEmailAjax(emailIdVal + "@" + emailInput.val())
		}
	}
})

emailId.blur(function() {
	let chkStr;
	emailIdVal = emailId.val();

	if (emailSelect.val() != '' && emailInput.val() == '') {
		console.log(1);
		chkStr = emailIdVal + "@" + emailSelect.val();
		email.val(chkStr);
		

	} else if (emailInput.val() != '') {
		console.log(2);
		chkStr = emailIdVal + "@" + emailInput.val();
		email.val(chkStr);
	}
	console.log(chkStr);

	if (chkStr != inputedemail) {
		checkEmailAjax(chkStr);
	}

})


$("#postCode").click(function() {
	this.blur();
})
$("#address_main").click(function() {
	this.blur();
})

function phoneForm(phone) {
	let temp = phone.replace(/-/g, "");
	let p1 = temp.substring(0, 3);
	let p2 = temp.substring(3, 7);
	let p3 = temp.substring(7);
	return p1 + "-" + p2 + "-" + p3;
}

function setThumbnail(event) {
	var reader = new FileReader();


	reader.onload = function(event) {
		$(".btn-upload").hide();
		$("#profilePic").show();
		let img = document.querySelector(".pie-img-viewsThumbs");
		img.setAttribute("src", event.target.result);
	};

	reader.readAsDataURL(event.target.files[0]);
}

function findAncestorWithClass(element, className) {
	while ((element = element.parentElement) && !element.classList.contains(className));
	return element;
}

function setErrorMessage(parentElement, message) {
	let invalidFeedback = parentElement.querySelector('.invalid-feedback');
	if (invalidFeedback) {
		invalidFeedback.textContent = message;
	}
}

function checkGroup(inputName) {
	if (!inputName.checkValidity()) {
		event.preventDefault();
		event.stopPropagation();
	}

	let group = findAncestorWithClass(inputName, 'form-group');

	if (group) {
		group.classList.add('was-validated');
	}
	// 부모 요소 중에 특정 클래스를 찾는 함수
}

function checkEmailAjax(inputEmail) {
	$.ajax({
		url: "chkEmail",
		type: 'POST',
		data: {
			"chkEmail": inputEmail
		},
		cache: false,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success: function(response) {
			if (response) {
				emailAlert.setCustomValidity("동일한 회원정보로 가입한 내역이 존재합니다.")
				setErrorMessage(findAncestorWithClass(emailAlert, 'form-group'), emailAlert.validationMessage);
				checkGroup(emailAlert);
			} else {
				emailAlert.setCustomValidity("");
			}
		},
		error: function(xhr, status, error) {
			console.log('error:' + error);
		}
	});
}

function confirmDelete() {
	if (confirm("서비스를 정말 탈퇴하시나요?")) {
		location.href = "outMember";
	} else {
		return false;
	}
}
function confirmUnSub() {
	if (confirm("구독을 취소하시겠습니까? 지금 구독을 취소해도 남은 기간 동안 혜택을 이용할 수 있습니다.")) {
		location.href = "deleteSubScribe";
	} else {
		return false;
	}
}