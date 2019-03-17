package RestAPI.Testing.users;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class JSONPath_Practice {

	/// BEFORE TEST ---> CREATE NEW USER AND GET ID
	/// TEST --- > UPDATE USER INFO 
	/// TEST ----> DELETE THAT CREATED USER
	
	Faker faker = new Faker();
	int newUserID ; 
	
	@BeforeClass
	public void init() {
		
		RestAssured.baseURI = "https://www.batch8-api-site.dev.cc" ; 
		RestAssured.basePath = "/wp-json/wp/v2" ; 
		
		newUserID = create_randomUser() ; 
	
		
	}
	
//	@Test
//	public void test() {
//		
//		create_randomUser();
//		
//	}
	
	@Test(priority = 1)
	public void testAdminUserShouldBeAbleToUpdateExistingUser() {
		
		given()
			.relaxedHTTPSValidation()
			.auth().preemptive().basic("admin", "admin")
			.contentType(ContentType.JSON)	
		.when()
			.log().all()
			.body("{\r\n" + 
					"	\"first_name\" : \"user18\", \r\n" + 
					"	\"last_name\" : \"user18\" \r\n" + 
					"}")
				
			.pathParam("newId", newUserID)
			.put("/users/{newId}")
		.then()
			.log().all()
		//	.statusCode(HttpStatus.SC_CREATED)
			.statusCode(200)
			.header("Content-Type", "application/json; charset=UTF-8")
			.body("first_name", is("user18"))
			;

	}
	
	
	@Test(priority = 2)
	public void testAdminUserShouldBeAbleToDeleteUser() {
		given()
			.relaxedHTTPSValidation()
			.auth().preemptive().basic("admin", "admin")
			.queryParam("force", true)
			.param("reassign", 1)   /// you can also use this for query parameter
			.pathParam("id", newUserID)
		.when()
			.log().all()
			.delete("/users/{id}")
			//.delete("/users/4")
			
		.then()
			.statusCode(HttpStatus.SC_OK)
			//.statusCode(200)
			.contentType(ContentType.JSON)
			.body("deleted", is(true) )
			.body("previous.id", equalTo( newUserID ))
			;
	}
	
	
	
	
	public int create_randomUser() {
		
		// JSONPATH IS A Library to work with JSON DATA 
			Response result  = 
					given()
						.relaxedHTTPSValidation()
						.auth().preemptive().basic("admin", "admin").
					when()
						.log().all()
						.accept(ContentType.JSON)
						.contentType(ContentType.JSON)
						// EITHER PASS A MAP 
						// OR PASS A POJO 
						.body("{" +
								"	\"username\" : \""+  faker.name().firstName()+"\",\n" + 
								"	\"name\" : \"user c\" ,\n" + 
								"	\"first_name\" : \"super b\", \n" + 
								"	\"last_name\" : \"user last\" ,\n" + 
								"	\"email\" : \""+  faker.internet().emailAddress()+ "\" ,\n" + 
								"	\"roles\" : \"author\" ,\n" + 
								"	\"password\" : \"user\" \n" + 
								"}")
						
						.post("/users") ; 
			
			int newID = result.jsonPath().getInt("id") ; 
			System.out.println( "new ID is " + newID );	
				
				//.statusCode(HttpStatus.SC_CREATED)
				 
				  
			return newID ; 
			
	}
	
}
