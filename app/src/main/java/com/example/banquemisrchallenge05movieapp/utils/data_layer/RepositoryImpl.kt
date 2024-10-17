package com.example.banquemisrchallenge05movieapp.utils.data_layer

import com.example.banquemisrchallenge05movieapp.utils.data_layer.local.LocalDataSource
import com.example.banquemisrchallenge05movieapp.utils.data_layer.remote.RemoteDataSource
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultNowPlaying
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultPopular
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDbResultUpcoming
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import kotlinx.coroutines.flow.Flow

class RepositoryImpl private constructor(
    private var moviesRemoteDataSource: RemoteDataSource,
    private var moviesLocalDataSource: LocalDataSource,

    ) : Repository {


    companion object {
        private var instance: RepositoryImpl? = null
        fun getInstance(
            moviesRemoteDataSource: RemoteDataSource,
            moviesLocalDataSource: LocalDataSource,
        ): RepositoryImpl {
            return instance ?: synchronized(this) {
                val temp = RepositoryImpl(
                    moviesRemoteDataSource,
                    moviesLocalDataSource
                )
                instance = temp
                temp
            }

        }
    }

    // Remote Data Source
    override suspend fun fetchUpcomingMovies(
        language: String,
        page: Int
    ): Flow<MovieDbResultUpcoming> {
        return moviesRemoteDataSource.fetchUpcomingMovies(language, page)
    }

    override suspend fun fetchPopularMovies(
        language: String,
        page: Int
    ): Flow<MovieDbResultPopular> {
        return moviesRemoteDataSource.fetchPopularMovies(language, page)
    }

    override suspend fun fetchNowPlayingMovies(
        language: String,
        page: Int
    ): Flow<MovieDbResultNowPlaying> {
        return moviesRemoteDataSource.fetchNowPlayingMovies(language, page)
    }

    override suspend fun fetchMovieDetails(movieId: Int): Flow<MovieDetails> {
        return  moviesRemoteDataSource.fetchMovieDetails(movieId)
    }

}