@automationPractice @shopping
Feature: Shopping
	As a user
	I want to add items to my cart
	So that I can proceed to payment

	@cartSummary
	Scenario Outline: Cart Summary
		Given I Am At Page: ap.summer.dresses
		When I Add Item To Cart: <name>
		And I Proceed To Checkout
		Then I Should See The Cart Summary: <name> <color> <qty>

		Examples:
			| name                  | color  | qty |
			| Printed Chiffon Dress | Yellow | 1   |