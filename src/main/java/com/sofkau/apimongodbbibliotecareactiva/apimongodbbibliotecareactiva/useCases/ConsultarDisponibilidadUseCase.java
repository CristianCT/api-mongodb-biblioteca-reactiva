package com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.useCases;

import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.collections.Ejemplar;
import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.repositories.EjemplarRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
@Validated
public class ConsultarDisponibilidadUseCase {

    private final EjemplarRepository ejemplarRepository;

    public ConsultarDisponibilidadUseCase(EjemplarRepository ejemplarRepository) {
        this.ejemplarRepository = ejemplarRepository;
    }

    public Mono<String> apply(String recursoId) {
        return ejemplarRepository.findByRecursoIdAndPrestado(recursoId, false)
                .last()
                .flatMap(recurso -> Mono.just("El recurso se encuentra disponible"))
                .onErrorResume(e ->
                        ejemplarRepository.findByRecursoIdAndPrestado(recursoId, true)
                                .sort(Comparator.comparing(Ejemplar::getFechaPrestamo))
                                .last()
                                .flatMap(ejemplar -> Mono.just(("El recurso no se encuentra disponible\nUltimo prestamo: " + ejemplar.getFechaPrestamo())))

                );
    }
}
