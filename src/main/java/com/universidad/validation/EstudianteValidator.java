package com.universidad.validation;

import org.springframework.stereotype.Component;

import com.universidad.dto.EstudianteDTO;
import com.universidad.repository.EstudianteRepository; // Importa la interfaz EstudianteRepository
import java.util.Arrays;
import java.util.List;


@Component // Indica que esta clase es un componente de Spring
public class EstudianteValidator {

    private final EstudianteRepository estudianteRepository;

    public EstudianteValidator(EstudianteRepository userRepository) {
        this.estudianteRepository = userRepository;
    }

    public void validaEmailUnico(String email) {
        if (estudianteRepository.existsByEmail(email)) {
            throw new BusinessException("Ya existe un usuario con este email");
        }
    }

    public void validaDominioEmail(String email) {
        String dominio = email.substring(email.indexOf('@') + 1);
        List<String> dominiosBloqueados = Arrays.asList("dominiobloqueado.com", "spam.com");

        if (dominiosBloqueados.contains(dominio)) {
            throw new BusinessException("El dominio de email no está permitido");
        }
    }

    public void validacionCompletaEstudiante(EstudianteDTO estudiante) {
        validaEmailUnico(estudiante.getEmail());
        validaDominioEmail(estudiante.getEmail());
        // Otras validaciones...
    }

    public class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }
}
