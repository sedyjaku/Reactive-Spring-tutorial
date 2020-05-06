package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import reactor.test.scheduler.VirtualTimeScheduler
import java.time.Duration

class VirtualTimeTest {

    @Test
    fun testingWithoutVirtualTime(){

        val longFlux = Flux.interval(Duration.ofSeconds(1)).take(3)

        StepVerifier.create(longFlux)
                .expectSubscription()
                .expectNext(0, 1,2)
                .verifyComplete()
    }


    @Test
    fun testingWithVirtualTime(){

        VirtualTimeScheduler.getOrSet()

        val longFlux = Flux.interval(Duration.ofSeconds(1)).take(3)

        StepVerifier.withVirtualTime { longFlux.log() }
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(3))
                .expectNext(0, 1,2)
                .verifyComplete()
    }
}