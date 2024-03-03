var IMP = window.IMP;
IMP.init("imp80647284");
/*******************상품 아이디*******************/
var today = new Date();
var hours = today.getHours(); // 시
var minutes = today.getMinutes();  // 분
var seconds = today.getSeconds();  // 초
var milliseconds = today.getMilliseconds();
var makeMerchantUid = `${hours}` + `${minutes}` + `${seconds}` + `${milliseconds}`;

function kgPay() {
	if (confirm("kg 이니시스로 결제 하시겠습니까?") == true) {
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
				pg: 'html5_inicis.INIBillTst',
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
						"pay_num" : numID,
						"buyer_id": member.id,
						"buyer_name": rsp.buyer_name,
						"buyer_nickname": member.nickname,
						"buyer_tel": rsp.buyer_tel,
						"buyer_addr": rsp.buyer_addr,
						"buyer_email": rsp.buyer_email,
						"buyer_postcode": rsp.buyer_postcode,
						"pay_uid": rsp.imp_uid,
						"pay_method": "카드결제",
						"pay_Merchant_uid": makeMerchantUid,
						"pay_name": Title,
						"pay_amount": Price,
						"pay_category": category,
						"pay_refund": 0						
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
						"pay_num" : numID,
						"buyer_id": member.id,
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
						"pay_category": category,
						"pay_refund": '0'	
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
	if (confirm("토스페이로 결제 하시겠습니까?") == true) {
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
						"pay_num" : numID,
						"buyer_id": member.id,
						"buyer_name": rsp.buyer_name,
						"buyer_nickname": member.nickname,
						"buyer_tel": rsp.buyer_tel,
						"buyer_addr": rsp.buyer_addr,
						"buyer_email": rsp.buyer_email,
						"buyer_postcode": rsp.buyer_postcode,
						"pay_uid": rsp.imp_uid,
						"pay_method": "tossPay",
						"pay_Merchant_uid": makeMerchantUid,
						"pay_name": Title,
						"pay_amount": Price,
						"pay_category": category,
						"pay_refund": '0'	
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
function sub(){
	if (confirm("구독 하시겠습니까?") == true) {
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
				"pay_num" : numID,
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
						alert('구독이 완료되었습니다.');
						document.location.href='subScribe'
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
