package app.net

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.future.await
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers.ofInputStream

class ApiGateway(private val http: HttpClient) {
  private val baseUrl = "https://api.github.com"
  companion object {
    val json = jacksonObjectMapper().setPropertyNamingStrategy(SnakeCaseStrategy()).configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
  }

  suspend fun getRaw(path: String) = http.sendAsync(request(path).GET().build(), ofInputStream()).await().body()
  suspend inline fun <reified T> get(path: String): T = getRaw(path).use { json.readValue(it, T::class.java) }

  private fun request(path: String) = HttpRequest.newBuilder(URI(baseUrl + path)).header("Accept", "application/vnd.github.v3+json")
}
