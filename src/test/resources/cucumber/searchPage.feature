Feature: Hello
#  Scenario: 1. This is the default page when a user visits the website
#    When I enter the website link
#    Then I should see the search page

  Scenario: 2. Displays a single textbox which has default focus, prompt text "Enter location (city or zip)"
    Given I am on the search page
    When I reload page
    Then I should see a single textbox
#    And The textbox should have default focus
#    And The textbox should have the prompt text "Enter location (city or zip)"
#
#  Scenario: 2.a  Prompt text disappears when user starts typing
#    Given I am on the search page
#    When I start typing in textbox
#    Then Prompt text should disappear
#
##  Scenario: 3.a. Clicking Show  me the weather retrieves weather information for the location and displays it in the weather display area
##    Given I am on the search page
##    When I click the show me the weather
##    Then Weather data is displayed in weather display area
#
##  Scenario: 3.b. When clicking Show  me the weather, an incorrect or illegal entry display “No weather data found.” in the weather display area
##    Given I am on the search page
##    When I click the show me the weather
##    And There was an incorrect or illegal entry
##    Then Display "No weather data found" in weather display area
#
#  Scenario: 4. Weather display area shows location information,date, corresponding weather graphic, temperature, description
#    Given I am on the search page
#    Then I should see location information
#    Then I should see date
#    Then I should see corresponding weather graphic
#    Then I should see temperature
#    Then I should see description
#
#  Scenario: 4.f. Mouse over the weather graphic animates the graphic
#    Given I am on the search page
#    When I mouse over weather graphic
#    Then Graphic should be animated
#
#  Scenario: 5. Page has a unit changer
#    Given I am on the search page
#    Then I should see unit changer
#
#  Scenario: 6. Page has a unit changer
#    Given I am on the search page
#    When I click unit changer
#    Then Unit should change
#
#  Scenario: 6. Page displays a navigation bar
#    Given I am on the search page
#    Then I should see navigation bar