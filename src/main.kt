import app.ApiGateway
import app.RepositoryRepository
import java.net.http.HttpClient

fun main() {
  val http = HttpClient.newHttpClient()
  val api = ApiGateway(http)
  val repo = RepositoryRepository(api)
  println(repo.search("selenide"))
}
