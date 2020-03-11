const resultsTable = $('#results');
const inputs = ["tempRangeLow", "tempRangeHigh", "numResults", "location"];
const url = 'http://127.0.0.1:8080/api/vacationPlanning/search';
const tableHeaders = ["City", "Country", "Avg. Min. Temp.", "Avg. Max. Temp.", "Distance", ""];
const tableDatas = ["city", "country", "avgMinTemp", "avgMaxTemp", "distance"];
const toggleSelector = $('#tempToggle');
const formSelector = $('#search-form');


initializeToggle(toggleSelector);
initializeSearch(formSelector, url, resultsTable, inputs, tableHeaders, tableDatas);