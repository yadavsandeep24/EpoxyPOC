package com.zeptolearn.epoxypoc.api

import androidx.lifecycle.LiveData
import com.zeptolearn.epoxypoc.db.entity.Containers
import retrofit2.http.GET

/**
 * REST API access points
 */
interface EpoxyPocService {

    @GET("homepage.json")
    fun  downloadFileJsonFileTypeOne(): LiveData<ApiResponse<Containers>>


    @GET("homepage1.json")
    fun  downloadJsonFileTypeTwo(): LiveData<ApiResponse<Containers>>
}
