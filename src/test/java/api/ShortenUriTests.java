package api;

import api.cleanuri.RequestBody;
import api.cleanuri.ResponseBody;
import api.cleanuri.ResponseBodyWithError;
import api.cleanuri.Specifications;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class ShortenUriTests {

    private final static String url = "https://cleanuri.com";

    @ParameterizedTest
    @CsvSource(value = {"https://www.youtube.com/, https://cleanuri.com/x9lZzk",
            "https://google.com/, https://cleanuri.com/AqJYaW",
            "https://metanit.com/ , https://cleanuri.com/R9NX4j"})
    @DisplayName("Корректный ответ от при валидном url")
    public void checkValidUri(String testUrl, String shortTestUrl) {
        Specifications.instSpec(Specifications.reqSpec(url), Specifications.resSpec200());

        RequestBody requestBody = new RequestBody(testUrl);

        ResponseBody responseBody = given()
                .when()
                .formParam("url", requestBody.getUrl())
                .post("/api/v1/shorten")
                .then()
                .log()
                .all()
                .extract()
                .body()
                .as(ResponseBody.class);

        assertThat(responseBody.getResultUrl()).isEqualTo(shortTestUrl);
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("Запрос с пустым телом")
    public void emptyRequestBody(String testUrl) {
        Specifications.instSpec(Specifications.reqSpec(url), Specifications.resSpec400());

        RequestBody requestBody = new RequestBody(testUrl);

        ResponseBodyWithError responseBodyWithError = given()
                .when()
                .formParam("url", requestBody.getUrl())
                .post("/api/v1/shorten")
                .then()
                .log()
                .all()
                .extract()
                .body()
                .as(ResponseBodyWithError.class);

        assertThat(responseBodyWithError.getError()).isEqualTo("API Error: After sanitization URL is empty");
    }

    @ParameterizedTest
    @ValueSource(strings = {"https:/", "//help.steampowered.c", "sdjfllfdkgjnknf"})
    @DisplayName("Передача не-url строки")
    public void postNotValidUrl(String testUrl) {
        Specifications.instSpec(Specifications.reqSpec(url), Specifications.resSpec400());

        RequestBody requestBody = new RequestBody(testUrl);

        ResponseBodyWithError responseBodyWithError = given()
                .when()
                .formParam("url", requestBody.getUrl())
                .post("/api/v1/shorten")
                .then()
                .log()
                .all()
                .extract()
                .body()
                .as(ResponseBodyWithError.class);

        assertThat(responseBodyWithError.getError()).isEqualTo("API Error: URL is invalid (check #1)");
    }


    @ParameterizedTest
    @ValueSource(strings = {"https://www.yout ube.com/", "https:// google.com/", "https://metanit.com/"})
    @DisplayName("Ошибка при передаче url с пробелом")
    public void postUrlWithSpace(String testUrl) {
        Specifications.instSpec(Specifications.reqSpec(url), Specifications.resSpec400());

        RequestBody requestBody = new RequestBody(testUrl);

        ResponseBody responseBody = given()
                .when()
                .formParam("url", requestBody.getUrl())
                .post("/api/v1/shorten")
                .then()
                .log()
                .all()
                .extract()
                .body()
                .as(ResponseBody.class);
    }
}
