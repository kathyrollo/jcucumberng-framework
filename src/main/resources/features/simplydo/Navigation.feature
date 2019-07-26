@simplyDo @navigation
Feature: Navigation
	As a user
	I want to visit the home page
	So that I can start using the application

	@pageTitle
	Scenario: Verify Page Title
		Given I Am At Page: 'simplydo.home'
		Then I Should See Page Title: 'Simply Do - Balance Projector'