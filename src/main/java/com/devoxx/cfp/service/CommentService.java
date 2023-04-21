package com.devoxx.cfp.service;

import com.devoxx.cfp.repository.CommentRepository;
import com.devoxx.cfp.service.dto.CommentDTO;
import com.devoxx.cfp.service.mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final Logger log = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;

    private static final CommentMapper commentMapper = CommentMapper.INSTANCE;

    public CommentService(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Get one comment by id.
     * @param id the id of the entity
     * @return the entity
     */
    public CommentDTO findById(final Long id) {
        log.debug("Request to get comment by id : {}", id);

        return commentRepository.findById(id)
            .map(commentMapper::toDto)
            .orElseThrow();
    }

    /**
     * Get all the comments.
     *
     * @return the list of entities.
     */
    public List<CommentDTO> findAll() {
        log.debug("Request to get all comments");

        return commentRepository.findAll()
            .stream()
            .map(commentMapper::toDto)
            .toList();
    }
}
