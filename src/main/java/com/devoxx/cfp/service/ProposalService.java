package com.devoxx.cfp.service;

import com.devoxx.cfp.domain.enumeration.ProposalState;
import com.devoxx.cfp.repository.ProposalRepository;
import com.devoxx.cfp.service.dto.ProposalDTO;
import com.devoxx.cfp.service.mapper.ProposalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposalService {

    private final Logger log = LoggerFactory.getLogger(ProposalService.class);

    private final ProposalRepository proposalRepository;

    private static final ProposalMapper proposalMapper = ProposalMapper.INSTANCE;

    public ProposalService(final ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }

    /**
     * Get one proposal by id.
     * @param id the id of the entity
     * @return the entity
     */
    public ProposalDTO findById(final Long id) {
        log.debug("Request to get proposal by id : {}", id);

        return proposalRepository.findById(id)
            .map(proposalMapper::toDto)
            .orElseThrow();
    }

    /**
     * Get proposals by state.
     * @param state the state of the entity
     * @return the entities by state
     */
    public Page<ProposalDTO> findByState(Pageable pageable, ProposalState state) {
        log.debug("Request to get proposal by state : {}", state);

        return proposalRepository.findProposalsByState(pageable, state)
            .map(proposalMapper::toDto);
    }

    /**
     * Get all the proposals.
     * @return the list of entities.
     */
    public List<ProposalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all proposals");

        return proposalRepository.findAll(pageable)
            .stream()
            .map(proposalMapper::toDto)
            .toList();
    }
}
