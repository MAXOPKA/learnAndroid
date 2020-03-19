package com.example.learnandroid.ui.screens.login

import androidx.lifecycle.Observer
import com.example.learnandroid.mocks.services.PreferencesMock
import com.example.learnandroid.models.LoginModel
import com.example.learnandroid.services.API
import com.example.learnandroid.services.IPreferences
import com.example.learnandroid.services.api.requests.LoginRequest
import com.example.learnandroid.services.api.responses.LoginResponse
import com.example.learnandroid.ui.utils.MessageTypes
import com.example.learnandroid.ui.utils.navigation.NavigationCommand
import com.example.learnandroid.utils.junit.rules.InstantExecutorExtension
import com.example.learnandroid.utils.junit.rules.RxImmediateSchedulerRule
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*

@ExtendWith(InstantExecutorExtension::class)
class LoginViewModelUnitTest {
    @get:Rule
    val rxImmediateSchedulerRule: RxImmediateSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var underTest: LoginViewModel

    var apiService: API = mock(API::class.java)

    @Mock
    var preferencesService: IPreferences = mock(PreferencesMock::class.java)

    @BeforeEach
    fun prepare() {
        underTest = LoginViewModel(apiService, preferencesService)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun LoginSuccessTest() {
        val loginDataObserver = mock(Observer::class.java) as Observer<LoginLiveDataModel>
        val navigationObserver = mock(Observer::class.java) as Observer<NavigationCommand>

        underTest.getLoginData()?.observeForever(loginDataObserver)
        underTest.getNavigationCommands()?.observeForever(navigationObserver)

        doReturn(Observable.just(LoginModel(false,null)))
            .`when`(apiService).login(LoginRequest("1@t.ru", "123456"))

        underTest.login("1@t.ru", "123456")

        verify(loginDataObserver, times(3))
            .onChanged(LoginLiveDataModel("Success!", MessageTypes.SUCCESS, isLoading = false))

        verify(navigationObserver, times(1))
            .onChanged(NavigationCommand.To(LoginDirections.actionLoginToTransactionsList()))
    }

    @Test
    fun LoginFailTest() {
        val loginDataObserver = mock(Observer::class.java) as Observer<LoginLiveDataModel>
        val navigationObserver = mock(Observer::class.java) as Observer<NavigationCommand>

        underTest.getLoginData()?.observeForever(loginDataObserver)
        underTest.getNavigationCommands()?.observeForever(navigationObserver)

        doReturn(Observable.just(LoginModel(true,"Error")))
            .`when`(apiService).login(LoginRequest("1@t.ru", "123456"))

        underTest.login("1@t.ru", "123456")

        verify(loginDataObserver, times(3))
            .onChanged(LoginLiveDataModel("Error", MessageTypes.ERROR, isLoading = false))

        verify(navigationObserver, times(0))
    }
}
