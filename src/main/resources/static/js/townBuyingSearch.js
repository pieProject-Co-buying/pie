// 동일제품 가격비교 js
$(function() {

	let keyWord = $("#townKeyword").val();
	console.log(keyWord);

	$
		.ajax({
			method: 'POST',
			url: '/search',
			contentType: 'application/json',
			data: keyWord,
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success: function(response) {
				const jsonData = JSON.parse(response);
				console.log('전송 성공', jsonData);
				$
					.each(
						jsonData.items,
						function(index, item) {
							$('#serachResult')
								.append(
									'<div class="col mb-4"><a href="' + item.link + '" class="card  hj-radius-30"><img src="' + item.image + '" class="card-img-top  hj-radius-img-30" alt="..."><div class="card-body"><h5 class="card-title">'
									+ item.title
									+ '</h5><p class="card-text">'
									+ item.lprice
									+ '</p></div></a></div>');
						});
			},
			error: function(xhr, desc, err) {
				console.error('전송 실패', err);
			}
		});

})



