/**
 * 
 */
package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author rajsh
 *
 */
public class Utils {
	static RequestSpecification req;

	public RequestSpecification requestSpecification() throws IOException {

		if (req == null) {
			PrintStream log = new PrintStream(new FileOutputStream("log.txt"));
			// RestAssured.baseURI="https://rahulshettyacademy.com";
			req = new RequestSpecBuilder().setBaseUri(getGlobalValue("BaseUrl")).addQueryParam("key", "qaclick123")
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).setContentType(ContentType.JSON).build();
			return req;
		} else
			return req;
	}

	public static String getGlobalValue(String key) throws IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}
	
	public String getJsonPath(Response response,String key)
	{
		String responseTx = response.asString();
		JsonPath js = new JsonPath(responseTx);
		return js.get(key).toString();
	}

	
	
}
