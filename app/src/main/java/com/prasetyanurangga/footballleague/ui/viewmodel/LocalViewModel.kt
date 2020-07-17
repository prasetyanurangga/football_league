package com.prasetyanurangga.footballleague.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.repository.LocalRepository
import com.prasetyanurangga.footballleague.utils.Resource
import kotlinx.coroutines.Dispatchers

class LocalViewModel(private val localRepository: LocalRepository): ViewModel() {

    fun getEvents(id: String) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                data = localRepository.getEvents(id)
            ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }
    }

    fun getEventByUid(id: String) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(
                data = localRepository.getEventByUid(id)
            ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }
    }

    fun getCountEvent(id: String) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(
                data = localRepository.getEventByUid(id).size
            ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }
    }

    fun saveEvent(eventModel: EventModel) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            localRepository.saveEvent(eventModel)
            emit(Resource.success(
                data = null
            ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }
    }

    fun deleteEvent(eventModel: EventModel) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            localRepository.deleteEvent(eventModel)
            emit(Resource.success(
                data = null
            ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }
    }
}