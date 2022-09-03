package com.example.backresultados.service;

import com.example.backresultados.entity.Resultado;

public interface ProducerService {
    //Object sendMsg(Long proId) throws Exception;

    void sendMsg(Resultado object);
}
