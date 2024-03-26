package pt.nunogneto.string_processor.controllers;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import pt.nunogneto.string_processor.events.IEventPublisher;
import pt.nunogneto.string_processor.events.IEventSubscriber;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class StringInputControllerTest {

    @Inject
    IEventPublisher publisher;

    @Inject
    IEventSubscriber subscriber;

    @Test
    void testStringEndPoint() {

        given()
                .when()
                .body("{\"inputStr\": \"com.SQILLS.assignment an.oth8er  Sample.1nput-Str\"}")
                .contentType("application/json")
                .post("/api/v1/strings")
                .then()
                .statusCode(200);
    }

    @Test
    void testStringMalformed() {
            given()
                    .when()
                    .body("no format")
                    .contentType("application/json")
                    .post("/api/v1/strings")
                    .then()
                    .statusCode(400);
    }

    @Test
    void testStringNoContentType() {
        given()
                .when()
                .body("{\"inputStr\": \"com.SQILLS.assignment an.oth8er  Sample.1nput-Str\"}")
                .post("/api/v1/strings")
                .then()
                .statusCode(415);
    }


}
