package com.example.exampleconnection

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Owner (
    @SerializedName("avatar_url")
    val mImageAvatar: String? = "",
): Serializable

