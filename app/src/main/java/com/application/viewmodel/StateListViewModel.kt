package com.application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.data.repositoryimpl.StateListRepositoryImpl
import com.application.data.usecase.StateListUseCase
import com.application.utils.states.GenericDataState
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.application.data.model.StateList
import com.application.data.usecase.StateDetailsUseCase
import com.google.gson.JsonArray
import com.google.gson.JsonObject


class StateListViewModel : ViewModel(){

    private val _stateList = MutableLiveData<GenericDataState<List<StateList>>>()
    val stateList get() = _stateList as LiveData<GenericDataState<List<StateList>>>

    private val _stateDetail = MutableLiveData<GenericDataState<JsonArray>>()
    val stateDetail get() = _stateDetail as LiveData<GenericDataState<JsonArray>>

    private val _selectedStateDetail = MutableLiveData<JsonArray>()
    val selectedStateDetail get() = _selectedStateDetail as LiveData<JsonArray>

    fun getStateList() {
        viewModelScope.launch {
            try {
                _stateList.value = GenericDataState.Loading
                val useCase= StateListUseCase(StateListRepositoryImpl())
                val response = useCase()
                if(response != null && response.isNotEmpty() ){
                    _stateList.value = GenericDataState.Result(response)
                }else{
                    _stateList.value = GenericDataState.Error("No Data Found")
                }

            }catch(e : Exception){
                _stateList.value = GenericDataState.Error(e.message?:"Oops! Somethings went wrong")
                e.printStackTrace()
            }

        }
    }

    fun getStateDetailsByName(stateName : String){
        viewModelScope.launch {
            try {
                _stateDetail.value = GenericDataState.Loading
                val useCase= StateDetailsUseCase(StateListRepositoryImpl())
                val response = useCase(stateName)
                if(response != null && response.size() != 0){
                    _stateDetail.value = GenericDataState.Result(response)
                }else{
                    _stateDetail.value = GenericDataState.Error("No Data Found")
                }

            }catch(e : Exception){
                _stateDetail.value = GenericDataState.Error(e.message?:"Oops! Somethings went wrong")
                e.printStackTrace()
            }

        }
    }

    fun displaySelectedStateDetails(stateDetails : JsonArray) {
        _selectedStateDetail.value = stateDetails
    }

    fun getSelectedStateValue(): JsonArray? {
        return _selectedStateDetail.value
    }
}