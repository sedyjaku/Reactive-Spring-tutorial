package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import reactor.test.scheduler.VirtualTimeScheduler
import java.time.Duration
import java.util.function.BiFunction

class FluxCombineTest {

    @BeforeEach
    fun setUpVirtualTime() {
        VirtualTimeScheduler.getOrSet()
    }

    @Test
    fun combineUsingMerge() {
        val flux1 = Flux.just("A", "B", "C")
        val flux2 = Flux.just("D", "E", "F")

        val mergedFlux = Flux.merge(flux1, flux2)

        StepVerifier.withVirtualTime { mergedFlux.log() }
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(6L))
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete()
    }

    @Test
    fun combineUsingMerge_WithDelay() {
        val flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1))
        val flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1))

        val mergedFlux = Flux.merge(flux1, flux2)

        StepVerifier.withVirtualTime { mergedFlux.log() }
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(6L))
                .expectNextCount(6)
                .verifyComplete()
    }

    @Test
    fun combineUsingConcat() {
        val flux1 = Flux.just("A", "B", "C")
        val flux2 = Flux.just("D", "E", "F")

        val mergedFlux = Flux.concat(flux1, flux2)

        StepVerifier.withVirtualTime { mergedFlux.log() }
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(6L))
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete()
    }

    @Test
    fun combineUsingConcat_WithDelay() {
        val flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1))
        val flux2 = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1))

        val mergedFlux = Flux.concat(flux1, flux2)

        StepVerifier.withVirtualTime { mergedFlux.log() }
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(6L))
                .expectNextCount(6)
                .verifyComplete()
    }


    @Test
    fun combineUsingZip() {
        val flux1 = Flux.just("A", "B", "C")
        val flux2 = Flux.just("D", "E", "F")

        val biFunction = BiFunction<String, String, String> { t1: String, t2: String -> t1.plus(t2) }

        val mergedFlux = Flux.zip(flux1, flux2, biFunction)

        StepVerifier.withVirtualTime { mergedFlux.log() }
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(6L))
                .expectNext("AD", "BE", "CF")
                .verifyComplete()
    }
}