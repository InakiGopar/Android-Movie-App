package ar.edu.unicen.seminariomobile.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ar.edu.unicen.seminariomobile.data.Movie

class MoviePagingSource (
    private val movieDataSource : MovieRemoteDataSource,
    private val query: String
): PagingSource<Int,Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val movies: List<Movie>

        return try {
            val page = params.key ?: 1

            movies =
                if (query.isNotBlank()) {
                movieDataSource.searchMovies(query, page) ?: emptyList()
                }
                else {
                    movieDataSource.getMovies(page) ?: emptyList()
                }

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}