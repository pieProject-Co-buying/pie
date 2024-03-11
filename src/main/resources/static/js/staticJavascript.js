/*CSRF 토큰 설정*/
const token = $("meta[name='_csrf']").attr("content")
const header = $("meta[name='_csrf_header']").attr("content");

$(function() {
	/*gnb wheel Effect*/
	let gnb = $("header nav").parents(".row");
	let logo = $(".pie-logo-gnb")

	$(".pie-otherGnbStyled .pie-logo-gnb").attr("src", "imgs/logo_color_C_white.png");
	$(".pie-otherGnbStyled .row:has(#gnb)").removeClass("bg-white");
	$(".pie-otherGnbStyled .row:has(#gnb)").addClass("navbar-dark").removeClass("navbar-light");
	
	$(".pie-otherGnbStyled2 .pie-logo-gnb").attr("src", "imgs/logo_color_C_white.png");
	$(".pie-otherGnbStyled2 .row:has(#gnb)").addClass("navbar-dark").removeClass("navbar-light");

	$(window).scroll(function() {
		let scrollNow = $(window).scrollTop();
		if (scrollNow >= 120) {
			logo.attr("src", "imgs/logo_color_C_white.png");
			gnb.addClass("pie-bg-red").addClass("navbar-dark").removeClass("navbar-light");
		} else {
			logo.attr("src", "imgs/logo_color_C_red.png");
			gnb.removeClass("pie-bg-red").removeClass("navbar-dark").addClass("navbar-light");


			$(".pie-otherGnbStyled2 .pie-logo-gnb").attr("src", "imgs/logo_color_C_white.png");
			$(".pie-otherGnbStyled2 .row:has(#gnb)").addClass("navbar-dark").removeClass("navbar-light");
			$(".pie-otherGnbStyled .pie-logo-gnb").attr("src", "imgs/logo_color_C_white.png");
			$(".pie-otherGnbStyled .row:has(#gnb)").addClass("navbar-dark").removeClass("navbar-light");
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