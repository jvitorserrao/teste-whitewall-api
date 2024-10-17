package com.springboot.webclient_blip_api.model.service;

import com.springboot.webclient_blip_api.model.dto.KeyDTO;

public interface ValidarKeyService {
    Boolean validarKey(KeyDTO key);
}