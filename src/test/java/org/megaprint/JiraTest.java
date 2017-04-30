package org.megaprint;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

/**
 * Created by Baurz on 4/17/2017.
 */
public class JiraTest {
    Properties prop=new Properties();
    Response resp;
    ITestContext context;

    @BeforeMethod
    public void init() throws IOException {
        FileInputStream fis= new FileInputStream("data//env.properties");
        prop.load(fis);
        RestAssured.baseURI=prop.getProperty("HOST");
    }
    @Test(priority = 1)
    public void login(ITestContext context){
        resp=given().
                header("Content-Type","application/json").
                body("{ \"username\": \"baurzhan-zh\", \"password\": \"smarot100\" }").
        when().
                post("/rest/auth/1/session").
        then().
                assertThat().
                statusCode(200).
        extract().
                response();
        JsonPath jpath=new JsonPath(resp.asString());
        context.setAttribute("sessionId",jpath.getString("session.name")+"="+jpath.getString("session.value"));
    }
    @Test(priority = 2)
    public void test1(ITestContext context) throws Exception {

    }

}
