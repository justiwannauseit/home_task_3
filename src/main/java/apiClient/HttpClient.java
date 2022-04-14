package apiClient;

import config.BaseConfig;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

@Log
@UtilityClass
public class HttpClient {

    private static final RequestSpecification requestSpecification = RestAssured.given().baseUri(BaseConfig.BASE_URL).contentType(ContentType.JSON);
    private static final ResponseSpecification responseSpecification = new ResponseSpecBuilder().log(LogDetail.ALL).build();

    public static Response doGetRequest(final String path) {
        return RestAssured.given(requestSpecification).when().get(path);
    }

    public static <T> Response doPostRequest(final String path, final T body) {
        return RestAssured.given(requestSpecification).log().all().when().body(body).post(path);
    }

    public static Response doDeleteRequest(final String path) {
        return RestAssured.given(requestSpecification).when().delete(path);
    }

    public static <T> T convert(final Response response, final Class<T> clazz) {
        log.info("Response converted:\n" + response.asPrettyString());
        return response.then().spec(responseSpecification).extract().as(clazz);
    }

}
