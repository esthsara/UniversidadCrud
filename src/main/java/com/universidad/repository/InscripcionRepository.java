package com.universidad.repository;

import com.universidad.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
}
// Este repositorio permite realizar operaciones CRUD sobre la entidad Inscripcion.
// Se extiende de JpaRepository, lo que proporciona métodos para guardar, buscar, eliminar y actualizar inscripciones.
// No se han definido métodos adicionales, pero se pueden agregar según sea necesario.