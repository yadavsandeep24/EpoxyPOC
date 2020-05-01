package com.zeptolearn.epoxypoc.api

import androidx.lifecycle.LiveData
import com.zeptolearn.epoxypoc.db.entity.Containers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * REST API access points
 */
interface EpoxyPocService {

    @GET("homepage.json")
    fun  downloadFileJsonFileTypeOne(): LiveData<ApiResponse<Containers>>


    @GET("homepage1.json")
    fun  downloadJsonFileTypeTwo(): LiveData<ApiResponse<Containers>>
}
