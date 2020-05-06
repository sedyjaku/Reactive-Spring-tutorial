package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground.controller

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.EntityExchangeResult
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@WebFluxTest
class MonoControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun testMono(){
        val expectedValue = 1

        val result = webTestClient
                .get()
                .uri("/mono")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(Int::class.java)
                .returnResult()

        assertEquals(expectedValue, result.responseBody)

    }
}