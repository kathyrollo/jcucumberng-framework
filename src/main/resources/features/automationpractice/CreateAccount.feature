@automationPractice @createAccount
Feature: Create Account
	As a user
	I want to create an account
	So that I can start online shopping

	@pass
	Scenario: Valid Email
		Given I Am At Page: ap.authentication
		When I Enter Email: rollo.katherine@yopmail.com
		Then I Should See Page Heading: 'CREATE AN ACCOUNT'