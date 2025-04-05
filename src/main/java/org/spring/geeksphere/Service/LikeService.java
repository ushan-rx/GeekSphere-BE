package org.spring.geeksphere.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.spring.geeksphere.DTO.PageResponse;
import org.spring.geeksphere.DTO.likeComment.LikeDto;
import org.spring.geeksphere.controller.LikeController;
import org.spring.geeksphere.exception.ResourceNotFoundException;
import org.spring.geeksphere.mapper.LikeMapper;
import org.spring.geeksphere.mapper.PaginationMapper;
import org.spring.geeksphere.model.Like;
import org.spring.geeksphere.repository.LikeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final PaginationMapper paginationMapper;

    public PageResponse<LikeDto> getAllLikes(Pageable pageable) {
        log.debug("Fetching likes page: {}", pageable);
        Page<Like> likes = likeRepository.findAllByOrderByCreatedAtDesc(pageable);
        PageResponse<LikeDto> response = paginationMapper.toPageResponse(
                likes,
                like -> addLinks(likeMapper.toDto(like)) // Convert Like to LikeDto and add links
        );

        try {
            // Add pagination links to the response
            paginationMapper.addPaginationLinks(response, likes, LikeController.class, "getAllLikes");
        } catch (NoSuchMethodException e) {
            log.error("Error adding pagination links", e);
        }

        return response;
    }

    public LikeDto getLike(String id) {
        log.debug("Fetching like with id: {}", id);
        Like like = likeRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Like not found"));
        return addLinks(likeMapper.toDto(like));
    }

    public PageResponse<LikeDto> getLikesByPost(String postId, Pageable pageable) {
        log.debug("Fetching likes for post: {}", postId);
        Page<Like> likes = likeRepository.findByPostId(new ObjectId(postId), pageable);
        PageResponse<LikeDto> response = paginationMapper.toPageResponse(
                likes,
                like -> addLinks(likeMapper.toDto(like))
        );

        try {
            paginationMapper.addPaginationLinks(response, likes, LikeController.class, "getLikesByPost");
        } catch (NoSuchMethodException e) {
            log.error("Error adding pagination links", e);
        }

        return response;
    }

    /**
     * Adds HATEOAS links to the given LikeDto object.
     *
     * @param dto the LikeDto object to which links will be added
     * @return the LikeDto object with added links
     */
    private LikeDto addLinks(LikeDto dto) {
        // Add self link
        dto.add(linkTo(methodOn(LikeController.class).getLikeById(dto.getId())).withSelfRel());
        // Add link to get likes by post
        dto.add(linkTo(methodOn(LikeController.class).getLikesByPost(dto.getPostId(), null))
                .withRel("post-likes"));
        return dto;
    }

}