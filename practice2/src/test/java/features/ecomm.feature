Feature: Test that the apis for ecomm app are working as expected
 Scenario Outline: Validate that the Login API is working as expected
    Given The "LoginAPI" url with "<userEmail>" and "<userPassword>"
    When The "LoginAPI" is invoked using "userEmail" and "userPassword"
    Then "statuscode" 200 is returned
    And "token","userId" and "message" are not null in the response.

   Examples:
   |userEmail|userPassword|
   |    ratika.sally@gmail.com     |      rahulshettyLAMPARD@18      |

   Scenario Outline: Validate that the AddProduct functionality is working as expected
     Given The "AddProductAPI" url with "<productName>" "<productCategory>" "<productSubCategory>" "<productPrice>" "<productDescription>" "<productFor>" "<productImage>"
     When The "AddProductAPI" is invoked using a valid token generated after calling the "LoginAPI"
     Then "statuscode" 201 is returned
     And "productId" and "message" are not null in the response.

     Examples:
     |productName|productCategory|productSubCategory|productPrice|productDescription|productFor|productImage|
     |Cake       |Cakes          |Chocolate Cake    |100         |Chocolate Cake    |All       |/Users/ratikasally/Desktop/cake.jpeg|

     Scenario: Validate that the Create Order functionality is working as expected
       Given The "CreateOrderAPI" url with a valid JSON request body
       When The "CreateOrderAPI" is invoked using a valid token generated after calling the "LoginAPI"
       Then "statuscode" 201 is returned
       And "orders" and "productOrderId" are returned in the response
       And "message" is returned as "Order Placed Successfully" in the response.

       Scenario: Validate that the Get Order functionality is working as expected
         Given The "GetOrderDetailsAPI" url with "orderId" as query parameter from "CreateOrderAPI"
         When The "GetOrderDetailsAPI" is invoked using a valid token generated after calling the "LoginAPI"
         Then "statuscode" 200 is returned
         And "productOrderedId" is same as the "productOrderedId" created after invoking the "CreateOrderAPI"



