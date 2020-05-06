package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@WebFluxTest
open class SpringTestParent {

    @Autowired
    protected lateinit var webTestClient: WebTestClient
}