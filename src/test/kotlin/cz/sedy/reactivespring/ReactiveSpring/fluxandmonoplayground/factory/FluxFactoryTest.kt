package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground.factory

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.*

class FluxFactoryTest {

    val namesList = Arrays.asList("adam", "anna", "jack", "jenny")

    @Test
    fun fluxUsingIterable(){
        val namesFlux = Flux.fromIterable(namesList)
                .log()

        StepVerifier.create(namesFlux)
                .expectNext("adam", "anna", "jack", "jenny")
                .verifyComplete()
    }

    @Test
    fun fluxUsingArray(){

        val names = arrayOf("Adam", "Anna", "Jack", "Jenny")
        val namesFlux = Flux.fromArray(names)
                .log()

        StepVerifier.create(namesFlux)
                .expectNext("Adam", "Anna", "Jack", "Jenny")
                .verifyComplete()
    }

    @Test
    fun fluxUsingStreams(){

        val namesFlux = Flux.fromStream(namesList.stream())
                .log()

        StepVerifier.create(namesFlux)
                .expectNext("adam", "anna", "jack", "jenny")
                .verifyComplete()
    }


    @Test
    fun fluxUsingRange(){

        val namesFlux = Flux.range(1,5)
                .log()

        StepVerifier.create(namesFlux)
                .expectNext(1,2,3,4,5)
                .verifyComplete()
    }
}