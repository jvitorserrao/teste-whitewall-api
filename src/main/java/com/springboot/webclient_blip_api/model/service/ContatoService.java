package com.springboot.webclient_blip_api.model.service;

import com.springboot.webclient_blip_api.model.dto.ContatoDTO;
import com.springboot.webclient_blip_api.model.dto.IdentityContatoDTO;
import com.springboot.webclient_blip_api.model.dto.PostRequestDTO;
import com.springboot.webclient_blip_api.model.dto.ResponseDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;

public interface ContatoService {

    Page<ContatoDTO> buscarContatosPaginados(PostRequestDTO<ContatoDTO> postRequest, HttpSession session);
    ResponseDTO buscarHistoricoContato(IdentityContatoDTO dto, HttpSession session);
}
