package SpacexAPITest.SpaceX.v1;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.internal.path.json.mapping.JsonObjectDeserializer;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import static org.hamcrest.Matchers.*;

/**
 * @author Naveen Kumar D
 *
 */

public class TC_SpacexAPI {

	public final String endPoint = "https://api.spacexdata.com/v4/launches/latest";

	/**
	 * To verify the headers
	 */
	@Test
	public void verifyHeaders() {
		Headers headers = given().contentType(ContentType.JSON).relaxedHTTPSValidation().when().get(endPoint).then()
				.extract().headers();
		System.out.println(headers);
		Assert.assertTrue(headers.hasHeaderWithName("Content-Security-Policy"));

	}

	/**
	 * To check size of the response
	 */
	@Test
	public void verifyContent() {
		LinkedHashMap map = given().contentType(ContentType.JSON).relaxedHTTPSValidation().when().get(endPoint).then()
				.extract().path("$");
		System.out.println(map);
		Assert.assertEquals(map.size(), 27);
	}

	/**
	 * To check fairing count
	 */
	@Test
	public void verifyFairingCount() {
		LinkedHashMap<String, String> map = given().contentType(ContentType.JSON).relaxedHTTPSValidation().when()
				.get(endPoint).then().extract().path("fairings");
		System.out.println(map);
		Assert.assertEquals(map.size(), 4);
	}

	/**
	 * To check the status code
	 */
	@Test
	public void testGetRequestStatusCode() {
		given().relaxedHTTPSValidation().when().get(endPoint).then().assertThat().statusCode(HttpStatus.SC_OK);

	}

	/**
	 * To check success response parameter
	 */
	@Test
	public void assertSuccess() {
		Response response = given().relaxedHTTPSValidation().when().get(endPoint);
		Assert.assertEquals(response.jsonPath().get("success"), true);

	}

	/**
	 * To verify the if response is null
	 */
	@Test
	public void verifyesponse() {
		Response response = given().relaxedHTTPSValidation().when().get(endPoint);
		response.prettyPrint();
		Assert.assertNotNull(response);// Check if the response is having data or is null

	}

	/**
	 * Verify response time
	 */
	@Test
	public void checkResponseTime() {
		Response response = given().relaxedHTTPSValidation().when().get(endPoint);
		System.out.println("REsposne time =" + response.getTime());
		Assert.assertTrue(response.getTime() < 1000);
	}

	/**
	 * To verify ship count
	 */
	@Test
	public void checkShipCount() {
		given().when().relaxedHTTPSValidation().get(endPoint).then().assertThat().body("ships", hasSize(1));

	}

	/**
	 * To verify pay load count
	 */
	@Test
	public void verifyPayloadCount() {
		given().when().relaxedHTTPSValidation().get(endPoint).then().assertThat().body("payloads", hasSize(1));

	}
}
