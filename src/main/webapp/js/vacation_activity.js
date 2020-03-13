let rowArray; // holds all of the rows so they can be reordered

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
		thead.append("<th class='header' for=\"col\">"+  header +"</th>");
	}
	const dist = $('<th class="header" id="distance">Distance</th>');
	initDistanceSort(dist, tableDatas, resultsTable);
	thead.append(dist);
	thead.append("<th></th>");
	resultsTable.append(thead);
}

function makeAddToFavoritesBtn(button, id){
	button.text("Add to favorites");
	button.on('click', function(){
		makeRemoveFromFavoritesBtn(button, id);
		$.post({
			url: "/api/favorites/add/" + id
		});
	});
}

function makeRemoveFromFavoritesBtn(button, id){
	button.text("Remove from favorites");
	button.on('click', function(){
		makeAddToFavoritesBtn(button, id);
		$.post({
			url: "/api/favorites/remove/" + id
		});
	});
}

function makeFavButton(favorite, id){
	// const a = $("<a></a>");
	const button = $('<button class="btn btn-outline-secondary fav-btn"></button>');
	if(favorite){
		makeAddToFavoritesBtn(button, id);
	}
	else{
		makeRemoveFromFavoritesBtn(button, id);
	}
	return button;
}

// order = 'asc'|'desc'
function makeRowArray(locations, tableDatas, order){ 
	let row;
	rowArray = [];
	for(let location of locations){
		row = $("<tr class=\"result-row\"></tr>");
		let td;
		for(let attr of tableDatas){
			td = $("<td>" + location[attr] + "</td>");
			row.append(td);
		}
		row.append(makeFavButton(location.favorite, location.city));
		row.children()[distanceIdx].classList.add('distance');
		rowArray.push(row);
		// resultsTable.append(row);
	}
	return rowArray;
}

function displayRows(resultsTable, rowArray){
	$('tbody').empty(); // this is a hack

	// clear dark rows
	rowArray.forEach((row) => {
		row.removeClass('dark-row');
	});
	let dark = false;
	for(let row of rowArray){
		if(dark){
			row.addClass('dark-row');
		}
		dark = !dark;
		resultsTable.append(row);
	}
}

function displayResults(locations, resultsTable, tableHeaders, tableDatas){
	if(locations.length == 0){
		resultsTable.append("No locations found");
	}
	else{
		createTableHeader(resultsTable, tableHeaders);
		makeRowArray(locations, tableDatas);
		ascending = true;
		sortRows(rowArray);
		displayRows(resultsTable, rowArray);
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

let distanceIdx;
function setDistanceIdx(tableDatas){
	distanceIdx = tableDatas.indexOf('distance');
}

// function cmp(a, b) {
// 	const ta = parseInt(a[0].children[distanceIdx].innerHTML);
// 	const tb = parseInt(b[0].children[distanceIdx].innerHTML);
// 	let ret;
// 	ascending = !ascending;

// 	if(ascending){
// 		ret = ta - tb;
// 		// return parseInt(a[0].children[distanceIdx].innerHTML) - parseInt(b[0].children[distanceIdx].innerHTML);
// 	}
// 	else{
// 		ret = tb - ta;
// 		// return parseInt(b[0].children[distanceIdx].innerHTML) - parseInt(a[0].children[distanceIdx].innerHTML);
// 	}
// 	return ret;
// }
function asc(a, b){
	return parseInt(a[0].children[distanceIdx].innerHTML) - parseInt(b[0].children[distanceIdx].innerHTML);
}
function desc(a, b){
	return parseInt(b[0].children[distanceIdx].innerHTML) - parseInt(a[0].children[distanceIdx].innerHTML);
}

let ascending = true; // gets flipped the first time
function sortRows(rowArray){
	rowArray.reverse();
	let cmp = desc;
	if(ascending){
		cmp = asc;
	}
	rowArray.sort(cmp);
	ascending = !ascending;
}

function initDistanceSort(distanceHeader, tableDatas, resultsTable){
	distanceHeader.on('click', function(){
		sortRows(rowArray);
		displayRows(resultsTable, rowArray);
	});
}