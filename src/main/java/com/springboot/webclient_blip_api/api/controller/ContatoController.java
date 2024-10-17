package com.springboot.webclient_blip_api.api.controller;

import com.springboot.webclient_blip_api.model.dto.ContatoDTO;
import com.springboot.webclient_blip_api.model.service.ContatoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/contato")
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoService contatoService;

    @PostMapping
    public ResponseEntity<ContatoDTO> buscarContatosDeFormaPaginada(@RequestBody ContatoDTO dto, HttpSession session) {
        ContatoDTO contatosPaginados = contatoService.buscarContatosPaginados(dto, session);
        if (contatosPaginados == null) {
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.ok(contatosPaginados);
    }
}