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

$(".follow-btn").click(function() {
	let following = $(this);
	$.ajax({
		data: {
			you: following.attr("data-following")
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
				$(".follow-btn").addClass("unfollow");
				$(".follow-btn").find("span").text("팔로잉");
			} else {
				$(".follow-btn").removeClass("unfollow");
				$(".follow-btn").find("span").text("팔로우");
			}
		},
		error: function(xhr, desc, err) {
			console.error('전송 실패', err);
		}
	});
})

$(".pie-thumbsUp-icon").click(function() {
	let following = $(this);
	$.ajax({
		data: {
			you: following.attr("data-following")
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
				following.addClass("active");
			} else {
				following.removeClass("active");
			}
		},
		error: function(xhr, desc, err) {
			console.error('전송 실패', err);
		}
	});
})

$(document).on("mouseenter", ".unfollow", function() {
    let following = $(this).find("span");
    following.text("언팔로우")
});

$(document).on("mouseleave", ".unfollow", function() {
    let following = $(this).find("span");
    following.text("팔로잉")
});