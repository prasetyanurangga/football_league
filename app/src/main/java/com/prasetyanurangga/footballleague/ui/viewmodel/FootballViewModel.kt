package com.prasetyanurangga.footballleague.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.prasetyanurangga.footballleague.data.repository.ApiRepository
import com.prasetyanurangga.footballleague.utils.Resource
import kotlinx.coroutines.Dispatchers

class FootballViewModel(private val apiRepository: ApiRepository): ViewModel() {

    fun getLeague(id: String) = liveData(Dispatchers.IO){


        try {
            emit(Resource.success(
                data = apiRepository.getLeagues(id)
            ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }

        emit(Resource.loading(data = null))

    }

    fun getSearchEvent(e: String) = liveData(Dispatchers.IO){

        try {
            emit(Resource.success(
                data = apiRepository.getSearchEvents(e).filter { it.SportName == "Soccer" }
            ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }

        emit(Resource.loading(data = null))
    }

    fun getEventDetails(id: String) = liveData(Dispatchers.IO){

        try {
            emit(Resource.success(
                data = apiRepository.getEventDetails(id)
            ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }

        emit(Resource.loading(data = null))
    }

    fun getEvent(id: String) = liveData(Dispatchers.IO){

        try {
            emit(Resource.success(
                data = apiRepository.getEvents(id)
            ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }

        emit(Resource.loading(data = null))
    }

    fun getEventNext(id: String) = liveData(Dispatchers.IO){

        try {
            emit(Resource.success(
                data = apiRepository.getEventNexts(id)
            ))
        }
        catch (exception: Exception)
        {
            Log.e("e", exception.message.toString())
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }

        emit(Resource.loading(data = null))
    }

    fun getTeam(id: String) = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(
                data = apiRepository.getTeams(id)
            ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }
        emit(Resource.loading(data = null))

    }

    fun getDetailTeam(id: String) = liveData(Dispatchers.IO){
        try {
            emit(Resource.success(
                data = apiRepository.getDetailTeams(id)
            ))
        }
        catch (exception: Exception)
        {
            emit(Resource.error(data = null, message = exception.message ?: "Error Unknown"))
        }

        emit(Resource.loading(data = null))

    }
}