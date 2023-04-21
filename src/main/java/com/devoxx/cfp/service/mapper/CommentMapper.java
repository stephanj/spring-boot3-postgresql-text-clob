package com.devoxx.cfp.service.mapper;

import com.devoxx.cfp.domain.Comment;
import com.devoxx.cfp.service.dto.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
}
