package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class Util {
    static RequestSpecification request;

    public RequestSpecification returnRequest() throws IOException {
        if(request == null) {
            PrintStream ps = new PrintStream("logging.txt");
            request = new RequestSpecBuilder()
                    .setBaseUri(returnProperty("baseURL"))
                    .addFilter(RequestLoggingFilter.logRequestTo(ps))
                    .addFilter(ResponseLoggingFilter.logResponseTo(ps))
                    .build();
        }
        return request;
    }

    public String returnProperty(String keyName) throws IOException {
        Properties prop = new Properties();
        FileInputStream fs = new FileInputStream("src/test/java/resources/ecomm.properties");
        prop.load(fs);
        return prop.getProperty(keyName);
    }

    public String returnJsonProperty(Response response, String keyName){
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(keyName);
    }

}
