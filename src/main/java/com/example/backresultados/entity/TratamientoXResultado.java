package com.example.backresultados.entity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = TratamientoXResultado.class)
public class TratamientoXResultado  implements Serializable {
    @ApiModelProperty(value="Llave compuesta que vincula tratamiento y resultado", dataType="TratamientoResultadoKey", position=0)
    @PrimaryKey
    private TratamientoResultadoKey tratamientoResultadoKey;
    @ApiModelProperty(value="Es el motivo por el que determinado resultado necesita determinado tratamiento", dataType="text", position=2)
   // @NotEmpty(message = "El motivo no puede ser vacio")
   // @NotNull(message = "El motivo no puede ser nulo")
    @Column("motivo")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String motivo;

}
