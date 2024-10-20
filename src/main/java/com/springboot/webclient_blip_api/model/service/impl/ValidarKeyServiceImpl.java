package com.springboot.webclient_blip_api.model.service.impl;

import com.springboot.webclient_blip_api.model.service.ValidarKeyService;

import com.springboot.webclient_blip_api.model.dto.KeyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ValidarKeyServiceImpl implements ValidarKeyService {

    private final WebClient webClient;

    @Override
    public Boolean validarKey(KeyDTO key) {

        if(Objects.isNull(key)) {
            return false;
        }
        try {
            Map<String, Object> resource = new HashMap<>();
            resource.put("id", "{{guid}}");
            resource.put("to", "postmaster@msging.net");
            resource.put("method", "get");
            resource.put("uri", "/delegations/postmaster@broadcast.msging.net?envelopeTypes=message");

            String response = webClient.post()
                    .uri("/commands")
                    .header("Authorization", key.getKey())
                    .header("Content-Type", "application/json")
                    .bodyValue(resource)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return Objects.nonNull(response) && !response.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}