package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.time.Duration

class FluxWithTimeTest {

    @Test
    fun infiniteSequence(){
        val infiniteFlux = Flux.interval(Duration.ofMillis(200)).log()

        infiniteFlux.subscribe{element -> println("Value is : $element")}

        Thread.sleep(3000)
    }


    @Test
    fun infiniteSequenceTest(){
        val finiteFlux = Flux.interval(Duration.ofMillis(200)).take(3).log()

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0,1,2)
                .verifyComplete()
    }


    @Test
    fun infiniteSequenceMap(){
        val finiteFlux = Flux.interval(Duration.ofMillis(200))
                .map { longValue -> longValue.toInt() }
                .take(3).log()

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0,1,2)
                .verifyComplete()
    }

    @Test
    fun infiniteSequenceMap_withDelay(){
        val finiteFlux = Flux.interval(Duration.ofMillis(200))
                .delayElements(Duration.ofSeconds(1))
                .map { longValue -> longValue.toInt() }
                .take(3).log()

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0,1,2)
                .verifyComplete()
    }
}