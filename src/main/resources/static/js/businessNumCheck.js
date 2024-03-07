function businessNumber() {
    let num = document.getElementById('bus_num').value;  //사업자번호

    const data = {
        "b_no": [num] 
    };

    console.log(data); //확인용

    $.ajax({
        url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=fNENBTZU38Lra4iyqhwseIWwuKhfrHvxcBnYRr6Y3D2dHHRdYa0EctJfEvM0S7%2FCrz7R4DgIKAst1XaVTrtIAw%3D%3D", // 서비스 키 값을 입력
        type: "POST",
        data: JSON.stringify(data), 
        dataType: "JSON",
        contentType: "application/json",
        accept: "application/json",
        success: function(result) {
            console.log(result);

            if (result.status_code === 'OK') {
                if (result.data[0].tax_type === '부가가치세 일반과세자') { //정상조회 사업자의 경우 통과
                    alert("사업자번호가 확인되었습니다."); // 조회성공시
                    document.getElementById('bus_num').classList.add('checked'); // 확인됨을 나타내는 클래스 추가

                    // 나머지 코드는 동일
                } else {
                    alert("조회 불가능한 사업자 번호입니다. 다시 한번 확인해주세요"); 
                    document.getElementById('bus_num').value = '';
                }
                
            } else {
                alert("확인되지 않았습니다."); // 조회되지 않은 경우            
                document.getElementById('bus_num').value = '';
            }
        },
        error: function(result) {
            console.log(result.responseText); 
            alert("에러가 발생했습니다."); 
        }
    });
}

