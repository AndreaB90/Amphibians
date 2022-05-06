/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.amphibians.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibians.network.Amphibian
import com.example.amphibians.network.AmphibiansApi
import kotlinx.coroutines.launch

enum class AmphibianApiStatus { LOADING, ERROR, DONE }

class AmphibianViewModel : ViewModel() {

    private val _status = MutableLiveData<AmphibianApiStatus>()
    val status: LiveData<AmphibianApiStatus> = _status

    private val _listAmphibian = MutableLiveData<List<Amphibian>>()
    val listAmphibian: LiveData<List<Amphibian>> = _listAmphibian

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _type = MutableLiveData<String>()
    val type: LiveData<String> = _type

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    init {
        getAmphibianList()
    }

    fun getAmphibianList() {
        viewModelScope.launch {
            _status.value = AmphibianApiStatus.LOADING
            try {
                _listAmphibian.value = AmphibiansApi.retrofitService.getAmphibians()
                _status.value = AmphibianApiStatus.DONE
            } catch (e: Exception) {
                _listAmphibian.value = listOf()
                _status.value = AmphibianApiStatus.ERROR
            }
        }
    }

    fun onAmphibianClicked(amphibian: Amphibian) {
        _name.value = amphibian.name
        _type.value = amphibian.type
        _description.value = amphibian.description
    }
}
