Feature: Vacation Planning
#	Scenario: check that all inputs on the search form exist
#		Given I am on the Vacation Planning page
#		Then I should see a text box 'tempRangeLow'
#		Then I should see a text box 'tempRangeHigh'
#		Then I should see a text box 'numResults'
#		Then I should see a text box 'location'
#		Then I should see a button with text 'Find My Vacation Spot'
#		
#	Scenario: empty input for all inputs should show error
#		Given I am on the Vacation Planning page
#		When I submit the form
#		Then I should see an illegal value error for 'tempRangeLow'
#		And I should see an illegal value error for 'tempRangeHigh'
#		And I should see an illegal value error for 'numResults'
#		And I should see an illegal value error for 'location'
		
#	Scenario: low temperature higher than high temperature in range
#		Given I am on the Vacation Planning page
#		When I enter '55' into input 'tempRangeLow'
#		And I enter '32' into input 'tempRangeHigh'
#		And I submit the form
#		Then I should see an illegal value error for 'tempRangeLow'
#		And I should see an illegal value error for 'tempRangeHigh'

#	Scenario: (valid) low temperature lower than high temperature should not produce error
#		Given I am on the Vacation Planning page
#		When I enter '40' into input 'tempRangeLow'
#		And I enter '80' into input 'tempRangeHigh'
#		And I submit the form
#		Then I should not see an illegal error value for 'tempRangeLow'
#		And I should not see an illegal error value for 'tempRangeLow'

#	Scenario: non-integer input to Num Results should show error
#		Given I am on the Vacation Planning page
#		When I enter 'not a number' into input 'numResults'
#		And I submit the form
#		Then I should see an illegal value error for 'numResults'
#		
#	Scenario: positive integer input to Num Results should not show error
#		Given I am on the Vacation Planning page
#		When I enter '12' into input 'numResults'
#		And I submit the form
#		Then I should not see an illegal error value for 'numResults'
		
		
		
		
		
		
		
		
		