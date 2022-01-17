package com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.useCases;

import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.mappers.RecursoMapper;
import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.models.RecursoDTO;
import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.repositories.RecursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class ActualizarRecursoUseCase {
    private final RecursoRepository recursoRepository;
    private final RecursoMapper recursoMapper;


    public ActualizarRecursoUseCase(RecursoRepository recursoRepository, RecursoMapper recursoMapper) {
        this.recursoRepository = recursoRepository;
        this.recursoMapper = recursoMapper;
    }

    public Mono<RecursoDTO> apply(String recursoId, RecursoDTO recursoDTO){
        return recursoRepository.findById(recursoId)
                .flatMap(recurso -> {
                    recurso.setTipoRecurso(recursoDTO.getTipoRecurso()!=null?recursoDTO.getTipoRecurso():recurso.getTipoRecurso());
                    recurso.setAreaTematica(recursoDTO.getAreaTematica()!=null?recursoDTO.getAreaTematica():recurso.getAreaTematica());
                    recurso.setNombre(recursoDTO.getNombre()!=null?recursoDTO.getNombre():recurso.getNombre());

                    return recursoRepository.save(recurso)
                            .flatMap(recursoSaved -> Mono.just(recursoMapper.fromRecursoDTO().apply(recursoSaved)));
                });
    }
}
