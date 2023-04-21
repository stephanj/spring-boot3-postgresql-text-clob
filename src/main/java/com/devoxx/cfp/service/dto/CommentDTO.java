package com.devoxx.cfp.service.dto;

import com.devoxx.cfp.service.dto.views.DetailView;
import com.devoxx.cfp.service.dto.views.ListView;
import com.fasterxml.jackson.annotation.JsonView;

public record CommentDTO(
    @JsonView({DetailView.class, ListView.class})
    Long id,
    @JsonView({DetailView.class, ListView.class})
    String state,
    @JsonView({DetailView.class, ListView.class})
    String description) {
}
