from flask import Flask, request, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app)


@app.route('/api/favorites/list')
def get_favorites():
    sample_data = [
        {
            "id": 1001,
            "city": "Bangkok",
            "country": "Thailand"
        },
        {
            "id": 1002,
            "city": "Cape Town",
            "country": "South Africa"
        },
        {
            "id": 1003,
            "city": "Los Angeles",
            "country": "California"
        },
        {
            "id": 1004,
            "city": "Seoul",
            "country": "South Korea"
        },
        {
            "id": 1005,
            "city": "Tokyo",
            "country": "Japan"
        }
    ]
    response = {'favorites': sample_data}
    return jsonify(response)


@app.route('/api/weather-analysis/data')
def get_data():
    location_id = request.args.get('location-id')
    response = {}
    if location_id == "1003":
        response = {
            "errorMsg": "",
            "current": {
                "city": "Los Angeles",
                "country": "US",
                "date": "Mar 11, 2020",
                "temp": 64,
                "desc": "Partly cloudy",
                "icon": "partly-cloudy-day"
            },
            "forecast": [
                {
                    "date": "Mar 11",
                    "max_temp": 64,
                    "min_temp": 48,
                    "icon": "PARTLY_CLOUDY_DAY"
                },
                {
                    "date": "Mar 12",
                    "max_temp": 64,
                    "min_temp": 48,
                    "icon": "PARTLY_CLOUDY_DAY"
                },
                {
                    "date": "Mar 13",
                    "max_temp": 64,
                    "min_temp": 48,
                    "icon": "PARTLY_CLOUDY_DAY"
                },
                {
                    "date": "Mar 14",
                    "max_temp": 64,
                    "min_temp": 48,
                    "icon": "PARTLY_CLOUDY_DAY"
                },
                {
                    "date": "Mar 15",
                    "max_temp": 64,
                    "min_temp": 48,
                    "icon": "PARTLY_CLOUDY_DAY"
                },
            ],
            "historic": {
                "highs": [68.1, 69.6, 69.8, 73.1, 74.5, 79.5, 83.8, 84.8, 83.3, 79, 73.2, 68.7],
                "lows": [48.5, 50.3, 51.6, 54.4, 57.9, 61.4, 64.6, 65.6, 64.6, 59.9, 52.6, 48.3]
            },
            "image": "https://upload.wikimedia.org/wikipedia/commons/thumb/3/32/20190616154621%21Echo_Park_Lake_with_Downtown_Los_Angeles_Skyline.jpg/2560px-20190616154621%21Echo_Park_Lake_with_Downtown_Los_Angeles_Skyline.jpg"
        }
    return jsonify(response)


if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5000, debug=True)
