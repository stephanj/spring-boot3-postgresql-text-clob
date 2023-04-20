package com.devoxx.cfp.service;

import com.devoxx.cfp.repository.TrackRepository;
import com.devoxx.cfp.service.dto.TrackDTO;
import com.devoxx.cfp.service.mapper.TrackMapper;
import com.devoxx.cfp.web.rest.TrackResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {

    private final Logger log = LoggerFactory.getLogger(TrackService.class);

    private final TrackRepository trackRepository;

    private static final TrackMapper trackMapper = TrackMapper.INSTANCE;

    public TrackService(final TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public TrackDTO findById(final Long id) {
        log.debug("Request to get Track by id : {}", id);

        return trackRepository.findById(id)
            .map(trackMapper::toDto)
            .orElseThrow();
    }

    /**
     * Get all the tracks.
     *
     * @return the list of entities.
     */
    public List<TrackDTO> findAll() {
        log.debug("Request to get all Tracks");

        return trackRepository.findAll()
            .stream()
            .map(trackMapper::toDto)
            .toList();
    }
}
