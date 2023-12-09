package com.musica.dashboard.search.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.musica.common.service.models.response.ResponseType
import com.musica.dashboard.player.repository.TrackRepository
import com.musica.dashboard.player.service.Album
import com.musica.dashboard.player.service.AlbumResponse.Companion.toAlbum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    private val trackRepository: TrackRepository
) : AndroidViewModel(application) {

    private val _albums = MutableSharedFlow<List<Album>?>()

    val albums: SharedFlow<List<Album>?> = _albums.asSharedFlow()

    fun searchAlbum(query: String) {
        viewModelScope.launch {
            val response = trackRepository.searchAlbum(query)

            if (response.serviceResponse.responseType == ResponseType.SUCCESS) {
                val albums = response.data?.albums?.map {
                    it.toAlbum()
                }
                _albums.emit(albums)
            }
        }
    }
}