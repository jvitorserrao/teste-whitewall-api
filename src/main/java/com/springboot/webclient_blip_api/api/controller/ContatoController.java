package com.springboot.webclient_blip_api.api.controller;

import com.springboot.webclient_blip_api.model.dto.*;
import com.springboot.webclient_blip_api.model.service.ContatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/contato")
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoService contatoService;

    @PostMapping("/listar")
    public PostResponseDTO<ContatoDTO> buscarSolicitacoesDashboardGealog(@RequestBody PostRequestDTO<ContatoDTO> request) {
        KeyDTO key = request.getKey();
        Page<ContatoDTO> listarContatos = contatoService.buscarContatosPaginados(request, key);
        return new PostResponseDTO<>(listarContatos);
    }

    @PostMapping("/historico/{id}")
    public ResponseEntity<ResponseDTO> buscarHistoricoContato(@RequestParam String id, @RequestBody PostRequestDTO<ContatoDTO> request) {
        KeyDTO key = request.getKey();

            ResponseDTO responseDTO = contatoService.buscarHistoricoContato(id, key);
            return ResponseEntity.ok(responseDTO);

    }
}