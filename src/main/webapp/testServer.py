from flask import Flask, request, jsonify
from flask_cors import CORS
app = Flask(__name__)
CORS(app)

@app.route('/')
def hello():
    return "Hello World!"

sampleVacationData = [
	{
		"city": "Las Vegas",
		"country": "USA",
		"avgMaxTemp": 105,
		"avgMinTemp": 23,
		"distance": 100,
		"favorite": True
	},
	{
		"city": "London",
		"country": "England",
		"avgMaxTemp": 67,
		"avgMinTemp": 43,
		"distance": 10000,
		"favorite": False
	},
	{
		"city": "Tokyo",
		"country": "Japan",
		"avgMaxTemp": 100,
		"avgMinTemp": 35,
		"distance": 20000,
		"favorite": False
	}
]

@app.route('/api/vacationPlanning/search/')
def vacaSearch():
	response = {}
	errors = {field[0] for field in request.args.items() if not field[1]}
	# errors = set()
	r = request.args
	if r['tempRangeLow'] > r['tempRangeHigh'] and r['tempRangeLow'] and r['tempRangeHigh']:
		errors.add('tempRangeLow')
		errors.add('tempRangeHigh')

	if not r['numResults'].isdigit():
		errors.add('numResults')

	if errors:
		response['success'] = False
		response['errors'] = list(errors)
	else:
		# get actual data
		response['success'] = True
		response['results'] = sampleVacationData

	return jsonify(response)

sampleActivityData = [
	{
		"city": "Las Vegas",
		"country": "USA",
		"currentTemp": 105,
		"distance": 100,
		"favorite": True
	},
	{
		"city": "London",
		"country": "England",
		"currentTemp": 43,
		"distance": 10000,
		"favorite": False
	},
	{
		"city": "Tokyo",
		"country": "Japan",
		"currentTemp": 35,
		"distance": 20000,
		"favorite": False
	},
	{
		"city": "Philadelphia",
		"country": "USA",
		"currentTemp": 65,
		"distance": 3000,
		"favorite": True
	},
]

@app.route('/api/activityPlanning/search')
def activitySearch():
	response = {}
	errors = {field[0] for field in request.args.items() if not field[1]}
	# errors = set()
	r = request.args

	if r['location'] == 'nowhere':
		return 'todo'

	if errors:
		response['success'] = False
		response['errors'] = list(errors)
	else:
		# get actual data
		response['success'] = True
		response['results'] = sampleActivityData

	return jsonify(response)



temperatures = ['hot:', 'medium:', 'cold:']
with open('activities.csv', 'r') as ifile:
		# hot, medium, cold
		activities = [line for line in ifile if line not in temperatures]

@app.route('/api/activityPlanning/activities')
def getActivities():
	
	response = {'activities': activities}
	return jsonify(response)

if __name__ == '__main__':
	app.run(debug=True, port=8080)