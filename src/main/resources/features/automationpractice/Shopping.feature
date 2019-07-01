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
		Then I Should See The Cart Summary: <name> <color> <size> <qty>
		Examples:
			| name                  | color  | size | qty |
			| Printed Chiffon Dress | Yellow | S    | 1   |