package api.cleanuri;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

public class Specifications {

    public static RequestSpecification reqSpec(String url) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(ContentType.URLENC)
                .build();
    }

    public static ResponseSpecification resSpec200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification resSpec400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

    public static void instSpec(RequestSpecification reqSpec, ResponseSpecification resSpec) {
        responseSpecification = resSpec;
        requestSpecification = reqSpec;
    }
}
