package com.devoxx.cfp.repository;

import com.devoxx.cfp.domain.Comment;
import com.devoxx.cfp.domain.enumeration.CommentState;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
public class CommentRepositoryIT {


    @Container
    public static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:14.7-alpine");

    @DynamicPropertySource
    static void configureTestContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", database::getJdbcUrl);
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        database.start();
    }

    @AfterAll
    static void afterAll() {
        database.stop();
    }

    @Autowired
    CommentRepository repository;

    public static final String TEST_COMMENT = "Test comment";

    @Test
    void simpleSaveTest() {
        assertTrue(database.isRunning());

        var comment = new Comment();
        comment.setState(CommentState.PRIVATE);
        comment.setDescription(TEST_COMMENT);
        repository.save(comment);

        List<Comment> comments = repository.findAll();
        assertEquals(1, comments.size());
        assertEquals(TEST_COMMENT, comments.get(0).getDescription());
    }
}
