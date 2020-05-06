package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground.handler

import cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground.SpringTestParent
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import reactor.test.StepVerifier

class MonoHandlerFunctionTest: SpringTestParent() {

    @Test
    fun testMonoFunctional(){
        val expectedValue = 1

        val result = webTestClient
                .get()
                .uri("/mono")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(Int::class.java)
                .returnResult()

        Assertions.assertEquals(expectedValue, result.responseBody)

    }
}