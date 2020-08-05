package com.prasetyanurangga.footballleague.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.prasetyanurangga.footballleague.data.model.EventModel
import com.prasetyanurangga.footballleague.data.model.TeamModel
import com.prasetyanurangga.footballleague.data.repository.LocalRepository
import com.prasetyanurangga.footballleague.utils.Resource
import kotlinx.coroutines.Dispatchers

class LocalViewModel(private val localRepository: LocalRepository): ViewModel() {

    fun getEvents() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                data = localRepository.getEvents()
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


    fun getTeams() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = localRepository.getTeams()
                ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }
    }

    fun getTeamByUid(id: String) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(
                data = localRepository.getTeamByUid(id)
            ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }
    }

    fun getCountTeam(id: String) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(
                data = localRepository.getTeamByUid(id).size
            ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }
    }

    fun saveTeam(teamModel: TeamModel) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            localRepository.saveTeam(teamModel)
            emit(Resource.success(
                data = null
            ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }
    }

    fun deleteTeam(teamModel: TeamModel) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            localRepository.deleteTeam(teamModel)
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