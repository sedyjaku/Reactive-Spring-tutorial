package cz.sedy.reactivespring.ReactiveSpring.exception

import kotlin.RuntimeException

class CustomException(cause: Throwable?) : RuntimeException(cause) {
}