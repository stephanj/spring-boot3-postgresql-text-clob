package com.devoxx.cfp.repository;

import com.devoxx.cfp.domain.Comment;
import com.devoxx.cfp.domain.enumeration.CommentState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
public class CommentRepositoryIT extends AbstractRepositoryIT {

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
