// 이미지 갤러리 드래그로 스크롤링
const slider = document.querySelector('.file-uploadGroup');
let isDown = false;
let startX;
let scrollLeft;
const token = $("meta[name='_csrf']").attr("content")
const header = $("meta[name='_csrf_header']").attr("content");
document.getElementById('today').value = new Date().toISOString().slice(0, 10);

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
let tags_str = '';
let pie_tagsOutput = $("#pie_tagsOutput");


pie_tags_input.keyup(function(key) {
	if (key.keyCode == 13 || key.keyCode == 32) {
		insertTag(this)
	}
})

function insertTag(obj){
	let tag = $(obj).val();
		tag = tag.replace(/ /g, "");
		tag = tag.replace(/[^\w\s가-힣ㄱ-ㅎㅏ-ㅣ]/g, '');
		if (tag == "") {
			alert("입력된 값이 없습니다.(특수문자는 태그에 포함할 수 없습니다.)")
			return false;
		}else if(tag.length>10){
			alert("태그는 10자 이하로 입력해주세요.")
			return false;
		}

		tagGroup.append('<span class="badge badge-info badge-pill d-flex align-items-center p-2 mr-2">#' + tag + '<svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" fill="currentColor" class="bi bi-x-circle-fill ml-1" viewBox="0 0 16 16"><path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0M5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293z"/></svg></span>');
		tags_str += "#" + tag;
		pie_tagsOutput.val(tags_str);

		$(obj).val('');
		console.log(tags_str);

		let tagNum = $("#tagGroup .badge").length;
		if (tagNum == 5) {
			pie_tags_input.hide();
		}
}

$(document).on("click", "span.badge", function(event) {
	let removedTag = $(this).text();
	let regex = new RegExp("#" + removedTag + "\\b", "g");
	tags_str = tags_str.replace(regex, '');
	pie_tagsOutput.val(tags_str);

	$(this).remove();

	let tagNum = $("#tagGroup .badge").length;

	if (tagNum < 5) {
		pie_tags_input.show();
	}
})

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

/*var sel_files = [];
	
$(function(){
	$("#file").on("change", handleImgFileSelect);
});
	
function fileUploadAction(e){
	console.log("fileUploadAction")
	$("#file").trigger('click');
}
	
function handleImgFileSelect(e){
	sel_files = [];
	$(".file-uploadGroup").empty();
	
	let files = e.target.files;
	let filesArr = Array.prototype.slice.call(files);
	
	let i = 0;
	filesArr.forEach(function(event){
		if(!event.type.match("image.*")){
			alert("확장자는 이미지 확장자만 가능합니다.");
			return;
		}
		
		sel_files.push(event);
		
		var reader = new FileReader();
		reader.onload = function(e){
			let html = "<img src ="+e.target.result +">";
			$(".file-uploadGroup").append(html);
			i++;
		}
		reader.readAsDataURL(event);
	})
}*/

/*function imageChange(event) {
	let i = event.target.files.length - 1;
	for (let image of event.target.files) {
		const reader = new FileReader();
		reader.onload = function (event) {
			let img = document.createElement("img");
			img.setAttribute("src", event.target.result);
			img.setAttribute("width", 120);
			img.setAttribute("height", 120);
			document.querySelector(".file-uploadGroup").appendChild(img);
		}
		reader.readAsDataURL(event.target.files[i--]);
	}
} 감지가 느림*/

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
		$("#personRange").val($(this).val())
		if (isNaN(price_total.val()) || isNaN(price_per.val()) || price_total.val() < 0 || price_per.val() < 0) {
			price_total.val(0)
			price_per.val(0)
		}
		price_per.val(Math.floor(price_total.val() / personNum.val()));
		price_total.val(Math.floor(price_per.val() * personNum.val()));
	})


	$("#personRange").change(function() {
		$("#personnelMax").val($(this).val())
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
		callbacks: {
			onKeyup: function(e) {
				fn_chk_byte('#summernote')
			}
		},
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

	/*function uploadSummernoteImageFile(file, editor) {
		var resultUrl;

		data = new FormData();
		data.append("file", file);

		$.ajax({
			data: data,
			type: "POST",
			url: "/uploadSummernoteImageFile",
			contentType: false,
			processData: false,
			success: function (data) {
				$(editor).summernote('insertImage', data.url);
			}
		});

	}*/

	$("#uploadBtn").on('click', submitForm);

	// 드롭다운 메뉴로 셀렉트하기
	let category = $(".dropdown-menu a")
	let input = $("#h-input");
	let selectCategory = $(".dropdown button")

	category.click(function() {
		/*let idx = $(this).index();

		if (idx === 0) {
			input.val("OTT");
			$("#h-input").val("OTT");
			selectCategory.text("OTT");
		} else if (idx === 1) {
			input.val("게임");
			$("#h-input").val("게임");
			selectCategory.text("게임");
		} else if (idx === 2) {
			input.val("도서/음악");
			$("#h-input").val("도서/음악");
			selectCategory.text("도서/음악");
		}*/

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

