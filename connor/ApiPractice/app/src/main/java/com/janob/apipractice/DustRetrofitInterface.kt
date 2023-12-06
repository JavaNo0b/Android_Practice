package com.janob.apipractice

import Dust
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface DustRetrofitInterface {

    @GET("getCtprvnRltmMesureDnsty")
    suspend fun getDust(@QueryMap param: HashMap<String, String>): Dust

}