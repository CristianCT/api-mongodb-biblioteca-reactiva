package com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.useCases;

import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.mappers.RecursoMapper;
import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.models.RecursoDTO;
import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.repositories.RecursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Validated
public class FiltrarRecursosPorTipoYAreaTematicaUseCase {

    private final RecursoRepository recursoRepository;
    private final RecursoMapper recursoMapper;

    public FiltrarRecursosPorTipoYAreaTematicaUseCase(RecursoRepository recursoRepository, RecursoMapper recursoMapper) {
        this.recursoRepository = recursoRepository;
        this.recursoMapper = recursoMapper;
    }

    public Flux<RecursoDTO> apply(String tipoRecurso, String areaTematica){
        return recursoRepository.findAll()
                .filter(recurso -> recurso.getTipoRecurso().equals(tipoRecurso))
                .filter(recurso -> recurso.getAreaTematica().equals(areaTematica))
                .flatMap(recurso -> Mono.just(recursoMapper.fromRecursoDTO().apply(recurso)));
    }
}
