package com.zeptolearn.epoxypoc.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.zeptolearn.epoxypoc.db.entity.Containers
import com.zeptolearn.epoxypoc.respository.DashboardRepository
import com.zeptolearn.epoxypoc.util.Resource
import javax.inject.Inject

class PageViewModel @Inject constructor(repository: DashboardRepository): ViewModel() {
    private val _containerPosition: MutableLiveData<Int> = MutableLiveData()

    val containers: LiveData<Resource<Containers>> = _containerPosition.switchMap { position ->
            repository.getScreenData(position)
    }

    fun setContainerPosition(position: Int) {
        _containerPosition.value = position
    }
}