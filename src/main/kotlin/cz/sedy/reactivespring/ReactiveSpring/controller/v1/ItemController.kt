package cz.sedy.reactivespring.ReactiveSpring.controller.v1

import cz.sedy.reactivespring.ReactiveSpring.document.Item
import cz.sedy.reactivespring.ReactiveSpring.repository.ItemReactiveRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class ItemController(

        val itemReactiveRepository: ItemReactiveRepository) {

    @GetMapping("/v1/items")
    fun getAllItems(): Flux<Item> {
        return itemReactiveRepository.findAll()
    }

    @GetMapping("/v1/items/{itemId}")
    fun getItem(@PathVariable itemId: String): Mono<Item> {
        return itemReactiveRepository.findById(itemId)
    }

    @PostMapping("/v1/items")
    fun createItem(@RequestBody request: Item): Mono<Item> {
        return itemReactiveRepository.save(request)
    }

    @DeleteMapping("/v1/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteItem(@PathVariable itemId: String): Mono<Void> {
        return itemReactiveRepository.deleteById(itemId)
    }

    @PutMapping("/v1/items/{itemId}")
    fun getItem(@PathVariable itemId: String,
                @RequestBody request: Item): Mono<Item> {
        return itemReactiveRepository.findById(itemId)
                .flatMap { currentItem ->
                    currentItem.price = request.price
                    currentItem.description = request.description
                    itemReactiveRepository.save(currentItem)
                }
    }

}