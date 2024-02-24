Fancybox.bind('[data-fancybox]', {});

let tableName = $("#tableName").val();
let getURL = window.location.href;
const url = new URL(getURL);
const urlParams = url.searchParams;
let num = urlParams.get('num');

$(".pie-heart-icon").click(function() {
	$.ajax({
		data: {
			num: num,
			tableName: tableName
		},
		dataType: 'json',
		type: "POST",
		url: "/updateHeart",
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success: function(response) {
			console.log(response);
			$("#likeCount").text(response);
		},
		error: function(xhr, desc, err) {
			console.error('전송 실패', err);
		}
	});
})

$(".pie-heart-icon").click(function() {
	$(this).toggleClass("active");
})