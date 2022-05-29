package backend.mwvb.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.*;

class BookControllerTest {
    private static final String API = "http://localhost:8080/api/v1.0/book";

    @Test
    void intro() {
        RestAssured.given()
                .get(API + "/intro")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data.paragraphs", hasSize(14));
    }

    @Test
    void allRoots() {
        RestAssured.given()
                .get(API + "/roots")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data.flatten().", hasSize(240));
    }

    @Test
    void rootsInUnit() {
        RestAssured.given()
                .get(API + "/roots/17")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data[5].name", equalTo("TANG/TACT"))
                .and()
                .body("data", hasSize(8));
    }

    @Test
    void quizzesInUnit() {
        RestAssured.given()
                .get(API + "/quizzes/27")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data", hasSize(6))
                .and()
                .body("data[1].quizPages[1].content", containsString("7. slander"));
    }

    @Test
    void wordsInRoot() {
        Response response = RestAssured.given()
                .get(API + "/roots/2");
        int rootId = response.body().jsonPath().get("data[5].id");
        RestAssured.given()
                .get(API + "/words/" + rootId)
                .then()
                .body("data[2].spell", equalTo("protracted"));
    }

    @Test
    void allQuizzes() {
        RestAssured.given()
                .get(API + "/quizzes")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data.flatten()", hasSize(180));

    }

    @Test
    void unit() {
        RestAssured.given()
                .get(API + "/units/7")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .body("data.specialSectionWords[2].sentences[0].text", containsString("Cassandra"));
    }
}