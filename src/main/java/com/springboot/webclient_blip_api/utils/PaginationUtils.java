package com.springboot.webclient_blip_api.utils;

import com.springboot.webclient_blip_api.model.dto.PostRequestDTO;
import com.springboot.webclient_blip_api.model.dto.SortColumn;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaginationUtils {
    private static Sort sortBuild(List<SortColumn> properties, boolean nativeQuery) {
        List<Sort.Order> orders = new ArrayList<>();
        properties.forEach(property -> orders.add(
                Sort.Order.by(nativeQuery ?
                                property.getProperty().replaceAll("[A-Z]", "_$0").toLowerCase()
                                : property.getProperty())
                        .with(Sort.Direction.fromString(property.getDirection()))));
        return Sort.by(orders);
    }
    public static <T> Pageable applyPaginationNative(PostRequestDTO<T> postRequestDTO) {
        return PageRequest.of(
                Optional.of(postRequestDTO.getPage() - 1).orElse(0),
                Optional.ofNullable(postRequestDTO.getSize()).orElse(30),
                Optional.of(sortBuild(postRequestDTO.getSortColumns(), true)).orElse(Sort.unsorted()));
    }

    public static <T> Pageable applyPagination(PostRequestDTO<T> postRequestDTO) {
        return PageRequest.of(
                Optional.of(postRequestDTO.getPage() - 1).orElse(0),
                Optional.ofNullable(postRequestDTO.getSize()).orElse(30),
                Optional.of(sortBuild(postRequestDTO.getSortColumns(), false)).orElse(Sort.unsorted()));
    }
}
