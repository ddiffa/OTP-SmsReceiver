package com.hellodiffa.myapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hellodiffa.myapplication.R
import com.hellodiffa.myapplication.common.ResultState
import com.hellodiffa.myapplication.data.model.RequestOtp
import com.hellodiffa.myapplication.data.model.ResponseOtp
import com.hellodiffa.myapplication.data.source.OtpRepositoryImpl
import com.hellodiffa.myapplication.utils.Dispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException

class MainViewModel(
    private val repository: OtpRepositoryImpl,
    private val dispatcher: Dispatcher
) : ViewModel() {

    private val _otp = MutableLiveData<ResultState<ResponseOtp>>()
    val otp: LiveData<ResultState<ResponseOtp>> get() = _otp

    private fun setResultOtp(resultState: ResultState<ResponseOtp>) {
        _otp.postValue(resultState)
    }

    internal fun sendOtp(request: RequestOtp) {
        viewModelScope.launch(dispatcher.mainExecutor()) {
            try {
                setResultOtp(ResultState.Loading())
                val result = async(context = dispatcher.backgroundExecutor()) {
                    repository.sendOtp(request)
                }
                showHasData(result)

            } catch (e: Throwable) {
                when (e) {
                    is IOException -> {
                        setResultOtp(ResultState.NoInternetConnection(R.string.no_internet_connection))
                    }
                    is TimeoutException -> {
                        setResultOtp(ResultState.TimeOut(R.string.timeout))
                    }
                    else -> {
                        setResultOtp(ResultState.Error(R.string.unknown_error))
                    }
                }
            }
        }
    }

    private suspend fun showHasData(result: Deferred<ResponseOtp>) {
        setResultOtp(ResultState.HasData(result.await())).takeUnless {
            false
        }
    }


}