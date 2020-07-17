package com.prasetyanurangga.footballleague.data.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prasetyanurangga.footballleague.data.dao.LocalDao
import com.prasetyanurangga.footballleague.data.local.RoomBuilder
import com.prasetyanurangga.footballleague.data.repository.LocalRepository
import com.prasetyanurangga.footballleague.ui.viewmodel.FootballViewModel
import com.prasetyanurangga.footballleague.ui.viewmodel.LocalViewModel

class LocalViewModelFactory (private val localDao: LocalDao): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocalViewModel::class.java)){
            return LocalViewModel(LocalRepository(localDao)) as T
        }

        throw IllegalArgumentException("UNKOnw")
    }
}