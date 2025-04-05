package org.spring.geeksphere.repository;

import org.bson.types.ObjectId;
import org.spring.geeksphere.model.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends MongoRepository<Like, ObjectId> {
    Page<Like> findByUserId(ObjectId userId, Pageable pageable);
    Page<Like> findByPostId(ObjectId postId, Pageable pageable);
    Page<Like> findAllByOrderByCreatedAtDesc(Pageable pageable);
    boolean existsByPostIdAndUserId(ObjectId postId, ObjectId userId);
}