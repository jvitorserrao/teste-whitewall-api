package com.springboot.webclient_blip_api.model.service.impl;

import com.springboot.webclient_blip_api.api.exception.NotificationException;
import com.springboot.webclient_blip_api.model.dto.ContatoDTO;
import com.springboot.webclient_blip_api.model.dto.KeyDTO;
import com.springboot.webclient_blip_api.model.service.ContatoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;



@Service
@RequiredArgsConstructor
public class ContatoServiceImpl implements ContatoService {

    private final WebClient webClient;

    @Override
    public ContatoDTO buscarContatosPaginados(ContatoDTO dto, HttpSession session) {

        KeyDTO key = (KeyDTO) session.getAttribute("key");

        if (key == null) {
            throw new NotificationException("Usuário não autenticado!");
        }

        String requestBody =
                       """
                        {
                           "id": "{{$guid}}",
                           "to": "postmaster@crm.msging.net",
                           "method": "get",
                           "uri": "/contacts?$skip=0&$take=3"
                        }
                        """;
         webClient.post()
                .uri("/commands")
                .header("Authorization", key.getKey())
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
         return dto;
    }
}