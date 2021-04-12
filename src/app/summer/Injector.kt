package app.summer

import kotlin.reflect.KClass

class Injector {
  private val instances = mutableMapOf<KClass<*>, Any>()

  inline fun <reified T: Any> require() = require(T::class)

  fun <T: Any> require(type: KClass<T>): T {
    val instance = instances[type] as T?
    if (instance != null) return instance
    val constructor = type.constructors.minByOrNull { it.parameters.size }!!
    val params = constructor.parameters.map { require(it.type.classifier as KClass<*>) }.toTypedArray()
    return constructor.call(*params)
  }

  fun <T: Any> register(type: KClass<T>, instance: T) {
    instances[type] = instance
  }
}
