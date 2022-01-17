package com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.useCases;


import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.mappers.EjemplarMapper;
import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.repositories.EjemplarRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class DevolverRecursoUseCase {

    private final EjemplarRepository ejemplarRepository;

    public DevolverRecursoUseCase(EjemplarRepository ejemplarRepository) {
        this.ejemplarRepository = ejemplarRepository;
    }

    public Mono<String> apply(String recursoId){
        return ejemplarRepository.findByRecursoIdAndPrestado(recursoId, true)
                .last()
                .flatMap(ejemplar -> {
                    ejemplar.setPrestado(false);
                    ejemplar.setFechaPrestamo(null);
                    return ejemplarRepository.save(ejemplar);
                })
                .then(Mono.just("Un ejemplar del recurso " + recursoId + " ha sido devuelto con exito"))
                .onErrorReturn("El recurso no se ha prestado");
    }
}
