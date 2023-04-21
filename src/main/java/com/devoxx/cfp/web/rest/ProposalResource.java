package com.devoxx.cfp.web.rest;

import com.devoxx.cfp.service.ProposalService;
import com.devoxx.cfp.service.dto.ProposalDTO;
import com.devoxx.cfp.service.dto.views.DetailView;
import com.devoxx.cfp.service.dto.views.ListView;
import com.devoxx.cfp.web.rest.util.PaginationUtil;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.List;
import java.util.Optional;

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

    @JsonView({ListView.class})
    @GetMapping("/proposals")
    public ResponseEntity<java.util.List<ProposalDTO>> getAllProposals(Pageable pageable) {
        log.debug("REST request to get all Proposals");
        return ResponseEntity.ok(proposalService.findAll(pageable));
    }

    @JsonView({DetailView.class})
    @GetMapping("/proposals/{id}")
    public ResponseEntity<ProposalDTO> getProposal(@PathVariable Long id) {
        log.debug("REST request to get Proposal by id : {}", id);
        return ResponseEntity.ok(proposalService.findById(id));
    }

    @JsonView({DetailView.class})
    @GetMapping("/proposals/state/{state}")
    public ResponseEntity<List<ProposalDTO>> getProposalByState(Pageable pageable,
                                                                @PathVariable("state") String state) {
        log.debug("REST request to get Proposal by state : {}", state);
        Optional<Page<ProposalDTO>> foundProposals = proposalService.findByState(pageable, state);

        return foundProposals.map(page -> {
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proposals/state/" + state);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }).orElse(ResponseEntity.notFound().build());
    }
}
