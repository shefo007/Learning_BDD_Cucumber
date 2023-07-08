@SHOUTY-11
Feature: Shout
  Shouty allows users to "hear" other users "shouts" as long as they are close enough to each other

  To do:
    - only shout to people within a certain distance

  Background:
    Given the range is 100
    And people are located at
      | name     | Sean | Lucy | Larry |
      | location | 0    | 100  | 150   |

  @smoke
  Rule: Shouts can be heard by other users

    Scenario: Listener hears a message
      When Sean shouts "free bagels at Sean's"
      Then Lucy should hear Sean's message

    Scenario: Listener hears a different message
      When Sean shouts "Free coffee!"
      Then Lucy should hear Sean's message

  Rule: Shouts should only be heard if listener is within range

    Scenario: Listener is within range
      When Sean shouts
      Then Lucy should hear a shout

    @focus @smoke
    Scenario: Listener is out of range
      When Sean shouts
      Then Larry should not hear a shout


  Rule: Listener should be able to hear multiple shouts

    Scenario: Two shouts
      When Sean shouts "Free bagels!"
      And Sean shouts "Free toast!"
      Then Lucy hears the following messages:
        | Free bagels! |
        | Free toast!  |


  Rule:  Maximum length of message is 180 characters

    Scenario: Message is too long
      When Sean shouts "3544444444444444656666666666666666546549749874564654987465413616579874654131654697984654644636579879846165498789463131654987866666666666666666666666666666666666666666666446465487984616546549878965134679"
      Then Lucy should not hear a shout


    Scenario: Message is too long but using docString
      When Sean shouts the following message
        """
        This is a really long message
        so long in fact that I am not going to
        be allowed to send it, at least if I keep
        typing like this until the length is over
        the limit of 180 characters.
        """
      Then Lucy should not hear a shout