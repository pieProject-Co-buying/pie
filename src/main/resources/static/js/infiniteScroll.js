const listEnd = document.querySelector('#showMoreButton'); // 관찰할 대상(요소)
const options = {
	root: null, // 뷰포트를 기준으로 타켓의 가시성 검사
	rootMargin: '0px 0px 0px 0px', // 확장 또는 축소 X
	threshold: 0, // 타켓의 가시성 0%일 때 옵저버 실행
};
const productList = document.getElementById("allCards");

let page = 0;
const onIntersect = (entries, observer) => {
	entries.forEach(async (entry) => {
		if (entry.isIntersecting) {
			console.log('무한 스크롤 실행');
			page++;
			console.log('page: ' + page);
			const products = await getData( // 상품의 데이터를 가져오는 함수
				page
			);
			products.forEach(pro => {
				var close = '';
				if(pro.process==0) close='pie-close'
				
				const $newItems = $('<a class="card pie-radius-small overflow-hidden '+close+'" href="viewProxyBoard?num=' + pro.num +
					'" data-end="' + pro.process +
					'" data-soon="' + pro.soon +
					'" data-category="' + pro.category +
					'" data-date="' + pro.updateDay +
					'" data-like="' + pro.likeNum +
					'" data-hit="' + pro.hit + '"><img src="/imgs/test/' +
					pro.productImg +
					'" class="card-img-top" alt="..."><div class="card-body position-absolute w-100"><small><span class="pie-c-red">' +
					pro.category + '</span></small><h5 class="card-title text-truncate font-weight-bolder">' +
					pro.title + '</h5><p class="card-text"><small class="text-muted">' +
                pro.updateinfo + '</small></p></div></a>');
					
				/*productList.insertAdjacentHTML(
					'beforeend',$newItems
				);*/
				$('#allCards').isotope().append($newItems)
				$('#allCards').isotope('appended', $newItems).isotope('layout');
			});
		}
		
	});
	
};

const observer = new IntersectionObserver(onIntersect, options);
observer.observe(listEnd);

function closeClosely(str1, a, b) {
    // str1을 'yyyy-MM-dd HH:mm:ss' 형식으로 파싱
    let updatedate = new Date(str1.replace(/-/g, "/"));
    
    // 현재 날짜 및 시간을 얻음
    let now = new Date();
    
    // 두 날짜 간의 차이를 분 단위로 계산
    let minutes = Math.floor((now - updatedate) / (1000 * 60));

    let remains = a - b;
    console.log("r : " + remains);
    console.log("m : " + minutes);

    // 조건을 만족하는지 여부를 반환
    return ((minutes > 0 && minutes < 1440 && remains > 0) || (minutes > 0 && (remains <= 5 && remains > 0)));
}


function getData(page) {
	return new Promise((resolve, reject) => {
		$.ajax({
			data: {
				page: page
			},
			dataType: 'json',
			type: "POST",
			url: "/infiniteLoading",
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success: function(response) {
				console.log(response);
				resolve(response); // 비동기 작업이 성공하면 resolve를 호출하여 Promise 완료
			},
			error: function(xhr, desc, err) {
				console.error('전송 실패', err);
				reject(err); // 비동기 작업이 실패하면 reject를 호출하여 Promise 완료
			}
		});
	});
}

