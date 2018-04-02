@sample @homePage
Feature: Navigate To Home Page
  As a user
  I want to visit the home page
  So that I can start using the app

  Scenario: Visit Home Page
    Given I Am At The Home Page
    Then I Should See Page Title 'Simply Do - Balance Projector'
