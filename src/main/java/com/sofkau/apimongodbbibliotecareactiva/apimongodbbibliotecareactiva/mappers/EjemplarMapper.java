package com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.mappers;

import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.collections.Ejemplar;
import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.models.EjemplarDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EjemplarMapper {

    public Function<Ejemplar, EjemplarDTO> fromEjemplarDTO(){
        return entity -> new EjemplarDTO(
                entity.getRecursoId(),
                entity.getPrestado(),
                entity.getFechaPrestamo()
        );
    }

    public Function<EjemplarDTO, Ejemplar> fromCollection(){
        return updateEjemplar -> {
            var ejemplar = new Ejemplar();

            ejemplar.setRecursoId(updateEjemplar.getIdRecurso());
            ejemplar.setPrestado(updateEjemplar.getPrestado());
            ejemplar.setFechaPrestamo(updateEjemplar.getFechaPrestamo());

            return ejemplar;
        };
    }
}
