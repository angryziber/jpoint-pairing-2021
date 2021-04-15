package summer

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class Injector {
  private val singletons = mutableMapOf<KClass<*>, Any>()

  fun <T: Any> provide(type: KClass<out T>, singleton: T) {
    singletons[type] = singleton
  }

  inline fun <reified T: Any> provide(singleton: T) = provide(T::class, singleton)

  fun <T: Any> require(type: KClass<T>): T {
    singletons[type]?.let { return it as T }

    val constructor = type.primaryConstructor ?: error("$type does not have a primary constructor")
    val params = constructor.parameters.map { require(it.type.classifier as KClass<*>) }
    return constructor.call(*params.toTypedArray()).also {
      singletons[type] = it
    }
  }
}
