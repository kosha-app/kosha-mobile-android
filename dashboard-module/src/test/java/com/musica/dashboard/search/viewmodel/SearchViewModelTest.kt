package com.musica.dashboard.search.viewmodel

import android.app.Application
import com.musica.common.service.models.response.ResponseType
import com.musica.common.service.models.response.ServiceResponse
import com.musica.common.service.volley.ServiceResult
import com.musica.dashboard.player.repository.TrackRepository
import com.musica.common.service.models.response.AlbumResponse
import com.musica.common.service.models.response.SearchAlbumsResponse
import com.musica.common.service.models.response.SearchTracksResponse
import com.musica.common.service.models.response.TrackResponse
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @Mock
    private lateinit var trackRepository: TrackRepository

    @Mock
    private lateinit var application: Application

    @InjectMocks
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher after each test
        Dispatchers.resetMain()
    }

    @Test
    fun `search should update albums and tracks on successful response`() = runTest {
        val query = "Name"
        val albumSuccessResponse = ServiceResult(
            ServiceResponse(ResponseType.SUCCESS),
            SearchAlbumsResponse(
                listOf(
                    AlbumResponse(
                        "album-id",
                        "albumName",
                        "albumArtist",
                        "20 July, 2020",
                        "coverUrl",
                        listOf(
                            TrackResponse(
                                "track-id",
                                "trackName",
                                "trackArtist",
                                "trackUrl",
                                "coverUrl"
                            ),
                            TrackResponse(
                                "track-id2",
                                "trackName2",
                                "trackArtist2",
                                "trackUrl2",
                                "coverUrl2"
                            )
                        ),
                        ""
                    )
                )
            )
        )

        val trackSuccessResponse = ServiceResult(
            ServiceResponse(ResponseType.SUCCESS),
            SearchTracksResponse(
                listOf(
                    TrackResponse(
                        "track-id",
                        "trackName",
                        "trackArtist",
                        "trackUrl",
                        "coverUrl"
                    ),
                    TrackResponse(
                        "track-id2",
                        "trackName2",
                        "trackArtist2",
                        "trackUrl2",
                        "coverUrl2"
                    )
                )
            )

        )

        whenever(trackRepository.searchAlbum(query)).thenReturn(albumSuccessResponse)
        whenever(trackRepository.searchTracks(query)).thenReturn(trackSuccessResponse)

        viewModel.search("Name")

        Assert.assertEquals(viewModel.albums.value[0].albumId, "album-id")
        Assert.assertEquals(viewModel.tracks.value[0].trackId, "track-id")
    }

    @Test
    fun `search should not update albums and tracks on unsuccessful response`() = runTest {
        val query = "Name"
        val albumSuccessResponse = ServiceResult(
            ServiceResponse(ResponseType.ERROR),
            SearchAlbumsResponse(listOf())
        )

        val trackSuccessResponse = ServiceResult(
            ServiceResponse(ResponseType.ERROR),
            SearchTracksResponse(listOf())

        )

        whenever(trackRepository.searchAlbum(query)).thenReturn(albumSuccessResponse)
        whenever(trackRepository.searchTracks(query)).thenReturn(trackSuccessResponse)

        viewModel.search("Name")

        Assert.assertEquals(viewModel.albums.value.size, 0)
        Assert.assertEquals(viewModel.tracks.value.size, 0)
    }
}