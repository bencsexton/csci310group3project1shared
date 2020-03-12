var tempUnit = "F";

$(document).ready(function() {
    loadFavorites();
});

function loadFavorites() {
    const endpoint = "http://127.0.0.1:5000/api/favorites/list";
    $.getJSON(endpoint, function(results) {
        console.log(results);
        const favorites = results.favorites;
        if (favorites.length == 0) {
            $('.list-group-no-items').show();
            $('.list-buttons').hide();
            $('.onoffswitch').hide();
        }
        else {
            displayFavorites(favorites);
        }
    });
}

function displayFavorites(favorites) {
    $('.list-group-no-items').hide();
    $('.list-buttons').show();
    $('.onoffswitch').show();

    const favoritesDiv = $('.list-group');
    for (let location of favorites) {
        let p1 = $('<p>').text(location.city);
        let p2 = $('<p>').text(location.country);
        let div1 = $('<div>').addClass('list-item-city').append(p1);
        let div2 = $('<div>').addClass('list-item-country').append(p2);    
        let a = $('<a>')
            .addClass('list-group-item')
            .addClass('list-group-item-action')
            .attr("data-location-id", location.id)
//            .attr('href', '#')
            .append(div1, div2);
        favoritesDiv.append(a);
    }
    
    handleCitySelection();
}

function handleCitySelection() {
    $('a').click(function() {        
        $('a.list-group-item.active').removeClass('active');
        $(this).addClass('active');   
        
        const locationId = $(this).data('location-id');
        getWeatherData(locationId)
    });    
}

function getWeatherData(locationId) {
    const endpoint = 'http://127.0.0.1:5000/api/weather-analysis/data?location-id=' + locationId;    
    $.getJSON(endpoint, function(results) {
        console.log(results);
        displayWeatherData(results);        
    });
}

function displayWeatherData(results) {
    displayCurrentWeather(results.current);
    displayForecast(results.forecast);
    displayHistoricData(results.historic);
    displayCityImage(results.image);    
}

function displayCurrentWeather(current) {
    $('#current-city-val').text(current.city + ', ' + current.country);
    $('#current-splitter-val').text('|');    
    $('#current-date-val').text(current.date);
    //TODO: icon
    $('#current-icon-val').addClass('wi-night-sleet');
    $('#current-temp-val').text(current.temp + '\xB0' + tempUnit);
    $('#current-desc-val').text(current.desc);
}

function displayForecast(forecast) {
    if ($('.forecast').length) {
        $('.forecast').remove();
    }
    let parent = $('<div>')
            .addClass('forecast')            
            .appendTo('#weather-section');
    for (let f of forecast) {
        let p1 = $('<p>').text(f.date);
        let p2 = $('<p>').text(f.max_temp + '\xB0' + tempUnit);
        let p3 = $('<p>').text(f.min_temp + '\xB0' + tempUnit);
        // TODO: icon
        let i1 = $('<i>').addClass('wi').addClass('wi-night-sleet');
        
        let div1 = $('<div>').addClass('forecast-date').append(p1);
        let div2 = $('<div>').addClass('forecast-icon').append(i1);
        let div3 = $('<div>').addClass('forecast-high').append(p2);        
        let div4 = $('<div>').addClass('forecast-low').append(p3);
        
        let div = $('<div>')
            .addClass('forecast-col')
            .append(div1, div2, div3, div4)
            .appendTo(parent);
    }    
}

function displayHistoricData(historic) {
    if ($('.historic').length) {
        $('.historic').remove();
    }
    let parent = $('<div>')
                .addClass('historic')
                .appendTo('#weather-section');
    $('<div>').addClass('col-12')
            .attr('id', 'historic-col')
            .appendTo('.historic');
    $('<div>').addClass('card')
            .appendTo('#historic-col');
    $('<div>').addClass('card-body')
            .appendTo('.card');
    $('<canvas>').attr('id', 'chLine')
            .appendTo('.card-body');
    drawChart(historic);    
}

function drawChart(historic) {
    const chLine = $("#chLine");
    if (chLine) {
        new Chart(chLine, {
            type: 'line',
            data: {
                labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
                datasets: [{
                data: historic.highs,
                backgroundColor: 'transparent',
                borderColor: '#f57b51',
                borderWidth: 4,
                pointBackgroundColor: '#f57b51'
                },
                {
                data: historic.lows,
                backgroundColor: 'transparent',
                borderColor: '#98d6ea',
                borderWidth: 4,
                pointBackgroundColor: '#98d6ea'
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: false
                        },
                        scaleLabel: {
                            display: true,
                            labelString: 'Temperature',
                            fontStyle: 'bold'
                        }
                    }],
                    xAxes: [{
                        scaleLabel: {
                            display: true,
                            labelString: 'Month',
                            fontStyle: 'bold'
                        }
                    }]
                },
                legend: {
                    display: false
                }

            }
        });
    }
}

function displayCityImage(image) {    
    if ($('#photo-section').length) {
        $('#photo-section').empty();
    }    
    $('<img>')
        .attr('id', 'city-photo')
        .attr('src', image)
        .appendTo($('#photo-section'));
}
