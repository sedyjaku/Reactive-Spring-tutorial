package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground.repository

import cz.sedy.reactivespring.ReactiveSpring.document.Item
import cz.sedy.reactivespring.ReactiveSpring.repository.ItemReactiveRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.math.BigDecimal

@DataMongoTest
@ExtendWith(SpringExtension::class)
@DirtiesContext
class ItemReactiveRepositoryTest {

    @Autowired
    lateinit var itemReactiveRepository: ItemReactiveRepository


    val itemList = listOf<Item>(
            Item(null, "Samsung TV", BigDecimal.valueOf(54153)),
            Item(null, "LG TV", BigDecimal.valueOf(1111.999)),
            Item(null, "Panasonic TV", BigDecimal.valueOf(1.00942)),
            Item(null, "Apple Watch", BigDecimal.valueOf(0.0043)),
            Item("ABC", "Samsung Watch", BigDecimal.valueOf(0.01))
    )

    @BeforeEach
    fun setUp() {

        itemReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(itemList))
                .flatMap { item -> itemReactiveRepository.save(item) }
                .doOnNext { item -> println("Inserted item is $item") }
                .blockLast()
    }

    @Test
    fun getAllItems() {
        StepVerifier.create(itemReactiveRepository.findAll())
                .expectSubscription()
                .expectNextCount(5)
                .verifyComplete()
    }


    @Test
    fun getItemById() {
        StepVerifier.create(itemReactiveRepository.findById("ABC"))
                .expectSubscription()
                .expectNextMatches { item: Item? ->
                    item?.description.equals("Samsung Watch")
                }
                .verifyComplete()
    }


    @Test
    fun getItemByDescription() {
        StepVerifier.create(itemReactiveRepository.findByDescription("Apple Watch"))
                .expectSubscription()
                .expectNextMatches { item: Item? ->
                    item?.price!! == BigDecimal.valueOf(0.0043)
                }
                .verifyComplete()
    }

    @Test
    fun saveItem() {
        val newItem: Item = Item(null, "Pear Watch", BigDecimal.valueOf(431.1))
        val savedItem: Mono<Item> = itemReactiveRepository.save(newItem)
        StepVerifier.create(savedItem.log())
                .expectSubscription()
                .expectNextMatches { item: Item -> item.id != null && item.description == "Pear Watch" }
                .verifyComplete()
    }


    @Test
    fun updateItem() {
        val updatedItem: Mono<Item> = itemReactiveRepository.findByDescription("LG TV")
                .map { item ->
                    item.price = BigDecimal.valueOf(99999.999)
                    item
                }
                .flatMap { item -> itemReactiveRepository.save(item) }
        StepVerifier.create(updatedItem.log())
                .expectSubscription()
                .expectNextMatches { item: Item -> item.price == BigDecimal.valueOf(99999.999)}
                .verifyComplete()
    }

    @Test
    fun deleteItem() {
        val deletedItem = itemReactiveRepository.findById("ABC")
                .map(Item::id!!)
                .flatMap { id: String? ->
                    itemReactiveRepository.deleteById(id!!)
                }
        StepVerifier.create(deletedItem)
                .expectSubscription()
                .verifyComplete()


        StepVerifier.create(itemReactiveRepository.findAll())
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete()
    }
}