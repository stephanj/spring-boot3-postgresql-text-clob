package com.devoxx.cfp.service.dto;

import com.devoxx.cfp.service.dto.views.Detail;
import com.devoxx.cfp.service.dto.views.List;
import com.fasterxml.jackson.annotation.JsonView;

public record ProposalDTO(
    @JsonView({Detail.class, List.class})
    Long id,
    @JsonView({Detail.class, List.class})
    String title,
    @JsonView({Detail.class})
    String state,
    @JsonView({Detail.class})
    String description) {
}
