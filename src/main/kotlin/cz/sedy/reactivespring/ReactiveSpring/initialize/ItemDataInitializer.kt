package cz.sedy.reactivespring.ReactiveSpring.initialize

import cz.sedy.reactivespring.ReactiveSpring.document.Item
import cz.sedy.reactivespring.ReactiveSpring.repository.ItemReactiveRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.math.BigDecimal

@Component
class ItemDataInitializer(val itemReactiveRepository: ItemReactiveRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        initialDataSetUp()
    }

    private fun initialDataSetUp() {

        val initItems: List<Item> = listOf<Item>(
                Item(null, "Samsung TV", BigDecimal.valueOf(54153)),
                Item(null, "LG TV", BigDecimal.valueOf(1111.999)),
                Item(null, "Panasonic TV", BigDecimal.valueOf(1.00942)),
                Item(null, "Apple Watch", BigDecimal.valueOf(0.0043)),
                Item("ABC", "Samsung Watch", BigDecimal.valueOf(0.01))
        )

        itemReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(initItems))
                .flatMap { item: Item -> itemReactiveRepository.save(item) }
                .subscribe()

    }

}