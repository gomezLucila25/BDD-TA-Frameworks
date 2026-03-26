Feature: User Authentication on SauceDemo

  Background:
    Given the user is on the SauceDemo login page

  Scenario: Successful login with standard user
    When the user enters username "standard_user" and password "secret_sauce"
    And the user clicks the login button
    Then the user should see the inventory page with title "Products"

  Scenario: Logout after successful login
    When the user enters username "standard_user" and password "secret_sauce"
    And the user clicks the login button
    Then the user should see the inventory page with title "Products"
    When the user logs out
    Then the user should be back on the login page

  Scenario Outline: Login attempts with various credential combinations
    When the user enters username "<username>" and password "<password>"
    And the user clicks the login button
    Then the login attempt should "<outcome>"

    Examples:
      | username        | password     | outcome |
      | standard_user   | secret_sauce | succeed |
      | locked_out_user | secret_sauce | fail    |
      | standard_user   | wrong_pass   | fail    |
      | invalid_user    | secret_sauce | fail    |
