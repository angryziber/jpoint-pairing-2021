package app

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RepositoryRepositoryTest {
  val api = mockk<ApiGateway>(relaxed = true)
  val repo = RepositoryRepository(api)

  @Test
  fun search() {
    every { api.get(any()) } returns javaClass.getResourceAsStream("github-search-response.json")!!.readBytes().decodeToString()
    assertThat(repo.search("selenide")).hasSize(1675)
    verify { api.get("/search/repositories?q=selenide") }
  }

  @Test
  fun `query is escaped`() {
    repo.search("привет/hello &")
    verify { api.get("/search/repositories?q=%D0%BF%D1%80%D0%B8%D0%B2%D0%B5%D1%82%2Fhello+%26") }
  }
}