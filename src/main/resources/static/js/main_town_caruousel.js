
		$(document).ready(function(){


		    // 텍스트 클릭 시 해당 캐로셀로 이동
		    $(".text-carousel-item").click(function(){
		        var index = $(this).index();
		        $("#section1Carousel").carousel(index);
		    });
		});