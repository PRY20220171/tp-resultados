package com.example.backresultados.entity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

//import javax.persistence.*;
import javax.persistence.EmbeddedId;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = TratamientoXResultado.class)
public class TratamientoXResultado  implements Serializable {

    @EmbeddedId
    DiagnosticoPruebaKey idtratxresul;
/*
    @ManyToOne
    @MapsId("idtratamiento")
    @JoinColumn(name = "idtratamiento")
    
    @ManyToOne
    @MapsId("idresultado")
    @JoinColumn(name = "idresultado")
*/  
    @ApiModelProperty(value="Es el ID del tratamiento del paciente", dataType="uuid", position=1)
    @NotEmpty(message = "El ID del tratamiento no puede ser vacio")
    @NotNull(message = "El ID del tratamiento no puede ser nulo")
    //@Column("idtratamiento")
    @MapsId("idtratamiento")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID idtratamiento;

    @ApiModelProperty(value="Es el ID del resultado del tratamiento del paciente", dataType="uuid", position=2)
    @NotEmpty(message = "El ID del resultado no puede ser vacio")
    @NotNull(message = "El ID del resultado no puede ser nulo")
    //@Column("idresultado")
    @MapsId("idresultado")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID idresultado;

    @ApiModelProperty(value="Es el motivo por el que determinado resultado necesita determinado tratamiento", dataType="text", position=3)
   // @NotEmpty(message = "El motivo no puede ser vacio")
   // @NotNull(message = "El motivo no puede ser nulo")
    @Column( "motivo")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String motivo;

}
