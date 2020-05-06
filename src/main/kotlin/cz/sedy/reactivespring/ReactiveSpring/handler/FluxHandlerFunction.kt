package cz.sedy.reactivespring.ReactiveSpring.handler

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class FluxHandlerFunction: HandlerFunction<ServerResponse> {

    override fun handle(serverRequest: ServerRequest): Mono<ServerResponse> {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Flux.just(1,2,3,4).log(), Int::class.java
                )

    }

}