package com.devoxx.cfp.service.mapper;

import com.devoxx.cfp.domain.Track;
import com.devoxx.cfp.service.dto.TrackDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrackMapper extends EntityMapper<TrackDTO, Track> {
    TrackMapper INSTANCE = Mappers.getMapper(TrackMapper.class);
}
