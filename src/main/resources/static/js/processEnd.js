if ($("#processNow").find(".badge").text() == "모집마감") {
	alert("모집이 완료된 게시글이예요!")
}

if ($("#perNow").val() > 1) {
	$(".updateIconBtn").on("click", function(e) {
		e.preventDefault();
		alert("모집이 진행중인 글은 수정할 수 없습니다.");
	})
	$(".deleteIconBtn").on("click", function(e) {
		e.preventDefault();
		alert("모집이 진행중인 글은 삭제할 수 없습니다.");
	})
}