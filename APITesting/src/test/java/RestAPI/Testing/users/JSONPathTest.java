package RestAPI.Testing.users;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.* ; 
import static org.hamcrest.MatcherAssert.assertThat ; 


import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class JSONPathTest {

	Faker faker = new Faker();
	int newUserID ; 
	
	@BeforeClass
	public void init() {
		
		RestAssured.baseURI = "https://www.batch8-api-site.dev.cc" ; 
		RestAssured.basePath = "/wp-json/wp/v2" ; 
		
	}
	//@Test
	public void testJSONPath() {
		
		Response response  = 
		
			given()
				.relaxedHTTPSValidation().
			//.auth().preemptive().basic("admin", "admin").
			when()
				.log().all()
				.get("/users/{id}",1) ;
		
		
		//System.out.println( response.asString()  );
		//response.prettyPrint();
		
		JsonPath jsonPath = response.jsonPath() ; 
		
		System.out.println( jsonPath.getInt("id") );
		
		
		System.out.println( jsonPath.getString("link") );
		System.out.println( jsonPath.getString("slug") );
		System.out.println( jsonPath.getString("_links.self[0].href") );
		
		/*
		 *  "_links": {
			        "self": [
			            {
			                "href": "https://www.batch8-api-site.dev.cc/wp-json/wp/v2/posts/5"
			            }
			        ]
			        
			   } 
		 * */
		
		// print these :  title , slug , self

	;
		
		
	}
	
	@Test
	public void driverinfoTest() {
		
			
			Response response  = 
				
				given()
					.relaxedHTTPSValidation().
				//.auth().preemptive().basic("admin", "admin").
				when()
					.log().all()
					.get("http://ergast.com/api/f1/drivers.json") 
					
					;

			JsonPath jpath = response.jsonPath() ; 
			
			String str = jpath.setRoot("MRData.DriverTable")
							.get("Drivers[1].givenName"  ) ; 
			System.out.println(  str    );
			
			assertThat(str , equalToIgnoringCase("george") )  ; 
			//assertThat(str , equalTo("george")) ; 
			
		
		
			// GSON
			// JACKSON ---- WILL USE THIS LIBRARY WITHOUT ADDING NEW DEPENDENCY
			
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
