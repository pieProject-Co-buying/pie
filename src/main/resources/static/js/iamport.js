
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
		var DataTitle = document.querySelector('[data-SHtitle]');
		var DataPrice = document.querySelector('[data-Shprice]');
		var DataNumID = document.querySelector('[data-Shnum]');
		// 가져온 속성값을 변수에 담음
		var Title = DataTitle.getAttribute('data-Shtitle');
		// 정수는 parseFloat으로 변환
		var Price = parseFloat(DataPrice.getAttribute('data-Shprice'));
		var numID = parseFloat(DataNumID.getAttribute('data-Shnum'));
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
		var category;
		var Title;
		var Price;
		var DataTitle;
		var DataPrice;
		var DataNumID
		var numID
		let url = window.location.href;
		var str = url.substring(url.lastIndexOf('/') + 1, url.indexOf('?'));

		if (str == 'boardList') {
			// num 값
			DataNumID = document.querySelector('[data-Shnum]');
			numID = parseFloat(DataNumID.getAttribute('data-Shnum'));
			// 제목, 가격, 카테고리
			DataTitle = document.querySelector('[data-shTitle]');
			DataPrice = document.querySelector('[data-shPrice]');
			Title = DataTitle.getAttribute('data-shTitle');
			Price = parseFloat(DataPrice.getAttribute('data-shPrice'));
			category = "Share"
			
		} else if (str == 'viewProxyBoard') {
			// num 값
			DataNumID = document.querySelector('[data-Prnum]');
			numID = parseFloat(DataNumID.getAttribute('data-Prnum'));
			// 제목, 가격, 카테고리
			DataTitle = document.querySelector('[data-prTitle]');
			DataPrice = document.querySelector('[data-prPrice]');
			Title = DataTitle.getAttribute('data-prTitle');
			Price = parseFloat(DataPrice.getAttribute('data-prPrice'));
			category = "Proxy"
		}
		
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

					var result = {
						"buyer_name": rsp.buyer_name,
						"buyer_nickname": member.nickname,
						"buyer_tel": rsp.buyer_tel,
						"buyer_addr": rsp.buyer_addr,
						"buyer_email": rsp.buyer_email,
						"buyer_postcode": rsp.buyer_postcode,
						"pay_uid": rsp.imp_uid,
						"pay_method": "kakaoPay",
						"pay_Merchant_uid": makeMerchantUid,
						"pay_name": Title,
						"pay_amount": Price,
						"pay_category": category
					}
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
							if (response == 'Share') {
								document.location.href = "shareServiceFinish?num=" + numID + '&merchant_uid='+makeMerchantUid+"&category=" + response;
							} else if (response == 'Proxy') {
								document.location.href = "shareServiceFinish?num=" + numID +'&merchant_uid='+makeMerchantUid+ "&category=" + response;
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
const unixTimestamp = Date.now();
			console.log(unixTimestamp);

function sub(){
	if (confirm("카카오페이로 결제 하시겠습니까?") == true) {
	IMP.request_pay({
	pg : "kakaopay.TCSUBSCRIP", // 실제 계약 후에는 실제 상점아이디로 변경
	pay_method : 'card', // 'card'만 지원됩니다.
	merchant_uid: makeMerchantUid, // 상점에서 관리하는 주문 번호
	name : '프리미엄 구독',
	amount : 17000, // 결제창에 표시될 금액. 실제 승인이 이루어지지는 않습니다. (모바일에서는 가격이 표시되지 않음)
	customer_uid : makeMerchantUid, // 필수 입력.
	buyer_email : mem.email,
	buyer_name : mem.name,
	buyer_tel : mem.phone,
	schedules:[15]
	}, function(rsp) {
		if (rsp.success) {
			
			console.log(rsp)
			var result = {
				"buyer_id": mem.id,
				"buyer_name": mem.name,
				"buyer_nickname": mem.nickname,
				"buyer_tel": mem.phone,
				"buyer_email": mem.email,
				"sub_uid": rsp.imp_uid,
				"sub_customer_uid": makeMerchantUid,
				"sub_method": "kakaopay",
				"sub_merchant_uid": makeMerchantUid,
				"sub_name": '프리미엄 구독',
				"sub_amount": 17000,
				"sub_premium": "pro",
				"schedules": 15
			}
			console.log(makeMerchantUid);
			$.ajax({
				url: "complete",
				type: 'POST',
				'contentType': "application/json",
				data: JSON.stringify(result),
				beforeSend: function(xhr) {
					xhr.setRequestHeader(header, token);
				},
				success: function(response) {
					if(response == 'pro'){						
						alert('빌링키 발급 성공 다시 로그인하소');
						document.location.href='subScribe'
					}else{
						alert('빌링키 발급 실패');
						return false;
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
	});
	}else{
		return;
	}
}
>>>>>>> branch 'develop' of https://github.com/pieProject-Co-buying/pie.git
