  /* 기업신청 비밀번호 체크 */
  
        function checkPassword(element, correctPassword) {
            var inputPassword = prompt("비밀번호를 입력해주세요:");
            var bus_apply_num = element.parentNode.parentNode.getElementsByTagName('th')[0].textContent;
            if (inputPassword === correctPassword) {
                window.location.href = '/readApplyBoard?bus_apply_num=' + bus_apply_num;
            } else {
                alert("비밀번호가 틀렸습니다.");
            }
        }