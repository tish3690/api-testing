package RestAPI.Testing.users;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

import java.util.List;
import java.util.Map;

import org.omg.CORBA.INTERNAL;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JsonPath_PRACTICE2 {

	Faker faker = new Faker();
	int newUserID ; 
	
	@BeforeClass
	public void init() {
		
		RestAssured.baseURI = "https://www.batch8-api-site.dev.cc" ; 
		RestAssured.basePath = "/wp-json/wp/v2" ; 
		RestAssured.useRelaxedHTTPSValidation();
	
		
	}
	
	@Test
	public void simpleGetRequest() {
		
		Response response = 
				
		given()
			//.relaxedHTTPSValidation()
		.when()
			.log().all()
			//.queryParam("per_page",2)
			.get("/posts")

			;

		
		JsonPath jp = response.jsonPath();
		// USE JSON PATH TO FIND OUT FIRST AUTHOR 
		//System.out.println(  jp.getInt("[0].author")  );
		System.out.println(  jp.getInt("author[0]")  );
		
		List<Object> lst = jp.getList("author") ; 
		
		List<Integer> lstNum = jp.getList("author",Integer.class) ;
		System.out.println(lstNum);
		
		
		List<String> titles = jp.getList("title.rendered",String.class) ; 

		
		//
		//List<Integer> lstCount = jp.getList("_links.version-history[0].count",Integer.class) ;
		List<Object> lstCount = jp.getList("_links.version-history.count") ;
		
		System.out.println(lstCount);
		
		// USE JSON PATH TO FIND OUT ALL OF THE AUTHOR 
		// USE JSON PATH TO FIND OUT version-history count 
		
	}
	
	@Test
	public void getAllDriverFirstName() {
		
		
		Response res = given()
						.when()
					.get("http://ergast.com/api/f1/drivers.json") ; 
		
		JsonPath jp = res.jsonPath();
		List<String> lstGivenName = jp.getList("MRData.DriverTable.Drivers.givenName", String.class);
		
		System.out.println( lstGivenName);
		
		//System.out.println(jp.get("MRData.DriverTable.Drivers[0]") );
		
		Map map1 = jp.getMap("MRData.DriverTable.Drivers[0]") ; 
		System.out.println(map1);
		System.out.println(map1.keySet());
		
		Map<String, String> map2 = jp.getMap("MRData.DriverTable.Drivers[0]",String.class, String.class) ;  
		System.out.println( map2.values() );
		
		System.out.println( jp.getString("MRData.DriverTable.Drivers[1].givenName" )) ; 
		
		// JSONPATH That rest assured use is the GPath from groovy 
		// Predicate 											 .find
		System.out.println(jp.getList("MRData.DriverTable.Drivers.findAll{ it.givenName=='George'}") ); 
		
		// this is similar to java way
		List<Object> lst4 = jp.getList("MRData.DriverTable.Drivers.findAll{ whatever-> whatever.givenName=='George'}") ; 
		System.out.println(lst4);
		
//		System.out.println(
//					jp.get("MRData.DriverTable.Drivers.findAll{ it.givenName=='George' && it.nationality=='American' }") ); 
		
		System.out.println();
		
		
		List<Object> lst5 = jp.getList("MRData.DriverTable.Drivers.findAll{it.givenName.length()==3} ") ;
		
		List<Object> lst6 = jp.getList("MRData.DriverTable.Drivers.findAll{  driver-> driver.givenName.length()==3 }.familyName ") ;
		
		System.out.println(lst6);
		
		// find out all the driver that has 3 letters given name   it.givenName.length()
		

		// single json object ---> Driver object in java 
		// Data binding ---> Binding Json field to POJO Field 
		
		
		Driver driverObj = jp.getObject("MRData.DriverTable.Drivers[1]", Driver.class) ; 
		
		System.out.println("--------");
		System.out.println( driverObj );
		System.out.println("--------");
		
		
	}
	
	
	
}
