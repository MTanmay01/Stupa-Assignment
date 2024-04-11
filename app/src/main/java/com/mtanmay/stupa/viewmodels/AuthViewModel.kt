package com.mtanmay.stupa.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtanmay.data.models.DBUser
import com.mtanmay.domain.models.LocalUser
import com.mtanmay.domain.models.interfaces.User
import com.mtanmay.domain.repository.IUserAuthRepository
import com.mtanmay.stupa.ui.states.SignInUIState
import com.mtanmay.stupa.ui.states.SignUpUIState
import com.mtanmay.stupa.utils.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userAuthRepository: IUserAuthRepository
) : ViewModel() {

    private val _signInState = MutableStateFlow(SignInUIState())
    val signInState: StateFlow<SignInUIState>
        get() = _signInState.asStateFlow()

    private val _signUpState = MutableStateFlow(SignUpUIState())
    val signUpState: StateFlow<SignUpUIState>
        get() = _signUpState.asStateFlow()

    fun signUp(user: User, mgr: PreferencesManager, onCompletion: (Boolean) -> Unit) {
        var exists = false

        viewModelScope.launch {
            _signUpState.update { SignUpUIState().copy(signUpInProcess = true) }
            delay(1000)

            exists = userAuthRepository.userAlreadyExists(user.emailId)

            _signUpState.update { SignUpUIState().copy(userAlreadyExists = exists) }

            if (!exists) {
                userAuthRepository.saveUser(user)
                mgr.saveUserToken(user.token)
            }
        }
            .invokeOnCompletion {
                onCompletion(exists)
            }
    }

    fun signIn(emailId: String, password: String) {
        viewModelScope.launch {
            _signInState.update { SignInUIState(loading = true) }
            delay(500)
            val user = userAuthRepository.signIn(emailId, password) as? LocalUser
            if (user == null)
                _signInState.update { SignInUIState().copy(failed = true) }
            else {
                _signInState.update { SignInUIState().copy(user = user) }
            }
        }
    }

    fun resetSignInUIState() = _signInState.update { SignInUIState() }
    fun resetSignUpUIState() = _signUpState.update { SignUpUIState() }


}