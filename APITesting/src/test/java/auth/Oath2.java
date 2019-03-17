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
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class Oath2 {

	/// BEFORE TEST ---> CREATE NEW USER AND GET ID
	/// TEST --- > UPDATE USER INFO 
	/// TEST ----> DELETE THAT CREATED USER
	
	Faker faker = new Faker();
	int newUserID ; 
	String token = "10960~NRf9yQTLLFj6v7Lg4a3dF73M3ArnflwIxIueOXlDhxyJI4igABFUequWINMiSqua"; 
	
	RequestSpecification req ; 
	Response res ; 
	ValidatableResponse var;
	
	@BeforeClass
	public void init() {
		
		RestAssured.baseURI = "https://learn.cybertekschool.com" ; 
		RestAssured.basePath = "/api/v1" ; 
		RestAssured.useRelaxedHTTPSValidation();
	
		
	}
	
	//@Given application is using oath2 a athentication method and expecting json as a respons e
	//@when  authenticated user make a request to get all module using /courses/73/modules 
	//@then  we expect the status code should be 200 OK 
	// 		 and body has a size of 9 
	
	@Test(priority=1)
	public void givenUserBlabla() {
		
	 req = 
		given()
		.accept(ContentType.JSON)
		.auth().oauth2(token) ;
	}
	
	@Test(priority=2)
	public void whenUserBlabla() {
		
		
	 res = req.when()
		.log().all()
		.get("/courses/73/modules"); 
		
	}
	
	@Test(priority=3)
	public void thenUserBlabla() {
		
		ValidatableResponse var = res.then()
		//.statusCode(HttpStatus.SC_OK)
		.statusCode(200)
		.body("$" , hasSize(9)) ; 
		
	}
	
	@Test(priority=3)
	public void thenUserBlablaAgain() {
		
//		var.and()
//			.assertThat(4,equalTo(4)) ; 
		
	}
	
	
	
	
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
