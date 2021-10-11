package tech.roonyx.android_starter.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tech.roonyx.android_starter.repository.Repository

class MainViewModel(repository: Repository) : ViewModel() {

    private val _messageLiveData = MutableLiveData(repository.getMessage())
    val messageLiveData: LiveData<String> get() = _messageLiveData
}