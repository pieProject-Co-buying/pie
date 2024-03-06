function checkPassword(element, correctPassword) {
   
   

    
    var inputPassword = prompt("비밀번호를 입력해주세요:");
    if (inputPassword === null) {
        return; // 취소 버튼을 누를시
    }
    
    
    var bus_apply_num = element.querySelector('.bus_apply_num').textContent;
    if (inputPassword === correctPassword) {
        window.location.href = '/readApplyBoard?bus_apply_num=' + bus_apply_num;
    } else {
        alert("비밀번호가 틀렸습니다.");
    }
    
    
}