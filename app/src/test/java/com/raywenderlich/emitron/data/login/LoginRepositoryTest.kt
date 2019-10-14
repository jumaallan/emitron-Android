package com.raywenderlich.emitron.data.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import com.raywenderlich.emitron.model.Contents
import com.raywenderlich.emitron.network.AuthInterceptor
import com.raywenderlich.emitron.utils.TestCoroutineRule
import com.raywenderlich.emitron.utils.async.ThreadManager
import com.raywenderlich.emitron.utils.isEqualTo
import com.raywenderlich.guardpost.data.SSOUser
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginRepositoryTest {

  private val loginApi: LoginApi = mock()

  private val loginPrefs: LoginPrefs = mock()

  private val authInterceptor: AuthInterceptor = mock()

  private val threadManager: ThreadManager = mock()

  private lateinit var repository: LoginRepository

  @get:Rule
  val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val testCoroutineRule: TestCoroutineRule = TestCoroutineRule()

  @Before
  fun setUp() {
    repository = LoginRepository(loginApi, loginPrefs, authInterceptor, threadManager)
  }

  @Test
  fun isLoggedIn() {
    whenever(loginPrefs.isLoggedIn()).doReturn(true)
    val result = repository.isLoggedIn()
    verify(loginPrefs).isLoggedIn()
    verifyNoMoreInteractions(loginPrefs)
    assertThat(result).isTrue()
  }

  @Test
  fun storeUser() {
    whenever(loginPrefs.authToken()).doReturn("Raywenderlich")
    val user = SSOUser(token = "Raywenderlich")
    repository.storeUser(user)
    verify(loginPrefs).storeUser(user)
    verify(loginPrefs).authToken()
    verify(authInterceptor).updateAuthToken("Raywenderlich")
    verifyNoMoreInteractions(loginPrefs)
    verifyNoMoreInteractions(authInterceptor)
  }

  @Test
  fun deleteUser() {
    repository.deleteUser()
    verify(loginPrefs).clear()
    verify(authInterceptor).clear()
    verifyNoMoreInteractions(loginPrefs)
    verifyNoMoreInteractions(authInterceptor)
  }

  @Test
  fun getPermissions() {
    testCoroutineRule.runBlockingTest {
      val expectedContent = Contents()
      whenever(loginApi.getPermissions()).doReturn(expectedContent)
      whenever(threadManager.io).doReturn(Dispatchers.Unconfined)
      val result = repository.getPermissions()
      verify(loginApi).getPermissions()
      assertThat(result).isEqualTo(expectedContent)
      verifyNoMoreInteractions(loginApi)
    }
  }


  @Test
  fun hasPermissions() {
    // Given
    whenever(loginPrefs.getPermissions()).doReturn(listOf("download-videos"))

    // Then
    repository.hasPermissions() isEqualTo true
  }

  @Test
  fun updatePermissions() {
    repository.updatePermissions(listOf("perm-1", "perm-2"))
    verify(loginPrefs).savePermissions(listOf("perm-1", "perm-2"))
    verifyNoMoreInteractions(loginPrefs)
  }

  @Test
  fun hasDownloadPermission() {
    // Given
    whenever(loginPrefs.getPermissions()).doReturn(listOf("download-videos"))

    // Then
    repository.hasDownloadPermission() isEqualTo true
  }
}
