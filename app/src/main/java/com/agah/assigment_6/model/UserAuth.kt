package com.agah.assigment_6.model

import com.google.gson.annotations.SerializedName

data class UserAuth(
    @SerializedName("data")
    val user: List<User>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String = "")


