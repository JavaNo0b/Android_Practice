package com.janob.apipractice

import Dust
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janob.apipractice.NetWorkClient.dustNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DustViewModel: ViewModel(){

    private val _dustData = MutableLiveData<Dust>()
    val dustData: LiveData<Dust>
        get() = _dustData

    fun communicateNetWork(param: HashMap<String, String>) = viewModelScope.launch(Dispatchers.IO){

        val responseData = dustNetWork.getDust(param)

        withContext(Dispatchers.Main) {
            _dustData.value = responseData
        }

    }

}