package com.devoxx.cfp.service.dto;

import com.devoxx.cfp.service.dto.views.DetailView;
import com.devoxx.cfp.service.dto.views.ListView;
import com.fasterxml.jackson.annotation.JsonView;

public record ProposalDTO(
    @JsonView({DetailView.class, ListView.class})
    Long id,
    @JsonView({DetailView.class, ListView.class})
    String title,
    @JsonView({DetailView.class})
    String state,
    @JsonView({DetailView.class})
    String description) {
}
