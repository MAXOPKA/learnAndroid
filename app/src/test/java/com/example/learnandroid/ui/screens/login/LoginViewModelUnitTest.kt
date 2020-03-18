package com.example.learnandroid.ui.screens.login

import com.example.learnandroid.mocks.services.APIMock
import com.example.learnandroid.mocks.services.PreferencesMock
import com.example.learnandroid.services.IAPI
import com.example.learnandroid.services.IPreferences
import com.example.learnandroid.services.api.responses.LoginResponse
import com.example.learnandroid.utils.DaggerAppComponent
import com.example.learnandroid.utils.RxImmediateSchedulerRule
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit


class LoginViewModelUnitTest {

    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Rule @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    var apiServiceMock: IAPI = Mockito.mock(APIMock::class.java)

    @Mock
    var preferencesService: IPreferences = Mockito.mock(PreferencesMock::class.java)

    internal lateinit var underTest: LoginViewModel

    @BeforeEach
    fun setUp() {
        underTest = LoginViewModel()
        DaggerAppComponent.builder().build().injectBaseViewModel(underTest)

        // mockInjectionComponent.injectBaseViewModel(underTest)
    }

    @Test
    fun Logintest() {
        print(underTest.apiService)
        val loginResponse = LoginResponse("12313123123131")
        // Mockito.`when`(apiServiceMock.login()).thenReturn()

        underTest.getLoginData()?.observeForever {
            // assertThat(listResult.size, `is`(1))
        }

        underTest.getNavigationCommands()?.observeForever {
            // assertThat(1, `is`(1))
        }

        underTest.login("1@t.ru", "123456")

//        val testObserver = TestObserver<LoginLiveDataModel>()
//        result.subscribe(testObserver)
//        testObserver.assertComplete()
//        testObserver.assertNoErrors()
//        testObserver.assertValueCount(1)

        // val loginResult = testObserver.values()[0]
        // assertThat(listResult.size, `is`(2))
    }
}
