package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.*

class FluxFilterTest {

    val namesList = Arrays.asList("adam", "anna", "jack", "jenny")

    @Test
    fun filterTest(){

        val namesFlux: Flux<String> = Flux.fromIterable(namesList)
                .filter{ s -> s.startsWith("a")}
                .log()

        StepVerifier.create(namesFlux)
                .expectNext("adam", "anna")
                .verifyComplete()
    }
}