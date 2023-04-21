package com.devoxx.cfp.web.rest;

import com.devoxx.cfp.service.CommentService;
import com.devoxx.cfp.service.dto.views.Detail;
import com.devoxx.cfp.service.dto.CommentDTO;
import com.devoxx.cfp.service.dto.views.List;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Comment.
 */
@RestController
@RequestMapping("/api")
public class CommentResource {

    private final Logger log = LoggerFactory.getLogger(CommentResource.class);

    private final CommentService commentService;
    public CommentResource(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * GET  /tracks : get all the comments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tracks in body
     */
    @JsonView({List.class})
    @GetMapping("/comments")
    public ResponseEntity<java.util.List<CommentDTO>> getAllComments() {
        log.debug("REST request to get all Comments");
        return ResponseEntity.ok(commentService.findAll());
    }

    @JsonView({Detail.class})
    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long id) {
        log.debug("REST request to get Comment by id : {}", id);
        return ResponseEntity.ok(commentService.findById(id));
    }
}
