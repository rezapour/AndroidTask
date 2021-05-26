package com.rezapour.carstask.repository



import com.rezapour.carstask.utils.UiState
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection
import com.google.common.truth.Truth.assertThat
import com.rezapour.carstask.data.network.*
class MainRepositoryImplTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var repository: MainRepositoryImpl

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        val apiHelperFake = ApihelperFack(mockWebServer)
        repository = MainRepositoryImpl(apiHelperFake, NetworkModelMapper())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getCarsTestSuccess() {
        val mock = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(FackRespondJson.RESPOND_OK)
        mockWebServer.enqueue(mock)

        runBlocking {
            val result = repository.getCars()
            assertThat(result).isInstanceOf(UiState.Success::class.java)
        }
    }


    @Test
    fun getCarsTestInternetProblem() {
        val mock = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_FORBIDDEN)
            .setBody(FackRespondJson.RESPOND_OK)
        mockWebServer.enqueue(mock)

        runBlocking {
            val result = repository.getCars()
            assertThat(result).isInstanceOf(UiState.Error::class.java)
        }
    }

    @Test
    fun getCarsTestRespondisEpity() {
        val mock = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_FORBIDDEN)
            .setBody("")
        mockWebServer.enqueue(mock)

        runBlocking {
            val result = repository.getCars()
            assertThat(result).isInstanceOf(UiState.Error::class.java)
        }
    }

}