package com.prenticedev.prenticeapp.data.pager

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prenticedev.prenticeapp.data.remote.response.ReviewFeedItems

//class FeedPagingSource : PagingSource<Int, ReviewFeedItems>() {
//    override fun getRefreshKey(state: PagingState<Int, ReviewFeedItems>): Int? {
//        return state.anchorPosition?.let { anchPos ->
//            val anchorPage = state.closestPageToPosition(anchPos)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewFeedItems> {
//        return try {
//            val
//        } catch (e: Exception) {
//            Log.e(TAG, "Error fetching feed data ${e.message.toString()}")
//        }
//    }
//
//    companion object {
//        private val TAG = FeedPagingSource::class.java.simpleName
//    }
//}