package app.net

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.net.http.HttpClient

class ApiGatewayTest {
  val http = mockk<HttpClient>(relaxed = true)
  val api = ApiGateway(http)

  @Test
  fun get() {
    every { http.send<String>(any(), any()).body() } returns "{body}"
    assertThat(api.get("/users/angryziber")).isEqualTo("{body}")
    verify { http.send<String>(match { it.method() == "GET" && it.uri().path == "/users/angryziber" }, any()) }
  }
}