package com.devoxx.cfp.service;

import com.devoxx.cfp.domain.Proposal;
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
import java.util.Optional;

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

    /**import java.util.Optional;

     /**
     * Finds all proposals by their state.
     *
     * @param pageable the pagination information
     * @param state the state of the proposal as a string
     * @return a list of ProposalDTO objects with the specified state
     */
    public Optional<Page<ProposalDTO>> findByState(Pageable pageable, String state) {
        log.info("Request to get proposal by state : '{}'", state);

        Optional<ProposalState> proposalStateOpt = toProposalState(state);
        if (proposalStateOpt.isEmpty()) {
            log.error("Invalid proposal state: {}", state);
            return Optional.empty();
        }

        Page<Proposal> foundProposals;
        try {
            foundProposals = proposalRepository.findProposalsByState(pageable, proposalStateOpt.get());
        } catch (Exception e) {
            log.error("Error fetching proposals by state: {}", state, e);
            return Optional.empty();
        }

        log.info("Repository size: {}", foundProposals.getTotalElements());

        return Optional.of(foundProposals.map(proposalMapper::toDto));
    }

    /**
     * Converts a string to a ProposalState enum value.
     *
     * @param state the state of the proposal as a string
     * @return an Optional containing the corresponding ProposalState, or an empty Optional if the string is invalid
     */
    private Optional<ProposalState> toProposalState(String state) {
        try {
            return Optional.of(ProposalState.valueOf(state));
        } catch (IllegalArgumentException | NullPointerException e) {
            return Optional.empty();
        }
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
