package org.spring.geeksphere.DTO.likeComment;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.spring.geeksphere.model.enums.PostType;

@Data
public class LikeRequest {
    @NotNull
    private String postId;

    @NotNull
    private PostType postType;

    @NotNull
    private String userId;
}