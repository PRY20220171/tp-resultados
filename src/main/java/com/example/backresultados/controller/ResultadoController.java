package com.example.backresultados.contresultadoler;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.example.backresultados.entity.Resultado;
import com.example.backresultados.service.ResultadoService;
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
@RequestMapping("/resultados")
public class ResultadoContresultadoler {
    @Autowired
    private ResultadoService resultadoService;

    @ApiOperation(value="Obtener un producto por su ID", notes="Provee un mecanismo para obtener todos los datos del resultado por su ID")
    @ApiResponses(value= {
            @ApiResponse(code=200, message="OK", response=Resultado.class),
            @ApiResponse(code=404, message="Not Found", response= ErrorMessage.class),
            @ApiResponse(code=500, message="Internal Server Error", response=ErrorMessage.class)
    })
    @GetMapping
    public ResponseEntity<List<Resultado>> listResultado(@RequestParam(name="idresultado",required = false) String idResultado){
        List<Resultado> resultados=new ArrayList<>();
        if(null==idResultado){
            resultados=resultadoService.findResultadoAll();
            if(resultados.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }
        else{
            resultados = Collections.singletonList(resultadoService.getResultado(UUID.fromString(idResultado)));
        }
        return ResponseEntity.ok(resultados);
    }

    @PostMapping
    public ResponseEntity<Resultado> createResultado(@Valid @RequestBody Resultado resultado, BindingResult result){
        resultado.setId(Uuids.timeBased());
        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.formatMessage(result));
        }
        Resultado resultadocreate = resultadoService.createResultado(resultado);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultadocreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resultado> updateResultado(@PathVariable("id") String id, @RequestBody Resultado resultado){
        resultado.setId(UUID.fromString(id));
        Resultado resultadoDB=resultadoService.updateResultado(resultado);
        if(resultadoDB==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultadoDB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResultado(@PathVariable("id") String id){
        String resultadoDelete=resultadoService.deleteResultado(UUID.fromString(id));
        if(resultadoDelete==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultadoDelete);
    }

    @Autowired
    ProducerService rabbitMQSender;

    @GetMapping(value = "/test")
    public String producer() {
        rabbitMQSender.sendMsg(new Resultado());
        return "Message sent to the RabbitMQ JavaInUse Successfully";
    }



}
