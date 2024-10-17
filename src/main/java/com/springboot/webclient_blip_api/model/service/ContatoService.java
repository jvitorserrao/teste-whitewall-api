package com.springboot.webclient_blip_api.model.service;

import com.springboot.webclient_blip_api.model.dto.ContatoDTO;
import jakarta.servlet.http.HttpSession;

public interface ContatoService {
    ContatoDTO buscarContatosPaginados(ContatoDTO dto, HttpSession session);
}
