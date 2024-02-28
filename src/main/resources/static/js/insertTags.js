//태그 작성
let pie_tags_input = $("#pie_tags_input");
let tagGroup = $("#tagGroup");
let pie_tagsOutput = $("#pie_tagsOutput");
let tags_str = pie_tagsOutput.val();

document.getElementById('today').value = new Date().toISOString().substring(0, 10);


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
	let removedtag = "#" + $(this).find("span").text();
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