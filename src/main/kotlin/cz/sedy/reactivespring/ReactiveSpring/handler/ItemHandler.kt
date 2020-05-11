package cz.sedy.reactivespring.ReactiveSpring.handler

import cz.sedy.reactivespring.ReactiveSpring.document.Item
import cz.sedy.reactivespring.ReactiveSpring.repository.ItemReactiveRepository
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Configuration
class ItemHandler(val itemReactiveRepository: ItemReactiveRepository) {


    fun getAllItems(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(itemReactiveRepository.findAll(), Item::class.java)
    }


    fun getItemById(serverRequest: ServerRequest): Mono<ServerResponse> {
        val id: String = serverRequest.pathVariable("id")
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(itemReactiveRepository.findById(id), Item::class.java)
    }


    fun deleteItemById(serverRequest: ServerRequest): Mono<ServerResponse> {
        val id: String = serverRequest.pathVariable("id")
        return ServerResponse.noContent()
                .build(itemReactiveRepository.deleteById(id))
    }


    fun createItem(serverRequest: ServerRequest): Mono<ServerResponse> {
        val itemMono: Mono<Item> = serverRequest.bodyToMono(Item::class.java)
        return itemMono.flatMap { item ->
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(itemReactiveRepository.save(item), Item::class.java)
        }
    }


    fun updateItemById(serverRequest: ServerRequest): Mono<ServerResponse> {
        val itemMono: Mono<Item> = serverRequest.bodyToMono(Item::class.java)
        val id: String = serverRequest.pathVariable("id")
        return itemMono.flatMap { item ->
            itemReactiveRepository.findById(id).flatMap { updatedItem ->
                updatedItem.description = item.description
                updatedItem.price = item.price
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(itemReactiveRepository.save(updatedItem), Item::class.java)
            }
        }
    }
}