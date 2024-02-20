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
	alert("kg 이니시스로 결제 하시겠습니까 ?");
	var DataTitle = document.querySelector('[data-title]');
	var DataPrice = document.querySelector('[data-price]');
	var Title= DataTitle.getAttribute('data-title');
	var Price = parseFloat(DataPrice.getAttribute('data-price'));
    IMP.request_pay(
        {
            pg: "html5_inicis",
            pay_method: "card",
            merchant_uid: "IMP" + makeMerchantUid, // 결제 고유번호
            name: Title,
            amount: Price,
            buyer_email: "Iamport@chai.finance",
            buyer_name: "수원 감자밭",
            buyer_tel: "010-1234-5678",
            buyer_addr: "수원시 팔달구",
            buyer_postcode: "123-456",
        },
        function(rsp) {
			if (rsp.success) {
				$.ajax({
					url: "/payments/complete",
					type: 'POST',
					dataType: 'json',
					data: {
						imp_uid: rsp.imp_uid
						//기타 필요한 데이터가 있으면 추가 전달
					}
				});
				alert("결제에 성공하였습니다");
				document.location.href = "shareServiceFinish?sh_numID="+numID;
			} else if (!rsp.success) {
				var msg = '결제에 실패하였습니다.';
				msg += '에러내용 : ' + rsp.error_msg;
				alert(msg);
			}
		}
    );
}
function kakaoPay() {
	alert("카카오페이로 결제 하시겠습니까 ?");
	var DataTitle = document.querySelector('[data-title]');
	var DataPrice = document.querySelector('[data-pricePer]');
	var DataNumID = document.querySelector('[data-num]');
	//var DataPersonnelNow = document.querySelector('[data-personnelNow]');
	var Title= DataTitle.getAttribute('data-title');
	var Price = parseFloat(DataPrice.getAttribute('data-pricePer'));
	var numID = parseFloat(DataNumID.getAttribute('data-num'));
	//var personnelNow = parseFloat(DataPersonnelNow.getAttribute('data-numID'));
	//imp_uid = extract_POST_value_from_url('imp_uid') //post ajax request로부터 imp_uid확인

	 IMP.request_pay(
        {
            pg: "kakaopay.TC0ONETIME",
            pay_method: "kakaopay",
            merchant_uid: makeMerchantUid,
            name: Title,
            amount: Price,
            buyer_email: "Iamport@chai.finance",
            buyer_name: "수원 감자밭",
            buyer_tel: "010-1234-5678",
			buyer_addr: "수원시 팔달구",
			buyer_postcode: "123-456",
			product_count: 2
		},function(rsp) {
			if (rsp.success) {
				var result={
					"imp_uid": rsp.imp_uid,
					"payMethod": rsp.pay_method,
					"payMerchant_uid": makeMerchantUid,
					"payName": Title,
					"payAmount": Price
				}
				$.ajax({
					url: "payCheck",
					type: 'POST',
					dataType: 'application/json',
					data:JSON.stringify(result)
					
				});
				alert("결제에 성공하였습니다");
				document.location.href = "shareServiceFinish?sh_numID="+numID;
			} else if (!rsp.success) {
				var msg = '결제에 실패하였습니다.';
				msg += '에러내용 : ' + rsp.error_msg;
				alert(msg);
			}
		}
	);
}
function tossPay() {
	var DataTitle = document.querySelector('[data-title]');
	var DataPrice = document.querySelector('[data-price]');
	var Title= DataTitle.getAttribute('data-title');
	var Price = parseFloat(DataPrice.getAttribute('data-price'));
    IMP.request_pay(
        {
            pg: "tosspay",
            pay_method: "card",
            merchant_uid: "IMP" + makeMerchantUid,
            name: Title,
            amount: Price,
            buyer_email: "Iamport@chai.finance",
			buyer_name: "수원 감자밭",
			buyer_tel: "010-1234-5678",
			buyer_addr: "수원시 팔달구",
			buyer_postcode: "123-456",
		},
		function(rsp) {
			if (rsp.success) {
				$.ajax({
					url: "shareServiceFinish",
					type: 'POST',
					dataType: 'json',
					data: {
						imp_uid: rsp.imp_uid
						//기타 필요한 데이터가 있으면 추가 전달
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
					url: "shareServiceFinish",
					type: 'POST',
					dataType: 'json',
					data: {
						imp_uid: rsp.imp_uid
						//기타 필요한 데이터가 있으면 추가 전달
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