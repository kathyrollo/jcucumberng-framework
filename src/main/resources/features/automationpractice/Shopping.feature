@automationPractice @shopping
Feature: Shopping
	As a user
	I want to add items to my cart
	So that I can proceed to payment

	@cartSummary
	Scenario: Cart Summary
		Given I Am At Page: ap.summer.dresses
		When I Add Item To Cart: 'Printed Chiffon Dress'
		And I Proceed To Checkout