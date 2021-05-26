package com.example.techtest.controller;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest_Int {

    @LocalServerPort
    private int port;

    @Test
    void signup_and_signin() {
        var username = UUID.randomUUID().toString();

        given()
                .baseUri("http://localhost:" + port)
                .body(new CredentialsDto(username, "asd123"))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/signup")
                .then()
                .assertThat()
                .statusCode(HTTP_CREATED);

        var firstToken = given()
                .baseUri("http://localhost:" + port)
                .body(new CredentialsDto(username, "asd123"))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/signin")
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .as(TokenDto.class);

        var secondToken = given()
                .baseUri("http://localhost:" + port)
                .body(new CredentialsDto(username, "asd123"))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/signin")
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .as(TokenDto.class);

        assertThat(firstToken.getToken(), is(firstToken.getToken()));
    }

    @Test
    void signing_with_inexistent_user() {
        given()
                .baseUri("http://localhost:" + port)
                .body(new CredentialsDto("idontexist", "asdasdasd"))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/signin")
                .then()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @Test
    void signup_and_signin_with_wrong_password() {
        var username = UUID.randomUUID().toString();

        given()
                .baseUri("http://localhost:" + port)
                .body(new CredentialsDto(username, "asd123"))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/signup")
                .then()
                .assertThat()
                .statusCode(HTTP_CREATED);

        given()
                .baseUri("http://localhost:" + port)
                .body(new CredentialsDto(username, "asdasd"))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/signin")
                .then()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @Test
    void access_without_token() {
        given()
                .baseUri("http://localhost:" + port)
                .body(new TicketCreationRequestDto(3))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/tickets")
                .then()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @Test
    void access_with_wrong_header() {
        given()
                .baseUri("http://localhost:" + port)
                .header(new Header("Authorization", "asdasdasd"))
                .body(new TicketCreationRequestDto(3))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/tickets")
                .then()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @Test
    void access_with_wrong_token() {
        given()
                .baseUri("http://localhost:" + port)
                .header(new Header("Authorization", "Bearer: asdasdasd"))
                .body(new TicketCreationRequestDto(3))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/tickets")
                .then()
                .assertThat()
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @Test
    void access_with_correct_token() {        var username = UUID.randomUUID().toString();
        given()
                .baseUri("http://localhost:" + port)
                .body(new CredentialsDto(username, "asd123"))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/signup")
                .then()
                .assertThat()
                .statusCode(HTTP_CREATED);

        var token = given()
                .baseUri("http://localhost:" + port)
                .body(new CredentialsDto(username, "asd123"))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/signin")
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .as(TokenDto.class)
                .getToken();

        given()
                .baseUri("http://localhost:" + port)
                .header(new Header("Authorization", "Bearer: " + token))
                .body(new TicketCreationRequestDto(3))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/tickets")
                .then()
                .assertThat()
                .statusCode(HTTP_OK);
    }
}