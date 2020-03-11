const resultsTable = $('#results');
const inputs = ["activity", "numResults", "location"];
const url = 'http://127.0.0.1:8080/api/activityPlanning/search';
const tableHeaders = ["City", "Country", "Current Temperature", "Distance", ""];
const tableDatas = ["city", "country", "avgMinTemp", "avgMaxTemp", "distance"];
const toggleSelector = $('#tempToggle');
const formSelector = $('#search-form');


initializeToggle(toggleSelector);
initializeSearch(formSelector, url, resultsTable, inputs, tableHeaders, tableDatas);