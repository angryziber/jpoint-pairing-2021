import app.RepositoryRepository
import summer.Injector
import java.net.http.HttpClient

fun main() {
  val injector = Injector().apply {
    provide<HttpClient>(HttpClient.newHttpClient())
  }

  val repo = injector.require(RepositoryRepository::class)
  repo.search("selenide").items.forEach { println(it.fullName) }
}
