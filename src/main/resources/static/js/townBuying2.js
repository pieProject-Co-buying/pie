// 페이지 로드 후 실행되는 함수
document.addEventListener('DOMContentLoaded', function() {
    // 카드 요소들을 가져옴
    var cards = document.querySelectorAll('.card.hj-radius-30');
    
    // 초기에는 6개까지만 보이도록 설정
    for (var i = 6; i < cards.length; i++) {
        cards[i].style.display = 'none';
    }
    
    // 더보기 버튼에 클릭 이벤트 추가
    var loadMoreButton = document.getElementById('loadMoreButton');
    loadMoreButton.addEventListener('click', function() {
        // 보이지 않는 카드를 6개씩 더 보이도록 설정
        var hiddenCards = 0;
        for (var i = 6; i < cards.length; i++) {
            if (cards[i].style.display === 'none') {
                cards[i].style.display = '';
            } else {
                hiddenCards++;
            }
        }
        // 더 이상 보일 카드가 없으면 더보기 버튼 숨김
        if (hiddenCards === 0) {
            loadMoreButton.style.display = 'none';
        }
    });
    
    // 초기에 카드가 6개 이하면 더보기 버튼 숨김
    if (cards.length <= 6) {
        loadMoreButton.style.display = 'none';
    }
});