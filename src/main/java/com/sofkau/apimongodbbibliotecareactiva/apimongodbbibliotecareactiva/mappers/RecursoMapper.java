package com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.mappers;

import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.collections.Recurso;
import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.models.RecursoDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RecursoMapper {
    public Function<Recurso, RecursoDTO> fromRecursoDTO(){
        return entity -> new RecursoDTO(
                entity.getNombre(),
                entity.getTipoRecurso(),
                entity.getAreaTematica()
        );
    }

    public Function<RecursoDTO, Recurso> fromCollection(){
        return updateRecurso -> {
            var recurso = new Recurso();
            recurso.setTipoRecurso(updateRecurso.getTipoRecurso());
            recurso.setNombre(updateRecurso.getNombre());
            recurso.setAreaTematica(updateRecurso.getAreaTematica());

            return recurso;
        };
    }
}
