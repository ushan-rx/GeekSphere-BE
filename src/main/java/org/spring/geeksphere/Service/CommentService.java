package org.spring.geeksphere.Service;

import org.spring.geeksphere.DTO.CommentRequest;
import org.spring.geeksphere.model.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(String postId, CommentRequest commentRequest);

    List<Comment> getAllCommentsByPostId(String postId);

    boolean deleteComment(String commentId, String userId);

    boolean updateComment(String commentId, String userId, CommentRequest commentRequest);
} 