@automationPractice @createAccount
Feature: Create Account
	As a user
	I want to create an account
	So that I can start online shopping

	@pass
	Scenario: Valid Email
		Given I Am At The Authentication Page
		When I Enter A Valid Email: rollo.katherine@yopmail.com
		Then I Should See The Create An Account Page