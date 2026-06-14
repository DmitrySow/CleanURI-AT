package api.cleanuri;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class Service extends AbstractService {

    private String v1ShortenEp; //ЭП конкретного сервиса то есть куда кидаю запрос

    public Service() {
        super("https://cleanuri.com");
        this.v1ShortenEp = "/api/v1/shorten";
    }

    @Step("Отправка POST-запроса со значением {testUrl}")
    public Response v1ShortenResPost(String testUrl) {

        Response response = given()
                .spec(getReqSpec()) //установил наследственную спеку запроса
                .when()
                .formParam("url",testUrl) //положил параметр, который отдам в теле запроса
                .post(v1ShortenEp) //тип метода и ЭП куда кидаю
                .then()
                .log()
                .all()
                .extract()
                .response();//верну полностью весь ответ

        return response;
    }
}