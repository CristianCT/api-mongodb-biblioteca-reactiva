package com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.routers;

import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.models.RecursoDTO;
import com.sofkau.apimongodbbibliotecareactiva.apimongodbbibliotecareactiva.useCases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequestMapping
public class RecursoController {

    @Bean
    public RouterFunction<ServerResponse> consultarDisponibilidad(ConsultarDisponibilidadUseCase consultarDisponibilidadUseCase) {
        return route(
                GET("/recursos/{recursoId}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(consultarDisponibilidadUseCase.apply(request.pathVariable("recursoId")), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> prestarRecurso(PrestarRecursoUseCase prestarRecursoUseCase){
        return route(
                PUT("/recursos/prestar/{recursoId}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(prestarRecursoUseCase.apply(request.pathVariable("recursoId")), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> devolverRecurso(DevolverRecursoUseCase devolverRecursoUseCase){
        return route(
                PUT("/recursos/devolver/{recursoId}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(devolverRecursoUseCase.apply(request.pathVariable("recursoId")), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> agregarEjemplar(AgregarEjemplaresUseCase agregarEjemplaresUseCase){
        return route(
                POST("/recursos/agregar/{recursoId}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(BodyInserters.fromPublisher(agregarEjemplaresUseCase.apply(request.pathVariable("recursoId"), 1), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> agregarEjemplares(AgregarEjemplaresUseCase agregarEjemplaresUseCase){
        return route(
                POST("/recursos/agregar/{recursoId}/{cantidad}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(BodyInserters.fromPublisher(agregarEjemplaresUseCase.apply(request.pathVariable("recursoId"), Integer.parseInt(request.pathVariable("cantidad"))), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> recomendarRecursos(FiltrarRecursosPorTipoYAreaTematicaUseCase filtrarRecursosPorTipoYAreaTematicaUseCase){
        return route(
                GET("/recursos/recomendar/tipoyarea/{tipo}/{areaTematica}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(filtrarRecursosPorTipoYAreaTematicaUseCase.apply(request.pathVariable("tipo"), request.pathVariable("areaTematica")), RecursoDTO.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> recomendarRecursosPorTipo(FiltrarRecursosPorTipoUseCase filtrarRecursosPorTipoUseCase){
        return route(
                GET("/recursos/recomendar/tipo/{tipo}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(filtrarRecursosPorTipoUseCase.apply(request.pathVariable("tipo")), RecursoDTO.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> recomendarRecursosPorAreaTematica(FiltrarRecursosPorAreaTematicaUseCase filtrarRecursosPorAreaTematicaUseCase){
        return route(
                GET("/recursos/recomendar/area/{areaTematica}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(filtrarRecursosPorAreaTematicaUseCase.apply(request.pathVariable("areaTematica")), RecursoDTO.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> obtenerRecursos(ObtenerRecursosUseCase obtenerRecursosUseCase){
        return route(
                GET("/recursos/").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(obtenerRecursosUseCase.apply(), RecursoDTO.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> actualizarRecurso(ActualizarRecursoUseCase actualizarRecursoUseCase){
        return route(
                PUT("/recursos/{recursoId}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(RecursoDTO.class)
                        .flatMap(recursoDTO -> actualizarRecursoUseCase.apply(request.pathVariable("recursoId"), recursoDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> eliminarRecurso(EliminarRecursoUseCase eliminarRecursoUseCase){
        return route(
                DELETE("/recursos/{recursoId}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(eliminarRecursoUseCase.apply(request.pathVariable("recursoId")), String.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> crearRecurso(CrearRecursoUseCase crearRecursoUseCase){
        return route(
                POST("/recursos").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(RecursoDTO.class)
                        .flatMap(recursoDTO -> crearRecursoUseCase.apply(recursoDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)))
        );
    }

    // https://www.woolha.com/tutorials/project-reactor-using-repeat-and-repeatwhen-examples
}
