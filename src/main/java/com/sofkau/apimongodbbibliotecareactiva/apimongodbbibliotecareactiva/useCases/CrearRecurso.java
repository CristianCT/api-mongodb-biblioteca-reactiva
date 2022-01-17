package com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.useCases;

import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.models.RecursoDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface CrearRecurso {
    Mono<RecursoDTO> apply(RecursoDTO recursoDTO);
}
