package com.example.backresultados.repository;

import com.example.backresultados.entity.TratamientoResultadoKey;
import com.example.backresultados.entity.TratamientoXResultado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface TratamientoXResultadoRepository extends CrudRepository<TratamientoXResultado, TratamientoResultadoKey> {
    List<TratamientoXResultado> findAllByTratamientoResultadoKey_Idresultado(UUID idresultado);
    List<TratamientoXResultado> findAllByTratamientoResultadoKey_Idtratamiento(UUID idtratamiento);

}
