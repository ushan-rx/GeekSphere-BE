package org.spring.geeksphere.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.spring.geeksphere.DTO.likeComment.LikeDto;
import org.spring.geeksphere.model.Like;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    @Mapping(target = "links", ignore = true)
    LikeDto toDto(Like like);
}