package summer

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InjectorTest {
  val injector = Injector()

  @Test
  fun `no dependencies`() {
    assertThat(injector.require(NoDeps::class)).isInstanceOf(NoDeps::class.java)
  }

  @Test
  fun `deps are singletons`() {
    assertThat(injector.require(NoDeps::class)).isSameAs(injector.require(NoDeps::class))
  }

  @Test
  fun `with dependencies`() {
    val subject = injector.require(WithDeps::class)
    assertThat(subject).isInstanceOf(WithDeps::class.java)
    assertThat(subject.dep).isInstanceOf(NoDeps::class.java)
  }

  class NoDeps
  class WithDeps(val dep: NoDeps)
}