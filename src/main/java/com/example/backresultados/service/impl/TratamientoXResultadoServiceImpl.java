package com.example.backresultados.service.impl;

import com.example.backresultados.entity.TratamientoResultadoKey;
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
    public TratamientoXResultado getTratamientoXResultado(UUID idtratamiento,UUID idresultado) {
        return tratamientoxresultadoRepository.findById(new TratamientoResultadoKey(idtratamiento,idresultado)).orElse(null);
    }
    @Override
    public TratamientoXResultado getTratamientoXResultado(TratamientoResultadoKey key) {
        return tratamientoxresultadoRepository.findById(key).orElse(null);
    }

    @Override
    public List<TratamientoXResultado> getTratamientoXResultadoByTratamientoId(UUID id) {
        return tratamientoxresultadoRepository.findAllByTratamientoResultadoKey_Idtratamiento(id);
    }
    @Override
    public List<TratamientoXResultado> getTratamientoXResultadoByResultadoId(UUID id) {
        return tratamientoxresultadoRepository.findAllByTratamientoResultadoKey_Idresultado(id);
    }

    @Override
    public TratamientoXResultado createTratamientoXResultado(TratamientoXResultado tratamientoxresultado) {
        //Aquí irian las validaciones al crear el tratamientoxresultado de ser necesario
        return tratamientoxresultadoRepository.save(tratamientoxresultado);
    }

    @Override
    public TratamientoXResultado updateTratamientoXResultado(TratamientoXResultado tratamientoxresultado) {
        TratamientoXResultado tratamientoxresultadoDB = this.getTratamientoXResultado(tratamientoxresultado.getTratamientoResultadoKey());
        if (tratamientoxresultadoDB == null) {
            return null;
        }
        //Actualizamos los valores del tratamientoxresultado:
        //tratamientoxresultadoDB.setIdtratamiento(tratamientoxresultado.getIdtratamiento());
        //tratamientoxresultadoDB.setIdresultado(tratamientoxresultado.getIdresultado());
        tratamientoxresultadoDB.setMotivo(tratamientoxresultado.getMotivo());
        return tratamientoxresultadoRepository.save(tratamientoxresultado);
    }
    @Override
    public String deleteTratamientoXResultado(UUID idtratamiento,UUID idresultado){
        TratamientoXResultado tratamientoxresultadoDB = this.getTratamientoXResultado(idtratamiento,idresultado);
        if (tratamientoxresultadoDB == null) {
            return null;
        }try{
            tratamientoxresultadoRepository.delete(tratamientoxresultadoDB);
        }catch (Exception e){
            return "ERROR INTERNO";
        }return "ELIMINADO CON EXITO";
    }
    @Override
    public String deleteTratamientoXResultado(TratamientoResultadoKey tratamientoxresultado) {
        TratamientoXResultado tratamientoxresultadoDB = this.getTratamientoXResultado(tratamientoxresultado);
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
}
