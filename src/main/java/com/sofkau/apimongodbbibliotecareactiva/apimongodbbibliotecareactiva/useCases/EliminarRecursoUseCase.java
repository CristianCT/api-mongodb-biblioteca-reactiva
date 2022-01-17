package com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.useCases;

import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.mappers.RecursoMapper;
import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.repositories.RecursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class EliminarRecursoUseCase {
    private final RecursoRepository recursoRepository;

    public EliminarRecursoUseCase(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }
    public Mono<String> apply(String recursoId){
        return recursoRepository.findById(recursoId)
                .flatMap(recurso -> recursoRepository.deleteById(recursoId)
                        .thenReturn("Recurso Eliminado"));
    }
}
