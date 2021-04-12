package app.summer

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.net.http.HttpClient
import java.net.http.HttpClient.newHttpClient
import java.util.*

class InjectorTest {
  val injector = Injector()

  @Test
  fun `no deps`() {
    assertThat(injector.require<Date>()).isInstanceOf(Date::class.java)
    assertThat(injector.require<String>()).isInstanceOf(String::class.java)
  }

  @Test
  fun `with deps`() {
    class WithDeps(val date: Date)
    assertThat(injector.require<WithDeps>().date).isInstanceOf(Date::class.java)
  }

  @Test
  fun register() {
    val http = newHttpClient()
    injector.register(HttpClient::class, http)
    assertThat(injector.require<HttpClient>()).isSameAs(http)
  }
}
