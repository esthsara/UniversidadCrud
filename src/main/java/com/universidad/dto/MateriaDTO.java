package com.universidad.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MateriaDTO implements Serializable {

    private Long id;

    @NotBlank(message = "El nombre de la materia es obligatorio")
    private String nombreMateria;

    @NotBlank(message = "El código único es obligatorio")
    private String codigoUnico;

    @NotNull(message = "Los créditos no pueden ser nulos")
    private Integer creditos;

    @NotNull(message = "Debe especificar el ID del docente")
    private Long docenteId;

    /**
     * Lista de IDs de materias que son prerequisitos para esta materia.
     */
    private List<Long> prerequisitos;
}
