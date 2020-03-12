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

#	Scenario: run a valid search and confirm table has proper headers and data
#		Given I am on the Vacation Planning page
#		When I enter '12' into input 'tempRangeLow'
#		And I enter '80' into input 'tempRangeHigh'
#		And I enter '5' into input 'numResults'
#		And I enter 'Los Angeles' into input 'location'
#		And I submit the form
#		Then I should see table header 'City'
#		And I should see table header 'Country'
#		And I should see table header 'Avg. Min. Temp.'
#		And I should see table header 'Avg. Max. Temp.'
#		And I should see table header 'Distance'
		
#	Scenario: run a valid search and toggle the distance sorting
#		Given I am on the Vacation Planning page
#		When I enter '12' into input 'tempRangeLow'
#		And I enter '80' into input 'tempRangeHigh'
#		And I enter '5' into input 'numResults'
#		And I enter 'Los Angeles' into input 'location'
#		And I submit the form		
#		Then the distances should be in 'ascending' order
#		When I click the distance header
#		Then the distances should be in 'descending' order
#		When I click the distance header
#		Then the distances should be in 'ascending' order
#		When I click the distance header
#		Then the distances should be in 'descending' order

#	Scenario: add and remove cities from favorites
#		Given I am on the Vacation Planning page
#		When I enter '12' into input 'tempRangeLow'
#		And I enter '80' into input 'tempRangeHigh'
#		And I enter '5' into input 'numResults'
#		And I enter 'Los Angeles' into input 'location'
#		And I submit the form
		
#		Then the first location favorite changer should say 'Add to favorites'
		
#		When I click the favorites changer of the first location
#		Then the first location favorite changer should say 'Remove from favorites'
		
#		When I click the favorites changer of the first location
#		Then the first location favorite changer should say 'Add to favorites'

# has a navigation bar

# displays "No locations found"
		
		
		
		
		
		
		