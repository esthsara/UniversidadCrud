package com.universidad.service.impl;

import com.universidad.dto.InscripcionDTO;
import com.universidad.model.Estudiante;
import com.universidad.model.Inscripcion;
import com.universidad.model.Materia;
import com.universidad.repository.EstudianteRepository;
import com.universidad.repository.InscripcionRepository;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IInscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscripcionServiceImpl implements IInscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    private InscripcionDTO mapToDTO(Inscripcion inscripcion) {
        if (inscripcion == null) return null;
        return InscripcionDTO.builder()
                .id(inscripcion.getId())
                .estudianteId(inscripcion.getEstudiante().getId())
                .materiaId(inscripcion.getMateria().getId())
                .fechaInscripcion(inscripcion.getFechaInscripcion())
                .build();
    }

    @Override
    @Cacheable(value = "inscripciones")
    public List<InscripcionDTO> obtenerTodasLasInscripciones() {
        return inscripcionRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "inscripcion", key = "#id")
    public InscripcionDTO obtenerInscripcionPorId(Long id) {
        return inscripcionRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    @CachePut(value = "inscripcion", key = "#result.id")
    @CacheEvict(value = "inscripciones", allEntries = true)
    public InscripcionDTO crearInscripcion(InscripcionDTO inscripcionDTO) {
        Estudiante estudiante = estudianteRepository.findById(inscripcionDTO.getEstudianteId())
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        Materia materia = materiaRepository.findById(inscripcionDTO.getMateriaId())
                .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));

        Inscripcion inscripcion = Inscripcion.builder()
                .estudiante(estudiante)
                .materia(materia)
                .fechaInscripcion(inscripcionDTO.getFechaInscripcion())
                .build();

        Inscripcion saved = inscripcionRepository.save(inscripcion);
        return mapToDTO(saved);
    }

    @Override
    @Transactional
    @CachePut(value = "inscripcion", key = "#id")
    @CacheEvict(value = "inscripciones", allEntries = true)
    public InscripcionDTO actualizarInscripcion(Long id, InscripcionDTO inscripcionDTO) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));

        Estudiante estudiante = estudianteRepository.findById(inscripcionDTO.getEstudianteId())
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        Materia materia = materiaRepository.findById(inscripcionDTO.getMateriaId())
                .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));

        inscripcion.setEstudiante(estudiante);
        inscripcion.setMateria(materia);
        inscripcion.setFechaInscripcion(inscripcionDTO.getFechaInscripcion());

        Inscripcion updated = inscripcionRepository.save(inscripcion);
        return mapToDTO(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"inscripcion", "inscripciones"}, allEntries = true)
    public void eliminarInscripcion(Long id) {
        inscripcionRepository.deleteById(id);
    }
}
