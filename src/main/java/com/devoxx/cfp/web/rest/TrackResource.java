package com.devoxx.cfp.web.rest;

import com.devoxx.cfp.service.TrackService;
import com.devoxx.cfp.service.dto.views.DetailView;
import com.devoxx.cfp.service.dto.TrackDTO;
import com.devoxx.cfp.service.dto.views.ListView;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing Track.
 */
@RestController
@RequestMapping("/api")
public class TrackResource {

    private final Logger log = LoggerFactory.getLogger(TrackResource.class);

    private final TrackService trackService;
    public TrackResource(TrackService trackService) {
        this.trackService = trackService;
    }

    /**
     * GET  /tracks : get all the tracks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tracks in body
     */
    @JsonView({ListView.class})
    @GetMapping("/tracks")
    public ResponseEntity<List<TrackDTO>> getAllTracks() {
        log.debug("REST request to get all Tracks");
        return ResponseEntity.ok(trackService.findAll());
    }

    @JsonView({DetailView.class})
    @GetMapping("/tracks/{id}")
    public ResponseEntity<TrackDTO> getTrack(@PathVariable Long id) {
        log.debug("REST request to get all Tracks");
        return ResponseEntity.ok(trackService.findById(id));
    }
}
