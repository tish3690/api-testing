package RestAPI.Testing.users;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class DatabidingPractice {

	Faker faker = new Faker();
	int newUserID ; 
	
	@BeforeClass
	public void init() {
		
		RestAssured.baseURI = "https://www.batch8-api-site.dev.cc" ; 
		RestAssured.basePath = "/wp-json/wp/v2" ; 
	
	}	
	
	@Test
	public void test() {

		Map<String,Object> mp = new HashMap<>() ; 
		mp.put("title", faker.book().title());
		mp.put("content", "super");
		mp.put("status", "publish");

		given()
		.relaxedHTTPSValidation()
		.auth().preemptive().basic("admin", "admin")
		.contentType(ContentType.JSON)
		.body(mp).
//		.body("{\n" + 
//				"	\n" + 
//				"	\"title \":\"abc\",\n" + 
//				"	\" content\":\"nmy super\",\n" + 
//				"	\"status\":\"publish\"\n" + 
//				"\n" + 
//				"}").
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
	
	
	@Test
	public void test_PassPOJO_ToBoy() {
		
		
		PostBody p = new PostBody( faker.book().title() ," content of post","publish");

		given()
		.relaxedHTTPSValidation()
		.auth().preemptive().basic("admin", "admin")
		.contentType(ContentType.JSON)
		.body(p).
//		.body("{\n" + 
//				"	\n" + 
//				"	\"title \":\"abc\",\n" + 
//				"	\" content\":\"nmy super\",\n" + 
//				"	\"status\":\"publish\"\n" + 
//				"\n" + 
//				"}").
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

/**/
