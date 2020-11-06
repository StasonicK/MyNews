package com.eburg_soft.mynews.presentation.screens.policies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eburg_soft.mynews.data.repository.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PoliciesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val countryCodeMutableLiveData: MutableLiveData<String> = MutableLiveData()

    fun getCountryCode(): LiveData<String> = countryCodeMutableLiveData

    fun getIpDetailsFromApi(ip: String) {
        viewModelScope.launch {
            countryCodeMutableLiveData.value = repository.getIpDetailsFromApi(ip).countryCode
        }
    }
}