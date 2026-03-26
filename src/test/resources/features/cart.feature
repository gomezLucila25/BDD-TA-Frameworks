Feature: Shopping Cart on SauceDemo

  Background:
    Given the user is logged in as "standard_user" with password "secret_sauce"
    And the user is on the inventory page

  Scenario: Add a single product to the cart
    When the user adds "Sauce Labs Backpack" to the cart
    Then the cart badge should show 1 item(s)

  Scenario: Navigate to cart and verify product is listed
    When the user adds "Sauce Labs Bike Light" to the cart
    And the user goes to the cart page
    Then the cart page should contain 1 item(s)
    And the cart should include the product "Sauce Labs Bike Light"

  Scenario Outline: Add different products and verify cart count updates
    When the user adds "<product>" to the cart
    Then the cart badge should show <count> item(s)

    Examples:
      | product                         | count |
      | Sauce Labs Backpack             | 1     |
      | Sauce Labs Bike Light           | 1     |
      | Sauce Labs Bolt T-Shirt         | 1     |
      | Sauce Labs Fleece Jacket        | 1     |
