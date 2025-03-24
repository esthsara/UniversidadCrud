package com.universidad.service.impl;

import com.universidad.dto.EstudianteDTO;

import com.universidad.model.Estudiante;

import com.universidad.repository.EstudianteRepository;
import com.universidad.service.IEstudianteService;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstudianteServiceImpl implements IEstudianteService {

    private final EstudianteRepository estudianteRepository;
    

    @Autowired
    public EstudianteServiceImpl(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }
    
    @PostConstruct
    public void init() {
        estudianteRepository.init();
    }

    @Override
    public EstudianteDTO createStudent(EstudianteDTO estudianteDTO) {
        
        // Convertir DTO a entidad
        Estudiante estudiante = convertToEntity(estudianteDTO);
        
        // Guardar estudiante
        Estudiante savedStudent = estudianteRepository.save(estudiante);
        
        // Convertir entidad guardada a DTO y retornar
        return convertToDTO(savedStudent);
    }

    @Override
    public List<EstudianteDTO> getAllStudents() {
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>();
        
        for (Estudiante estudiante : estudiantes) {
            estudiantesDTO.add(convertToDTO(estudiante));
        }
        
        return estudiantesDTO;
    }

    @Override
    public void deleteStudent(Long id) {
        // Eliminar estudiante
        estudianteRepository.deleteById(id);
    }

    // Método auxiliar para convertir entidad a DTO
    private EstudianteDTO convertToDTO(Estudiante estudiante) {
                
        return EstudianteDTO.builder()
                .id(estudiante.getId())
                .nombre(estudiante.getNombre())
                .apellido(estudiante.getApellido())
                .email(estudiante.getEmail())
                .fechaNacimiento(estudiante.getFechaNacimiento())
                .numeroInscripcion(estudiante.getNumeroInscripcion())
                .build();
    }
    
    // Método auxiliar para convertir DTO a entidad
    private Estudiante convertToEntity(EstudianteDTO estudianteDTO) {
        return Estudiante.builder()
                .id(estudianteDTO.getId())
                .nombre(estudianteDTO.getNombre())
                .apellido(estudianteDTO.getApellido())
                .email(estudianteDTO.getEmail())
                .fechaNacimiento(estudianteDTO.getFechaNacimiento())
                .numeroInscripcion(estudianteDTO.getNumeroInscripcion())
                .build();
    }
}