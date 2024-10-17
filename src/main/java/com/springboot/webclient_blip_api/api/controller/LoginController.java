package com.springboot.webclient_blip_api.api.controller;

import com.springboot.webclient_blip_api.model.service.ValidarKeyService;
import com.springboot.webclient_blip_api.model.dto.KeyDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/login")
@RequiredArgsConstructor
public class LoginController {

    private final ValidarKeyService validarKeyService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody KeyDTO key, HttpSession session){
        boolean isValid = validarKeyService.validarKey(key);
        if(isValid){
            session.setAttribute("key", key);
            return ResponseEntity.ok("Login realizado com sucesso!");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Chave inválida!");
        }
    }
}