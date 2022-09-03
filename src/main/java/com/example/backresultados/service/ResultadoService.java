package com.example.backresultados.service;

import com.example.backresultados.entity.Resultado;

import java.util.List;
import java.util.UUID;

public interface ResultadoService {
    List<Resultado> findResultadoAll();
    Resultado getResultado(UUID id);
    Resultado createResultado(Resultado resultado);
    Resultado updateResultado(Resultado resultado);
    String deleteResultado(UUID id);
}
