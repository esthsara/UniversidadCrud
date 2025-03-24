package com.universidad.service;

import com.universidad.dto.EstudianteDTO;

import java.util.List;

public interface IEstudianteService {
    
    EstudianteDTO createStudent(EstudianteDTO estudianteDTO);
    
    List<EstudianteDTO> getAllStudents();
    
    void deleteStudent(Long id);
    
}