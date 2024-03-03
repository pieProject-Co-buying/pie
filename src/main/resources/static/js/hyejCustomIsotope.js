$('#allCards').isotope({
	// options
	itemSelector: '.card',
	layoutMode: 'masonry',
	getSortData: {
		name: '.name', // text from querySelector
		category: '[data-category]', // value of attribute
		date: '[data-date]',
		like: '[data-like]',
		hit: '[data-hit]',
	},
	// fast transitions
	transitionDuration: '0.2s'
});

// bind sort button click
$('.sort-by-button-group').on('click', 'button', function() {
	var sortValue = $(this).attr('data-sort-value');
	$('#allCards').isotope({ sortBy: sortValue });
	if (sortValue == "popular") {
		$('#allCards').isotope({ sortBy: ['like', 'hit'] });
	}
});

/*// bind filter button click
$('.filters-button-group').on('click', 'button', function() {
	var filterValue = $(this).attr('data-filter');
	// use filterFn if matches value
	if (filterValue == "*") {
		$('#allCards').isotope({ filter: filterValue });

	} else {
		if(filterValue == 1){
			$('#allCards').isotope({ filter: '[data-end ="' + filterValue + '"]' });

		}else if(filterValue=='endSoon'){
			console.log($("[data-soon=true]").val());
			$('#allCards').isotope({ filter: '[data-end ="1"][data-soon=true]' });
		}else{
			$('#allCards').isotope({ filter: '[data-category ="' + filterValue + '"]' });

		}
			}
});*/
var filters = {};

$(document).on('click', '.filter-btn', function() {
	var $this = $(this);
	// get group key
	var $buttonGroup = $this.parents('.button-group');
	var filterGroup = $buttonGroup.attr('data-filter-group');
	// set filter for group
	filters[filterGroup] = $this.attr('data-filter');
	// combine filters
	var filterValue = concatValues(filters);
	$('#allCards').isotope({ filter: filterValue });
});

// flatten object by concatting values
function concatValues(obj) {
	var value = '';
	for (var prop in obj) {
		value += obj[prop];
	}
	return value;
}


// change is-checked class on buttons
$('.button-group').each(function(i, buttonGroup) {
	var $buttonGroup = $(buttonGroup);
	$buttonGroup.on('click', 'button', function() {
		$buttonGroup.find('.is-checked').removeClass('is-checked');
		$(this).addClass('is-checked');
	});
});
