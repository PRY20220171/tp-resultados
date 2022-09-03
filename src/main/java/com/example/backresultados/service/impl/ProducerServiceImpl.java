package com.example.backresultados.service.impl;

import com.example.backresultados.entity.Resultado;
import com.example.backresultados.service.ProducerService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProducerServiceImpl implements ProducerService {
     @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendMsg(Resultado object) {
        amqpTemplate.convertSendAndReceive("salud.resultados.exchange","salud.resultados.routingkey",object);
    }
}
