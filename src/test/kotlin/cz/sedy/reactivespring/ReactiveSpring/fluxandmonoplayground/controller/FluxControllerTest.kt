package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground.controller

import cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground.SpringTestParent
import org.hamcrest.core.Is
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.bind.annotation.ResponseBody
import reactor.test.StepVerifier
import java.util.*

class FluxControllerTest: SpringTestParent() {

    @Test
    fun flux_approach1(){
        val integerFlux = webTestClient
                .get()
                .uri("/flux")
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


    @Test
    fun flux_approach2(){
        webTestClient
                .get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBodyList(Int::class.java)
                .hasSize(4)
    }


    @Test
    fun flux_approach3(){

        val expectedIntegerList = listOf(1,2,3,4)

        val entityExchangeResult = webTestClient
                .get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Int::class.java)
                .returnResult()

        assertEquals(expectedIntegerList, entityExchangeResult.responseBody)

    }

    @Test
    fun flux_approach4(){

        val expectedIntegerList = listOf(1,2,3,4)

        webTestClient
                .get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Int::class.java)
                .consumeWith<WebTestClient.ListBodySpec<Int>>{ response ->
                    assertEquals(expectedIntegerList, response.responseBody)
                }


    }

    @Test
    fun fluxStream(){
        val longStreamFlux = webTestClient
                .get()
                .uri("/fluxstream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk
                .returnResult(Long::class.java)
                .responseBody

        StepVerifier.create(longStreamFlux)
                .expectSubscription()
                .expectNext(0)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .thenCancel()
                .verify()
    }
}