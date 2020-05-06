package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground

import cz.sedy.reactivespring.ReactiveSpring.exception.CustomException
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import reactor.util.retry.Retry
import java.time.Duration

class FluxErrorTest {

    @Test
    fun fluxErrorHandling() {
        val stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Exception Occured")))
                .concatWith(Flux.just("D"))
                .onErrorResume { e ->
                    println("Exception is : $e")
                    Flux.just("default", "default1")
                }

        StepVerifier.create(stringFlux)
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("default", "default1")
//                .expectError(RuntimeException::class.java)
//                .verify()
                .verifyComplete()
    }

    @Test
    fun fluxErrorHandling_OnErrorReturn() {
        val stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Exception Occured")))
                .concatWith(Flux.just("D"))
                .onErrorReturn("default")

        StepVerifier.create(stringFlux)
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("default")
                .verifyComplete()
    }


    @Test
    fun fluxErrorHandling_OnErrorMap() {
        val stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Exception Occured")))
                .concatWith(Flux.just("D"))
                .onErrorMap { e ->
                    CustomException(e)
                }

        StepVerifier.create(stringFlux)
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectError(CustomException::class.java)
                .verify()
    }


    @Test
    fun fluxErrorHandling_OnErrorMap_WithRetry() {
        val stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Exception Occured")))
                .concatWith(Flux.just("D"))
                .onErrorMap { e ->
                    CustomException(e)
                }
                .retry(2)

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectError(CustomException::class.java)
                .verify()
    }


    @Test
    fun fluxErrorHandling_OnErrorMap_WithRetryBackoff() {
        val stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(RuntimeException("Exception Occured")))
                .concatWith(Flux.just("D"))
                .onErrorMap { e ->
                    CustomException(e)
                }
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(5)))

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectError(Exception::class.java)
                .verify()
    }
}