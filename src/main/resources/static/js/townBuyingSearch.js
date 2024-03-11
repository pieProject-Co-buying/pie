// 동일제품 가격비교 js
$(function() {

	let keyWord = $("#shoppingKey").val();
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
				console.log('전송 성공', jsonData.total);
				let sum = 0;

				if (jsonData.total == 0) {
					$('#serachResult').removeClass("row-cols-2");
					$('#serachResult').removeClass("row-cols-md-4");
					$('#serachResult')
						.html('<div class="col">'
							+ '<hr class="my-2" />'
							+ '<p class="small py-4 px-5">검색된 결과가 없습니다.</p>'
							+ '<hr class="my-2" />'
							+ '</div>'
							+ '</div >'
						)
				}

				$
					.each(
						jsonData.items,
						function(index, item) {
							$('#serachResult')
								.append(
									'<div class="col px-1 mb-2"><div class="card border-0 shadow hj-radius-30 shadow"><img src="'
									+ item.image
									+ '" class="hyej-custom-town-naversearch-img card-img-top hj-radius-img-30" alt="..."><div class="card-body"><h6 class="small card-title hyej-text-truncate2">'
									+ item.title
									+ '</h6><p class="card-text small">'
									+ parseInt(item.lprice).toLocaleString('ko-KR')
									+ ' 원</p></div></div></div>');

							sum += parseInt(item.lprice);
						});

				$(".priceBetween").text((sum / 5).toLocaleString('ko-KR'));

			},
			error: function(xhr, desc, err) {
				console.error('전송 실패', err);
			}
		});

})


