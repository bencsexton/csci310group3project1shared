from flask import Flask, request, jsonify
from flask_cors import CORS
app = Flask(__name__)
CORS(app)

@app.route('/')
def hello():
    return "Hello World!"

@app.route('/api/vacationPlanning/search/')
def vacaSearch():
	response = {}
	errors = {field[0] for field in request.args.items() if not field[1]}
	# errors = set()
	r = request.args
	if r['tempRangeLow'] > r['tempRangeHigh']:
		errors.add('tempRangeLow')
		errors.add('tempRangeHigh')

	if not r['numResults'].isdigit():
		errors.add('numResults')

	if errors:
		response['success'] = False
		response['errors'] = list(errors)

	return jsonify(response)

if __name__ == '__main__':
	app.run(debug=True)