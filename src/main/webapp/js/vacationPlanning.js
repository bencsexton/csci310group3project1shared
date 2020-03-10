//initialize toggle
$(function(){
	$('#tempToggle').bootstrapToggle({
		on: '°F',
		off: '°C'
	});
})

const results = $('#results');
const inputs = ["tempRangeLow", "tempRangeHigh", "numResults", "location"];
function hideErrors(){
	results.empty();
	for(let input of inputs){
		$('#'+input).removeClass('error-input');
	}
}
function showErrors(errors){
	for(let error of errors){
		results.append($("<div class=\"error-text\">illegal value for input " + error + "</div>"));
		$('#' + error).addClass('error-input');
	}
}

const tableHeaders = ["City", "Country", "Avg. Min. Temp.", "Avg. Max. Temp.", "Distance", ""];
function createTableHeader(){
	const thead = $("<thead></thead>");
	for(let header of tableHeaders){
		thead.append("<th for=\"col\">"+  header +"</th>");
	}
	results.append(thead);
}

function makeFavButton(favorite, id){
	const a = $("<a></a>");
	const button = $('<button class="btn btn-outline-secondary fav-btn"></button>');
	if(favorite){
		a.attr('href', "/api/favorites/remove/" + id);
		button.text("Remove from favorites");
	}
	else{
		a.attr('href', "/api/favorites/add/" + id);
		button.text("Add to favorites");
	}
	a.append(button);
	return a;
}

const tableDatas = ["city", "country", "avgMinTemp", "avgMaxTemp", "distance"];
function displayResults(locations){
	if(locations.length == 0){
		results.append("No locations found");
	}
	else{
		createTableHeader();
		let row;
		let dark = false;
		for(let location of locations){
			row = $("<tr></tr>");
			if(dark){
				row.addClass('dark-row');
			}
			dark = !dark;
			for(let td of tableDatas){
				row.append("<td>" + location[td] + "</td>");
			}
			row.append(makeFavButton(location.favorite, location.city));
			results.append(row);
		}
	}
}

$('#search-form').on('submit', function(){
	event.preventDefault();
	hideErrors();
	$.get({
		// url: '/api/vacationPlanning/search',
		url: 'http://127.0.0.1:5000/api/vacationPlanning/search',
		data: $('#search-form').serialize(),
		dataType: 'JSON',
		success: (response) => {
			console.log(response);
			if(response.success){
				displayResults(response.results);
			}
			else{
				showErrors(response.errors);
			}
		}
	});
	// return false;
});

