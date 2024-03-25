var IMP = window.IMP;

/*******************상품 아이디*******************/
var today = new Date();
var hours = today.getHours(); // 시
var minutes = today.getMinutes();  // 분
var seconds = today.getSeconds();  // 초
var milliseconds = today.getMilliseconds();
var makeMerchantUid = `${hours}` + `${minutes}` + `${seconds}` + `${milliseconds}` + $("#nowBoard").val() + $("#nowLogin").val();

var category;

var str = nowUrl.substring(nowUrl.lastIndexOf('/') + 1, nowUrl.indexOf('?'));



function payment(method){
	$.ajax({
		data : {
			nowLogin : $("#nowLogin").val(),
			nowBoard : $("#nowBoard").val()
		},
		type : "POST",
		url : "/shoppingInfo",
		beforeSend: function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		dataType : "json",
		success:function(result){
			
			console.log(result);
			IMP.init(result.shopkey);
			if(method=="kgPay"){
				kgPay(result);
			}else if(method=="kakaoPay"){
				kakaoPay(result);
			}else if(method=="tossPay"){
				tossPay(result);
			}
		},
		error: function(xhr, desc, err) {
			console.error('전송 실패', err);
		}
	})
}


function kgPay(payInfo) {
	if (confirm("kg 이니시스로 결제 하시겠습니까?") == true) {

		if (str == 'boardList') {
			/*			// num 값
						DataNumID = document.querySelector('[data-num]');
						numID = parseFloat(DataNumID.getAttribute('data-num'));
						// 제목, 가격, 카테고리
						DataTitle = document.querySelector('[data-title]');
						DataPrice = document.querySelector('[data-price]');
						Title = DataTitle.getAttribute('data-shTitle');
						Price = parseFloat(DataPrice.getAttribute('data-shPrice'));*/
			category = "Share"

		} else if (str == 'viewProxyBoard') {
			/*	// num 값
				DataNumID = document.querySelector('[data-Prnum]');
				numID = parseFloat(DataNumID.getAttribute('data-Prnum'));
				// 제목, 가격, 카테고리
				DataTitle = document.querySelector('[data-prTitle]');
				DataPrice = document.querySelector('[data-prPrice]');
				Title = DataTitle.getAttribute('data-prTitle');
				Price = parseFloat(DataPrice.getAttribute('data-prPrice'));*/
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
						"pay_num": numID,
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
								document.location.href = "shareServiceFinish?num=" + numID + '&merchant_uid=' + makeMerchantUid + "&category=" + response;
							} else if (response == 'Proxy') {
								document.location.href = "shareServiceFinish?num=" + numID + '&merchant_uid=' + makeMerchantUid + "&category=" + response;
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
function kakaoPay(payInfo) {
	if (confirm("카카오페이로 결제 하시겠습니까?") == true) {
		
		if (str == 'boardList') {
			category = "Share"
		} else if (str == 'viewProxyBoard') {
			category = "Proxy"
		}
		IMP.request_pay(
			{
				pg: "kakaopay.TC0ONETIME",
				merchant_uid: makeMerchantUid,
				name: payInfo.name,
				amount: payInfo.amount,
				buyer_name: payInfo.buyer_name,
				buyer_tel: payInfo.buyer_tel,
				buyer_addr: payInfo.buyer_addr,
				buyer_email: payInfo.buyer_email,
				buyer_postcode: payInfo.buyer_postcode
			}, function(rsp) {
				if (rsp.success) {
					var result = {
						"pay_num": payInfo.boardNum,
						"buyer_id": payInfo.id,
						"buyer_name": rsp.buyer_name,
						"buyer_nickname": "test",
						"buyer_tel": rsp.buyer_tel,
						"buyer_addr": rsp.buyer_addr,
						"buyer_email": rsp.buyer_email,
						"buyer_postcode": rsp.buyer_postcode,
						"pay_uid": rsp.imp_uid,
						"pay_method": "kakaoPay",
						"pay_Merchant_uid": makeMerchantUid,
						"pay_name": payInfo.name,
						"pay_amount": payInfo.amount,
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
								document.location.href = "shareServiceFinish?num=" + result.pay_num + '&merchant_uid=' + makeMerchantUid + "&category=" + response;
							} else if (response == 'Proxy') {
								document.location.href = "shareServiceFinish?num=" + result.pay_num + '&merchant_uid=' + makeMerchantUid + "&category=" + response;
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
function tossPay(payInfo) {
	if (confirm("토스페이로 결제 하시겠습니까?") == true) {
	/*	var category;
		var Title;
		var Price;
		var DataTitle;
		var DataPrice;
		var DataNumID
		var numID
		let url = window.location.href;
		var str = url.substring(url.lastIndexOf('/') + 1, url.indexOf('?'));*/

		if (str == 'boardList') {
			/*			// num 값
						DataNumID = document.querySelector('[data-num]');
						numID = parseFloat(DataNumID.getAttribute('data-num'));
						// 제목, 가격, 카테고리
						DataTitle = document.querySelector('[data-title]');
						DataPrice = document.querySelector('[data-price]');
						Title = DataTitle.getAttribute('data-shTitle');
						Price = parseFloat(DataPrice.getAttribute('data-shPrice'));*/
			category = "Share"

		} else if (str == 'viewProxyBoard') {
			/*	// num 값
				DataNumID = document.querySelector('[data-Prnum]');
				numID = parseFloat(DataNumID.getAttribute('data-Prnum'));
				// 제목, 가격, 카테고리
				DataTitle = document.querySelector('[data-prTitle]');
				DataPrice = document.querySelector('[data-prPrice]');
				Title = DataTitle.getAttribute('data-prTitle');
				Price = parseFloat(DataPrice.getAttribute('data-prPrice'));*/
			category = "Proxy"
		}
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
						"pay_num": numID,
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
					console.log(result)
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
								document.location.href = "shareServiceFinish?num=" + numID + '&merchant_uid=' + makeMerchantUid + "&category=" + response;
							} else if (response == 'Proxy') {
								document.location.href = "shareServiceFinish?num=" + numID + '&merchant_uid=' + makeMerchantUid + "&category=" + response;
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
function sub() {
	var makeMerchantUid = `${hours}` + `${minutes}` + `${seconds}` + `${milliseconds}`;
	if (confirm("구독 하시겠습니까?") == true) {
		IMP.request_pay({
			pg: "kakaopay.TCSUBSCRIP",
			pay_method: 'card', // 'card'만 지원
			merchant_uid: makeMerchantUid, // 상점에서 관리하는 주문 번호
			name: '프리미엄 구독',
			amount: 17000, // 결제창에 표시될 금액
			customer_uid: makeMerchantUid, // 필수 입력.
			buyer_email: mem.email,
			buyer_name: mem.name,
			buyer_tel: mem.phone,
			schedules: [15]
		}, function(rsp) {
			if (rsp.success) {
				var result = {
					"buyer_id": mem.id,
					"buyer_name": mem.name,
					"buyer_nickname": mem.nickname,
					"buyer_tel": mem.phone,
					"buyer_email": mem.email,
					"schedules": 15,
					"sub_uid": rsp.imp_uid,
					"sub_customer_uid": rsp.merchant_uid,
					"sub_method": "kakaopay",
					"sub_merchant_uid": rsp.customer_uid,
					"sub_name": '프리미엄 구독',
					"sub_amount": 17000,
					"sub_premium": "pro"
				}
				$.ajax({
					url: "complete",
					type: 'POST',
					'contentType': "application/json",
					data: JSON.stringify(result),
					beforeSend: function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					success: function(response) {
						if (response == 'pro') {
							alert('구독이 완료되었습니다.');
							document.location.href = 'subScribe'
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
	} else {
		return;
	}
}
