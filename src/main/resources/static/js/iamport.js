var IMP = window.IMP;
IMP.init("imp80647284");
/*******************상품 아이디*******************/
var today = new Date();
var hours = today.getHours(); // 시
var minutes = today.getMinutes();  // 분
var seconds = today.getSeconds();  // 초
var milliseconds = today.getMilliseconds();
var makeMerchantUid = `${hours}` + `${minutes}` + `${seconds}` + `${milliseconds}`;
const token = $("meta[name='_csrf']").attr("content")
const header = $("meta[name='_csrf_header']").attr("content");
const name = $("#userName").val();

function kgPay() {
	if (confirm("kg 이니시스로 결제 하시겠습니까?") == true) { // 확인 클릭 시
		// html의 th:attr의 값을 가져옴
		var DataTitle = document.querySelector('[data-title]');
		var DataPrice = document.querySelector('[data-pricePer]');
		var DataNumID = document.querySelector('[data-num]');
		// 가져온 속성값을 변수에 담음
		var Title = DataTitle.getAttribute('data-title');
		// 정수는 parseFloat으로 변환
		var Price = parseFloat(DataPrice.getAttribute('data-pricePer'));
		var numID = parseFloat(DataNumID.getAttribute('data-num'));
		IMP.request_pay({
			pg: 'html5_inicis.INIBillTst',
			merchant_uid: makeMerchantUid, // 상점에서 관리하는 주문번호
			name: Title, // 상품 이름
			amount: Price, // 상품 가격
			buyer_name: member.name, // 구매자 이름
			buyer_tel: member.phone, // 구매자 전화번호
			buyer_addr: member.address_main + member.address_sub, // 구매자 주소
			buyer_email: member.email, // 구매자 이메일
			buyer_postcode: member.postCode, // 구매자 우편번호

		}, function(rsp) {
			if (rsp.success) {

				var result = {
					"buyer_name": rsp.buyer_name, // 구매자 이름
					"buyer_nickname": member.nickname, // 구매자 닉네임
					"buyer_tel": rsp.buyer_tel, // 구매자 전화번호
					"buyer_addr": rsp.buyer_addr, // 구매자 주소
					"buyer_email": rsp.buyer_email, // 구매자 이메일
					"buyer_postcode": rsp.buyer_postcode, // 구매자 우편번호
					"pay_uid": rsp.imp_uid, // 고유 거래 식별자
					"pay_method": "카드결제", // 결제수단
					"payMerchant_uid": makeMerchantUid, // 상점에서 관리하는 주문번호
					"payName": Title, // 상품 이름
					"payAmount": Price // 상품 가격
				}
				$.ajax({
						url: "payCheck",
						type: 'POST',
						contentType: "application/json",
						data: JSON.stringify(result),
						beforeSend: function(xhr) {
							xhr.setRequestHeader(header, token);
						},
						success: function(response) {
							alert("결제에 성공하였습니다");
							document.location.href = "shareServiceFinish?sh_numID=" + numID;
						},
						error: function(xhr, status, error) {
							console.log('error:' + error);
						}
					});
			} else if (!rsp.success) {
				var msg = '결제에 실패하였습니다.';
				msg += '에러내용 : ' + rsp.error_msg;
				alert(msg);
			}
		}
		);
	} else { // 취소 클릭 시
		return;
	}

}
function kakaoPay() {
	if (confirm("카카오페이로 결제 하시겠습니까?") == true) {
		var DataTitle = document.querySelector('[data-title]');
		var DataPrice = document.querySelector('[data-pricePer]');
		var Title = DataTitle.getAttribute('data-title');
		var Price = parseFloat(DataPrice.getAttribute('data-pricePer'));
		//var DataPersonnelNow = document.querySelector('[data-personnelNow]');
		//var personnelNow = parseFloat(DataPersonnelNow.getAttribute('data-numID'));
		//imp_uid = extract_POST_value_from_url('imp_uid') //post ajax request로부터 imp_uid확인

		IMP.request_pay(
			{
				pg: "kakaopay.TC0ONETIME",
				merchant_uid: makeMerchantUid,
				name: Title,
				amount: Price,
				buyer_name: member.name,
				buyer_tel: member.phone,
				buyer_addr: member.address_main + member.address_sub,
				buyer_email: member.email,
				buyer_postcode: member.postCode
			}, function(rsp) {
				if (rsp.success) {
					
					console.log("성공1")
					var category;
					let url = window.location.href;
					var str = url.substring(url.lastIndexOf('/') + 1,url.indexOf('?'));
					
					if(str=='boardList'){
						var DataNumID = document.querySelector('[data-num]');
						var numID = parseFloat(DataNumID.getAttribute('data-num'));
						category="Share"
					}else if(str=='viewProxyBoard'){
						var DataPrNumID = document.querySelector('[data-Prnum]');
						var PrnumID = parseFloat(DataPrNumID.getAttribute('data-Prnum'));
						category="Proxy"
					}			
					console.log(str);
					
					var result = {
						"buyer_name": rsp.buyer_name,
						"buyer_nickname": member.nickname,
						"buyer_tel": rsp.buyer_tel,
						"buyer_addr": rsp.buyer_addr,
						"buyer_email": rsp.buyer_email,
						"buyer_postcode": rsp.buyer_postcode,
						"pay_uid": rsp.imp_uid,
						"pay_method": "카카오페이",
						"payMerchant_uid": makeMerchantUid,
						"payName": "제목",
						"payAmount": 300,
						"pay_category":category
					}
					console.log(
						result.buyer_nickname);


					$.ajax({
						url: "payCheck",
						type: 'POST',
						'contentType': "application/json",
						data: JSON.stringify(result),
						beforeSend: function(xhr) {
							xhr.setRequestHeader(header, token);
						},
						success: function(response) {
							alert("결제에 성공하였습니다");
							if (response == 'Share'){
								document.location.href = "shareServiceFinish?num=" + numID +"&cate=" + response;
							}else if(response == 'Proxy'){
								document.location.href = "shareServiceFinish?num=" + PrnumID + "&cate=" + response;
							}
						},
						error: function(xhr, status, error) {
							console.log('error:' + error);
						}
					});
				} else if (!rsp.success) {
					var msg = '결제에 실패하였습니다.';
					msg += '에러내용 : ' + rsp.error_msg;
					alert(msg);
				}
			}
		);
	} else {
		return;
	}
}
function tossPay() {
	if (confirm("tossPay로 결제 하시겠습니까?") == true) {
		var DataTitle = document.querySelector('[data-title]');
		var DataPrice = document.querySelector('[data-pricePer]');
		var DataNumID = document.querySelector('[data-num]');
		var Title = DataTitle.getAttribute('data-title');
		var Price = parseFloat(DataPrice.getAttribute('data-pricePer'));
		var numID = parseFloat(DataNumID.getAttribute('data-num'));
		IMP.request_pay(
			{
				pg: "tosspay.tosstest",
				merchant_uid: makeMerchantUid,
				name: Title,
				amount: Price,
				buyer_name: member.name,
				buyer_tel: member.phone,
				buyer_addr: member.address_main + member.address_sub,
				buyer_email: member.email,
				buyer_postcode: member.postCode
			}, function(rsp) {
				if (rsp.success) {
					var result = {
						"buyer_name": rsp.buyer_name,
						"buyer_nickname": member.nickname,
						"buyer_tel": rsp.buyer_tel,
						"buyer_addr": rsp.buyer_addr,
						"buyer_email": rsp.buyer_email,
						"buyer_postcode": rsp.buyer_postcode,
						"pay_uid": rsp.imp_uid,
						"pay_method": "토스페이",
						"payMerchant_uid": makeMerchantUid,
						"payName": Title,
						"payAmount": Price
					}
					console.log(
						result.buyer_nickname);
					$.ajax({
						url: "payCheck",
						type: 'POST',
						'contentType': "application/json",
						data: JSON.stringify(result),
						beforeSend: function(xhr) {
							xhr.setRequestHeader(header, token);
						},
						success: function(response) {
							alert("결제에 성공하였습니다");
							document.location.href = "shareServiceFinish?sh_numID=" + numID;
						},
						error: function(xhr, status, error) {
							console.log('error:' + error);
						}
					});
				} else if (!rsp.success) {
					var msg = '결제에 실패하였습니다.';
					msg += '에러내용 : ' + rsp.error_msg;
					alert(msg);
				}
			}
		);
	} else {
		return;
	}

}

function paycoPay() {
	IMP.request_pay(
		{
			pg: "payco",
			pay_method: "card",
			merchant_uid: "IMP" + makeMerchantUid,
			name: "감자 132kg",
			amount: 1300,
			buyer_email: "Iamport@chai.finance",
			buyer_name: "수원 감자밭",
			buyer_tel: "010-1234-5678",
			buyer_addr: "수원시 팔달구",
			buyer_postcode: "123-456",
		},
		function(rsp) {
			if (rsp.success) {
				$.ajax({
						url: "payCheck",
						type: 'POST',
						'contentType': "application/json",
						data: JSON.stringify(result),
						beforeSend: function(xhr) {
							xhr.setRequestHeader(header, token);
						},
						success: function(response) {
							alert("결제에 성공하였습니다");
							document.location.href = "shareServiceFinish?sh_numID=" + numID;
						},
						error: function(xhr, status, error) {
							console.log('error:' + error);
						}
					});
				alert("결제에 성공하였습니다");
				document.location.href = "shareServiceFinish";
			} else if (!rsp.success) {
				var msg = '결제에 실패하였습니다.';
				msg += '에러내용 : ' + rsp.error_msg;
				alert(msg);
			}
		}
	);
}
