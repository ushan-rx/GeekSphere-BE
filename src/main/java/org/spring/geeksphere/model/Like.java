package org.spring.geeksphere.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.spring.geeksphere.model.enums.PostType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Like {
    @Id
    private ObjectId id;
    @Column(name = "post_id")
    private ObjectId postId;
    @Column(name = "post_type")
    private PostType postType;
    @Column(name = "user_id")
    private ObjectId userId;
    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;
}

