package com.example.backresultados.service;

import com.example.backresultados.entity.TratamientoXResultado;

import java.util.List;
import java.util.UUID;

public interface TratamientoXResultadoService {
    List<TratamientoXResultado> findTratamientoXResultadoAll();
    //TratamientoXResultado getTratamientoXResultado(UUID idtratamiento,UUID idresultado);
    TratamientoXResultado getTratamientoXResultado(UUID id);
    TratamientoXResultado createTratamientoXResultado(TratamientoXResultado tratamientoxresultado);
    TratamientoXResultado updateTratamientoXResultado(TratamientoXResultado tratamientoxresultado);
    String deleteTratamientoXResultado(UUID id);
    List<TratamientoXResultado> findAllByIdtratamientoAndIdresultado(UUID idtratamiento, UUID idresultado);
}
