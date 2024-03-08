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

			selectedMarker = null; // 클릭한 마커를 담을 변수

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
							content: '<div class="customoverlay shadow text-truncate" data-category="' + jsonObject.category + '">' +
								'  <a href="https://map.kakao.com/link/map/11394059" target="_blank">' +
								'    <span class="title text-truncate w-100">' + jsonObject.title + '</span>' +
								'  </a>' +
								'</div>',
							num: jsonObject.num,
							category: jsonObject.category,
							endSoon: jsonObject.endSoon
						};

						console.log(parseFloat(jsonObject.x));
						resultArray.push(convertedObject);

						var normalimageSrc;
						var hoverImgSrc;
						if (convertedObject.category == "food") {
							normalimageSrc = "/imgs/foodmarker.png"
							hoverImgSrc = "/imgs/hfoodmarker.png"
						} else if (convertedObject.category == "baby") {
							normalimageSrc = "/imgs/babymarker.png"
							hoverImgSrc = "/imgs/hbabymarker.png"
						} else if (convertedObject.category == "beautyAndFashion") {
							normalimageSrc = "/imgs/beautymarker.png"
							hoverImgSrc = "/imgs/hbeautymarker.png"
						} else if (convertedObject.category == "life") {
							normalimageSrc = "/imgs/lifemarker.png"
							hoverImgSrc = "/imgs/hlifemarker.png"
						} else if (convertedObject.category == "etc") {
							normalimageSrc = "/imgs/etcmarker.png"
							hoverImgSrc = "/imgs/hetcmarker.png"
						} else if (convertedObject.category == "pet") {
							normalimageSrc = "/imgs/petmarker.png"
							hoverImgSrc = "/imgs/hpetmarker.png"
						}

						var nimageSize = new kakao.maps.Size(30, 41);
						var nimageOption = { offset: new kakao.maps.Point(15, 41) };

						var himageSize = new kakao.maps.Size(50, 68);
						var himageOption = { offset: new kakao.maps.Point(25, 68) };

						var normalmarkerImage = new kakao.maps.MarkerImage(normalimageSrc, nimageSize, nimageOption);
						var hovermarkerImage = new kakao.maps.MarkerImage(hoverImgSrc, himageSize, himageOption);


						// 'latlng' 속성이 정의된 경우에만 마커 생성
						if (convertedObject.latlng) {
							var marker = new kakao.maps.Marker(
								{
									map: map, // 마커를 표시할 지도
									position: convertedObject.latlng,
									image: normalmarkerImage
								});

							var customOverlay = new kakao.maps.CustomOverlay({
								map: map,
								position: convertedObject.latlng,
								content: convertedObject.content,
								yAnchor: 1
							});

							console.log(convertedObject.num);
							customOverlay.setMap(null);

							kakao.maps.event
								.addListener(marker,
									'mouseover',
									function() {
										customOverlay.setMap(map);
										if (!selectedMarker || selectedMarker !== marker) {
										/*	makeOverListener(map, marker, infowindow);*/
											marker.setImage(hovermarkerImage);
										}
									});
							kakao.maps.event
								.addListener(
									marker,
									'mouseout',
									function() {
										customOverlay.setMap(null);
										if (!selectedMarker || selectedMarker !== marker) {
									/*		makeOutListener(infowindow)*/
											marker.setImage(normalmarkerImage);
										}
									});

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

		let mapBtns = $(".categoryBtn");
		let nums = {
			foodNum: markerGroups["food"].length,
			babyNum: markerGroups["baby"].length,
			bFNum: markerGroups["beautyAndFashion"].length,
			petNum: markerGroups["pet"].length,
			lifeNum: markerGroups["life"].length,
			etcNum: markerGroups["etc"].length,
			endNum: markerGroupsByDay['1'].length
		}

		let numsSum = nums.foodNum + nums.babyNum + nums.bFNum + nums.petNum + nums.lifeNum + nums.etcNum



		$(".custom_zoomcontrol").show();
		mapBtns.eq(0).find(".num").text(numsSum);
		mapBtns.eq(1).find(".num").text(nums.foodNum);
		mapBtns.eq(2).find(".num").text(nums.babyNum);
		mapBtns.eq(3).find(".num").text(nums.bFNum);
		mapBtns.eq(4).find(".num").text(nums.petNum);
		mapBtns.eq(5).find(".num").text(nums.lifeNum);
		mapBtns.eq(6).find(".num").text(nums.etcNum);
		mapBtns.eq(7).find(".num").text(nums.endNum);


		// 예제: '카테고리1'을 선택하면 해당 카테고리의 마커만 표시

		mapBtns.click(function() {
			let txt = $(this).attr("data-category");
			if (txt === 'endSoon') {
				showMarkersByEndSoon(txt);
			} else {
				showMarkersByCategory(txt);
			}
			mapBtns.removeClass("active");
			$(this).addClass("active");
		})

		$("#pie-map-zoomIn").click(function() {
			zoomIn();
		})

		$("#pie-map-zoomOut").click(function() {
			zoomOut();
		})

		// 지도 확대, 축소 컨트롤에서 확대 버튼을 누르면 호출되어 지도를 확대하는 함수입니다
		function zoomIn() {
			map.setLevel(map.getLevel() - 1);
		}

		// 지도 확대, 축소 컨트롤에서 축소 버튼을 누르면 호출되어 지도를 확대하는 함수입니다
		function zoomOut() {
			map.setLevel(map.getLevel() + 1);
		}


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