package com.amalfi.nidaco.service;

import com.amalfi.nidaco.entity.Categoria;
import com.amalfi.nidaco.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    public List<Categoria> findAll(){

        return repository.findAll();

    }

    public Categoria save(Categoria categoria){

        return repository.save(categoria);

    }

    public void delete(Long id){

        repository.deleteById(id);

    }

}