package tech.roonyx.android_starter.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.roonyx.android_starter.common.Result
import tech.roonyx.android_starter.common.runCatchingResult
import tech.roonyx.android_starter.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {

    /**
     * Example of using Result<T>
     */
    private val _messageLiveData = MutableLiveData<Result<String>>()
    val messageLiveData: LiveData<Result<String>> get() = _messageLiveData

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            _messageLiveData.postValue(Result.Loading)
            _messageLiveData.postValue(runCatchingResult {
                repository.getMessage()
            })
        }
    }
}