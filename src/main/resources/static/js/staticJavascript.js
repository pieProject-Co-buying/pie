
$(function() {
	$('.owl-carousel').owlCarousel({
		loop: true,
		margin: 10,
		responsiveClass: true,
		responsive: {
			0: {
				items: 1
			},
			1000: {
				items: 3
			}
		},
		nav: true
	})


	let gnb = $("header nav")
	$("html, body").on("mousewheel", function(event, delta) {
		if (delta > 0) {
			gnb.stop().animate({
				top: 0
			}, 200)
		} else if (delta < 0) {
			gnb.stop().animate({
				top: "-100%"
			}, 200)
		}
	})
})