// 이미지 갤러리 드래그로 스크롤링
const slider = document.querySelector('.file-uploadGroup');
let isDown = false;
let startX;
let scrollLeft;

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

//태그 작성
let pie_tags_input = $("#pie_tags_input");
let tagGroup = $("#tagGroup");
let pie_tagsOutput = $("#pie_tagsOutput");
let tags_str = pie_tagsOutput.val();


pie_tags_input.keyup(function(key) {
	if (key.keyCode == 13 || key.keyCode == 32) {
		let tag = $(this).val();
		tag = tag.replace(" ", "");
		tag = tag.replace(/[^\w\s가-힣ㄱ-ㅎㅏ-ㅣ]/g, '');
		if (tag == "") {
			alert("입력된 값이 없습니다.(특수문자는 태그에 포함할 수 없습니다.)")
			return;
		}
		console.tag
		tagGroup.append('<span class="badge badge-info badge-pill d-flex align-items-center p-2 mr-2">#' + tag + '<svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" fill="currentColor" class="bi bi-x-circle-fill ml-1" viewBox="0 0 16 16"><path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0M5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293z"/></svg></span>');
		tags_str += "#" + tag;
		pie_tagsOutput.val(tags_str);

		$(this).val('');
		console.log(tags_str);

		let tagNum = $("#tagGroup .badge").length;
		if (tagNum == 5) {
			pie_tags_input.hide();
		}
	}
})

let originalfileArray = $("#ori_files").val();

$(document).on("click", "span.badge", function(event) {
	let removedtag = $(this).find("span").text();
	tags_str = tags_str.replace(removedtag, '');

	console.log(removedtag);
	console.log(tags_str);

	pie_tagsOutput.val(tags_str);

	$(this).remove();


	let tagNum = $("#tagGroup .badge").length;

	if (tagNum < 5) {
		pie_tags_input.show();
	}
})

function deleteFileNum(num) {
	document.querySelector("#file" + num).remove();
	filesArr[num].is_delete = true;

	let maxFileCnt = 5;
	let attFileCnt = document.querySelectorAll('.pie-img-viewsThumbs').length;
	let remainFileCnt = maxFileCnt - attFileCnt;

	if (remainFileCnt > 0) {
		$(".btn-upload").show();
	}

}

// 이미지 파일 삽입하기
var filesArr = new Array();
/*첨부되어있는 파일 load 해오기*/
let loadFiles = $("#files").val().split("/")
let idx = loadFiles.indexOf("");
let removed = loadFiles.splice(idx, 1);
var fileNo = 0;

let originFile = $("#files").val();

console.log(idx);
console.log(loadFiles);


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
					let htmlData = "<figure onclick = 'deleteFileNum(" + fileNo + ")'><img src ='" + result + "' id='file" + fileNo + "' class='pie-img-viewsThumbs'><figcaption></figcaption><figure>";
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
	if (obj.name.length > 100) {
		alert("파일명이 100자 이상인 파일은 제외되었습니다.");
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
function deleteFile(obj) {
	var parent = obj.parentNode;
	parent.removeChild(obj);
	let deletedOrignFileStr;
	let deletedOrignFile = obj.getAttribute('data-originFile');
	deletedOrignFileStr = originalfileArray.replace(deletedOrignFile+"/", '');
	console.log(deletedOrignFileStr);
	originalfileArray = deletedOrignFileStr;

	let maxFileCnt = 5;
	let attFileCnt = document.querySelectorAll('.pie-img-viewsThumbs').length;
	let remainFileCnt = maxFileCnt - attFileCnt;
	
	if (remainFileCnt > 0) {
		$(".btn-upload").show();
	}
	
}

/* 폼 전송 */
function submitForm() {
	// 폼데이터 담기
	var form = document.proxyForm;
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
		success: function(response) {
			console.log('전송 성공', response);
			$("#files").val(response);

			let url = window.location.href;
			var str = url.substring(url.lastIndexOf('/') + 1,url.indexOf('?'));
			console.log(str);

			if (str == 'updateTownProductForm') document.townUpdateForm.submit();
			else if (str == 'updateProxyForm') document.proxyUpdateForm.submit();
			else if (str == 'writePost') document.shareForm.submit();
		},
		error: function(xhr, desc, err) {
			console.error('전송 실패', err);
		}
	});
}

$(function() {
	// 가격 계산하기
	let price_total = $("#price_total");
	let price_per = $("#price_per");
	let personNum = $("#personnelMax");


	price_total.change(function() {
		console.log(personNum.val());
		if (isNaN($(this).val()) || $(this).val() < 0) {
			price_total.val(0)
		}
		price_per.val(Math.floor(price_total.val() / personNum.val()));
	})


	price_per.change(function() {
		console.log(price_total.val());
		if (isNaN($(this).val()) || $(this).val() < 0) {
			price_per.val(0)
		}
		price_total.val(Math.floor(price_per.val() * personNum.val()));
	})

	personNum.change(function() {

		console.log(price_total.val());
		console.log(price_per.val());
		if (isNaN(price_total.val()) || isNaN(price_per.val()) || price_total.val() < 0 || price_per.val() < 0) {
			price_total.val(0)
			price_per.val(0)
		}
		price_per.val(Math.floor(price_total.val() / personNum.val()));
		price_total.val(Math.floor(price_per.val() * personNum.val()));
	})

	//summerNote 설정
	$('#summernote').summernote({
		minHeight: 400,             // 최소 높이
		maxHeight: null,             // 최대 높이
		focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
		lang: "ko-KR",					// 한글 설정
		spellCheck: false,
		/*callbacks: {	//이미지 첨부하는 부분
			onImageUpload: function (files) {
				uploadSummernoteImageFile(files[0], this);
			}
		},*/
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

	let c = selectCategory.attr("data-category");
	let ctext;

	if (c == 'food') ctext = '식품';
	else if (c == 'baby') ctext = '육아';
	else if (c == 'beautyAndFashion') ctext = '뷰티/패션';
	else if (c == 'pet') ctext = '반려동물';
	else if (c == 'life') ctext = '생활';
	else if (c == 'etc') ctext = '기타';

	selectCategory.text(ctext);
	input.val(c);

	category.click(function() {
		selectCategory.text($(this).text());
		input.val($(this).attr("data-category"))
		console.log(input.val());
	})


})

