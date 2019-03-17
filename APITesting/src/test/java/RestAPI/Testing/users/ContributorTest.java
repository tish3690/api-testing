package RestAPI.Testing.users;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.*;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContributorTest {

	/// BEFORE TEST ---> CREATE NEW USER AND GET ID
	/// TEST --- > UPDATE USER INFO 
	/// TEST ----> DELETE THAT CREATED USER
	
	Faker faker = new Faker();
	int newUserID ; 
	
	@BeforeClass
	public void init() {
		
		RestAssured.baseURI = "https://www.batch8-api-site.dev.cc" ; 
		RestAssured.basePath = "/wp-json/wp/v2" ; 
	
		
	}
	
	/*
	 * Feauture : Add A new Post 
	 * 	
	 * 	
	 * 	  Scenario : Contributor should be able to submit post for review ---> status can only be pending 
	 * 
	 * 
	 * 	  Scenario : Contributor must not be able to publish a post   ---> publish X  403
	 * 
	 * 
	 * 	  Scenario : admin user should be able to publish the pending posts by contributor
	 * 
	 * 
	 * */
	
	//@Test
	public void contributorShouldBeAbleTo_SubmitAPostForAdminReview_Test() {
		
		
		given()
			.relaxedHTTPSValidation()
			.auth().preemptive().basic("user1", "user1")
			.contentType(ContentType.JSON)
			.body("{\n" + 
					"	\n" + 
					"	\"title\":\"abc\",\n" + 
					"	\"content\":\"nmy super\",\n" + 
					"	\"status\":\"pending\"\n" + 
					"\n" + 
					"}").
		when()
			.log().all()
			.post("/posts").
		then()
			//.statusCode(HttpStatus.SC_CREATED)
			.statusCode(201)
			.header("Content-Type" , containsString("application/json") )
			.body("title.raw", is("abc") ) ; 
			
		
		
		
	}
	
	
	//@Test
	public void contributorShouldNoyBeAbleTo_PublishAPost_Test() {
		
		
		given()
			.relaxedHTTPSValidation()
			.auth().preemptive().basic("user1", "user1")
			.contentType(ContentType.JSON)
			.body("{\n" + 
					"	\n" + 
					"	\"title\":\"abc\",\n" + 
					"	\"content\":\"nmy super\",\n" + 
					"	\"status\":\"publish\"\n" + 
					"\n" + 
					"}").
		when()
			.post("/posts").
		then()
			.log().all()
			//.statusCode(HttpStatus.SC_CREATED)
			.statusCode(403)
			.header("Content-Type" , containsString("application/json") )
			.body("message", is("Sorry, you are not allowed to publish posts in this post type.") ) ; 
			
		
		
		
	}
	
	@Test
	public void adminUser_ShouldBeAbleTo_PublishThePendingPosts() {
		
	given()
		.relaxedHTTPSValidation()
		.auth().preemptive().basic("admin", "admin")
		.contentType(ContentType.JSON)
		.body("{\"status\":\"publish\"}" )
		.pathParam("idOfPendingPost", 56).
	when()
		.put("/posts/{idOfPendingPost}").
	then()
		.log().all()
		//.statusCode(HttpStatus.SC_OK)
		.statusCode(200)
		.header("Content-Type" , containsString("application/json") )
		.body("status", is("publish") ) ; 
		
	}
	
	
	
	
}
