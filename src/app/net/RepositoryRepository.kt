package app.net

import java.nio.charset.StandardCharsets.UTF_8
import java.net.URLEncoder.encode as encodeUrl

class RepositoryRepository(private val api: ApiGateway) {
  suspend fun search(q: String, perPage: Int = 100, pageNumber: Int = 0) =
    api.get<SearchResult>("/search/repositories?q=${encodeUrl(q, UTF_8)}&per_page=$perPage&page=$pageNumber")

  data class SearchResult(val totalCount: Int, val items: List<ResultItem>)
  data class ResultItem(val fullName: String)
}