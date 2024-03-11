// 드롭다운 메뉴로 셀렉트하기
let dropdownMenu = $(".dropdown-menu");
let category = $(".dropdown-menu a")
let input = $("#h-input");
let selectCategory = $(".dropdown button")

	let c = selectCategory.attr("data-category");
	let ctext;

	if (c == 'food') ctext = '식품';
	else if (c == 'baby') ctext = '육아';
	else if (c == 'beautyAndFashion') ctext = '뷰티/패션';
	else if (c == 'pet') ctext = '반려동물';
	else if (c == 'life') ctext = '생활';
	else if (c == 'etc') ctext = '기타';
	else if (c == 'OTT') ctext = 'OTT';
	else if (c == 'game') ctext = '게임';
	else if (c == 'bookAndMusic') ctext = '도서/음악';
	else if (c == '') ctext = '전체';


	selectCategory.text(ctext);
	input.val(c);

	category.click(function() {
		$(this).parents(".dropdown").find("button").text($(this).text());
		input.val($(this).attr("data-category"))
	})