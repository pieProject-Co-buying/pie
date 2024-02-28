$(function(){
	// 가격 계산하기
	let price_total = $("#price_total");
	let price_per = $("#price_per");
	let personNum = $("#personnelMax");


	price_total.change(function() {
		console.log(personNum.val());
		if (isNaN($(this).val()) || $(this).val() < 0) {
			price_total.val(0)
		}
		price_per.val(Math.floor(price_total.val() / personNum.val()));
	})


	price_per.change(function() {
		console.log(price_total.val());
		if (isNaN($(this).val()) || $(this).val() < 0) {
			price_per.val(0)
		}
		price_total.val(Math.floor(price_per.val() * personNum.val()));
	})

	personNum.change(function() {
		$("#personRange").val($(this).val())
		if (isNaN(price_total.val()) || isNaN(price_per.val()) || price_total.val() < 0 || price_per.val() < 0) {
			price_total.val(0)
			price_per.val(0)
		}
		price_per.val(Math.floor(price_total.val() / personNum.val()));
		price_total.val(Math.floor(price_per.val() * personNum.val()));
	})


	$("#personRange").change(function() {
		$("#personnelMax").val($(this).val())
		if (isNaN(price_total.val()) || isNaN(price_per.val()) || price_total.val() < 0 || price_per.val() < 0) {
			price_total.val(0)
			price_per.val(0)
		}
		price_per.val(Math.floor(price_total.val() / personNum.val()));
		price_total.val(Math.floor(price_per.val() * personNum.val()));
	})
})