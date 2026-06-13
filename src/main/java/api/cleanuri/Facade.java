package api.cleanuri;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class Facade {

    private Service service;
    private ResponseSpecification resSpec;

    public Facade(Integer statusCode) {
        this.service = new Service();
        this.resSpec = new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .build();
    }

    public String getShortenUrl(String testUrl) {

        v1_shorten_Response response = (v1_shorten_Response)service.v1_shorten_res_post(testUrl);

        return response.getResult_url();
    }

    public String getShortenUrlError(String testUrl) {

        v1_shorten_ResponseWithError response = (v1_shorten_ResponseWithError)service.v1_shorten_res_post(testUrl);

        return response.getError();
    }
}
