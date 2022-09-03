package com.example.backresultados.contresultadoler;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.backresultados.entity.TratamientoXResultado;
import com.example.backresultados.service.TratamientoXResultadoService;
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

@RestContresultadoler
@RequestMapping("/tratamientosxresultados")
public class TratamientoXResultadoContresultadoler {
    @Autowired
    private TratamientoXResultadoService tratamientoxresultadoService;

    @ApiOperation(value="Obtener un producto por su ID", notes="Provee un mecanismo para obtener todos los datos de una tratamientoxresultado por su ID")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="OK", response=TratamientoXResultado.class),
            @ApiResponse(code=404, message="Not Found", response= ErrorMessage.class),
            @ApiResponse(code=500, message="Internal Server Error", response=ErrorMessage.class)
    })
    @GetMapping
    public ResponseEntity<List<TratamientoXResultado>> listTratamientoXResultado(@RequestParam(name="idtratxresul",required = false) String idTratxresul){
        List<TratamientoXResultado> tratamientoxresultados=new ArrayList<>();
        if(null==idTratxresul){
            tratamientoxresultados=tratamientoxresultadoService.findTratamientoXResultadoAll();
            if(tratamientoxresultados.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }
        else{
            tratamientoxresultados = Collections.singletonList(tratamientoxresultadoService.getTratamientoXResultado(UUID.fromString(idTratxresul)));
        }
        return ResponseEntity.ok(tratamientoxresultados);
    }

    @PostMapping
    public ResponseEntity<TratamientoXResultado> createTratamientoXResultado(@Valid @RequestBody TratamientoXResultado tratamientoxresultado, BindingResult result){
        tratamientoxresultado.setId(Uuids.timeBased());
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.formatMessage(result));
        }
        TratamientoXResultado tratamientoxresultadocreate = tratamientoxresultadoService.createTratamientoXResultado(tratamientoxresultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(tratamientoxresultadocreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TratamientoXResultado> updateTratamientoXResultado(@PathVariable("id") String id, @RequestBody TratamientoXResultado tratamientoxresultado){
        tratamientoxresultado.setId(UUID.fromString(id));
        TratamientoXResultado tratamientoxresultadoDB=tratamientoxresultadoService.updateTratamientoXResultado(tratamientoxresultado);
        if(tratamientoxresultadoDB==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tratamientoxresultadoDB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTratamientoXResultado(@PathVariable("id") String id){
        String tratamientoxresultadoDelete=tratamientoxresultadoService.deleteTratamientoXResultado(UUID.fromString(id));
        if(tratamientoxresultadoDelete==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tratamientoxresultadoDelete);
    }

    @Autowired
    ProducerService rabbitMQSender;

    @GetMapping(value = "/test")
    public String producer() {
        rabbitMQSender.sendMsg(new TratamientoXResultado());
        return "Message sent to the RabbitMQ JavaInUse Successfully";
    }



}
