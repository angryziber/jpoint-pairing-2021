package app.net

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpClient.Redirect.NORMAL
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ApiGateway(private val http: HttpClient) {
  private val baseUrl = "https://api.github.com"

  fun get(path: String) = http.send(request(path).GET().build(), HttpResponse.BodyHandlers.ofString()).body()

  private fun request(path: String) = HttpRequest.newBuilder(URI(baseUrl + path)).header("Accept", "application/vnd.github.v3+json")
}

fun main() {
  println(ApiGateway(HttpClient.newBuilder().followRedirects(NORMAL).build()).get("/repos/codeborne/selenide"))
}