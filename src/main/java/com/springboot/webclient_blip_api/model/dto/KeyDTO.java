package com.springboot.webclient_blip_api.model.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyDTO {

    @NonNull
    private String key;
}