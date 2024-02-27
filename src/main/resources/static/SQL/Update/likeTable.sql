var IMP = window.IMP;
IMP.init("imp80647284");
/*******************��ǰ ���̵�*******************/
var today = new Date();
var hours = today.getHours(); // ��
var minutes = today.getMinutes();  // ��
var seconds = today.getSeconds();  // ��
var milliseconds = today.getMilliseconds();
var makeMerchantUid = `${hours}` + `${minutes}` + `${seconds}` + `${milliseconds}`;

function kgPay() {
	alert("kg �̴Ͻý��� ���� �Ͻðڽ��ϱ� ?");
	var DataTitle = document.querySelector('[data-title]');
	var DataPrice = document.querySelector('[data-price]');
	var Title= DataTitle.getAttribute('data-title');
	var Price = parseFloat(DataPrice.getAttribute('data-price'));
    IMP.request_pay(
        {
            pg: "html5_inicis",
            pay_method: "card",
            merchant_uid: "IMP" + makeMerchantUid, // ���� ������ȣ
            name: Title,
            amount: Price,
            buyer_email: "Iamport@chai.finance",
            buyer_name: "���� ���ڹ�",
            buyer_tel: "010-1234-5678",
            buyer_addr: "������ �ȴޱ�",
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
						//��Ÿ �ʿ��� �����Ͱ� ������ �߰� ����
					}
				});
				alert("������ �����Ͽ����ϴ�");
				document.location.href = "shareServiceFinish?sh_numID="+numID;
			} else if (!rsp.success) {
				var msg = '������ �����Ͽ����ϴ�.';
				msg += '�������� : ' + rsp.error_msg;
				alert(msg);
			}
		}
    );
}
function kakaoPay() {
	alert("īī�����̷� ���� �Ͻðڽ��ϱ� ?");
	var DataTitle = document.querySelector('[data-title]');
	var DataPrice = document.querySelector('[data-pricePer]');
	var DataNumID = document.querySelector('[data-num]');
	//var DataPersonnelNow = document.querySelector('[data-personnelNow]');
	var Title= DataTitle.getAttribute('data-title');
	var Price = parseFloat(DataPrice.getAttribute('data-pricePer'));
	var numID = parseFloat(DataNumID.getAttribute('data-num'));
	//var personnelNow = parseFloat(DataPersonnelNow.getAttribute('data-numID'));
	//imp_uid = extract_POST_value_from_url('imp_uid') //post ajax request�κ��� imp_uidȮ��

	 IMP.request_pay(
        {
            pg: "kakaopay.TC0ONETIME",
            pay_method: "kakaopay",
            merchant_uid: makeMerchantUid,
            name: Title,
            amount: Price,
            buyer_email: "Iamport@chai.finance",
            buyer_name: "���� ���ڹ�",
            buyer_tel: "010-1234-5678",
			buyer_addr: "������ �ȴޱ�",
			buyer_postcode: "123-456",
			product_count: 2
		},function(rsp) {
			if (rsp.success) {
				$.ajax({
					url: "shareServiceFinish",
					type: 'POST',
					dataType: 'json',
					data: {
						imp_uid: rsp.imp_uid,
						
						//��Ÿ �ʿ��� �����Ͱ� ������ �߰� ����
					}
				});
				alert("������ �����Ͽ����ϴ�");
				document.location.href = "shareServiceFinish?sh_numID	="+numID;
			} else if (!rsp.success || product_count==0) {
				var msg = '������ �����Ͽ����ϴ�.';
				msg += '�������� : ' + rsp.error_msg;
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
			buyer_name: "���� ���ڹ�",
			buyer_tel: "010-1234-5678",
			buyer_addr: "������ �ȴޱ�",
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
						//��Ÿ �ʿ��� �����Ͱ� ������ �߰� ����
					}
				});
				alert("������ �����Ͽ����ϴ�");
				document.location.href = "shareServiceFinish";
			} else if (!rsp.success) {
				var msg = '������ �����Ͽ����ϴ�.';
				msg += '�������� : ' + rsp.error_msg;
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
            name: "���� 132kg",
            amount: 1300,
            buyer_email: "Iamport@chai.finance",
            buyer_name: "���� ���ڹ�",
            buyer_tel: "010-1234-5678",
            buyer_addr: "������ �ȴޱ�",
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
						//��Ÿ �ʿ��� �����Ͱ� ������ �߰� ����
					}
				});
				alert("������ �����Ͽ����ϴ�");
				document.location.href = "shareServiceFinish";
			} else if (!rsp.success) {
				var msg = '������ �����Ͽ����ϴ�.';
				msg += '�������� : ' + rsp.error_msg;
				alert(msg);
			}
		}
    );
}