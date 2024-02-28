/*CSRF 토큰 설정*/
const token = $("meta[name='_csrf']").attr("content")
const header = $("meta[name='_csrf_header']").attr("content");

$(function() {
	/*gnb wheel Effect*/
	let gnb = $("header nav").parents(".row");

	$(window).scroll(function() {
		let scrollNow = $(window).scrollTop();
		if (scrollNow >= 120) {
			gnb.addClass("pie-bg-red");
		} else {
			gnb.removeClass("pie-bg-red");
		}
	})

	$("html, body").on("mousewheel", function(event, delta) {
		if (delta > 0) {
			gnb.stop().css({ "top": 0 })
		} else if (delta < 0) {
			gnb.stop().css({ "top": "-100%" })
		}
	})
})