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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Resultado.class)
public class Resultado  implements Serializable {

    @ApiModelProperty(value="ID del resultado del paciente", dataType="uuid", position=1)
    @Id
    @Column("idresultado")
    @CassandraType(type = CassandraType.Name.UUID)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKey
    private UUID id;

    @ApiModelProperty(value="Es la fecha de registro del resultado del paciente", dataType="datetime", position=2)
    @NotNull(message = "La fecha de registro no puede ser nulo")
    @Column("registro")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Date registro;

    @ApiModelProperty(value="Es la descripción del resultado del paciente", dataType="text", position=3)
    @NotEmpty(message = "La descripcion no puede ser vacio")
    @NotNull(message = "La descripcion no puede ser nulo")
    @Column( "descripcion")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String descripcion;

    @ApiModelProperty(value="Es el estado del resultado del paciente", dataType="text", position=4)
    @NotEmpty(message = "El estado no puede ser vacio")
    @NotNull(message = "El estado no puede ser nulo")
    @Column("estado")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String estado;

}
