package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.lang.RuntimeException
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class MonoTest {

    @Test
    fun monoTest(){
        val stringMono = Mono.just("Spring")

        StepVerifier.create(stringMono)
                .expectNext("Spring")
                .verifyComplete()
    }

    @Test
    fun monoTest_WithError(){
        val stringMono: Mono<String> = Mono.error(RuntimeException("exc"))

        StepVerifier.create(stringMono)
                .expectError(RuntimeException::class.java)
                .verify()
    }

}