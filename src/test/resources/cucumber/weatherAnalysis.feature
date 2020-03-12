Feature: Weather Analysis

  Scenario: AIP1 - Favorite cities list: Empty favorite cities list shows a blank list with no button and unit changer
    Given I am on the Weather Analysis page
    And The favorite cities list has no item
    Then I should see a text "Currently, there is no city in your favorites list." in "list-group-no-items"
    And I should not see "remove-from-favorites-button" element
    And I should not see "unit-changer" element

  Scenario: AIP2 - 1.a. Favorite cities list: User can select a city from the favorite cities list area by left-clicking on the city’s name
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    Then I should see the city is selected

  Scenario: AIP3 - 1.b. Clicking on a city displays information in the weather analysis area
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    Then The weather information for the city should be displayed in the weather analysis area

  Scenario: AIP4 - 1.c. Clicking on a city displays images in city image area
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    Then The images for the city should be displayed in city image area

  Scenario: AIP4 - 2.a. Shows the current weather in the form of the weather display area
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    Then The location should be displayed in the current weather area
    And The date should be displayed in the current weather area
    And The icon should be displayed in the current weather area
    And The temperature should be displayed in the current weather area
    And The description should be displayed in the current weather area

  Scenario: AIP5 - 2.b. Displays five day forecast for the location
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    Then The weather icon should be displayed for the forecast for each day
    And The date should be displayed for the forecast for each day
    And The forecasted high should be displayed for the forecast for each day
    And The forecasted low should be displayed for the forecast for each day

  Scenario: AIP6 - 2.c. Graph of the average highs and lows for the previous year
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    Then A graph of the monthly average high and low temperatures for the previous year should be displayed

  Scenario: AIP7 - 3.a. List of favorite cities is shown alphabetically
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    Then The list should be sorted alphabetically on city

  Scenario: AIP8 - 3.b. Selecting an entry and then pressing the Remove from Favorites button, removes the city from the favorites list
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    And I click a city to select it
    And I click the Remove from Favorites button
    Then The city should be removed from the favorites city list
    And The weather analysis area and the city images area should be cleared

  Scenario: AIP9 - 3.c. Pressing the remove button should trigger a confirmation popup box before the removal takes place
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    And I click a city to select it
    And I click the Remove from Favorites button
    Then A confirmation popup box should be displayed before the removal

  Scenario: AIP10 - 3.c.i. Prompt for box "Are you sure you want to remove <city name> from favorites?" Options: "Yes" and "Cancel"
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    And I click a city to select it
    And I click the Remove from Favorites button
    And A confirmation popup box is displayed
    Then The message on the popup box should be "Are you sure you want to remove <city name> from favorites?"
    And The options should be "Yes" and "Cancel"

  Scenario: AIP11 - Selecting "Yes" from the removal confirmation removes the city from the list
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    And I click a city to select it
    And I click the Remove from Favorites button
    And I click Yes from the confirmation popup box
    Then The city should be removed from the favorites city list

  Scenario: AIP12 - Selecting "No" from the removal confirmation leave the city in the list
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    And I click a city to select it
    And I click the Remove from Favorites button
    And I click No from the confirmation popup box
    Then The city should be remain in the favorites city list

  Scenario: AIP13 - 4. Page has a unit changer
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    And I click a city to select it
    Then A unit changer should be displayed

  Scenario: AIP14 - Changing unit changes the units for the weather analysis area
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    And I click a city to select it
    And I click the unit changer
    Then The units for the temperatures in the weather analysis area should be changed

  Scenario: AIP15 - 5. Page displays a navigation bar
    Given I am on the Weather Analysis page
    Then A navigation bar should be displayed
