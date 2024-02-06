var IMP = window.IMP;
IMP.init("imp80647284");

function requestPay1() {
    IMP.request_pay(
        {
            pg: "html5_inicis",
            pay_method: "card",
            merchant_uid: "57008833-33004",
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
function requestPay2() {
    IMP.request_pay(
        {
            pg: "kakaopay",
            pay_method: "card",
            merchant_uid: "57008833-33004",
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
function requestPay3() {
    IMP.request_pay(
        {
            pg: "tosspay",
            pay_method: "card",
            merchant_uid: "57008833-33004",
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
function requestPay4() {
    IMP.request_pay(
        {
            pg: "danal",
            pay_method: "card",
            merchant_uid: "57008833-33004",
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