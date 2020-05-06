package cz.sedy.reactivespring.ReactiveSpring.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration

@RestController
class FluxController {

    @GetMapping("/flux")
    fun getFlux(): Flux<Int>{
        return Flux.just(1,2,3,4)
                .delayElements(Duration.ofSeconds(1))
                .log()
    }


    @GetMapping(value = ["/fluxstream"], produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getFluxStream(): Flux<Long>{
        return Flux.interval(Duration.ofSeconds(1))
                .log()
    }
}