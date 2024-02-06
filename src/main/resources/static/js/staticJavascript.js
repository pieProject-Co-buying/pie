
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
	
	$(window).scroll(function(){
		let scrollNow = $(window).scrollTop();
		if(scrollNow>=120){
			gnb.addClass("pie-bg-red");
		}else{
			gnb.removeClass("pie-bg-red");
		}
	})
	
	$("html, body").on("mousewheel", function(event, delta) {
		if (delta > 0) {
			gnb.css({"top": 0})
				

		} else if (delta < 0) {
			gnb.css({"top": "-100%"})
		}
	})
})