package cz.sedy.reactivespring.ReactiveSpring.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@RestController
class MonoController {


    @GetMapping("/mono")
    fun getMono(): Mono<Int> {
        return Mono.just(1)
                .log()
    }
}