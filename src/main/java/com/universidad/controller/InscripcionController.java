package com.universidad.controller;

import com.universidad.dto.InscripcionDTO;
import com.universidad.service.IInscripcionService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final IInscripcionService inscripcionService;
    private static final Logger logger = LoggerFactory.getLogger(InscripcionController.class);

    @Autowired
    public InscripcionController(IInscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @GetMapping
    public ResponseEntity<List<InscripcionDTO>> obtenerTodasLasInscripciones() {
        long inicio = System.currentTimeMillis();
        logger.info("[INSCRIPCION] Inicio obtenerTodasLasInscripciones: {}", inicio);
        List<InscripcionDTO> result = inscripcionService.obtenerTodasLasInscripciones();
        long fin = System.currentTimeMillis();
        logger.info("[INSCRIPCION] Fin obtenerTodasLasInscripciones: {} (Duración: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionDTO> obtenerInscripcionPorId(@PathVariable Long id) {
        long inicio = System.currentTimeMillis();
        logger.info("[INSCRIPCION] Inicio obtenerInscripcionPorId: {}", inicio);
        InscripcionDTO inscripcion = inscripcionService.obtenerInscripcionPorId(id);
        long fin = System.currentTimeMillis();
        logger.info("[INSCRIPCION] Fin obtenerInscripcionPorId: {} (Duración: {} ms)", fin, (fin - inicio));
        if (inscripcion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inscripcion);
    }

    @PostMapping
    public ResponseEntity<InscripcionDTO> crearInscripcion(@RequestBody @Valid InscripcionDTO dto) {
        InscripcionDTO nueva = inscripcionService.crearInscripcion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InscripcionDTO> actualizarInscripcion(@PathVariable Long id, @RequestBody @Valid InscripcionDTO dto) {
        InscripcionDTO actualizada = inscripcionService.actualizarInscripcion(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInscripcion(@PathVariable Long id) {
        inscripcionService.eliminarInscripcion(id);
        return ResponseEntity.noContent().build();
    }
}
