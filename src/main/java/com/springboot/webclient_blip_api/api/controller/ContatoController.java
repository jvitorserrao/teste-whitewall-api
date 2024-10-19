package com.springboot.webclient_blip_api.api.controller;

import com.springboot.webclient_blip_api.model.dto.*;
import com.springboot.webclient_blip_api.model.service.ContatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/contato")
@RequiredArgsConstructor
public class ContatoController {

    private final ContatoService contatoService;

    @PostMapping
    public PostResponseDTO<ContatoDTO> buscarContatosPaginados(@RequestBody PostRequestDTO<ContatoDTO> request) {
        KeyDTO key = request.getKey();
        Page<ContatoDTO> listarContatos = contatoService.buscarContatosPaginados(request, key);
        return new PostResponseDTO<>(listarContatos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ComentariosContatoDTO>> buscarComentariosContato(@PathVariable String id, @RequestParam KeyDTO key) {
        List<ComentariosContatoDTO> comentarios = contatoService.buscarComentariosContato(id, key);
        return ResponseEntity.ok(comentarios);
    }

    @PostMapping("/adicionar-comentario/{id}")
    public ResponseEntity<TextComentarioDTO> adicionarComentario(@PathVariable String id, @RequestBody TextComentarioDTO request, @RequestParam KeyDTO key) {
        TextComentarioDTO comentarioDTO = contatoService.adicionarComentarios(id, request, key);
        return ResponseEntity.ok(comentarioDTO);
    }
}