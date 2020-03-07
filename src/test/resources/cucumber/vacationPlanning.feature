Feature: Vacation Planning
	Scenario: check that all inputs on the search form exist
		Given I am on the Vacation Planning page
		Then I should see VP text box 'tempRangeLow'
		Then I should see VP text box 'tempRangeHigh'
		Then I should see VP text box 'numResults'
		Then I should see VP text box 'location'
		Then I should see VP button with text 'Find My Vacation Spot'
		
	Scenario: empty input for all inputs should show error
		Given I am on the Vacation Planning page
		When I submit the form
		Then I should see a VP illegal value error for 'tempRangeLow'
		Then I should see a VP illegal value error for 'tempRangeHigh'
		Then I should see a VP illegal value error for 'numResults'
		Then I should see a VP illegal value error for 'location'