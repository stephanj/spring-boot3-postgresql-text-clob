package com.devoxx.cfp.service.dto;

import com.devoxx.cfp.service.dto.views.Detail;
import com.devoxx.cfp.service.dto.views.List;
import com.fasterxml.jackson.annotation.JsonView;

public record CommentDTO(
    @JsonView({Detail.class, List.class})
    Long id,
    @JsonView({Detail.class, List.class})
    String state,
    @JsonView({Detail.class, List.class})
    String description) {
}
