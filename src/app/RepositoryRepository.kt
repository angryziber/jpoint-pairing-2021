package app

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.nio.charset.StandardCharsets.UTF_8
import java.net.URLEncoder.encode as urlEncode

class RepositoryRepository(private val api: ApiGateway) {
  private val json = jacksonObjectMapper()
    .setPropertyNamingStrategy(SNAKE_CASE)
    .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)

  fun search(query: String, page: Int = 0): SearchResult {
    val json = api.get("/search/repositories?q=${urlEncode(query, UTF_8)}" +
      if (page > 0) "&page=$page" else "")
    return this.json.readValue(
      json,
      SearchResult::class.java
    )
  }

  data class SearchResult(val totalCount: Int, val items: List<SearchItem>)
  data class SearchItem(val fullName: String)
}
