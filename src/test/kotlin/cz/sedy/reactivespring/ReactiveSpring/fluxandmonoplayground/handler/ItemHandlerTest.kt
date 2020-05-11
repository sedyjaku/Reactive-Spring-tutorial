package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground.handler

import cz.sedy.reactivespring.ReactiveSpring.document.Item
import cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground.SpringTestParent
import cz.sedy.reactivespring.ReactiveSpring.repository.ItemReactiveRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.math.BigDecimal

class ItemHandlerTest : SpringTestParent() {

    @Autowired
    lateinit var itemReactiveRepository: ItemReactiveRepository

    val ENDPOINT = "/v1/fun/items"

    @BeforeEach
    fun setUp() {

        val itemList = listOf<Item>(
                Item(null, "Samsung TV", BigDecimal.valueOf(54153)),
                Item(null, "LG TV", BigDecimal.valueOf(1111.999)),
                Item(null, "Panasonic TV", BigDecimal.valueOf(1.00942)),
                Item(null, "Apple Watch", BigDecimal.valueOf(0.0043)),
                Item("ABC", "Samsung Watch", BigDecimal.valueOf(0.01))
        )

        itemReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(itemList))
                .flatMap { item -> itemReactiveRepository.save(item) }
                .doOnNext { item -> println("Inserted item is $item") }
                .blockLast()
    }

    @Test
    fun getAllItems() {
        webTestClient
                .get()
                .uri(ENDPOINT)
                .exchange()
                .expectStatus().isOk
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Item::class.java)
                .hasSize(5)
    }

    @Test
    fun getAllItems_approach2() {
        webTestClient
                .get()
                .uri(ENDPOINT)
                .exchange()
                .expectStatus().isOk
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Item::class.java)
                .hasSize(5)
                .consumeWith<WebTestClient.ListBodySpec<Item>> { response ->
                    response.responseBody?.forEach { item -> item.id != null }
                }
    }

    @Test
    fun getAllItems_approach3() {
        val responseFlux = webTestClient
                .get()
                .uri(ENDPOINT)
                .exchange()
                .expectStatus().isOk
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .returnResult(Item::class.java)
                .responseBody

        StepVerifier.create(responseFlux)
                .expectNextCount(5)
                .verifyComplete()
    }

    @Test
    fun getItem() {
        webTestClient
                .get()
                .uri(ENDPOINT.plus("/{id}"), "ABC")
                .exchange()
                .expectStatus()
                .isOk
                .expectBody()
                .jsonPath("$.price", 0.01)
    }

    @Test
    fun getItem_notFound() {
        webTestClient
                .get()
                .uri(ENDPOINT.plus("/{id}"), "DEF")
                .exchange()
                .expectStatus()
                .isOk
                .expectBody()
                .isEmpty
    }

    @Test
    fun createItem() {
        val item: Item = Item(null, "Iphone X", BigDecimal.valueOf(999.99))
        webTestClient.post()
                .uri(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(item), Item::class.java)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.id").isNotEmpty
                .jsonPath("$.description").isEqualTo("Iphone X")
                .jsonPath("$.price").isEqualTo(999.99)
    }

    @Test
    fun deleteItem() {
        webTestClient
                .delete()
                .uri(ENDPOINT.plus("/{id}"), "ABC")
                .exchange()
                .expectStatus()
                .isNoContent
                .expectBody(Void::class.java)
    }

    @Test
    fun updateItem() {
        val item: Item = Item(null, "new Item", BigDecimal.valueOf(111.11))
        webTestClient
                .put()
                .uri(ENDPOINT.plus("/{id}"), "ABC")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(item), Item::class.java)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.price").isEqualTo(111.11)
                .jsonPath("$.description").isEqualTo("new Item")
    }
}