package com.application.data.usecase

import com.application.data.repository.StateListRepository
import com.google.gson.JsonArray

class StateDetailsUseCase (private val stateListRepository: StateListRepository) {
    suspend operator fun invoke(
        stateName: String
    ): JsonArray {
        return stateListRepository.getStateDetailsByName(stateName)
    }
}