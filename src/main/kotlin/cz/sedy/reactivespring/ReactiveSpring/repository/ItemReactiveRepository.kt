package cz.sedy.reactivespring.ReactiveSpring.repository

import cz.sedy.reactivespring.ReactiveSpring.document.Item
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ItemReactiveRepository: ReactiveMongoRepository<Item, String> {
}