package com.devoxx.cfp.service.mapper;

import com.devoxx.cfp.domain.Proposal;
import com.devoxx.cfp.service.dto.ProposalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProposalMapper extends EntityMapper<ProposalDTO, Proposal> {
    ProposalMapper INSTANCE = Mappers.getMapper(ProposalMapper.class);
}
