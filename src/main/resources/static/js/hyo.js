	
	var mapOptions = {
		    center: new naver.maps.LatLng(37.3595704, 127.105399),
		    zoom: 10
		};

	var map = new naver.maps.Map('map', mapOptions);
	
	
	/* input type="range"로 지도 크기 조절하는 함수 */
/* 	
    function initMap() {
        map = new naver.maps.Map('map', {
            center: new naver.maps.LatLng(37.5665, 126.9780), // 서울의 위도, 경도로 설정됨, 이 기준을 사용자 등록 기준으로 잡으면 될듯..?
            zoom: 10
        });

        zoomRange.addEventListener('input', function() {
            var zoomLevel = parseInt(zoomRange.value);
            map.setZoom(zoomLevel);
        });
    }

    initMap();
 */
    
    var zoomRange = document.getElementById('zoomRange');

    zoomRange.addEventListener('input', function() {
        var kmValue = parseFloat(zoomRange.value); // Get the selected value in kilometers
        var zoomLevel = convertKmToZoomLevel(kmValue); // Convert kilometers to zoom level
        map.setZoom(zoomLevel);
    });

    function convertKmToZoomLevel(km) {
        // Conversion formula, adjust as needed based on your map's behavior
        // This is just a simple example
        return Math.floor(14 - Math.log(km) / Math.LN2);
    }
	