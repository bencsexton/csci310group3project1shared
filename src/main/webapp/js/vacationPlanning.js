const resultsTable = $('#results');
const inputs = ["tempRangeLow", "tempRangeHigh", "numResults", "location"];
const url = 'http://localhost:7890/api/vacationPlanning/search';
const tableHeaders = ["City", "Country", "Avg. Min. Temp.", "Avg. Max. Temp.", "Distance", ""];
const tableDatas = ["city", "country", "avgMinTemp", "avgMaxTemp", "distance"];
const toggleSelector = $('#tempToggle');
const formSelector = $('#search-form');


initializeToggle(toggleSelector);
initializeSearch(formSelector, url, resultsTable, inputs, tableHeaders, tableDatas);