package com.springboot.webclient_blip_api.api.controller;

import com.springboot.webclient_blip_api.model.dto.*;
import com.springboot.webclient_blip_api.model.service.ContatoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoService contatoService;

    @PostMapping
    public PostResponseDTO<ContatoDTO> buscarSolicitacoesDashboardGealog(@RequestBody PostRequestDTO<ContatoDTO> request, HttpSession session) {
        Page<ContatoDTO> listarContatos = contatoService.buscarContatosPaginados(request, session);
        return new PostResponseDTO<>(listarContatos);
    }

    @PostMapping("/contato")
    public ResponseEntity<ResponseDTO> buscarHistoricoContato(@RequestBody IdentityContatoDTO dto, HttpSession session) {
        ResponseDTO responseDTO = contatoService.buscarHistoricoContato(dto, session);
        return ResponseEntity.ok(responseDTO);
    }
}