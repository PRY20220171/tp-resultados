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
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Tratamiento.class)
public class Tratamiento  implements Serializable {
    
    @ApiModelProperty(value="ID del tratamiento del paciente", dataType="uuid", position=1)
    @Id
    @Column("idtratamiento")
    @CassandraType(type = CassandraType.Name.UUID)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKey
    private UUID id;

    @ApiModelProperty(value="Es el medicamento recetado al paciente", dataType="text", position=2)
    @NotEmpty(message = "El medicamento no puede ser vacio")
    @NotNull(message = "El medicamento no puede ser nulo")
    @Column("medicamento")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String medicamento;

    @ApiModelProperty(value="Es la descripcion de uso del medicamento recetado al paciente", dataType="text", position=3)
    @NotEmpty(message = "La descripcion no puede ser vacio")
    @NotNull(message = "La descripcion no puede ser nulo")
    @Column("descripcion")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String descripcion;

}
