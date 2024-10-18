package com.springboot.webclient_blip_api.model.service;

import com.springboot.webclient_blip_api.model.dto.*;

import org.springframework.data.domain.Page;

public interface ContatoService {

    Page<ContatoDTO> buscarContatosPaginados(PostRequestDTO<ContatoDTO> postRequest, KeyDTO key);
    ResponseDTO buscarHistoricoContato(String id, KeyDTO key);
}
