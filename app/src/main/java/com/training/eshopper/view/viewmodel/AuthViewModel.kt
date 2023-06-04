package com.training.eshopper.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.eshopper.common.Result
import com.training.eshopper.data.repository.AuthRepository
import com.training.eshopper.utils.AppPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean>
        get() = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            handleLoginResult(result)
        }
    }

    private fun handleLoginResult(result: Result<String>) {
        if (result is Result.Success) {
            _loginState.value = true
            AppPreferences.JWT = result.data
        } else {
            _loginState.value = false
        }
    }
}