package com.prasetyanurangga.footballleague.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prasetyanurangga.footballleague.data.model.LeagueModel
import com.prasetyanurangga.footballleague.data.repository.ApiRepository
import com.prasetyanurangga.footballleague.ui.viewmodel.FootballViewModel

class FootballViewModelFactory(private val apiRepository: ApiRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FootballViewModel::class.java)){
            return FootballViewModel(apiRepository) as T
        }

        throw IllegalArgumentException("UNKOnw")
    }
}