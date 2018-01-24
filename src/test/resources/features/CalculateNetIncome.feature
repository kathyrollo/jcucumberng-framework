@netIncome
Feature: Calculate Net Income

  Scenario Outline: Calculate Net Income
    Given I Am At The Home Page
    When I Enter My Start Balance: <startBalance>
    When I Click Add Regular Income: <incomeCount>
    When I Enter My Regular Income Sources
      # frequency - daily, work days, weekly, every 2 weeks, monthly
      | name   | amount | frequency     |
      | Salary |  25000 | every 2 weeks |
    When I Click Add Regular Expenses: <expenseCount>
    When I Enter My Regular Expenses
      # frequency - Daily, Business Daily, Weekly, Every 2 Weeks, Monthly
      | name        | amount | frequency     |
      | Electricity |   5500 | Monthly       |
      | Water       |    900 | Weekly        |
      | Internet    |   1900 | Every 2 Weeks |
      | Cable TV    |    555 | Daily         |
    Then I Should See Net Income Per Month: <netIncomePerMonth>
    And I Should See Net Income Per Year: <netIncomePerYear>

    Examples: 
      # netIncomePerMonth - 23769 (default)
      # netIncomePerYear - 285225 (default)
      | startBalance | incomeCount | expenseCount | netIncomePerMonth | netIncomePerYear |
      |       348000 |           1 |            4 |             23769 |           285225 |
