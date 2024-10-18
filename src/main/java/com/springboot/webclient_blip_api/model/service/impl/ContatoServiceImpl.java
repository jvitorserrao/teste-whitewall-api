package com.springboot.webclient_blip_api.model.service.impl;

import com.springboot.webclient_blip_api.api.exception.NotificationException;
import com.springboot.webclient_blip_api.model.dto.*;
import com.springboot.webclient_blip_api.model.service.ContatoService;
import com.springboot.webclient_blip_api.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ContatoServiceImpl implements ContatoService {

    private final WebClient webClient;

    @Override
    public Page<ContatoDTO> buscarContatosPaginados(PostRequestDTO<ContatoDTO> postRequest, KeyDTO key) {


        if (Objects.isNull(key)) {
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
        ResponseDTO response = webClient.post()
                .uri("/commands")
                .header("Authorization", key.getKey())
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(ResponseDTO.class)
                .block();

        if (Objects.isNull(response) || Objects.isNull(response.getResource()) || response.getResource().getItems().isEmpty()) {
            throw new NotificationException("Nenhum contato encontrado!");
        }

        List<ContatoDTO> contatosList = response.getResource().getItems();

        Pageable pageable = PaginationUtils.applyPagination(postRequest);
        return new PageImpl<>(contatosList, pageable, response.getResource().getTotal());

    }

    @Override
    public ResponseDTO buscarHistoricoContato(String id, KeyDTO key) {

        if (Objects.isNull(key)) {
            throw new NotificationException("Usuário não autenticado!");
        }

        String requestBody = String.format(
                """
                        {
                           "id": "%s",
                           "method": "get",
                           "uri": "/threads/%s"
                        }
                        """,
                UUID.randomUUID().toString(),
                id
        );


        return webClient.post()
                .uri("/commands")
                .header("Authorization", key.getKey())
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(ResponseDTO.class)
                .block();
    }
}