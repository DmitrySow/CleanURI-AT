package api.cleanuri;

import io.qameta.allure.Step;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;

public class Facade {

    private Service service;
    private ResponseSpecification resSpec200;
    private ResponseSpecification resSpec400;

    public Facade() {
        this.service = new Service();
        this.resSpec200 = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
        this.resSpec400 = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

    @Step("Получить тело успешного ответа")
    public String shortenUrlSuccess(String testUrl) {

        Response response = service.v1ShortenResPost(testUrl);

        v1_shorten_Response body = response.as(v1_shorten_Response.class);

        return body.getResult_url();
    }

    public String ShortenUrlError(String testUrl) {

        Response response = service.v1ShortenResPost(testUrl);

        v1_shorten_ResponseWithError body = response.as(v1_shorten_ResponseWithError.class);

        return body.getError();
    }
}
