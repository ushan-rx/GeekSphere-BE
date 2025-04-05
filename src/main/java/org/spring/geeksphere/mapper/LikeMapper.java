package org.spring.geeksphere.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.spring.geeksphere.DTO.likeComment.LikeDto;
import org.spring.geeksphere.model.Like;

@Mapper(componentModel = "spring")
public abstract class LikeMapper {
    @Mapping(target = "links", ignore = true)   // Ignore links field in DTO
    @Mapping(target = "id", expression = "java(like.getId().toHexString())")
    @Mapping(target = "postId", expression = "java(like.getPostId().toHexString())")
    @Mapping(target = "userId", expression = "java(like.getUserId().toHexString())")
    public abstract LikeDto toDto(Like like);
}