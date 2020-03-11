//initialize toggle
function initializeToggle(toggleSelector){
	$(function(){
		toggleSelector.bootstrapToggle({
			on: '°F',
			off: '°C'
		});
	});
}

function hideErrors(resultsTable, inputs){
	resultsTable.empty();
	for(let input of inputs){
		$('#'+input).removeClass('error-input');
	}
}
function showErrors(errors, resultsTable){
	for(let error of errors){
		resultsTable.append($("<div class=\"error-text\">illegal value for input " + error + "</div>"));
		$('#' + error).addClass('error-input');
	}
}

function createTableHeader(resultsTable, tableHeaders){
	const thead = $("<thead></thead>");
	for(let header of tableHeaders){
		thead.append("<th for=\"col\">"+  header +"</th>");
	}
	resultsTable.append(thead);
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

function displayResults(locations, resultsTable, tableHeaders, tableDatas){
	if(locations.length == 0){
		resultsTable.append("No locations found");
	}
	else{
		createTableHeader(resultsTable, tableHeaders);
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
			resultsTable.append(row);
		}
	}
}

// $('#search-form').on('submit', function(){
// 	event.preventDefault();
function initializeSearch(formSelector, url, resultsTable, inputs, tableHeaders, tableDatas){
	formSelector.on('submit', function(){
		event.preventDefault();
		hideErrors(resultsTable, inputs);
		$.get({
			// url: '/api/vacationPlanning/search',
			url: url,
			data: $('#search-form').serialize(),
			dataType: 'JSON',
			success: (response) => {
				console.log(response);
				if(response.success){
					displayResults(response.results, resultsTable, tableHeaders, tableDatas);
				}
				else{
					showErrors(response.errors, resultsTable);
				}
			}
		});
	});
	// return false;
}

