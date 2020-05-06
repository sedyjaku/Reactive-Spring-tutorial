package cz.sedy.reactivespring.ReactiveSpring.router

import cz.sedy.reactivespring.ReactiveSpring.handler.FluxHandlerFunction
import cz.sedy.reactivespring.ReactiveSpring.handler.MonoHandlerFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class RouterFunctionConfig {

    @Bean
    fun route(fluxHandlerFunction: FluxHandlerFunction,
              monoHandlerFunction: MonoHandlerFunction): RouterFunction<ServerResponse> {
        return RouterFunctions
                .route(RequestPredicates.GET("/functional/flux")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        fluxHandlerFunction)
                .andRoute(RequestPredicates.GET("/functional/mono")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        monoHandlerFunction)
    }
}