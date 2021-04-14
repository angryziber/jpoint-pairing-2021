import app.net.ApiGateway
import app.net.RepositoryRepository
import java.net.http.HttpClient
import java.net.http.HttpClient.Redirect.NORMAL

suspend fun main(args: Array<String>) {
  val http = HttpClient.newBuilder().followRedirects(NORMAL).build()
  val api = ApiGateway(http)
  val repo = RepositoryRepository(api)
  println(repo.search("jpoint"))
}
