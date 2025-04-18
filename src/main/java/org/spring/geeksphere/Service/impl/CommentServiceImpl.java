package org.spring.geeksphere.Service.impl;

import org.spring.geeksphere.DTO.CommentRequest;
import org.spring.geeksphere.model.Comment;
import org.spring.geeksphere.repository.CommentRepository;
import org.spring.geeksphere.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(String postId, CommentRequest commentRequest) {
        Comment newComment = new Comment();
        newComment.setPostid(postId);
        newComment.setText(commentRequest.getText());
        newComment.setUid(commentRequest.getUid());
        newComment.setUname(commentRequest.getUname());
        return commentRepository.save(newComment);
    }

    @Override
    public List<Comment> getAllCommentsByPostId(String postId) {
        List<Comment> allComments = commentRepository.findAll();
        List<Comment> commentsForPost = new ArrayList<>();

        for (Comment comment : allComments) {
            if (comment.getPostid().equals(postId)) {
                commentsForPost.add(comment);
            }
        }

        return commentsForPost;
    }

    @Override
    public boolean updateComment(String commentId, String userId, CommentRequest commentRequest) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            // Check if the user is authorized to update the comment
            if (comment.getUid().equals(userId)) {
                comment.setText(commentRequest.getText());
                commentRepository.save(comment);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteComment(String commentId, String userId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            if (comment.getUid().equals(userId)) {
                commentRepository.delete(comment);
                return true;
            }
        }
        return false;
    }
} 