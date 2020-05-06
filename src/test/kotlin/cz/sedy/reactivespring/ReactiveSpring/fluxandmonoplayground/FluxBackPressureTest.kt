package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class FluxBackPressureTest {

    @Test
    fun backPressureTest() {
        val finiteFlux: Flux<Int> = Flux.range(1, 10)
                .log()

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenCancel()
                .verify()
    }

    @Test
    fun backPressure() {

        val finiteFlux: Flux<Int> = Flux.range(1, 10)
                .log()

        finiteFlux.subscribe(
                { element -> println("Element is $element") },
                { exception -> System.err.print("Exception is: $exception") },
                { println("done") },
                { subscription -> subscription.request(2) })
    }

    @Test
    fun backPressure_withCancel() {

        val finiteFlux: Flux<Int> = Flux.range(1, 10)
                .log()

        finiteFlux.subscribe(
                { element -> println("Element is $element") },
                { exception -> System.err.print("Exception is: $exception") },
                { println("done") },
                { subscription -> subscription.cancel() })
    }


    @Test
    fun customized_backPressure() {

        val finiteFlux: Flux<Int> = Flux.range(1, 10)
                .log()

        finiteFlux.subscribe(object: BaseSubscriber<Int>() {
            override fun hookOnNext(value: Int){
                request(1)
                println("Value recieved is: $value")
                if(value == 4){
                    cancel()
                }
            }
        })
    }
}