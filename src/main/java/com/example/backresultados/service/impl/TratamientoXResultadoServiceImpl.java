package com.example.backresultados.service.impl;

import com.example.backresultados.entity.TratamientoXResultado;
import com.example.backresultados.repository.TratamientoXResultadoRepository;
import com.example.backresultados.service.TratamientoXResultadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TratamientoXResultadoServiceImpl implements TratamientoXResultadoService {
    @Autowired
    private TratamientoXResultadoRepository tratamientoxresultadoRepository;

    @Override
    public List<TratamientoXResultado> findTratamientoXResultadoAll() {
        return (List<TratamientoXResultado>) tratamientoxresultadoRepository.findAll();
    }

    @Override
    public TratamientoXResultado getTratamientoXResultado(UUID id) {
        return tratamientoxresultadoRepository.findById(id).orElse(null);
    }

    @Override
    public TratamientoXResultado createTratamientoXResultado(TratamientoXResultado tratamientoxresultado) {
        //Aquí irian las validaciones al crear el tratamientoxresultado de ser necesario
        return tratamientoxresultadoRepository.save(tratamientoxresultado);
    }

    @Override
    public TratamientoXResultado updateTratamientoXResultado(TratamientoXResultado tratamientoxresultado) {
        TratamientoXResultado tratamientoxresultadoDB = this.getTratamientoXResultado(tratamientoxresultado.getId());
        if (tratamientoxresultadoDB == null) {
            return null;
        }
        //Actualizamos los valores del tratamientoxresultado:
        tratamientoxresultadoDB.setIdtratamiento(tratamientoxresultado.getIdtratamiento());
        tratamientoxresultadoDB.setIdresultado(tratamientoxresultado.getIdresultado());
        tratamientoxresultadoDB.setMotivo(tratamientoxresultado.getMotivo());
        return tratamientoxresultadoRepository.save(tratamientoxresultado);
    }

    @Override
    public String deleteTratamientoXResultado(UUID id) {
        TratamientoXResultado tratamientoxresultadoDB = this.getTratamientoXResultado(id);
        if (tratamientoxresultadoDB == null) {
            return null;
        }
        try{
            tratamientoxresultadoRepository.delete(tratamientoxresultadoDB);
        }catch (Exception e){
            return "ERROR INTERNO";
        }
        return "ELIMINADO CON EXITO";
    }
/*
    @Override
    public List<TratamientoXResultado> findAllByIdtratamientoAndIdresultado(UUID idtratamiento, UUID idresultado){
        return tratamientoxresultadoRepository.findAllByIdtratamientoAndIdresultado("DNI", dni.toString());
    }

 */
}
