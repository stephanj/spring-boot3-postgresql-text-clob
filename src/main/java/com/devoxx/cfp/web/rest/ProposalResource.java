package com.devoxx.cfp.web.rest;

import com.devoxx.cfp.domain.enumeration.ProposalState;
import com.devoxx.cfp.service.ProposalService;
import com.devoxx.cfp.service.dto.ProposalDTO;
import com.devoxx.cfp.service.dto.views.Detail;
import com.devoxx.cfp.service.dto.views.List;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing proposals.
 */
@RestController
@RequestMapping("/api")
public class ProposalResource {

    private final Logger log = LoggerFactory.getLogger(ProposalResource.class);

    private final ProposalService proposalService;
    public ProposalResource(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @JsonView({List.class})
    @GetMapping("/proposals")
    public ResponseEntity<java.util.List<ProposalDTO>> getAllProposals(Pageable pageable) {
        log.debug("REST request to get all Proposals");
        return ResponseEntity.ok(proposalService.findAll(pageable));
    }

    @JsonView({Detail.class})
    @GetMapping("/proposals/{id}")
    public ResponseEntity<ProposalDTO> getProposal(@PathVariable Long id) {
        log.debug("REST request to get Proposal by id : {}", id);
        return ResponseEntity.ok(proposalService.findById(id));
    }

    @JsonView({Detail.class})
    @GetMapping("/proposals/state/{state}")
    public ResponseEntity<Page<ProposalDTO>> getProposalByState(Pageable pageable,
                                                                @PathVariable("state") String state) {
        log.debug("REST request to get Proposal by state : {}", state);

        ProposalState proposalState = ProposalState.valueOf(state.toUpperCase());

        return ResponseEntity.ok(proposalService.findByState(pageable, proposalState));
    }
}
