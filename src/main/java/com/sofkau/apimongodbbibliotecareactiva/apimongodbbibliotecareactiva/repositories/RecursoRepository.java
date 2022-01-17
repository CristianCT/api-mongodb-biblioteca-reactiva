package com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.repositories;

import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.collections.Recurso;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecursoRepository extends ReactiveMongoRepository<Recurso, String> {
}
