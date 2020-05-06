package cz.sedy.reactivespring.ReactiveSpring.repository

import cz.sedy.reactivespring.ReactiveSpring.document.Item
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface ItemReactiveRepository: ReactiveMongoRepository<Item, String> {

    fun findByDescription(description: String): Mono<Item>
}