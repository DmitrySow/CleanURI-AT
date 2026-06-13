package api.cleanuri;

import io.restassured.response.Response;
import lombok.NoArgsConstructor;

import static io.restassured.RestAssured.given;

public class Service extends AbstractService {

    public String v1_shorten_ep; //ЭП конкретного сервиса то есть куда кидаю запрос

    public Service() {
        super();
        this.v1_shorten_ep  = "/api/v1/shorten";
    }

    public Object v1_shorten_res_post (String testUrl) {

        Response response = given()
                .spec(getReqSpec()) //установил наследственную спеку запроса
                .when()
                .formParam("url",testUrl) //положил параметр, который отдам в теле запроса
                .post(v1_shorten_ep) //тип метода и ЭП куда кидаю
                .then()
                .log()
                .all()
                .extract()
                .response();

        if(response.statusCode() == 200) {
            return response.as(v1_shorten_Response.class);
        } else {
            return response.as(v1_shorten_ResponseWithError.class);
        }

    }
}