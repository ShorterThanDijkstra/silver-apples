package backend.mwvb.test_util;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static backend.mwvb.config.SecurityConfig.TOKEN_HEADER_NAME;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredTestUtil {

    public static ValidatableResponse postWithTokenSuccessfully(String url, String token, Object data) {
        return RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(TOKEN_HEADER_NAME, token)
                .body(data)
                .post(url)
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()));
    }

    public static ValidatableResponse postWithoutTokenSuccessfully(String url, Object data) {
        return RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(data).
                when().
                post(url)
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()));
    }

    public static ValidatableResponse getWithTokenSuccessfully(String url, String token) {
        return getWithToken(url, token)
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()));
    }

    public static void getWithBadToken(String url, String badToken) {
        RestAssured.given()
                .header(TOKEN_HEADER_NAME, badToken)
                .get(url)
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.FORBIDDEN.value()));
    }

    public static void getWithoutTokenForbidden(String url) {
        RestAssured.given()
                .get(url)
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.FORBIDDEN.value()));
    }

    public static ValidatableResponse getWithoutTokenSuccessfully(String url) {
        return RestAssured.given()
                .get(url)
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()));
    }

    public static Response getWithToken(String url, String token) {
        return RestAssured.given()
                .header(TOKEN_HEADER_NAME, token)
                .get(url);
    }

    public static void assertBody(ValidatableResponse response, Map<String, Matcher<?>> asserts) {
        for (String path : asserts.keySet()) {
            response.assertThat().body(path, asserts.get(path));
        }
    }

    public static void assertBody(ValidatableResponse response, String path, Matcher<?> matcher) {
        response.assertThat().body(path, matcher);
    }

    public static void assertBody(ValidatableResponse response, String path0, Matcher<?> matcher0, String path1, Matcher<?> matcher1) {
        response.assertThat().body(path0, matcher0).and().body(path1, matcher1);
    }
}
