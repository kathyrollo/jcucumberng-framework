@simplyDo @netIncome
Feature: Net Income
	As a user
	I want to enter my income and expenses
	So that I can see my net income per month and per year

	@perMonth
	Scenario: Calculate Per Month
		Given I Am At Page: 'simplydo.home'
		When I Enter My Start Balance: '348000'
		And I Enter My Regular Income Sources
			| name      | Salary        |
			| amount    | 25000         |
			| frequency | every 2 weeks |
		And I Enter My Regular Expenses
			| name        | amount | frequency     |
			| Electricity | 5500   | Monthly       |
			| Water       | 900    | Weekly        |
			| Internet    | 1900   | Every 2 Weeks |
			| Cable TV    | 555    | Daily         |
		Then I Should See Net Income Per Month: '23769'

	@perYear
	Scenario: Calculate Per Year
		Given I Am At Page: 'simplydo.home'
		When I Enter My Start Balance: '353000'
		And I Enter My Regular Income Sources
			| name      | Salary    |
			| amount    | 2180      |
			| frequency | work days |
		And I Enter My Regular Expenses
			| name        | amount | frequency     |
			| Electricity | 5500   | Monthly       |
			| Water       | 900    | Weekly        |
			| Internet    | 1900   | Every 2 Weeks |
			| Cable TV    | 555    | Daily         |
		Then I Should See Net Income Per Year: '202025'