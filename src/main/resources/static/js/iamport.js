var IMP = window.IMP;
IMP.init("imp80647284");

function kgPay() {
    IMP.request_pay(
        {
            pg: "html5_inicis",
            pay_method: "card",
            merchant_uid: "57008833-330041",
            name: "감자 132kg",
            amount: 1300,
            buyer_email: "Iamport@chai.finance",
            buyer_name: "수원 감자밭",
            buyer_tel: "010-1234-5678",
            buyer_addr: "수원시 팔달구",
            buyer_postcode: "123-456",
        },
        function (rsp) {
            // callback
            //rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
            /*IMP.request_pay(
                {/!* 결제 요청 객체 *!/},
                async requestPayResponse => {
                    const { success, error_msg } = requestPayResponse;
                    if (!success) {
                        alert(`결제에 실패하였습니다. 에러 내용: ${error_msg}`);
                        return;
                    }
                    // 이전 단계에서 구현한 결제정보 사후 검증 API 호출
                    const res = await axios({
                        url: "/payments/complete",
                        method: "post",
                        headers: { "Content-Type": "application/json" },
                        data: { imp_uid: "...", merchant_uid: "..." },
                    });
                    switch (res.status) {
                        case 1: "vbankIssued":
                            alert("!");
                            break;
                        case 2: "success":
                            // 결제 성공 시 로직
                            break;
                    }
                }
            );*/
        }
    );
}
function kakaoPay() {
    IMP.request_pay(
        {
            pg: "kakaopay",
            pay_method: "card",
            merchant_uid: "57008833-330049",
            name: "감자 132kg",
            amount: 1300,
            buyer_email: "Iamport@chai.finance",
            buyer_name: "수원 감자밭",
            buyer_tel: "010-1234-5678",
            buyer_addr: "수원시 팔달구",
            buyer_postcode: "123-456",
        },
        function (rsp) {
            if ( rsp.success ) {
                //[1] 서버단에서 결제정보 조회를 위해 jQuery ajax로 imp_uid 전달하기
                jQuery.ajax({
                    url: "/payments/complete", //cross-domain error가 발생하지 않도록 주의해주세요
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        imp_uid : rsp.imp_uid
                        //기타 필요한 데이터가 있으면 추가 전달
                    }
                }).done(function(data) {
                    //[2] 서버에서 REST API로 결제정보확인 및 서비스루틴이 정상적인 경우
                    if ( everythings_fine ) {
                        var msg = '결제가 완료되었습니다.';
                        msg += '\n고유ID : ' + rsp.imp_uid;
                        msg += '\n상점 거래ID : ' + rsp.merchant_uid;
                        msg += '\n결제 금액 : ' + rsp.paid_amount;
                        msg += '카드 승인번호 : ' + rsp.apply_num;

                        alert(msg);
                    } else {
                        //[3] 아직 제대로 결제가 되지 않았습니다.
                        //[4] 결제된 금액이 요청한 금액과 달라 결제를 자동취소처리하였습니다.
                    }
                });
            } else {
                var msg = '결제에 실패하였습니다.';
                msg += '에러내용 : ' + rsp.error_msg;

                alert(msg);
            }
        }
    );
}
function tossPay() {
    IMP.request_pay(
        {
            pg: "tosspay",
            pay_method: "card",
            merchant_uid: "57008833-330043",
            name: "감자 132kg",
            amount: 1300,
            buyer_email: "Iamport@chai.finance",
            buyer_name: "수원 감자밭",
            buyer_tel: "010-1234-5678",
            buyer_addr: "수원시 팔달구",
            buyer_postcode: "123-456",
        },
        function (rsp) {
            // callback
            //rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
            /*IMP.request_pay(
                {/!* 결제 요청 객체 *!/},
                async requestPayResponse => {
                    const { success, error_msg } = requestPayResponse;
                    if (!success) {
                        alert(`결제에 실패하였습니다. 에러 내용: ${error_msg}`);
                        return;
                    }
                    // 이전 단계에서 구현한 결제정보 사후 검증 API 호출
                    const res = await axios({
                        url: "/payments/complete",
                        method: "post",
                        headers: { "Content-Type": "application/json" },
                        data: { imp_uid: "...", merchant_uid: "..." },
                    });
                    switch (res.status) {
                        case 1: "vbankIssued":
                            alert("!");
                            break;
                        case 2: "success":
                            // 결제 성공 시 로직
                            break;
                    }
                }
            );*/
        }
    );
}
function paycoPay() {
    IMP.request_pay(
        {
            pg: "payco",
            pay_method: "card",
            merchant_uid: "57008833-330044",
            name: "감자 132kg",
            amount: 1300,
            buyer_email: "Iamport@chai.finance",
            buyer_name: "수원 감자밭",
            buyer_tel: "010-1234-5678",
            buyer_addr: "수원시 팔달구",
            buyer_postcode: "123-456",
        },
        function (rsp) {
            // callback
            //rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
            /*IMP.request_pay(
                {/!* 결제 요청 객체 *!/},
                async requestPayResponse => {
                    const { success, error_msg } = requestPayResponse;
                    if (!success) {
                        alert(`결제에 실패하였습니다. 에러 내용: ${error_msg}`);
                        return;
                    }
                    // 이전 단계에서 구현한 결제정보 사후 검증 API 호출
                    const res = await axios({
                        url: "/payments/complete",
                        method: "post",
                        headers: { "Content-Type": "application/json" },
                        data: { imp_uid: "...", merchant_uid: "..." },
                    });
                    switch (res.status) {
                        case 1: "vbankIssued":
                            alert("!");
                            break;
                        case 2: "success":
                            // 결제 성공 시 로직
                            break;
                    }
                }
            );*/
        }
    );
}