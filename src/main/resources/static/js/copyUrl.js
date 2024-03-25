
$(function() {
	/***************************************************
	 * URL 복사기능
	 * *************************************************/

	let urlInput = $("#downloadURL");
	let urlBtn = $(".copyBtn");

	urlInput.val(nowUrl);

	urlBtn.click(function() {
		new ClipboardJS('.copyBtn');
		alert("링크가 복사되었습니다.")
	})

})
function deleteForm() {
	if (confirm("삭제 하시겠습니까?") == true) {
		document.deleteForm.submit();
	} else {
		return false;
	}
}