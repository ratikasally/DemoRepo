package stepdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.runner.Request;
import pojo.CreateOrderRequest;
import pojo.CreateOrderResponse;
import pojo.GetOrderDetailsResponse;
import pojo.Orders;
import utils.ApiUrls;
import utils.TestDataBuild;
import utils.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StepDefs extends Util {
    RequestSpecification request;
    Response response;

    static String accessToken;
    static String userid;

    static String productID;
    CreateOrderResponse cor;

    static String productOrderId;

    static String orderId;

    @Given("The {string} url with {string} and {string}")
    public void the_url_with_and(String apiName, String userEmail, String userPassword) throws IOException {
        TestDataBuild tdb = new TestDataBuild();
        request = given()
                .contentType(ContentType.JSON)
                .spec(returnRequest())
                .body(tdb.returnLoginPayload(userEmail,userPassword));
    }
    @When("The {string} is invoked using {string} and {string}")
    public void the_is_invoked_using_and(String apiName, String userEmail, String userPassword) {
        response = request
                .expect()
                .defaultParser(Parser.JSON)
                .when()
                .post(ApiUrls.valueOf(apiName).returnUrl())
                .then()
                .extract()
                .response();
    }
    @Then("{string} {int} is returned")
    public void is_returned(String statuscode, int statusCode) {
        assertEquals(response.getStatusCode(),statusCode);
      //  System.out.println(returnJsonProperty(response,"userId"));
    }
    @Then("{string},{string} and {string} are not null in the response.")
    public void and_are_not_null_in_the_response(String token, String userId, String message) {
        accessToken = returnJsonProperty(response,token);
        userid = returnJsonProperty(response,userId);
        String msg = returnJsonProperty(response,message);
        assertTrue(!(accessToken.isBlank()));
        assertTrue(!(userid.isBlank()));
        assertTrue(!(msg.isBlank()));
    }

    @Given("The {string} url with {string} {string} {string} {string} {string} {string} {string}")
    public void the_url_with(String apiName,String productName, String productCategory, String productSubCategory, String productPrice, String productDescription, String productFor, String productImage) throws IOException {
        TestDataBuild tdb = new TestDataBuild();
        request = given()
                .spec(returnRequest())
                .header("Authorization",accessToken)
                .formParams(tdb.returnAddProductBody(productName,productCategory,productSubCategory,productPrice,productDescription,productFor,userid))
                .multiPart("productImage",new File(productImage));
    }
    @When("The {string} is invoked using a valid token generated after calling the {string}")
    public void the_the_is_invoked_using_a_valid_token_generated_after_calling_the(String apiName, String string2) {
        if(apiName.equals("AddProductAPI")) {
            response = request
                    .expect()
                    .defaultParser(Parser.JSON)
                    .when()
                    .post(ApiUrls.valueOf(apiName).returnUrl())
                    .then()
                    .extract()
                    .response();
        }
        else if(apiName.equals("CreateOrderAPI")) {
            response = request
                    .expect()
                    .defaultParser(Parser.JSON)
                    .when()
                    .post(ApiUrls.valueOf(apiName).returnUrl())
                    .then()
                    .extract()
                    .response();
            cor = response.as(CreateOrderResponse.class);
        }

        else if(apiName.equals("GetOrderDetailsAPI")) {
            response = request
                    .expect()
                    .defaultParser(Parser.JSON)
                    .when()
                    .get(ApiUrls.valueOf(apiName).returnUrl())
                    .then()
                    .extract()
                    .response();
        }
    }
    @Then("{string} and {string} are not null in the response.")
    public void and_are_not_null_in_the_response(String productId, String message) {
        productID = returnJsonProperty(response,productId);
        String msg = returnJsonProperty(response,message);
        assertTrue(!(productID.isBlank()));
        assertTrue(!(msg.isBlank()));
    }

    @Given("The {string} url with a valid JSON request body")
    public void the_url_with_a_valid_json_request_body(String string) throws IOException {
        TestDataBuild tdb = new TestDataBuild();
        request = given()
                .contentType(ContentType.JSON)
                .spec(returnRequest())
                .header("Authorization",accessToken)
                .body(tdb.returnCreateOrderRequestPayload());
    }
    @Then("{string} and {string} are returned in the response")
    public void and_are_returned_in_the_response(String string, String string2) {
       assertTrue(!(cor.getOrders().size() == 0));
       assertTrue(!(cor.getProductOrderId().size() == 0));
       orderId = cor.getOrders().get(0);
       productOrderId = cor.getProductOrderId().get(0);
    }
    @Then("{string} is returned as {string} in the response.")
    public void is_returned_as_in_the_response(String string, String message) {
        String msg = cor.getMessage();
        assertEquals(message,msg);
    }

    @Given("The {string} url with {string} as query parameter from {string}")
    public void the_url_with_as_query_parameter_from(String string, String string2, String string3) throws IOException {
        request = given()
                .spec(returnRequest())
                .header("Authorization",accessToken)
                .queryParam("id",orderId);
    }
    @Then("{string} is same as the {string} created after invoking the {string}")
    public void is_same_as_the_created_after_invoking_the(String productOrderedID, String string2, String string3) {
        GetOrderDetailsResponse godResponse = response.as(GetOrderDetailsResponse.class);
        productOrderedID = godResponse.getData().getProductOrderedId();
        assertEquals(productOrderId,productOrderedID);
    }






}
