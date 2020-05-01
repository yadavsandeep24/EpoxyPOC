package com.zeptolearn.epoxypoc.respository

import android.util.Log
import androidx.lifecycle.LiveData
import com.zeptolearn.epoxypoc.AppExecutors
import com.zeptolearn.epoxypoc.api.ApiResponse
import com.zeptolearn.epoxypoc.api.EpoxyPocService
import com.zeptolearn.epoxypoc.db.dao.ContainersDao
import com.zeptolearn.epoxypoc.db.entity.Containers
import com.zeptolearn.epoxypoc.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Repo instances.
 */
@Singleton
class DashboardRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val containersDao: ContainersDao,
    private val service: EpoxyPocService
) {
    fun getScreenData(position: Int?): LiveData<Resource<Containers>> {
        var containers: Containers? = null
        return object : NetworkBoundResource<Containers, Containers>(appExecutors) {
            override fun saveCallResult(item: Containers) {
                Log.d("SAN","item.header -->"+item.header)
                containers = item
            }

            override fun shouldFetch(data: Containers?) = true

            override fun loadFromDb(): LiveData<Containers>{
                return object : LiveData<Containers>() {
                    override fun onActive() {
                        super.onActive()
                        value = containers
                    }

                }
            }

            override fun createCall() :LiveData<ApiResponse<Containers>>{
                return if (position == 0) {
                    service.downloadFileJsonFileTypeOne()
                } else {
                    service.downloadJsonFileTypeTwo()
                }
            }
        }.asLiveData()
    }
}
