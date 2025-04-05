package org.spring.geeksphere.controller;

import lombok.RequiredArgsConstructor;
import org.spring.geeksphere.DTO.PageResponse;
import org.spring.geeksphere.DTO.likeComment.LikeDto;
import org.spring.geeksphere.Service.LikeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<PageResponse<LikeDto>> getAllLikes(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(likeService.getAllLikes(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LikeDto> getLikeById(@PathVariable String id) {
        return ResponseEntity.ok(likeService.getLike(id));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PageResponse<LikeDto>> getLikesByPost(
            @PathVariable String postId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(likeService.getLikesByPost(postId, pageable));
    }
}