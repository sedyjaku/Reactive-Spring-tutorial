package cz.sedy.reactivespring.ReactiveSpring.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document
data class Item(

        @Id
        val id: String,

        var description: String,

        var price: BigDecimal
)