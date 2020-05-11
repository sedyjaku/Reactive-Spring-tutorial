package cz.sedy.reactivespring.ReactiveSpring.controller.v1

import cz.sedy.reactivespring.ReactiveSpring.document.Item
import cz.sedy.reactivespring.ReactiveSpring.repository.ItemReactiveRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class ItemController(

        val itemReactiveRepository: ItemReactiveRepository) {

    @GetMapping("/v1/items")
    fun getAllItems(): Flux<Item> {
        return itemReactiveRepository.findAll()
    }

}