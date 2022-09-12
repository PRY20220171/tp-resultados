package com.example.backresultados.service.impl;

import com.example.backresultados.entity.Resultado;
import com.example.backresultados.service.ProducerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProducerServiceImpl implements ProducerService {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private Environment environment;
    @Autowired
    private DirectExchange exchange;
    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object sendMsg(String idObj) {
        try{
            Object response = amqpTemplate.convertSendAndReceive(exchange.getName(), routingkey, idObj);
            if(response!=null){
                return objectMapper.readValue(response.toString(), Resultado.class);
            }
            else{
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
