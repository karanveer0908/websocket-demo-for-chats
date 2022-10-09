package com.craveretailtestproject.model

import com.google.gson.annotations.SerializedName




data class PusherDataModel(
    @SerializedName("title")
    var title: String? = null,

    @SerializedName("message")
    var message: String? = null


)

