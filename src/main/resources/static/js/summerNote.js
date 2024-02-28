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
	
	/*submit*/
	$("#uploadBtn").on('click', submitForm);
})

/*쓸수 있는 글씨 수 체크*/
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
