Feature: Hello
  Scenario: 1. This is the default page when a user visits the website
  	Given I am at root
    Then I should see the search page

  Scenario: 2. Displays a single textbox which has default focus, prompt text "Enter location (city or zip)"
    Given I am on the search page
    When I reload page
    Then I should see a single textbox
    And The textbox should have default focus
    And The textbox should have the prompt text Enter location (city or zip)

  Scenario: 2.a  Prompt text disappears when user starts typing
    Given I am on the search page
    When I start typing in textbox
    Then Prompt text should disappear

  Scenario: 3.a. Clicking Show  me the weather retrieves weather information for the location and displays it in the weather display area
    Given I am on the search page
    When There is legal input in textbox
    And I click 'submitButton'
    Then Weather data is displayed in weather display area

  Scenario: 3.b. When clicking Show  me the weather, an incorrect or illegal entry display “No weather data found.” in the weather display area
    Given I am on the search page
    When There is illegal input in textbox
    And I click the show me the weather
    Then Display "No weather data found" in weather display area

  Scenario: 4. Weather display area shows location information,date, corresponding weather graphic, temperature, description
    Given I am on the search page
    Then I should see 'label' 'currLocation'
    And I should see 'label' 'currDate'
    And I should see 'img' 'icon'
    And I should see 'label' 'currTemperature'
    And I should see 'label' 'currDescription'

  Scenario: 4.f. Mouse over the weather graphic animates the graphic
    Given I am on the search page
    When I mouse over weather graphic
    Then Graphic should be animated

  Scenario: 5. Page has a unit changer
    Given I am on the search page
    Then I should see 'input' 'unitChanger'

  Scenario: 6. Page has a unit changer
    Given I am on the search page
    And unit is F
    And temperature has value
    When I click 'unitSlider'
    Then Unit should change to C
	And Temperature should change correctly

  Scenario: 6. Page displays a navigation bar
    Given I am on the search page
    Then I should see navigation bar