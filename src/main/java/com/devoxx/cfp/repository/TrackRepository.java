package com.devoxx.cfp.repository;

import com.devoxx.cfp.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long>, JpaSpecificationExecutor<Track> {
}
