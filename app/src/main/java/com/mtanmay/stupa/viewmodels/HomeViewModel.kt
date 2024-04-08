package com.mtanmay.stupa.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtanmay.domain.repository.IUsersInfoRepository
import com.mtanmay.stupa.ui.states.ProfileUIState
import com.mtanmay.stupa.ui.states.RegisteredUsersUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: IUsersInfoRepository
): ViewModel() {

    private val _userFlow = MutableStateFlow(ProfileUIState())
    val userFlow: StateFlow<ProfileUIState>
        get() = _userFlow.asStateFlow()

    private val _registeredUsersFlow = MutableStateFlow(RegisteredUsersUIState())
    val registeredUsersFlow: StateFlow<RegisteredUsersUIState>
        get() = _registeredUsersFlow.asStateFlow()

    init {
        getRegisteredUsers()
    }
    fun getUser(token: Int) {
        viewModelScope.launch {
            _userFlow.update {
                val user = repository.getUser(token)
                ProfileUIState().copy(
                    loading = false,
                    user = user
                )
            }
        }
    }

    private fun getRegisteredUsers() {
        viewModelScope.launch {
            _registeredUsersFlow.update {
                val users = repository.getRegisteredUsers()
                RegisteredUsersUIState().copy(
                    loading = false,
                    users = users
                )
            }
        }
    }
}