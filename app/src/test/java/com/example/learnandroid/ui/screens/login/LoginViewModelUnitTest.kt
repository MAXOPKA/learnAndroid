package com.example.learnandroid.ui.screens.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.learnandroid.mocks.services.APIMock
import com.example.learnandroid.mocks.services.PreferencesMock
import com.example.learnandroid.services.IAPI
import com.example.learnandroid.services.IPreferences
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit


class LoginViewModelUnitTest {
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var underTest: LoginViewModel

    @Mock
    var apiServiceMock: IAPI = Mockito.mock(APIMock::class.java)

    @Mock
    var preferencesService: IPreferences = Mockito.mock(PreferencesMock::class.java)

    @BeforeEach
    fun prepare() {
        underTest = LoginViewModel(apiServiceMock, preferencesService)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun Logintest() {
        underTest.login("1@t.ru", "123456")
    }
}
