let originalfileArray = $("#ori_files").val();

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
let loadFiles = $("#fileStr").val().split("/")
let idx = loadFiles.indexOf("");
let removed = loadFiles.splice(idx, 1);
var fileNo = 0;

let originFile = $("#fileStr").val();

console.log(idx);
console.log(loadFiles);
chkFiles();


function chkFiles() {
	if (idx == 5) $(".btn-upload").hide();
}


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
function deleteFile(obj) {
	var parent = obj.parentNode;
	parent.removeChild(obj);
	let deletedOrignFileStr;
	let deletedOrignFile = obj.getAttribute('data-originFile');
	deletedOrignFileStr = originalfileArray.replace(deletedOrignFile + "/", '');
	console.log(deletedOrignFileStr);
	originalfileArray = deletedOrignFileStr;

	let maxFileCnt = 5;
	let attFileCnt = document.querySelectorAll('.pie-img-viewsThumbs').length;
	let remainFileCnt = maxFileCnt - attFileCnt;

	if (remainFileCnt > 0) {
		$(".btn-upload").show();
	}

}