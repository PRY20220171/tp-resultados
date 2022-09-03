package com.example.backresultados.service.impl;

import com.example.backresultados.entity.Tratamiento;
import com.example.backresultados.repository.TratamientoRepository;
import com.example.backresultados.service.TratamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TratamientoServiceImpl implements TratamientoService {
    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Override
    public List<Tratamiento> findTratamientoAll() {
        return (List<Tratamiento>) tratamientoRepository.findAll();
    }

    @Override
    public Tratamiento getTratamiento(UUID id) {
        return tratamientoRepository.findById(id).orElse(null);
    }

    @Override
    public Tratamiento createTratamiento(Tratamiento tratamiento) {
        //Aquí irian las validaciones al crear el tratamiento de ser necesario
        return tratamientoRepository.save(tratamiento);
    }

    @Override
    public Tratamiento updateTratamiento(Tratamiento tratamiento) {
        Tratamiento tratamientoDB = this.getTratamiento(tratamiento.getId());
        if (tratamientoDB == null) {
            return null;
        }
        //Actualizamos los valores del tratamiento:
        tratamientoDB.setMedicamento(tratamiento.getMedicamento());
        tratamientoDB.setDescripcion(tratamiento.getDescripcion());
        return tratamientoRepository.save(tratamiento);
    }

    @Override
    public String deleteTratamiento(UUID id) {
        Tratamiento tratamientoDB = this.getTratamiento(id);
        if (tratamientoDB == null) {
            return null;
        }
        try{
            tratamientoRepository.delete(tratamientoDB);
        }catch (Exception e){
            return "ERROR INTERNO";
        }
        return "ELIMINADO CON EXITO";
    }
/*
    @Override
    public Tratamiento getByDni(Long dni) {
        return tratamientoRepository.findAllByTipoAndEstado("DNI", dni.toString());
    }

    @Override
    public Tratamiento getByDocExtranjeria(Long estado) {
        return tratamientoRepository.findAllByTipoAndEstado("DOCUMENTO EXTRANJERIA", estado.toString());
    }
 */
}
