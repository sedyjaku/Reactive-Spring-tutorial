package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground.handler

import cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground.SpringTestParent
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import reactor.test.StepVerifier

class FluxHandlerFunctionTest: SpringTestParent() {

    @Test
    fun flux_approach1(){
        val integerFlux = webTestClient
                .get()
                .uri("/functional//flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .returnResult(Int::class.java)
                .responseBody

        StepVerifier.create(integerFlux)
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .verifyComplete()
    }
}