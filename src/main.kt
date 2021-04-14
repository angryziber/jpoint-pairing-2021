import app.net.RepositoryRepository
import app.summer.Injector
import java.net.http.HttpClient
import java.net.http.HttpClient.Redirect.NORMAL

suspend fun main(args: Array<String>) {
  val injector = Injector().apply {
    register(HttpClient::class, HttpClient.newBuilder().followRedirects(NORMAL).build())
  }
  val repo: RepositoryRepository = injector.require()
  println(repo.search("jpoint"))
}
