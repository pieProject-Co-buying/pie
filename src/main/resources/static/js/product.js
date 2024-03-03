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
			$(".pie-heart-icon").toggleClass("active");
		},
		error: function(xhr, desc, err) {
			console.error('전송 실패', err);
		}
	});
})

$(".btn>.pie-thumbsUp-icon").click(function() {
	$.ajax({
		data: {
			you: $("#yourId").val()
		},
		dataType: 'json',
		type: "POST",
		url: "/Follwing",
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success: function(response) {
			console.log(response);
			if (response) {
				$(".btn>.pie-thumbsUp-icon").addClass("active");
			} else {
				$(".btn>.pie-thumbsUp-icon").removeClass("active");
			}
		},
		error: function(xhr, desc, err) {
			console.error('전송 실패', err);
		}
	});
})