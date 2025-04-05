package org.spring.geeksphere.DTO.likeComment;

import lombok.*;
import org.spring.geeksphere.model.enums.PostType;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class LikeDto extends RepresentationModel<LikeDto> {
    private String id;
    private String postId;
    private PostType postType;
    private String userId;
    private Instant createdAt;

//   custom constructor with links for builder
    @Builder
    public LikeDto(String id, String postId, PostType postType, String userId, Instant createdAt, List<Link> links) {
        super(links != null ? links : List.of()); // Initialize with an empty list if null
        this.id = id;
        this.postId = postId;
        this.postType = postType;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
