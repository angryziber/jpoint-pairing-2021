package app

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.nio.charset.StandardCharsets.UTF_8
import java.net.URLEncoder.encode as urlEncode

class RepositoryRepository(private val api: ApiGateway) {
  private val json = jacksonObjectMapper()

  fun search(query: String): List<String> {
    return listOf(api.get("/search/repositories?q=${urlEncode(query, UTF_8)}"), "")
  }
}
