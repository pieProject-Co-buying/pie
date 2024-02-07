
$(function() {

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