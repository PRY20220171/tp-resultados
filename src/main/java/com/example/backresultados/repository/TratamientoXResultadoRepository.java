package com.example.backresultados.repository;

import com.example.backresultados.entity.TratamientoXResultado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

//import org.springframework.data.cassandra.repository.Query;
//import org.springframework.data.repository.Repository;
@Repository
public interface TratamientoXResultadoRepository extends CrudRepository<TratamientoXResultado, UUID> {
    //TratamientoXResultado findOneById(String id);
    List<TratamientoXResultado> findAllByIdtratamientoAndIdresultado(UUID idtratamiento, UUID idresultado);
    //@Query("SELECT * from pizza_orders WHERE orderdate = ?0 and zoneid = ?1 ALLOW FILTERING")
    //Order findOrderByOrderDateAndZoneId(LocalDate orderDate, ZoneId zoneId);
}
