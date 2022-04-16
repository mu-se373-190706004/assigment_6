package com.agah.assigment_6.networking

import com.google.android.gms.auth.api.Auth
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Services {

    @FormUrlEncoded
    @POST("/login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Call<Auth>

    @FormUrlEncoded
    @POST("/register.php")
    fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("gender") gender: String
    ) : Call<Auth>

}