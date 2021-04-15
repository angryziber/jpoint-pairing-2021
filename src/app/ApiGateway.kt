package app

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers.ofString

class ApiGateway(val http: HttpClient) {
  private val baseUrl: String = "https://api.github.com"
  fun get(path: String) = http.send(HttpRequest.newBuilder(URI("$baseUrl$path")).GET().build(), ofString()).body()
}