package com.mtanmay.stupa.viewmodels

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mtanmay.data.models.DBUser
import com.mtanmay.domain.models.LocalUser
import com.mtanmay.domain.models.interfaces.User
import com.mtanmay.domain.repository.IUserAuthRepository
import com.mtanmay.stupa.ui.states.SignInUIState
import com.mtanmay.stupa.ui.states.SignUpUIState
import com.mtanmay.stupa.utils.PreferencesManager
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class AuthViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var userAuthRepository: IUserAuthRepository

    @MockK
    private lateinit var prefMgr: PreferencesManager

    private lateinit var authViewModel: AuthViewModel
    private val testDispatcher = UnconfinedTestDispatcher()
    
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        authViewModel = AuthViewModel(userAuthRepository)
    }

    // SIGN IN TESTS

    @Test
    fun `IF viewmodel initialized, THEN sign in state set to default`() {
        assertThat(authViewModel.signInState.value).isEqualTo(SIGN_IN_DEFAULT_STATE)
    }

    @Test
    fun `IF attempt sign in, THEN sign in state set to loading`() = runTest(testDispatcher) {

        coEvery { userAuthRepository.signIn(any(String::class), any(String::class)) } returns testLocalUser

        authViewModel.signIn("", "")

        authViewModel.signInState.test {
            assertThat(awaitItem())
                .isEqualTo(SIGN_IN_LOADING_STATE)
        }

    }

    @Test
    fun `IF user does not exist, THEN sign in fails`() = runTest(context = testDispatcher) {

        coEvery { userAuthRepository.signIn(any(String::class), any(String::class)) } returns null

        authViewModel.signIn("", "")

        authViewModel.signInState.test {
            skipItems(1)

            assertThat(awaitItem())
                .isEqualTo(SIGN_IN_FAILED_STATE)
        }

    }

    @Test
    fun `IF user exists, THEN sign in succeeds`() = runTest(testDispatcher) {

        coEvery {
            userAuthRepository.signIn(
                any(String::class),
                any(String::class)
            )
        } returns testLocalUser

        authViewModel.signIn("", "")

        authViewModel.signInState.test {
            skipItems(1)

            assertThat(awaitItem())
                .isEqualTo(SIGN_IN_SUCCESS_STATE)

        }
    }

    @Test
    fun `IF sign in attempt fails, THEN reset state succeeds`() = runTest(testDispatcher) {

        coEvery { userAuthRepository.signIn(any(String::class), any(String::class)) } returns null

        // attempt sign in ; expected to fail
        authViewModel.signIn("", "")

        // assert that sign in failed
        authViewModel.signInState.test {
            skipItems(1)

            assertThat(awaitItem())
                .isEqualTo(SIGN_IN_FAILED_STATE)
        }

        // reset sign in state
        authViewModel.resetSignInUIState()

        // assert that state is set to default        }
        assertThat(authViewModel.signInState.value)
            .isEqualTo(SIGN_IN_DEFAULT_STATE)
    }

    // SIGN UP TESTS
    
    @Test
    fun `IF viewmodel initialized, THEN sign up state set to default`() = runTest(testDispatcher) {
        assertThat(authViewModel.signUpState.value)
            .isEqualTo(SIGN_UP_DEFAULT_STATE)
    }

    @Test
    fun `IF attempt sign up, THEN sign up state set to in_process`() = runTest(testDispatcher) {
        coEvery { userAuthRepository.userAlreadyExists(any(String::class)) } returns false

        authViewModel.signUp(testDbUser, prefMgr) {}

        authViewModel.signUpState.test {
            assertThat(awaitItem())
                .isEqualTo(SIGN_UP_IN_PROCESS_STATE)
        }

    }

    @Test
    fun `IF user already exists, sign up fails`() = runTest(testDispatcher) {
        coEvery { userAuthRepository.userAlreadyExists(any(String::class)) } returns true

        authViewModel.signUp(testDbUser, prefMgr) {}

        authViewModel.signUpState.test {
            skipItems(1)

            assertThat(awaitItem())
                .isEqualTo(SIGN_UP_USER_EXISTS_STATE)
        }

    }

    @Test
    fun `IF user does not exist, sign up succeeds`() = runTest(testDispatcher) {
        coEvery { userAuthRepository.userAlreadyExists(any(String::class)) } returns false
        coEvery { userAuthRepository.saveUser(any(User::class)) } returns Unit
        coEvery { prefMgr.saveUserToken(any(Int::class)) } returns Unit

        authViewModel.signUp(testDbUser, prefMgr) {}

        authViewModel.signUpState.test {
            skipItems(1)

            assertThat(awaitItem())
                .isEqualTo(SIGN_UP_NO_USER_EXISTS_STATE)
        }

    }

    @Test
    fun `IF sign up fails, THEN reset sign up state succeeds`() = runTest(testDispatcher) {
        coEvery { userAuthRepository.userAlreadyExists(any(String::class)) } returns true

        authViewModel.signUp(testDbUser, prefMgr) {}

        authViewModel.signUpState.test {
            skipItems(1)

            assertThat(awaitItem())
                .isEqualTo(SIGN_UP_USER_EXISTS_STATE)
        }

        authViewModel.resetSignUpUIState()

        assertThat(authViewModel.signUpState.value)
            .isEqualTo(SIGN_UP_DEFAULT_STATE)
    }

}

private val testLocalUser = LocalUser(
    username = "test_user",
    emailId = "testemail@mail.com",
    phoneNumber = "test_phone",
    country = "test_country"
)

private val testDbUser = DBUser(
    username = "test_user",
    emailId = "testemail@mail.com",
    password = "test_password",
    country = "test_country",
    phoneNumber = "test_phone"
)

private val SIGN_IN_DEFAULT_STATE = SignInUIState()
private val SIGN_IN_LOADING_STATE = SIGN_IN_DEFAULT_STATE.copy(loading = true)
private val SIGN_IN_FAILED_STATE = SIGN_IN_DEFAULT_STATE.copy(failed = true)
private val SIGN_IN_SUCCESS_STATE = SIGN_IN_DEFAULT_STATE.copy(user = testLocalUser)

private val SIGN_UP_DEFAULT_STATE = SignUpUIState()
private val SIGN_UP_IN_PROCESS_STATE = SIGN_UP_DEFAULT_STATE.copy(signUpInProcess = true)
private val SIGN_UP_NO_USER_EXISTS_STATE = SIGN_UP_DEFAULT_STATE.copy(userAlreadyExists = false)
private val SIGN_UP_USER_EXISTS_STATE = SIGN_UP_DEFAULT_STATE.copy(userAlreadyExists = true)
