package summer

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.net.http.HttpClient

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

  @Test
  fun `provided instance`() {
    val impl = AnImpl()
    injector.provide<AnInterface>(impl)
    val subject = injector.require(DependsOnInterface::class)
    assertThat(subject.impl).isSameAs(impl)
  }

  @Test
  fun `no primary constructor`() {
    assertThatThrownBy { injector.require(HttpClient::class) }
      .hasMessage("${HttpClient::class} does not have a primary constructor")
  }

  class NoDeps
  class WithDeps(val dep: NoDeps)

  interface AnInterface
  class AnImpl: AnInterface
  class DependsOnInterface(val impl: AnInterface)
}