Feature: Activity Planning
 	Scenario: check that all inputs on the search form exist
 		Given I am on the Activity Planning page
 		Then I should see a text box 'activity'
 		Then I should see a text box 'numResults'
 		Then I should see a text box 'location'
 		Then I should see a button with text 'Find My Activity Spot'
 		
 	Scenario: empty input for all inputs should show errors for all inputs
 		Given I am on the Activity Planning page
 		When I submit the form
 		Then I should see an illegal value error for 'activity'
 		And I should see an illegal value error for 'numResults'
 		And I should see an illegal value error for 'location'

 	Scenario: non-integer input to Num Results should show error
 		Given I am on the Activity Planning page
 		When I enter 'not a number' into input 'numResults'
 		And I submit the form
 		Then I should see an illegal value error for 'numResults'
 		
 	Scenario: positive integer input to Num Results should not show error
 		Given I am on the Activity Planning page
 		When I enter '12' into input 'numResults'
 		And I submit the form
 		Then I should not see an illegal error value for 'numResults'

 	Scenario: run a valid search and confirm table has proper headers and data
 		Given I am on the Activity Planning page
 		When I enter 'skiing' into input 'activity'
 		And I enter '5' into input 'numResults'
 		And I enter 'Los Angeles' into input 'location'
 		And I submit the form
 		Then I should see table header 'City'
 		And I should see table header 'Country'
 		And I should see table header 'Current temp'
 		And I should see table header 'Distance'
		
 	Scenario: run a valid search and toggle the distance sorting
 		Given I am on the Activity Planning page
 		When I enter 'skiing' into input 'activity'
 		And I enter '5' into input 'numResults'
 		And I enter 'Los Angeles' into input 'location'
 		And I submit the form		
 		Then the distances should be in 'ascending' order
 		When I click the distance header
 		Then the distances should be in 'descending' order
 		When I click the distance header
 		Then the distances should be in 'ascending' order
 		When I click the distance header
 		Then the distances should be in 'descending' order

 	Scenario: add and remove cities from favorites
 		Given I am on the Activity Planning page
 		When I enter 'skiing' into input 'activity'
 		And I enter '5' into input 'numResults'
 		And I enter 'Los Angeles' into input 'location'
 		And I submit the form
		
 		Then the first location favorite changer should say 'Add to favorites'
		
 		When I click the favorites changer of the first location
 		Then the first location favorite changer should say 'Remove from favorites'
		
 		When I click the favorites changer of the first location
 		Then the first location favorite changer should say 'Add to favorites'
	
	Scenario: type partial activity and see autocomplete options
  	Given I am on the Activity Planning page
  	When I enter 's' into input 'activity'
  	Then I should see activity autocomplete option 'skiing'
  	And I should see activity autocomplete option 'snowboarding'
  	And I should see activity autocomplete option 'surfing'
  	And I should see activity autocomplete option 'soccer'
  	When I click the activity autocomplete option 'skiing'
  	Then the activity input should say 'skiing'
  	
  #has a navigation bar

  #displays "No locations found"
  
  
		
		
		
		
		
		
		