package com.razeware.emitron.ui.mytutorial.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razeware.emitron.data.bookmarks.BookmarkRepository
import com.razeware.emitron.model.Data
import com.razeware.emitron.ui.content.ContentPagedViewModel
import com.razeware.emitron.utils.BoundaryCallbackNotifier
import com.razeware.emitron.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for bookmarks
 */
@HiltViewModel
class BookmarkViewModel @Inject constructor(
  private val bookmarkRepository: BookmarkRepository,
  private val contentPagedViewModel: ContentPagedViewModel,
  private val bookmarkActionDelegate: BookmarkActionDelegate,
  private val boundaryCallbackNotifier: BoundaryCallbackNotifier
) : ViewModel() {


  /**
   * Observer for bookmark delete action
   *
   */
  val bookmarkDeleteActionResult: LiveData<Event<BookmarkActionDelegate.BookmarkActionResult>>
    get() {
      return bookmarkActionDelegate.bookmarkActionResult
    }

  /**
   * Load bookmarks from database
   */
  fun loadBookmarks() {
    val listing =
      bookmarkRepository.getBookmarks(
        boundaryCallbackNotifier = boundaryCallbackNotifier
      )
    contentPagedViewModel.localRepoResult.value = listing
  }

  /**
   * Delete a bookmark
   */
  fun updateContentBookmark(content: Data?) {
    viewModelScope.launch {
      bookmarkActionDelegate.updateContentBookmark(content, boundaryCallbackNotifier)
    }
  }

  /**
   * Get pagination helper
   */
  fun getPaginationViewModel(): ContentPagedViewModel = contentPagedViewModel
}
