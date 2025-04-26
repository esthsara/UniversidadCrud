package com.universidad.service.impl;

import com.universidad.model.Materia;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServiceImpl implements IMateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Override
    @Cacheable(value = "materias")
    public List<Materia> obtenerTodasLasMaterias() {
        return materiaRepository.findAll();
    }

    @Override
    @Cacheable(value = "materia", key = "#id")
    public Materia obtenerMateriaPorId(Long id) {
        return materiaRepository.findById(id).orElse(null);
    }

    @Override
    @Cacheable(value = "materia", key = "#codigoUnico")
    public Materia obtenerMateriaPorCodigoUnico(String codigoUnico) {
        return materiaRepository.findByCodigoUnico(codigoUnico);
    }

    @Override
    @CachePut(value = "materia", key = "#result.id")
    @CacheEvict(value = "materias", allEntries = true)
    public Materia crearMateria(Materia materia) {
        return materiaRepository.save(materia);
    }

    @Override
    @CachePut(value = "materia", key = "#id")
    @CacheEvict(value = "materias", allEntries = true)
    public Materia actualizarMateria(Long id, Materia materia) {
        materia.setId(id);
        return materiaRepository.save(materia);
    }

    @Override
    @CacheEvict(value = {"materia", "materias"}, allEntries = true)
    public void eliminarMateria(Long id) {
        materiaRepository.deleteById(id);
    }
}
