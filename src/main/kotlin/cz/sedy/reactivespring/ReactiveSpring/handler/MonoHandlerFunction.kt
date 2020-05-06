package cz.sedy.reactivespring.ReactiveSpring.handler

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class MonoHandlerFunction: HandlerFunction<ServerResponse> {

    override fun handle(serverRequest: ServerRequest): Mono<ServerResponse> {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Mono.just(1).log(), Int::class.java
                )

    }

}