#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
#@tag
#Feature: Title of your feature
  #I want to use this template for my feature file
#
  #@tag1
  #Scenario: Title of your scenario
    #Given I want to write a step with precondition
    #And some other precondition
    #When I complete action
    #And some other action
    #And yet another action
    #Then I validate the outcomes
    #And check more outcomes
#
  #@tag2
  #Scenario Outline: Title of your scenario outline
    #Given I want to write a step with <name>
    #When I check for the <value> in step
    #Then I verify the <status> in step
#
    #Examples: 
      #| name  | value | status  |
      #| name1 |     5 | success |
      #| name2 |     7 | Fail    |


Feature: Weather Analysis

Scenario: 1.a. Favorite cities list: User can select a city from the favorite cities list area by left-clicking on the city’s name
Given: I am on the Weather Analysis page
And: The favorite cities list has one or more items
When: I click a city from the favorite cities list
Then: The city should be selected

Scenario: 1.b. Clicking on a city displays information in the weather analysis area
Given: I am on the Weather Analysis page
And: The favorite cities list has one or more items
When: I click a city from the favorite cities list
Then: The weather information for the city should be displayed in the weather analysis area

Scenario: 1.c. Clicking on a city displays images in city image area
Given: I am on the Weather Analysis page
And: The favorite cities list has one or more items
When: I click a city from the favorite cities list
Then: The images for the city should be displayed in city image area

Scenario: 2.a. Shows the current weather in the form of the weather display area
Given: I am on the Weather Analysis page
And: The favorite cities list has one or more items
When: I click a city from the favorite cities list
Then: The location should be displayed in the first line of the current weather area
And: The date should be displayed in the first line of the current weather area
And: The icon should be displayed in the second line of the current weather area
And: The temperature should be displayed in the third line of the current weather area
And: The description should be displayed in the third line of the current weather area

Scenario: 2.b. Displays five day forecast for the location
Given: I am on the Weather Analysis page
And: The favorite cities list has one or more items
When: I click a city from the favorite cities list
Then: The weather icon should be displayed for the forecast for each day
And: The date should be displayed for the forecast for each day
And: The forecasted high should be displayed for the forecast for each day
And: The forecasted low should be displayed for the forecast for each day

Scenario: 2.c. Graph of the average highs and lows for the previous year
Given: I am on the Weather Analysis page
And: The favorite cities list has one or more items
When: I click a city from the favorite cities list
Then: A graph of the monthly average high and low temperatures for the previous year should be displayed

Scenario: 3.a. List of favorite cities is shown alphabetically
Given: I am on the Weather Analysis page
And: The favorite cities list has one or more items
Then: The list should be sorted alphabetically on city

Scenario: 3.b. Selecting an entry and then pressing the Remove from Favorites button, removes the
city from the favorites list
Given: I am on the Weather Analysis page
And: The favorite cities list has one or more items
And: I click a city to select it
And: I click the Remove from Favorites button
Then: The city should be removed from the favorites city list
And: The weather analysis area and the city images area should be cleared

Scenario: 3.c. Pressing the remove button should trigger a confirmation popup box before the removal
takes place
Given: I am on the Weather Analysis page
And: The favorite cities list has one or more items
And: I click a city to select it
And: I click the Remove from Favorites button
Then: A confirmation popup box should be displayed before the removal

Scenario: 3.c.i. Prompt for box "Are you sure you want to remove <city name> from favorites?" Options: "Yes" and "Cancel"
Given: I am on the Weather Analysis page
And: The favorite cities list has one or more items
And: I click a city to select it
And: I click the Remove from Favorites button
And: A confirmation popup box is displayed
Then: The message on the popup box should be "Are you sure you want to remove <city name> from favorites?"
And: The options should be "Yes" and "Cancel"

Scenario: 4. Page has a unit changer
Given: I am on the Weather Analysis page
And: The favorite cities list has one or more items
Then: A unit changer should be displayed

Scenario: 5. Page displays a navigation bar
Given: I am on the Weather Analysis page
Then: A navigation bar should be displayed
