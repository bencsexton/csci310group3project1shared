Feature: Vacation Planning
	Scenario: type partial activity and see autocomplete options
  	Given I am on the Activity Planning page
  	When I click the input 'activity'
  	When I enter 's' into input 'activity'
  	Then I should see activity autocomplete option 'skiing'
  	And I should see activity autocomplete option 'snowboarding'
  	And I should see activity autocomplete option 'surfing'
  	And I should see activity autocomplete option 'soccer'
  	When I click the activity autocomplete option 'skiing'
  	Then the activity input should say 'skiing'
 