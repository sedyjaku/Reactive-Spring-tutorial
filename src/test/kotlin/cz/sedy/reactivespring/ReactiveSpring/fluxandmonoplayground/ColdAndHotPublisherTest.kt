package cz.sedy.reactivespring.ReactiveSpring.fluxandmonoplayground

import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import java.time.Duration

class ColdAndHotPublisherTest {

    @Test
    fun coldPublisherTest(){
        val stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1L))

        stringFlux.subscribe{s -> println("Subscriber 1 is $s")}
        Thread.sleep(1000)
        stringFlux.subscribe{s -> println("Subscriber 2 is $s")}
        Thread.sleep(2000)
        stringFlux.subscribe{s -> println("Subscriber 3 is $s")}
        Thread.sleep(3000)

    }

    @Test
    fun hardPublisherTest(){
        val stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1L))

        val connectableFlux = stringFlux.publish()
        connectableFlux.connect()
        connectableFlux.subscribe{s -> println("Subscriber 1 is $s")}
        Thread.sleep(1000)
        connectableFlux.subscribe{s -> println("Subscriber 2 is $s")}
        Thread.sleep(2000)
        connectableFlux.subscribe{s -> println("Subscriber 3 is $s")}
        Thread.sleep(3000)

    }
}