//initialize toggle
$(function(){
	$('#tempToggle').bootstrapToggle({
		on: '°F',
		off: '°C'
	});
})

const results = $('#results');
function showErrors(errors){
	results.empty();
	for(let error of errors){
		results.append($("<div class=\"error-text\">illegal value for input " + error + "</div>"))
	}
}

$('#search-form').on('submit', function(){
	event.preventDefault();
	$.get({
		// url: '/api/vacationPlanning/search',
		url: 'http://127.0.0.1:5000/api/vacationPlanning/search',
		data: $('#search-form').serialize(),
		dataType: 'JSON',
		success: (response) => {
			console.log(response);
			if(response.success){
				alert('success!');
			}
			else{
				showErrors(response.errors);
			}
		}
	});
	// return false;
});

