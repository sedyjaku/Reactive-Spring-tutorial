package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.lang.RuntimeException

class FluxTest {

    @Test
    fun fluxTest(){

        val stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
//                .concatWith(Flux.error(RuntimeException("Exc Occured")))
                .concatWith(Flux.just("After error"))
                .log()

        stringFlux.subscribe(System.out::println, { e -> System.err.println(e)}, { println("completed") })
    }

    @Test
    fun fluxTestElements_WithoutError(){

        val stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .log()

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring boot")
                .expectNext("Reactive Spring")
                .verifyComplete()
    }

    @Test
    fun fluxTestElements_WithError(){

        val stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .concatWith(Flux.error(RuntimeException("Exc Occured")))
                .log()

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring boot")
                .expectNext("Reactive Spring")
//                .expectError(RuntimeException::class.java)
                .expectErrorMessage("Exc Occured")
                .verify()
    }

    @Test
    fun fluxTestElements_WithErrorWithExpectNextMultipleParams(){

        val stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .concatWith(Flux.error(RuntimeException("Exc Occured")))
                .log()

        StepVerifier.create(stringFlux)
                .expectNext("Spring","Spring boot","Reactive Spring")
                .expectErrorMessage("Exc Occured")
                .verify()
    }

    @Test
    fun fluxTestElements_WithErrorAndExpectNextCount(){

        val stringFlux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .concatWith(Flux.error(RuntimeException("Exc Occured")))
                .log()

        StepVerifier.create(stringFlux)
                .expectNextCount(3L)
                .expectError(RuntimeException::class.java)
                .verify()
    }
}