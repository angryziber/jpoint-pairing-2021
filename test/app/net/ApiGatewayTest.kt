package app.net

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.InputStream
import java.net.http.HttpClient
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletableFuture.completedFuture

class ApiGatewayTest {
  val http = mockk<HttpClient>(relaxed = true)
  val api = ApiGateway(http)

  @Test
  fun get() = runBlocking {
    val res = mockk<HttpResponse<InputStream>> {
      every { body() } returns "{body}".byteInputStream()
    }
    every { http.sendAsync<InputStream>(any(), any()) } returns completedFuture(res)
    assertThat(api.getRaw("/users/angryziber").readBytes().decodeToString()).isEqualTo("{body}")
    verify { http.sendAsync<InputStream>(match { it.method() == "GET" && it.uri().path == "/users/angryziber" }, any()) }
  }
}
