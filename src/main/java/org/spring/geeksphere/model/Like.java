package org.spring.geeksphere.model;

import lombok.Data;
import org.spring.geeksphere.model.enums.PostType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "post")
public class Like {
    @Id
    private String id;
    private String postId;
    private PostType postType;
    private String userId;
    private Instant createdAt;
}

