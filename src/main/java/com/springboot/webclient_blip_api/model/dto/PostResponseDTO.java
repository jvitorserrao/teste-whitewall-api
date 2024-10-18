package com.springboot.webclient_blip_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO<T>  implements Serializable {

    private List<T> results;
    private Long count;
    private Integer page;

    public PostResponseDTO(Page<T> pageRequest) {
        this.results = pageRequest.toList();
        this.count = pageRequest.getTotalElements();
        this.page = pageRequest.getNumber() + 1;
    }

}
