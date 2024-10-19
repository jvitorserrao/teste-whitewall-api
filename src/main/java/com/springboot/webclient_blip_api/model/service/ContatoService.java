package com.springboot.webclient_blip_api.model.service;

import com.springboot.webclient_blip_api.model.dto.*;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ContatoService {

    Page<ContatoDTO> buscarContatosPaginados(PostRequestDTO<ContatoDTO> postRequest, KeyDTO key);
    List<ComentariosContatoDTO> buscarComentariosContato(String id, KeyDTO key);
    TextComentarioDTO adicionarComentarios(String id, TextComentarioDTO request, KeyDTO key);
}
