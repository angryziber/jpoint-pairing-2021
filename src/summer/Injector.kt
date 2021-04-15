package summer

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class Injector {
  private val singletons = mutableMapOf<KClass<*>, Any>()

  fun <T: Any> require(type: KClass<T>): T {
    singletons[type]?.let { return it as T }

    val constructor = type.primaryConstructor!!
    val params = constructor.parameters.map { require(it.type.classifier as KClass<*>) }
    return constructor.call(*params.toTypedArray()).also {
      singletons[type] = it
    }
  }
}
