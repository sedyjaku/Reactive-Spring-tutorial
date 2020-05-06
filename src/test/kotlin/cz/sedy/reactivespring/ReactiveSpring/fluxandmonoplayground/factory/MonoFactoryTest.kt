package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground.factory

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*
import java.util.function.Supplier

class MonoFactoryTest {


    @Test
    fun monoUsingJustOrEmpty(){
        val mono: Mono<String> = Mono.justOrEmpty(null)

        StepVerifier.create(mono.log())
                .verifyComplete()
    }

    @Test
    fun monoUsingSupplier(){

        val mono: Mono<String> = Mono.fromSupplier { "adam" }

        StepVerifier.create(mono.log())
                .expectNext("adam")
                .verifyComplete()
    }
}