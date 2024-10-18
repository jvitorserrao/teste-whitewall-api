package com.springboot.webclient_blip_api.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostRequestDTO<T> implements Serializable {

    private Integer size = 30;
    private Integer page = 1;
    private List<SortColumn> sortColumns = new ArrayList<>();
    private T filters;
}
