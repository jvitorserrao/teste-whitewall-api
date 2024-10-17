package com.springboot.webclient_blip_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContatoDTO {

    private String name;
    private String group;
    private String lastMessageDate;
    private String lastUpdateDate;
    private String identity;
    private String gender;
    private ExtrasDTO extras;
}
