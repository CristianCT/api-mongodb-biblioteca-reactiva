package com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.useCases;

import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.mappers.RecursoMapper;
import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.models.RecursoDTO;
import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.repositories.RecursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Service
@Validated
public class CrearRecursoUseCase implements CrearRecurso{

    private final RecursoRepository recursoRepository;
    private final RecursoMapper recursoMapper;

    public CrearRecursoUseCase(RecursoRepository recursoRepository, RecursoMapper recursoMapper) {
        this.recursoRepository = recursoRepository;
        this.recursoMapper = recursoMapper;
    }

    @Override
    public Mono<RecursoDTO> apply(RecursoDTO recursoDTO){
        return recursoRepository.save(recursoMapper.fromCollection().apply(recursoDTO))
                .flatMap(recursoSaved -> Mono.just(recursoMapper.fromRecursoDTO().apply(recursoSaved)));
    }
}
