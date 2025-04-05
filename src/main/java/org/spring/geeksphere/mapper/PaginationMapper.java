package org.spring.geeksphere.mapper;

import org.spring.geeksphere.DTO.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaginationMapper {

    /**
     * Converts a Page object to a PageResponse object.
     *
     * @param <T> the type of the content in the Page object
     * @param <R> the type of the content in the PageResponse object
     * @param page the Page object to be converted
     * @param mapper a function to map the content of the Page object to the content of the PageResponse object
     * @return the converted PageResponse object
     */
    public <T, R> PageResponse<R> toPageResponse(Page<T> page, Function<T, R> mapper) {
        PageResponse<R> response = new PageResponse<>();
        response.setContent(page.getContent().stream().map(mapper).toList());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());
        return response;
    }

    /**
     * Adds HATEOAS pagination links to the given PageResponse object.
     *
     * @param <T> the type of the content in the PageResponse
     * @param response the PageResponse object to which links will be added
     * @param page the Page object containing pagination information
     * @param controllerClass the controller class containing the method to generate links
     * @param methodName the name of the method in the controller class to generate links
     * @throws NoSuchMethodException if the method with the specified name is not found in the controller class
     */
    public <T> void addPaginationLinks(PageResponse<T> response, Page<?> page,
                                       Class<?> controllerClass, String methodName) throws NoSuchMethodException {
        response.add(linkTo(methodOn(controllerClass).getClass()
                .getMethod(methodName, Pageable.class))
                .withSelfRel());

        if (page.hasNext()) {
            response.add(linkTo(methodOn(controllerClass).getClass()
                    .getMethod(methodName, Pageable.class))
                    .withRel("next"));
        }
        if (page.hasPrevious()) {
            response.add(linkTo(methodOn(controllerClass).getClass()
                    .getMethod(methodName, Pageable.class))
                    .withRel("prev"));
        }
    }
}