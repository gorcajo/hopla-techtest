package com.example.techtest.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TicketControllerTest_Int {

    @LocalServerPort
    private int port;

    @Test
    void create_and_retrieve_ticket() {
        var ticket = given()
                .baseUri("http://localhost:" + port)
                .body(new TicketCreationRequestDto(3))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/tickets")
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .as(TicketDto.class);

        given()
                .baseUri("http://localhost:" + port)
                .when()
                .get("/v1/tickets/" + ticket.getId())
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .as(TicketDto.class);
    }

    @Test
    void upload_image_to_ticket() {
        var ticket = given()
                .baseUri("http://localhost:" + port)
                .body(new TicketCreationRequestDto(3))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/tickets")
                .then()
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .as(TicketDto.class);

        given()
                .baseUri("http://localhost:" + port)
                .body(new ImageDto("image", ""))
                .contentType(ContentType.JSON)
                .when()
                .post("/v1/tickets/" + ticket.getId() + "/images")
                .then()
                .assertThat()
                .statusCode(HTTP_ACCEPTED);
    }

    @Test
    void list_tickets() {
        for (int i = 0; i < 3; i++) {
            given()
                    .baseUri("http://localhost:" + port)
                    .body(new TicketCreationRequestDto(3))
                    .contentType(ContentType.JSON)
                    .when()
                    .post("/v1/tickets")
                    .then()
                    .assertThat()
                    .statusCode(HTTP_OK)
                    .extract()
                    .as(TicketDto.class);
        }

        // TODO: Verificar el contenido:
        given()
                .baseUri("http://localhost:" + port)
                .when()
                .get("/v1/tickets")
                .then()
                .assertThat()
                .statusCode(HTTP_OK);
    }

    @Test
    void get_inexistent_ticket() {
        given()
                .baseUri("http://localhost:" + port)
                .when()
                .get("/v1/tickets/-1")
                .then()
                .assertThat()
                .statusCode(HTTP_NOT_FOUND);
    }
}