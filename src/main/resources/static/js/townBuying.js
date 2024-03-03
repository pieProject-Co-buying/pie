		
		// 상품 6개씩 보여주는 스크립트
		
		
		document.addEventListener('DOMContentLoaded', function() {
			// 페이지가 로드될 때 처음 6개의 카드만 보이도록 설정
			showInitialCards(6);

			// 더보기 버튼 클릭 이벤트 추가
			document.getElementById('loadMoreButton').addEventListener('click',
					loadMoreCards);
		});

		function showInitialCards(numToShow) {
			const allCards = document.querySelectorAll('.townBuyCard');
			// 처음에 표시할 카드 수만큼만 보이도록 설정
			for (let i = 0; i < allCards.length; i++) {
				if (i < numToShow) {
					allCards[i].style.display = 'block';
				} else {
					allCards[i].style.display = 'none';
				}
			}
		}

		function loadMoreCards() {
			// 추가 카드를 가져오는 로직 구현
			// 이 부분은 서버에서 데이터를 가져와서 새로운 카드를 생성하고 추가
			// 여기서는 예시로 각 카드의 display 속성을 변경하여 가상으로 추가
			const additionalCards = document
					.querySelectorAll('.townBuyCard:not([style="display: block;"])');
			const numToShow = 6; // 한 번에 보여줄 추가 카드의 수
			let cardsToShow = 0;
			for (let i = 0; i < additionalCards.length; i++) {
				if (cardsToShow < numToShow) {
					additionalCards[i].style.display = 'block';
					cardsToShow++;
				} else {
					break;
				}
			}

			// 모든 카드를 보여주었는지 확인
			if (cardsToShow === 0) {
				document.getElementById('loadMoreButton').style.display = 'none';
			}

		}
		
	/*	$("#selectCategory").val($("#category").val())
		

		// 선택한 카테고리 보여지게 하는 기능
		$(document).on(
				'click',
				'.dropdown-menu .dropdown-item',
				function() {
					let selectedCategory = $(this).text();
					$(this).closest('.dropdown').find('.dropdown-toggle').text(
							selectedCategory);
					alert(selecteCategory);
					$("#category").val($(this).attr("data-category"));
					alert($("#category").val());
				});
		*/
		
		
		// townBuySearch

		/* 캐로셀 첫 index active 적용하는 js */

		$(function() {
			$("#carousel1").find(".carousel-item").eq(0).addClass("active");
			$("#carousel2").find(".carousel-item").eq(0).addClass("active");
			$("#carousel3").find(".carousel-item").eq(0).addClass("active");
		})
		
		
		


				// 선택한 카테고리 보여지게 하는 기능

				$(function() {
					if ($("#category").val() != null
							&& $("#category").val() != "") {
						$("#selectCategory .dropdown-toggle").text(
								$(
										"[data-category="
												+ $("#category").val() + "]")
										.text())
					}
				})

				$(document).on(
						'click',
						'.dropdown-menu .dropdown-item',
						function() {
							let selectedCategory = $(this).text();
							$(this).closest('.dropdown').find(
									'.dropdown-toggle').text(selectedCategory);
							$("#category").val($(this).attr("data-category"));
						});


