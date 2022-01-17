package com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.useCases;

import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.repositories.EjemplarRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Validated
public class PrestarRecursoUseCase {

    private final EjemplarRepository ejemplarRepository;

    public PrestarRecursoUseCase(EjemplarRepository ejemplarRepository) {
        this.ejemplarRepository = ejemplarRepository;
    }

    public Mono<String> apply(String recursoId){
        return ejemplarRepository.findByRecursoIdAndPrestado(recursoId, false)
                .last()
                .flatMap(ejemplar -> {
                    ejemplar.setPrestado(true);
                    ejemplar.setFechaPrestamo(LocalDateTime.now());
                    return ejemplarRepository.save(ejemplar);
                })
                .then(Mono.just("Un ejemplar del recurso " + recursoId + " ha sido prestado con exito"))
                .onErrorReturn("El recurso no se encuentra disponible");
    }
}
