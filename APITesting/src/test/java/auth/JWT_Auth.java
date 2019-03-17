package auth;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class JWT_Auth {

	/// BEFORE TEST ---> CREATE NEW USER AND GET ID
	/// TEST --- > UPDATE USER INFO 
	/// TEST ----> DELETE THAT CREATED USER
	
	Faker faker = new Faker();
	int newUserID ; 
	String token ; 
	
	@BeforeClass
	public void init() {
		
		RestAssured.baseURI = "https://www.qa-batch8.dev.cc" ; 
		RestAssured.basePath = "/wp-json/wp/v2" ; 
		RestAssured.useRelaxedHTTPSValidation();
		
		token = getToken() ; 
		
	}
	
	
	public String getToken() {
		
		RestAssured.basePath = "/wp-json/jwt-auth/v1" ; 
		//https://www.qa-batch8.dev.cc/wp-json/jwt-auth/v1/token
		
		Response res = 
			given()
				.accept(ContentType.JSON)
				.contentType(ContentType.URLENC)
				.formParam("username", "admin")
				.formParam("password", "admin")
				.log().all().
				
			when()
				.log().all()
				.post("/token") ; 
		
		token = res.jsonPath().getString("token"); 
		return token ; 
		
	}
	
	
	@Test
	public void getTokenTest() {
		
		RestAssured.basePath = "/wp-json/jwt-auth/v1" ; 
		//https://www.qa-batch8.dev.cc/wp-json/jwt-auth/v1/token
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.URLENC)
			.formParam("username", "admin")
			.formParam("password", "admin")
			.log().all().
			
		when()
			.log().all()
			.post("/token").
		then()
			//.statusCode(HttpStatus.SC_OK)
			.statusCode(200) ; 

		
	}
	
	
	@Test
	public void test() {
		
	RestAssured.basePath = "/wp-json/wp/v2" ; 

		Map<String,Object> mp = new HashMap<>() ; 
		mp.put("title", faker.book().title());
		mp.put("content", "super");
		mp.put("status", "publish");

	given()
		.header("Authorization", "Bearer " + token)
		.contentType(ContentType.JSON)
		.body(mp).

	when()
		.log().all()
		.post("/posts").
	then()
		//.statusCode(HttpStatus.SC_CREATED)
		.statusCode(201)
		.header("Content-Type" , containsString("application/json") )
		;
		//.body("title.raw", is("abc") ) ; 
		
	}
	
	
	
	
}
