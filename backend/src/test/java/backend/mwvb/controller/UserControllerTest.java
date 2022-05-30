package backend.mwvb.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class UserControllerTest {
    private static final String API = "http://localhost:8080/api/v1.0/user";

    @Test
    @Disabled
    void register0() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "admin12");
        map.put("password", "Test12qqsssq7123");
        map.put("email", "abc1qq@gmail.com");
        map.put("nickName", "nickName");
        map.put("confirmPassword", "Test12qqq7123");

        RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post(API + "/register")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data", is("创建用户成功"));
    }

    @Test
    void registerError0() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "adwqwmin");
        map.put("password", "Teswqwsst127123");
        map.put("email", "abc@gmail.com");
        map.put("nickName", "nickName");
        map.put("confirmPassword", "Test1271");

        RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post(API + "/register")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("code", is(400))
                .body("data", is("两次密码输入不一致"));
    }

    @Test
    void registerError1() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "adwqwsq    s   min");
        map.put("password", "Teswqwsst127123");
        map.put("email", "abcgmail.com");
        map.put("nickName", "nickName");
        map.put("confirmPassword", "Teswqwsst127123");

        RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post(API + "/register")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("code", is(400))
                .body("data", is("邮箱格式不正确"));
    }

    @Test
    void registerError2() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "");
        map.put("password", "Teswqwsst127123");
        map.put("email", "abc@gmail.com");
        map.put("nickName", "nickName");
        map.put("confirmPassword", "Teswqwsst127123");

        RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post(API + "/register")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("code", is(400))
                .body("data", is("用户信息填写不全"));
    }

    @Test
    void registerError3() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "adm");
        map.put("password", "Teswqwsst127123");
        map.put("email", "abcg@mail.com");
        map.put("nickName", "nickName");
        map.put("confirmPassword", "Teswqwsst127123");

        RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post(API + "/register")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("code", is(400))
                .body("data", is("用户名长度至少为5"));
    }

    @Test
    void registerError4() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "admddd");
        map.put("password", "Tesw");
        map.put("email", "abcg@mail.com");
        map.put("nickName", "nickName");
        map.put("confirmPassword", "Tesw");

        RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(map).
                when().
                post(API + "/register")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("code", is(400))
                .body("data", is("密码长度至少为10"));
    }

    @Test
    @Disabled
    void emailExist() {
        RestAssured.given()
                .get(API + "/register/email/dededew@qq.com")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data", is(false));

        RestAssured.given()
                .get(API + "/register/email/dhlidddddddddj@qq.com")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data", is(true));
    }

    @Test
    @Disabled
    void usernameExist() {
        RestAssured.given()
                .get(API + "/register/username/test")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data", is(true));

        RestAssured.given()
                .get(API + "/register/username/dededew@qq.com")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data", is(false));
    }
}