package app.summer

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class Injector {
  private val instances = mutableMapOf<KClass<*>, Any>()

  fun <T: Any> register(type: KClass<T>, instance: T) {
    instances[type] = instance
  }

  inline fun <reified T: Any> require() = require(T::class)

  fun <T: Any> require(type: KClass<T>): T {
    val instance = instances[type] as T?
    if (instance != null) return instance
    val constructor = type.primaryConstructor ?: type.constructors.minByOrNull { it.parameters.size } ?: error("$type has no available constructors")
    val params = constructor.parameters.map { require(it.type.classifier as KClass<*>) }.toTypedArray()
    return constructor.call(*params)
  }
}
