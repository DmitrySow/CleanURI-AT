package api;

import api.cleanuri.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class ShortenUriTests {

    @ParameterizedTest
    @CsvSource(value = {"https://www.youtube.com/, https://cleanuri.com/x9lZzk",
            "https://google.com/, https://cleanuri.com/AqJYaW",
            "https://metanit.com/ , https://cleanuri.com/R9NX4j"})
    @DisplayName("Корректный ответ от при валидном url")
    public void validUrlTest(String testUrl, String shortTestUrl) {

        Facade facade = new Facade();

        assertThat(facade.shortenUrlSuccess(testUrl)).isEqualTo(shortTestUrl);

    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("Запрос с пустым телом")
    public void emptyUrlTest(String testUrl) {

        Facade facade = new Facade();

        assertThat(facade.ShortenUrlError("")).isEqualTo("API Error: After sanitization URL is empty");
    }

    @ParameterizedTest
    @ValueSource(strings = {"https:/", "//help.steampowered.c", "sdjfllfdkgjnknf"})
    @DisplayName("Передача не-url строки")
    public void notValidUrlTest(String testUrl) {

        Facade facade = new Facade();

        assertThat(facade.ShortenUrlError(testUrl)).isEqualTo("API Error: URL is invalid (check #1)");
    }


    @ParameterizedTest
    @ValueSource(strings = {"https://www.yout ube.com/", "https:// google.com/", "https://metanit.com/"})
    @DisplayName("Ошибка при передаче url с пробелом")
    public void urlWithSpaceTest(String testUrl) {

        Facade facade = new Facade();

        assertThat(facade.ShortenUrlError(testUrl)).isEqualTo("API Error: After sanitization URL is empty");
    }
}