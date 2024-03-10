/*비밀번호 보이기 / 숨기기*/
$(".view-button").eq(0).hide();
$(".view-button").eq(2).hide();
$(".view-button").click(function() {
	$(this).siblings("input#password").attr('type', function(index, attr) {
		return attr == 'password' ? 'text' : 'password';
	});
	$(this).siblings("input#passwordChk").attr('type', function(index, attr) {
		return attr == 'password' ? 'text' : 'password';
	});
	$(this).hide();
	$(this).siblings(".view-button").show();
})
