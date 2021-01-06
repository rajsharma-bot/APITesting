/**
 * 
 */
package stepDefinations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResource;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinations extends Utils {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;
	

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		// Write code here that turns the phrase above into concrete actions
		res = given().spec(requestSpecification()).body(data.AddPlace(name, language, address));
	}

	@When("User calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		// Write code here that turns the phrase above into concrete actions
		APIResource ResourceAPI = APIResource.valueOf(resource);
		System.out.println(ResourceAPI.getResource());
		// resspec =new
		// ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if (method.equalsIgnoreCase("POST"))
			response = res.when().post(ResourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response = res.when().get(ResourceAPI.getResource());
	}

	@Then("then API call is success with status code {int}")
	public void then_api_call_is_success_with_status_code(Integer int1) {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(response.getStatusCode(), 200);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) {
		
		
		assertEquals(getJsonPath(response,keyValue), Expectedvalue);
	}
	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		place_id= getJsonPath(response, "place_id");
		//System.out.println(place_id);
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_http_request(resource,"GET");
		@SuppressWarnings("unused")
		String actualName=getJsonPath(response, "name");
		//System.out.println("Input 1");
		assertEquals(actualName, expectedName);
		//System.out.println("out 1");
	}
	@Given("DeletePlace payload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    res=given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}

}
