package com.devoxx.cfp.repository;

import com.devoxx.cfp.domain.Proposal;
import com.devoxx.cfp.domain.enumeration.ProposalState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long>, JpaSpecificationExecutor<Proposal> {

    Page<Proposal> findProposalsByState(Pageable pageable, ProposalState state);
}
