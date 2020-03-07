Feature: Vacation Planning
	Scenario: check that all inputs on the search form exist
		Given I am on the Vacation Planning page
		Then I should see VP text box 'tempRangeLow'
		Then I should see VP text box 'tempRangeHigh'
		Then I should see VP text box 'numResults'
		Then I should see VP text box 'location'
		Then I should see VP button with text 'Find My Vacation Spot'