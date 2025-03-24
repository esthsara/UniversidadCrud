package com.universidad.repository;


import com.universidad.model.Estudiante;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EstudianteRepository {
    private final Map<Long, Estudiante> estudiantes = new ConcurrentHashMap<>();
    private final AtomicLong idContador = new AtomicLong(1);
    
    public Estudiante save(Estudiante estudiante) {
        if (estudiante.getId() == null) {
            estudiante.setId(idContador.getAndIncrement());
        }
        estudiantes.put(estudiante.getId(), estudiante);
        return estudiante;
    }
    
    public List<Estudiante> findAll() {
        return new ArrayList<Estudiante>(estudiantes.values());
    }
    
    public void deleteById(Long id) {
        estudiantes.remove(id);
    }
    
    // Método para inicializar algunos datos de ejemplo, con la ayuda de LOMBOK
    public void init() {
        Estudiante student1 = Estudiante.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .email("juan.perez@example.com")
                .fechaNacimiento(LocalDate.of(2000, 5, 15))
                .numeroInscripcion("S001")
                .build();
                
        Estudiante student2 = Estudiante.builder()
                .nombre("María")
                .apellido("González")
                .email("maria.gonzalez@example.com")
                .fechaNacimiento(LocalDate.of(2001, 8, 22))
                .numeroInscripcion("S002")  
                .build();
                
        save(student1);
        save(student2);
    }
}