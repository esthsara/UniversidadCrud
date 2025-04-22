package com.universidad.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler { // Clase que maneja excepciones globalmente en la aplicación

    // 1. Maneja errores de validación de Bean Validation (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Extrae todos los errores de validación y crea un mapa campo -> mensaje
        Map<String, String> errores = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .collect(Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    // Si hay campos duplicados, mantén el último error
                    (error1, error2) -> error2
                ));
        
        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            "Error de validación en los datos de entrada",
            errores,
            LocalDateTime.now()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
    
    // 2. Maneja errores de conversión de tipo (por ejemplo, String a Integer)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String nombre = ex.getName();
        String tipo = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido";
        Object valor = ex.getValue();
        String mensaje = String.format("El parámetro '%s' debería ser de tipo '%s', pero se recibió: '%s'", 
                                      nombre, tipo, valor);
        
        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            "Error de tipo de datos",
            mensaje,
            LocalDateTime.now()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
    
    // 3. Maneja errores de validación a nivel de parámetros (@RequestParam, @PathVariable)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errores = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                    violation -> violation.getPropertyPath().toString(),
                    violation -> violation.getMessage(),
                    // Si hay campos duplicados, mantén el último error
                    (error1, error2) -> error2
                ));
        
        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST.value(),
            "Error de validación en los parámetros",
            errores,
            LocalDateTime.now()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
    
    // 4. Maneja errores de entidad no encontrada
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(
            HttpStatus.NOT_FOUND.value(),
            "Recurso no encontrado",
            ex.getMessage(),
            LocalDateTime.now()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
    
    // 5. Puedes crear excepciones personalizadas para tu negocio
    @ExceptionHandler(RecursoNoDisponibleException.class)
    public ResponseEntity<ApiError> handleRecursoNoDisponible(RecursoNoDisponibleException ex) {
        ApiError apiError = new ApiError(
            HttpStatus.CONFLICT.value(),
            "Recurso no disponible",
            ex.getMessage(),
            LocalDateTime.now()
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }
    
    // 6. Maneja rutas no encontradas
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> handleNoHandlerFound(NoHandlerFoundException ex) {
        // Extraer información de la excepción
        String requestURL = ex.getRequestURL();
        String httpMethod = ex.getHttpMethod();
        
        // Crear mensaje detallado
        String mensaje = String.format("No se encontró un controlador para %s %s", 
                                    httpMethod, requestURL);
        
        // Crear respuesta de error
        ApiError apiError = new ApiError(
            HttpStatus.NOT_FOUND.value(),
            "Endpoint no encontrado",
            mensaje,
            LocalDateTime.now()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
    
    // 7. Manejador global para cualquier otra excepción no controlada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, WebRequest request) {
        // En producción, podrías querer loggear la excepción pero no mostrarla al cliente
        ApiError apiError = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Error interno del servidor",
            "Ocurrió un error inesperado. Por favor contacte al administrador si el problema persiste.",
            LocalDateTime.now()
        );
        
        // Log detallado para depuración
        ex.printStackTrace();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    public class RecursoNoDisponibleException extends RuntimeException {
        public RecursoNoDisponibleException(String mensaje) {
            super(mensaje);
        }
    }
}