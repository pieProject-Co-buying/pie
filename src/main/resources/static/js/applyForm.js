// 이미지 갤러리 드래그로 스크롤링
const slider = document.querySelector('.file-uploadGroup');
let isDown = false;
let startX;
let scrollLeft;
const token = $("meta[name='_csrf']").attr("content")
const header = $("meta[name='_csrf_header']").attr("content");

slider.addEventListener('mousedown', e => {
	isDown = true;
	slider.classList.add('active');
	startX = e.pageX - slider.offsetLeft;
	scrollLeft = slider.scrollLeft;
});

slider.addEventListener('mouseleave', () => {
	isDown = false;
	slider.classList.remove('active');
});

slider.addEventListener('mouseup', () => {
	isDown = false;
	slider.classList.remove('active');
});

slider.addEventListener('mousemove', e => {
	if (!isDown) return;
	e.preventDefault();
	const x = e.pageX - slider.offsetLeft;
	const walk = x - startX;
	slider.scrollLeft = scrollLeft - walk;
});

// 이미지 파일 삽입하기
var fileNo = 0;
var filesArr = new Array();

/* 첨부파일 추가 */
function addFile(event) {
	let maxFileCnt = 5;
	let attFileCnt = document.querySelectorAll('.pie-img-viewsThumbs').length;
	let remainFileCnt = maxFileCnt - attFileCnt;
	let curFileCnt = event.target.files.length;


	if (curFileCnt > remainFileCnt) {
		alert("첨부파일은 최대 " + maxFileCnt + "개 까지 첨부 가능합니다.");
	}

	async function processFiles() {
		for (var i = 0; i < Math.min(curFileCnt, remainFileCnt); i++) {
			const file = event.target.files[i];

			if (validation(file)) {
				await new Promise((resolve) => {
					var reader = new FileReader();
					reader.onload = function() {
						resolve(reader.result);
					};
					reader.readAsDataURL(file);
				}).then(function(result) {
					filesArr.push(file);
					let htmlData = "<figure onclick = 'deleteFile(" + fileNo + ")'><img src ='" + result + "' id='file" + fileNo + "' class='pie-img-viewsThumbs'><figcaption></figcaption><figure>";
					$('.file-uploadGroup').append(htmlData);
					fileNo++;
				});
			}
		}
		document.querySelector("input[type=file]").value = "";

		attFileCnt = document.querySelectorAll('.pie-img-viewsThumbs').length;
		remainFileCnt = maxFileCnt - attFileCnt;
		if (remainFileCnt == 0) {
			$(".btn-upload").hide();
		}
	}
	processFiles();

}

/* 첨부파일 검증 */
function validation(obj) {
	const fileTypes = ['image/gif', 'image/jpeg', 'image/png', 'image/bmp', 'image/tif'];
	if (obj.name.length > 50) {
		alert("파일명이 50자 이상인 파일은 제외되었습니다.");
		return false;
	} else if (obj.size > (3 * 1024 * 1024)) {
		alert("최대 파일 용량인 3MB를 초과한 파일은 제외되었습니다.");
		return false;
	} else if (obj.name.lastIndexOf('.') == -1) {
		alert("확장자가 없는 파일은 제외되었습니다.");
		return false;
	} else if (!fileTypes.includes(obj.type)) {
		alert("첨부가 불가능한 파일은 제외되었습니다.");
		return false;
	} else {
		return true;
	}
}

/* 첨부파일 삭제 */
function deleteFile(num) {
	document.querySelector("#file" + num).remove();
	filesArr[num].is_delete = true;

	let maxFileCnt = 5;
	let attFileCnt = document.querySelectorAll('.pie-img-viewsThumbs').length;
	let remainFileCnt = maxFileCnt - attFileCnt;

	if (remainFileCnt > 0) {
		$(".btn-upload").show();
	}

}

$(function() {

	//summerNote 설정
	$('#summernote').summernote({
		minHeight: 400,             // 최소 높이
		maxHeight: null,             // 최대 높이
		focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
		lang: "ko-KR",					// 한글 설정
		spellCheck: false,
		callbacks: {
			onKeyup: function(e) {
				fn_chk_byte('#summernote')
			}
		},

		toolbar: [
			// [groupName, [list of button]]
			['style', ['bold', 'italic', 'underline', 'clear']],
			['font', ['strikethrough', 'superscript', 'subscript']],
			['color'],
			['para', ['ul', 'ol', 'paragraph']],
			['height', ['height']]
		],
		disableDragAndDrop: true
	});


	$("#uploadBtn").on('click', submitForm);

	// 드롭다운 메뉴로 셀렉트하기
	let category = $(".dropdown-menu a")
	let input = $("#h-input");
	let selectCategory = $(".dropdown button")

	category.click(function() {
		selectCategory.text($(this).text());
		input.val($(this).attr("data-category"))
		console.log(input.val());
	})


})

function fn_chk_byte(obj) {
	totalByte = 0;
	var message = $(obj).val();

	for (var i = 0; i < message.length; i++) {
		var currentByte = message.charCodeAt(i);
		if (currentByte > 128) {
			totalByte += 3;
		} else {
			totalByte++;
		}
	}

	$("#messagebyte").text(totalByte);
	if (!sms_send()) return false;
	else return true;
}

function sms_send() {
	if (totalByte > 2000) {
		alert("2000Byte 까지 전송가능합니다.");
		return false;
	} else return true;
}

