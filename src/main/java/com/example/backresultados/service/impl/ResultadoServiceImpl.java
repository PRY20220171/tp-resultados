package com.example.backresultados.service.impl;

import com.example.backresultados.entity.Resultado;
import com.example.backresultados.repository.ResultadoRepository;
import com.example.backresultados.service.ResultadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ResultadoServiceImpl implements ResultadoService {
    @Autowired
    private ResultadoRepository resultadoRepository;

    @Override
    public List<Resultado> findResultadoAll() {
        return (List<Resultado>) resultadoRepository.findAll();
    }

    @Override
    public Resultado getResultado(UUID id) {
        return resultadoRepository.findById(id).orElse(null);
    }

    @Override
    public Resultado createResultado(Resultado resultado) {
        //Aquí irian las validaciones al crear el resultado de ser necesario
        return resultadoRepository.save(resultado);
    }

    @Override
    public Resultado updateResultado(Resultado resultado) {
        Resultado resultadoDB = this.getResultado(resultado.getId());
        if (resultadoDB == null) {
            return null;
        }
        //Actualizamos los valores del resultado:
        resultadoDB.setRegistro(resultado.getRegistro());
        resultadoDB.setDescripcion(resultado.getDescripcion());
        resultadoDB.setEstado(resultado.getEstado());
        return resultadoRepository.save(resultado);
    }

    @Override
    public String deleteResultado(UUID id) {
        Resultado resultadoDB = this.getResultado(id);
        if (resultadoDB == null) {
            return null;
        }
        try{
            resultadoRepository.delete(resultadoDB);
        }catch (Exception e){
            return "ERROR INTERNO";
        }
        return "ELIMINADO CON EXITO";
    }
/*
    @Override
    public Resultado getByDni(Long dni) {
        return resultadoRepository.findAllByPasswordAndEstado("DNI", dni.toString());
    }

    @Override
    public Resultado getByDocExtranjeria(Long estado) {
        return resultadoRepository.findAllByPasswordAndEstado("DOCUMENTO EXTRANJERIA", estado.toString());
    }
 */
}
