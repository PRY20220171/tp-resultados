package com.example.backresultados.service;

import com.example.backresultados.entity.Resultado;

import java.util.UUID;

public interface ConsumerService {
    Object consumerMessage(String objId) throws Exception;
}
