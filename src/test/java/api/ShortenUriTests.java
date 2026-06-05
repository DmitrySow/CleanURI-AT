package api;

import api.cleanuri.v1_shorten_Request;
import api.cleanuri.v1_shorten_Response;
import api.cleanuri.v1_shorten_ResponseWithError;
import api.cleanuri.Specifications;
import org.junit.jupiter.api.DisplayName;
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
    public void validUrlTest(String testUrl, String shortTestUrl) {
        Specifications.instSpec(Specifications.reqSpec(url), Specifications.resSpec200());

        v1_shorten_Request v1shortenRequest = new v1_shorten_Request(testUrl);

        v1_shorten_Response v1shortenResponse = given()
                .when()
                .formParam("url", v1shortenRequest.getUrl())
                .post("/api/v1/shorten")
                .then()
                .log()
                .all()
                .extract()
                .body()
                .as(v1_shorten_Response.class);

        assertThat(v1shortenResponse.getResult_url()).isEqualTo(shortTestUrl);
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("Запрос с пустым телом")
    public void emptyUrlTest(String testUrl) {
        Specifications.instSpec(Specifications.reqSpec(url), Specifications.resSpec400());

        v1_shorten_Request v1shortenRequest = new v1_shorten_Request(testUrl);

        v1_shorten_ResponseWithError v1shortenResponseWithError = given()
                .when()
                .formParam("url", v1shortenRequest.getUrl())
                .post("/api/v1/shorten")
                .then()
                .log()
                .all()
                .extract()
                .body()
                .as(v1_shorten_ResponseWithError.class);

        assertThat(v1shortenResponseWithError.getError()).isEqualTo("API Error: After sanitization URL is empty");
    }

    @ParameterizedTest
    @ValueSource(strings = {"https:/", "//help.steampowered.c", "sdjfllfdkgjnknf"})
    @DisplayName("Передача не-url строки")
    public void notValidUrlTest(String testUrl) {
        Specifications.instSpec(Specifications.reqSpec(url), Specifications.resSpec400());

        v1_shorten_Request v1shortenRequest = new v1_shorten_Request(testUrl);

        v1_shorten_ResponseWithError v1shortenResponseWithError = given()
                .when()
                .formParam("url", v1shortenRequest.getUrl())
                .post("/api/v1/shorten")
                .then()
                .log()
                .all()
                .extract()
                .body()
                .as(v1_shorten_ResponseWithError.class);

        assertThat(v1shortenResponseWithError.getError()).isEqualTo("API Error: URL is invalid (check #1)");
    }


    @ParameterizedTest
    @ValueSource(strings = {"https://www.yout ube.com/", "https:// google.com/", "https://metanit.com/"})
    @DisplayName("Ошибка при передаче url с пробелом")
    public void urlWithSpaceTest(String testUrl) {
        Specifications.instSpec(Specifications.reqSpec(url), Specifications.resSpec400());

        v1_shorten_Request v1shortenRequest = new v1_shorten_Request(testUrl);

        v1_shorten_Response v1shortenResponse = given()
                .when()
                .formParam("url", v1shortenRequest.getUrl())
                .post("/api/v1/shorten")
                .then()
                .log()
                .all()
                .extract()
                .body()
                .as(v1_shorten_Response.class);
    }
}