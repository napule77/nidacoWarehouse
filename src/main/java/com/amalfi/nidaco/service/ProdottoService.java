package com.amalfi.nidaco.service;

import com.amalfi.nidaco.entity.Prodotto;
import com.amalfi.nidaco.repository.ProdottoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdottoService {


    private final ProdottoRepository repository;



    public List<Prodotto> findAll(){

        return repository.findAll();

    }



    public List<Prodotto> search(String filtro){


        return repository.search(filtro);


    }



    public Prodotto save(
            Prodotto prodotto){


        return repository.save(prodotto);


    }



    public void delete(Long id){


        repository.deleteById(id);


    }



    public Prodotto findById(Long id){


        return repository.findById(id)
                .orElse(null);


    }



}