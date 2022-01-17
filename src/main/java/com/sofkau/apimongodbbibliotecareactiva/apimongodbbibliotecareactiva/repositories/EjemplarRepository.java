package com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.repositories;

import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.collections.Ejemplar;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EjemplarRepository extends ReactiveMongoRepository<Ejemplar, String> {
    Flux<Ejemplar> findByRecursoId(String RecursoId);

    Flux<Ejemplar> findByRecursoIdAndPrestado(String RecursoId, Boolean prestado);
}
