package com.graphql.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 * 
 * @author naveenautomationlabs
 *
 */

public class GraphQLQueryTest {

	@Test
	public void getAllFilmsTest() {
		

		RestAssured.baseURI ="https://swapi-graphql.netlify.app";
		String query = "{\"query\":\"{\\n  allFilms {\\n    films {\\n      title\\n    }\\n  }\\n}\\n\",\"variables\":null}";
		
		given().log().all()
			.contentType("application/json")
			.body(query)
				.when().log().all()
					.post("/.netlify/functions/index")
						.then().log().all()	
							.assertThat()
								.statusCode(200)
									.and()
										.body("data.allFilms.films[0].title", equalTo("A New Hope"));
		//Read my properties
		Properties prop = new Properties();
		try {
			prop.load(GraphQLQueryTest.class.getClassLoader().getResourceAsStream("myproject.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String env = prop.getProperty("env");
		String url = prop.getProperty(env.toUpperCase()+"URL");

		System.out.println("url is "+url);

	}
}
