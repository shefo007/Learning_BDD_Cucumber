Feature: Premium account

  Background:
    Given the range is 100
    And people are located at
      | name     | Sean | Lucy |
      | location | 0    | 100  |
    And Sean has bought 30 credits


  Scenario: Sean shouts some over-long messages and some messages containing the word “buy”
    When Sean shouts 2 over-long messages
    And Sean shouts 3 messages containing the word "buy"
    Then Lucy hears all Sean's messages
    And Sean should have 11 credits

  Rule: Mention the word "buy" and you lose 5 credits.

    Scenario: Sean some messages containing the word “buy”
      When Sean shouts 3 messages containing the word "buy"
      Then Lucy hears all Sean's messages
      And Sean should have 15 credits

  Rule: Over-long messages cost 2 credits
    Scenario: Sean shouts some over-long messages
      When Sean shouts 2 over-long messages
      Then Lucy hears all Sean's messages
      And Sean should have 26 credits
