package com.example.backresultados.controller;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.backresultados.entity.Tratamiento;
import com.example.backresultados.service.TratamientoService;
import com.example.backresultados.service.ProducerService;
import com.example.backresultados.util.ErrorMessage;
import com.example.backresultados.util.Message;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tratamientos")
public class TratamientoController {
    @Autowired
    private TratamientoService tratamientoService;

    @ApiOperation(value="Obtener un producto por su ID", notes="Provee un mecanismo para obtener todos los datos del tratamiento por su ID")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="OK", response=Tratamiento.class),
            @ApiResponse(code=404, message="Not Found", response= ErrorMessage.class),
            @ApiResponse(code=500, message="Internal Server Error", response=ErrorMessage.class)
    })
    @GetMapping
    public ResponseEntity<List<Tratamiento>> listTratamiento(@RequestParam(name="idtratamiento",required = false) String idTratamiento){
        List<Tratamiento> tratamientos=new ArrayList<>();
        if(null==idTratamiento){
            tratamientos=tratamientoService.findTratamientoAll();
            if(tratamientos.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }
        else{
            tratamientos = Collections.singletonList(tratamientoService.getTratamiento(UUID.fromString(idTratamiento)));
        }
        return ResponseEntity.ok(tratamientos);
    }

    @PostMapping
    public ResponseEntity<Tratamiento> createTratamiento(@Valid @RequestBody Tratamiento tratamiento, BindingResult result){
        tratamiento.setId(Uuids.timeBased());
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.formatMessage(result));
        }
        Tratamiento tratamientocreate = tratamientoService.createTratamiento(tratamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(tratamientocreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tratamiento> updateTratamiento(@PathVariable("id") String id, @RequestBody Tratamiento tratamiento){
        tratamiento.setId(UUID.fromString(id));
        Tratamiento tratamientoDB=tratamientoService.updateTratamiento(tratamiento);
        if(tratamientoDB==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tratamientoDB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTratamiento(@PathVariable("id") String id){
        String tratamientoDelete=tratamientoService.deleteTratamiento(UUID.fromString(id));
        if(tratamientoDelete==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tratamientoDelete);
    }

    @Autowired
    ProducerService rabbitMQSender;

    @GetMapping(value = "/test")
    public String producer() {
        rabbitMQSender.sendMsg(new Tratamiento());
        return "Message sent to the RabbitMQ JavaInUse Successfully";
    }



}
