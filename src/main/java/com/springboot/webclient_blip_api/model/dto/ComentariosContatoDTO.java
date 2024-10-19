package com.springboot.webclient_blip_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentariosContatoDTO {

    private String id;
    private String storageDate;
    private String content;
}
