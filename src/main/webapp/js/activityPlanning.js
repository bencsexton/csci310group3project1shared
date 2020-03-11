const resultsTable = $('#results');
const inputs = ["activity", "numResults", "location"];
const url = 'http://127.0.0.1:8080/api/activityPlanning/search';
const tableHeaders = ["City", "Country", "Current Temperature"];
const tableDatas = ["city", "country",  "currentTemp", "distance"];
const toggleSelector = $('#tempToggle');
const formSelector = $('#search-form');

const activityInput = $('#activity');
const activityUrl = 'http://127.0.0.1:8080/api/activityPlanning/activities'

const distanceHeader = $('#distance');


initializeToggle(toggleSelector);
initializeSearch(formSelector, url, resultsTable, inputs, tableHeaders, tableDatas);
setDistanceIdx(tableDatas);
// initDistanceSort(distanceHeader, tableDatas, resultsTable);

function initializeAutocomplete(response){
	$( function() {
		activityInput.autocomplete({
			source: response.activities
		});
	});
}

$.get({
	url: activityUrl,
	dataType: 'JSON',
	success: initializeAutocomplete
});
