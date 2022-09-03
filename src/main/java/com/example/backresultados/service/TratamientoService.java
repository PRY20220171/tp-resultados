package com.example.backtratamientos.service;

import com.example.backtratamientos.entity.Tratamiento;

import java.util.List;
import java.util.UUID;

public interface TratamientoService {
    List<Tratamiento> findTratamientoAll();
    Tratamiento getTratamiento(UUID id);
    Tratamiento createTratamiento(Tratamiento tratamiento);
    Tratamiento updateTratamiento(Tratamiento tratamiento);
    String deleteTratamiento(UUID id);
}
