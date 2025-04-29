package com.universidad.service;

import com.universidad.model.Materia;
import java.util.List;

public interface IMateriaService {
    List<Materia> obtenerTodasLasMaterias();
    Materia obtenerMateriaPorId(Long id);
    Materia obtenerMateriaPorCodigoUnico(String codigoUnico);
    Materia crearMateria(Materia materia);
    Materia actualizarMateria(Long id, Materia materia);
    void eliminarMateria(Long id);
}
