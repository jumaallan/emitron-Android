package com.raywenderlich.emitron.data.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import com.raywenderlich.emitron.utils.isEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsRepositoryTest {

  private lateinit var settingsRepository: SettingsRepository

  private val settingsPrefs: SettingsPrefs = mock()

  @get:Rule
  val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setUp() {
    settingsRepository = SettingsRepository(settingsPrefs)
  }

  @Test
  fun updateCrashReportingAllowed() {
    settingsRepository.updateCrashReportingAllowed(true)
    verify(settingsPrefs).saveCrashReportingAllowed(true)
    verifyNoMoreInteractions(settingsPrefs)
  }

  @Test
  fun updateNightMode() {
    settingsRepository.updateNightMode(1)
    verify(settingsPrefs).saveNightMode(1)
    verifyNoMoreInteractions(settingsPrefs)
  }

  @Test
  fun getNightMode() {
    whenever(settingsPrefs.getNightMode()).doReturn(1)
    val result = settingsRepository.getNightMode()
    result isEqualTo 1
    verify(settingsPrefs).getNightMode()
    verifyNoMoreInteractions(settingsPrefs)
  }

  @Test
  fun isCrashReportingAllowed() {
    whenever(settingsPrefs.isCrashReportingAllowed()).doReturn(true)
    val result = settingsRepository.isCrashReportingAllowed()
    result isEqualTo true
    verify(settingsPrefs).isCrashReportingAllowed()
    verifyNoMoreInteractions(settingsPrefs)
  }

  @Test
  fun updateAutoPlaybackAllowed() {
    settingsRepository.updateAutoPlaybackAllowed(true)
    verify(settingsPrefs).saveAutoPlayAllowed(true)
    verifyNoMoreInteractions(settingsPrefs)
  }

  @Test
  fun updateSubtitleLanguage() {
    settingsRepository.updateSubtitleLanguage("en")
    verify(settingsPrefs).saveSubtitleLanguage("en")
    verifyNoMoreInteractions(settingsPrefs)
  }

  @Test
  fun updateSelectedPlaybackSpeed() {
    settingsRepository.updateSelectedPlaybackSpeed(1.0f)
    verify(settingsPrefs).savePlaybackSpeed(1.0f)
    verifyNoMoreInteractions(settingsPrefs)
  }

  @Test
  fun updateSelectedPlaybackQuality() {
    settingsRepository.updateSelectedPlaybackQuality(1024)
    verify(settingsPrefs).savePlaybackQuality(1024)
    verifyNoMoreInteractions(settingsPrefs)
  }

  @Test
  fun getPlaybackSpeed() {
    whenever(settingsPrefs.getPlaybackSpeed()).doReturn(1.0f)
    val result = settingsRepository.getPlaybackSpeed()
    result isEqualTo 1.0f
    verify(settingsPrefs).getPlaybackSpeed()
    verifyNoMoreInteractions(settingsPrefs)
  }

  @Test
  fun getPlaybackQuality() {
    whenever(settingsPrefs.getPlaybackQuality()).doReturn(1024)
    val result = settingsRepository.getPlaybackQuality()
    result isEqualTo 1024
    verify(settingsPrefs).getPlaybackQuality()
    verifyNoMoreInteractions(settingsPrefs)
  }

  @Test
  fun getSubtitleLanguage() {
    whenever(settingsPrefs.getSubtitleLanguage()).doReturn("en")
    val result = settingsRepository.getSubtitleLanguage()
    result isEqualTo "en"
    verify(settingsPrefs).getSubtitleLanguage()
    verifyNoMoreInteractions(settingsPrefs)
  }

  @Test
  fun getAutoPlayNextAllowed() {
    whenever(settingsPrefs.getAutoPlayAllowed()).doReturn(true)
    val result = settingsRepository.getAutoPlayNextAllowed()
    result isEqualTo true
    verify(settingsPrefs).getAutoPlayAllowed()
    verifyNoMoreInteractions(settingsPrefs)
  }
}
