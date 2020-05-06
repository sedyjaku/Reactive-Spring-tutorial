package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers.parallel
import reactor.test.StepVerifier
import java.util.*

class FluxTransformTest {

    val namesList = Arrays.asList("adam", "anna", "jack", "jenny")

    @Test
    fun transformUsingMap() {

        val namesFlux = Flux.fromIterable(namesList)
                .map { s -> s.toUpperCase() }
                .log()

        StepVerifier.create(namesFlux)
                .expectNext("ADAM", "ANNA", "JACK", "JENNY")
                .verifyComplete()
    }


    @Test
    fun transformUsingMap_Length() {

        val namesFlux = Flux.fromIterable(namesList)
                .map { s -> s.length }
                .log()

        StepVerifier.create(namesFlux)
                .expectNext(4, 4, 4, 5)
                .verifyComplete()
    }

    @Test
    fun transformUsingMap_Length_repeat() {

        val namesFlux = Flux.fromIterable(namesList)
                .map { s -> s.length }
                .repeat(1)
                .log()

        StepVerifier.create(namesFlux)
                .expectNext(4, 4, 4, 5, 4, 4, 4, 5)
                .verifyComplete()
    }

    @Test
    fun transformUsingFlatMap() {
        val stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .flatMap { s -> Flux.fromIterable(convertToList(s)) }
                .log()
        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete()
    }

    @Test
    fun transformUsingFlatMap_usingParallel() {
        val stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2)
                .flatMap { s: Flux<String> ->
                    s.map(this::convertToList).subscribeOn(parallel())
                            .flatMap { str -> Flux.fromIterable(str) }
                            .log()
                }

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete()
    }


    @Test
    fun transformUsingFlatMap_usingParallelMaintainOrder() {
        val stringFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
                .window(2)
                .flatMapSequential { s: Flux<String> ->
                    s.map(this::convertToList).subscribeOn(parallel())
                            .flatMap { str -> Flux.fromIterable(str) }
                            .log()
                }

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete()
    }

    fun convertToList(s: String): List<String> {
        Thread.sleep(1000);
        return listOf(s, "newValue")
    }
}