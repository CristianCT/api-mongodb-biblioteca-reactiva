package com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.useCases;

import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.mappers.EjemplarMapper;
import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.models.EjemplarDTO;
import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.repositories.EjemplarRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

@Service
@Validated
public class AgregarEjemplaresUseCase {

    private final EjemplarRepository ejemplarRepository;
    private final EjemplarMapper ejemplarMapper;

    public AgregarEjemplaresUseCase(EjemplarRepository ejemplarRepository, EjemplarMapper ejemplarMapper) {
        this.ejemplarRepository = ejemplarRepository;
        this.ejemplarMapper = ejemplarMapper;
    }

    public Mono<String> apply(String recursoId, Integer cantidad){
        return Mono.defer(() -> ejemplarRepository.save(ejemplarMapper.fromCollection().apply(new EjemplarDTO(recursoId, false, null))))
                .repeat(cantidad-1)
                .then(Mono.just(cantidad + " Ejemplares han sido agregados"));
    }
}
