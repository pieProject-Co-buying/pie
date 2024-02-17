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
		success: function(response) {
			console.log(response);
			$("#likeCount").text(response);
		}
	});
})

$(".pie-heart-icon").click(function() {
	$(this).toggleClass("active");
})