@netIncome
Feature: Net Income
  As a user
  I want to enter my income and expenses
  So that I can see my net income per month and per year

  Scenario Outline: Calculate Net Income
    Given I Am At The Home Page
    When I Enter My Start Balance: <startBalance>
    And I Click Add Regular Income: <incomeCount>
    And I Enter My Regular Income Sources
      | name   | amount | frequency     |
      | Salary |  25000 | every 2 weeks |
    And I Click Add Regular Expenses: <expenseCount>
    And I Enter My Regular Expenses
      | name        | amount | frequency     |
      | Electricity |   5500 | Monthly       |
      | Water       |    900 | Weekly        |
      | Internet    |   1900 | Every 2 Weeks |
      | Cable TV    |    555 | Daily         |
    Then I Should See Net Income Per Month: <netIncomePerMonth>
    And I Should See Net Income Per Year: <netIncomePerYear>

    Examples: 
      | startBalance | incomeCount | expenseCount | netIncomePerMonth | netIncomePerYear |
      |       348000 |           1 |            4 |             23769 |           285225 |
