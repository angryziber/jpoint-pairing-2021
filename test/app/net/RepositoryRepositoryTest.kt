package app.net

import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RepositoryRepositoryTest {
  val api = mockk<ApiGateway>()
  val repository = RepositoryRepository(api)

  @BeforeEach
  fun setUp() {
    coEvery { api.getRaw(any()) } returns javaClass.getResourceAsStream("search-repository-response.json")!!
  }

  @Test
  fun `search for keyword`() = runBlocking {
    val result = repository.search("selenide")
    assertThat(result.totalCount).isEqualTo(1664)
    assertThat(result.items).hasSize(30)
    assertThat(result.items[0].fullName).isEqualTo("selenide/selenide")
  }

  @Test
  fun `non-default page size`() = runBlocking {
    repository.search("selenide", perPage = 100, pageNumber = 3)
    coVerify { api.getRaw("/search/repositories?q=selenide&per_page=100&page=3") }
  }

  @Test
  fun `query encoding`() = runBlocking {
    repository.search("привет&hello")
    coVerify { api.getRaw("/search/repositories?q=%D0%BF%D1%80%D0%B8%D0%B2%D0%B5%D1%82%26hello&per_page=100&page=0") }
  }
}
