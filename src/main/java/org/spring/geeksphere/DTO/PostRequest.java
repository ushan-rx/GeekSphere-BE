package org.spring.geeksphere.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class PostRequest {
    private String userId;
    private String description;
    private MultipartFile file;
    private List<String> tags;
} 