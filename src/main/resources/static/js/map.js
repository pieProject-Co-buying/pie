$.ajax({
	data: {
		nowlogin: $("#nowLogin").val()
	},
	type: "GET",
	url: "/map",
	dataType: "json", // 서버에서 JSON으로 반환되는 것을 명시적으로 지정
	success: function(response) {
		try {
			var mapContainer = document.getElementById('map'); // 지도를 표시할 div 
			var mapOption = {
				center: new kakao.maps.LatLng(
					37.3713202419567, 127.110662276962), // 지도의 중심좌표
				level: 3
				// 지도의 확대 레벨
			};


			// 지도를 생성합니다    
			var map = new kakao.maps.Map(mapContainer,
				mapOption);

			var geocoder = new kakao.maps.services.Geocoder();

			geocoder.addressSearch(nowlocation, function(result, status) {

				// 정상적으로 검색이 완료됐으면 
				if (status === kakao.maps.services.Status.OK) {
					var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

					// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
					map.setCenter(coords);
				}
			});



			var jsonArray = response.addressList;
			var resultArray = [];
			var markerGroups = {};
			var markerGroupsByDay = {};

			// 각 JSON 객체를 변환하여 결과 배열에 추가
			for (var i = 0; i < jsonArray.length; i++) {
				(function() {
					var jsonObject = jsonArray[i];

					// title, x, y 속성이 존재하는지 확인
					if (jsonObject.title
						&& jsonObject.x !== undefined
						&& jsonObject.y !== undefined) {
						var convertedObject = {
							title: jsonObject.title,
							latlng: new kakao.maps.LatLng(
								parseFloat(jsonObject.y),
								parseFloat(jsonObject.x)),
							content: '<div class="customoverlay" data-category="' + jsonObject.category + '">' +
								'  <a href="https://map.kakao.com/link/map/11394059" target="_blank">' +
								'    <span class="title text-truncate">' + jsonObject.title + '</span>' +
								'  </a>' +
								'</div>',
							num: jsonObject.num,
							category: jsonObject.category,
							endSoon: jsonObject.endSoon
						};

						console.log(parseFloat(jsonObject.x));
						resultArray.push(convertedObject);

						// 'latlng' 속성이 정의된 경우에만 마커 생성
						if (convertedObject.latlng) {
							var marker = new kakao.maps.Marker(
								{
									map: map, // 마커를 표시할 지도
									position: convertedObject.latlng
								});

							var infowindow = new kakao.maps.InfoWindow(
								{
									content: convertedObject.content
									// 인포윈도우에 표시할 내용
								});

							console.log(convertedObject.num);

							kakao.maps.event
								.addListener(marker,
									'mouseover',
									makeOverListener(
										map,
										marker,
										infowindow));
							kakao.maps.event
								.addListener(
									marker,
									'mouseout',
									makeOutListener(infowindow));

							kakao.maps.event
								.addListener(
									marker,
									'click',
									function() {
										location.href = 'townBuyproduct?num='
											+ convertedObject.num
									});

							if (!markerGroups[convertedObject.category]) {
								markerGroups[convertedObject.category] = [];
							}
							markerGroups[convertedObject.category]
								.push(marker);

							if (!markerGroupsByDay[convertedObject.endSoon]) {
								console
									.log(convertedObject.endSoon)
								markerGroupsByDay[convertedObject.endSoon] = [];
							}
							markerGroupsByDay[convertedObject.endSoon]
								.push(marker);

						}
					} else {
						console
							.error("Invalid JSON object at index "
								+ i
								+ ": "
								+ JSON
									.stringify(jsonObject));
					}
				})(); // 이 부분을 추가하여 클로저 문제를 해결합니다.

			}

			console.log(resultArray);
		} catch (error) {
			console.error("Error parsing JSON response: "
				+ error);
		}
		function makeOverListener(map, marker, infowindow) {
			return function() {
				infowindow.open(map, marker);
			};
		}
		function makeOutListener(infowindow) {
			return function() {
				infowindow.close();
			};
		}

		// 예제: '카테고리1'을 선택하면 해당 카테고리의 마커만 표시

		$(".categoryBtn").click(function() {
			let txt = $(this).attr("data-category");
			if (txt === 'endSoon') {
				showMarkersByEndSoon(txt);
			} else {
				showMarkersByCategory(txt);
			}
		})

		function showMarkersByCategory(category) {
			for (var key in markerGroups) {
				if (markerGroups.hasOwnProperty(key)) {
					var markers = markerGroups[key];
					for (var i = 0; i < markers.length; i++) {
						markers[i].setMap(category === 'all'
							|| key === category ? map
							: null);
					}
				}
			}
		}

		function showMarkersByEndSoon(category) {
			for (var key in markerGroupsByDay) {
				if (markerGroupsByDay.hasOwnProperty(key)) {
					var markers = markerGroupsByDay[key];
					for (var i = 0; i < markers.length; i++) {
						markers[i]
							.setMap(category === 'all'
								|| (category === 'endSoon' && key === '1') ? map
								: null);
					}
				}
			}
		}

	}
});