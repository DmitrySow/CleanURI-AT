package api.cleanuri;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

@Data
public abstract class AbstractService {

    private RequestSpecification reqSpec; //все запросы к приложению содержат общие штуки. Урл и спеку запроса поэтому сюда, а отдельные сервисы уже унаследуют
    private String baseUrl;

    public AbstractService() {

        this.baseUrl = "https://cleanuri.com";

        this.reqSpec =  new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.URLENC)
                .build();
    }

}