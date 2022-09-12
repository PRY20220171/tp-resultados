package com.example.backresultados.service;

import com.example.backresultados.entity.TratamientoResultadoKey;
import com.example.backresultados.entity.TratamientoXResultado;

import java.util.List;
import java.util.UUID;

public interface TratamientoXResultadoService {
    List<TratamientoXResultado> findTratamientoXResultadoAll();
    List<TratamientoXResultado> getTratamientoXResultadoByTratamientoId(UUID id);
    List<TratamientoXResultado> getTratamientoXResultadoByResultadoId(UUID id);
    TratamientoXResultado getTratamientoXResultado(UUID idtratamiento,UUID idresultado);
    TratamientoXResultado getTratamientoXResultado(TratamientoResultadoKey key);
    TratamientoXResultado createTratamientoXResultado(TratamientoXResultado tratamientoxresultado);
    TratamientoXResultado updateTratamientoXResultado(TratamientoXResultado tratamientoxresultado);
    String deleteTratamientoXResultado(UUID idtratamiento,UUID idresultado);
    String deleteTratamientoXResultado(TratamientoResultadoKey key);
    //List<TratamientoXResultado> findAllByIdtratamientoAndIdresultado(UUID idtratamiento, UUID idresultado);
}
