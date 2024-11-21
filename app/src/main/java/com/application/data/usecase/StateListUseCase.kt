package com.application.data.usecase

import com.application.data.model.StateList
import com.application.data.repository.StateListRepository
import com.google.gson.JsonObject

class StateListUseCase (private val stateListRepository: StateListRepository) {
    suspend operator fun invoke(): List<StateList>? {
        return stateListRepository.getStateList()
    }
}