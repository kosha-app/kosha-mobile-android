package com.musica.dashboard.search.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.musica.common.service.models.response.ResponseType
import com.musica.dashboard.player.repository.TrackRepository
import com.musica.common.service.models.response.Album
import com.musica.common.service.models.response.AlbumResponse.Companion.toAlbum
import com.musica.common.service.models.response.Track
import com.musica.common.service.models.response.TrackResponse.Companion.toTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    private val trackRepository: TrackRepository
) : AndroidViewModel(application) {

    private val _albums = MutableStateFlow<List<Album>>(listOf())
    private val _tracks = MutableStateFlow<List<Track>>(listOf())

    val albums: StateFlow<List<Album>> = _albums.asStateFlow()
    val tracks: StateFlow<List<Track>> = _tracks.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch {
            val albumSearchResponse = trackRepository.searchAlbum(query)
            val trackSearchResponse = trackRepository.searchTracks(query)

            if (albumSearchResponse.serviceResponse.responseType == ResponseType.SUCCESS) {
                val albums = albumSearchResponse.data?.albums?.map {
                    it.toAlbum()
                }
                if (albums != null) {
                    _albums.value = albums
                }
            }

            if (trackSearchResponse.serviceResponse.responseType == ResponseType.SUCCESS) {
                val tracks = trackSearchResponse.data?.tracks?.map {
                    it.toTrack()
                }
                if (tracks != null) {
                    _tracks.value = tracks
                }
            }
        }
    }
}