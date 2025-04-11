package org.spring.geeksphere.DTO;

import lombok.Data;

@Data
public class CommentRequest {
    private String uid;
    private String uname;
    private String text;
} 