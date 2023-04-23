package com.devoxx.cfp.web.rest;

import com.devoxx.cfp.domain.Comment;
import com.devoxx.cfp.domain.enumeration.CommentState;
import com.devoxx.cfp.repository.AbstractRepositoryIT;
import com.devoxx.cfp.repository.CommentRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Integration tests for the CommentResource REST controller.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class CommentResourceIT extends AbstractRepositoryIT {

    public static final String TEST_COMMENT = "Test comment";
    @LocalServerPort
    Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    CommentRepository commentRepository;

    @Test
    void getComments() {
        Comment comment = new Comment();
        comment.setDescription(TEST_COMMENT);
        comment.setState(CommentState.PUBLIC);

        commentRepository.save(comment);

        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/api/comments")
            .then()
            .statusCode(200)
            .body("$", hasSize(1)) // Validate the size of the list
            .body("[0].description", equalTo(TEST_COMMENT)) // Validate the description field
            .body("[0].state", equalTo("PUBLIC")); // Validate the state field
    }
}
