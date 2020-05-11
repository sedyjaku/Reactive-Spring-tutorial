package cz.sedy.reactivespring.ReactiveSpring.router

import cz.sedy.reactivespring.ReactiveSpring.handler.ItemHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class ItemRouter(private val handler: ItemHandler) {

    @Bean
    fun itemsRouter() = router {
        (accept(MediaType.APPLICATION_JSON) and "/v1/fun/items").nest {
            GET("", handler::getAllItems)
            GET("/{id}", handler::getItemById)
            DELETE("/{id}", handler::deleteItemById)
            POST("", handler::createItem)
            PUT("/{id}", handler::updateItemById)
        }
    }
}