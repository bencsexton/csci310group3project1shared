Feature: Weather Analysis

  Scenario: AIP1 - Favorite cities list: Empty favorite cities list shows a blank list with no button and unit changer
    Given I am on the Weather Analysis page
    And The favorite cities list has no item
    Then AIP: I should see "Currently, there is no city in your favorites list." text in "list-group-no-items"
    And AIP: I should not see "remove-from-favorites-button" element
    And AIP: I should not see "unit-changer" element

  Scenario: AIP2 - 1.a. Favorite cities list: User can select a city from the favorite cities list area by left-clicking on the city's name
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    Then I should see the city is selected

  Scenario: AIP3 - 1.b. Clicking on a city displays information in the weather analysis area
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    Then AIP: I should see "weather-section" element

  Scenario: AIP4 - 1.c. Clicking on a city displays images in city image area
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    Then AIP: I should see "photo-section" element

  Scenario: AIP4 - 2.a. Shows the current weather in the form of the weather display area
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    Then AIP: I should see "current-city-val" element
    And AIP: I should see "current-date-val" element
    And AIP: I should see "current-icon-val" element
    And AIP: I should see "current-temp-val" element
    And AIP: I should see "current-desc-val" element

  Scenario: AIP5 - 2.b. Displays five day forecast for the location
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    Then AIP: I should see 5 "forecast-icon" elements
    Then AIP: I should see 5 "forecast-date" elements
    Then AIP: I should see 5 "forecast-high" elements
    Then AIP: I should see 5 "forecast-low" elements

  Scenario: AIP6 - 2.c. Graph of the average highs and lows for the previous year
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    Then AIP: I should see "historic-chart" element

  Scenario: AIP7 - 3.a. List of favorite cities is shown alphabetically
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    Then The list should be sorted alphabetically by city

  Scenario: AIP8 - 3.b. Pressing the remove button should trigger a confirmation popup box before the removal takes place.
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    And AIP: I click "remove-from-favorites-button"
    Then A confirmation popup box should be displayed before the removal
    When I click "Yes" from the confirmation box
    Then The city should be removed from the list

  Scenario: AIP9 - 3.c. Pressing the remove button should trigger a confirmation popup box before the removal takes place.
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    And AIP: I click "remove-from-favorites-button"
    Then A confirmation popup box should be displayed before the removal
    And The message on the popup box should be Are you sure you want to remove <city name> from favorites?

  Scenario: AIP10 - 3.c.i Prompt for box "Are you sure you want to remove <city name> from favorites?" Options: "Yes" and "Cancel"
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    And AIP: I click "remove-from-favorites-button"
    Then A confirmation popup box should be displayed before the removal
    And The options should be "Yes" and "Cancel"

  Scenario: AIP11 - Selecting "Cancel" from the removal confirmation leave the city in the list
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    And I click a city from the favorite cities list
    And AIP: I click "remove-from-favorites-button"
    When I click "Cancel" from the confirmation box
    Then The city should be remain in the favorites city list

  Scenario: AIP12 - 4. Page has a unit changer
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    And I click a city from the favorite cities list
    Then AIP: I should see "unit-changer" element

  Scenario: AIP13 - Changing unit changes the units for the weather analysis area
    Given I am on the Weather Analysis page
    And The favorite cities list has one or more items
    When I click a city from the favorite cities list
    And AIP: I click "unit-changer"
    Then The units for the temperatures in the weather analysis area should be changed

  Scenario: AIP14 - 5. Page displays a navigation bar
    Given I am on the Weather Analysis page
    Then AIP: I should see "navigation-bar" element
