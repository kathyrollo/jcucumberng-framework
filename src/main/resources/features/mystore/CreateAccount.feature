@myStore @createAccount
Feature: Create Account
	As a user
	I want to create an account
	So that I can start online shopping

	@validEmail
	Scenario: Valid Email
		Given I Am At Page: 'ap.auth'
		When I Enter Email: 'rollo.katherine@yopmail.com'
		Then I Should See Page Heading: 'CREATE AN ACCOUNT'

	@invalidEmail
	Scenario: Invalid Email
		Given I Am At Page: 'ap.auth'
		When I Enter Email: 'rollo.katherine@xyz'
		Then I Should See Error Message: 'Invalid email address.'