package com.springboot.webclient_blip_api.model.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.webclient_blip_api.api.exception.NotificationException;
import com.springboot.webclient_blip_api.model.dto.*;
import com.springboot.webclient_blip_api.model.service.ContatoService;
import com.springboot.webclient_blip_api.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ContatoServiceImpl implements ContatoService {

    private final WebClient webClient;

    @Override
    public Page<ContatoDTO> buscarContatosPaginados(PostRequestDTO<ContatoDTO> postRequest, KeyDTO key) {


        if (Objects.isNull(key)) {
            throw new NotificationException("Usuário não autenticado!");
        }

        Map<String, Object> resource = new HashMap<>();
        resource.put("id", UUID.randomUUID().toString());
        resource.put("to", "postmaster@crm.msging.net");
        resource.put("method", "get");
        resource.put("uri", "/contacts?$skip=0&$take=100");

        ResponseDTO response = webClient.post()
                .uri("/commands")
                .header("Authorization", key.getKey())
                .header("Content-Type", "application/json")
                .bodyValue(resource)
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
    public List<ComentariosContatoDTO> buscarComentariosContato(String id, KeyDTO key) {
        if (Objects.isNull(key)) {
            throw new NotificationException("Usuário não autenticado!");
        }

        Map<String, Object> resource = new HashMap<>();
        resource.put("id", UUID.randomUUID().toString());
        resource.put("to", "postmaster@crm.msging.net");
        resource.put("method", "get");
        resource.put("uri", String.format("/contacts/%s/comments", id));


        Map<String, Object> response = webClient.post()
                .uri("/commands")
                .header("Authorization", key.getKey())
                .header("Content-Type", "application/json")
                .bodyValue(resource)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        if (response == null || response.get("resource") == null) {
            throw new NotificationException("Recurso não encontrado!");
        }

        List<Map<String, Object>> items = (List<Map<String, Object>>) ((Map<String, Object>) response.get("resource")).get("items");
        if (items == null) {
            throw new NotificationException("Nenhum comentário encontrado!");
        }

        return items.stream()
                .map(item -> new ComentariosContatoDTO(
                        (String) item.get("id"),
                        (String) item.get("storageDate"),
                        (String) item.get("content")
                ))
                .collect(Collectors.toList());
    }

    @Override
    public TextComentarioDTO adicionarComentarios(String id, TextComentarioDTO request, KeyDTO key) {
        if (Objects.isNull(key)) {
            throw new NotificationException("Usuário não autenticado!");
        }

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> resource = new HashMap<>();
        resource.put("content", request.getTexto());

        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("id", "{{$guid}}");
        requestBodyMap.put("to", "postmaster@crm.msging.net");
        requestBodyMap.put("method", "set");
        requestBodyMap.put("uri", "/contacts/" + id + "/comments");
        requestBodyMap.put("type", "application/vnd.iris.crm.comment+json");
        requestBodyMap.put("resource", resource);

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyMap);
        } catch (Exception e) {
            throw new NotificationException("Erro ao criar o corpo da requisição: " + e.getMessage());
        }

        try {
            return webClient.post()
                    .uri("/commands")
                    .header("Authorization", key.getKey())
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(status -> !status.is2xxSuccessful(), clientResponse -> {
                        throw new NotificationException("Erro na requisição: " + clientResponse.statusCode());
                    })
                    .bodyToMono(TextComentarioDTO.class)
                    .block();
        } catch (Exception e) {
            throw new NotificationException("Erro ao adicionar comentário: " + e.getMessage());
        }
    }
}
