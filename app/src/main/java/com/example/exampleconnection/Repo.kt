package com.example.exampleconnection

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Repo(
    @SerializedName("full_name")
    val mName: String? = "",

    @SerializedName("owner")
    val mOwner: Owner,


    @SerializedName("stargazers_count")
    val mStar: String? = "",

    @SerializedName("language")
    val mLanguage: String? = "",

    @SerializedName("watchers")
    val mWatcher: String? = "",

    @SerializedName("created_at")
    val mCreateDate: String? = "",

    @SerializedName("html_url")
    val mGitUrl: String? = "",

    @SerializedName("description")
    val mDescription: String? = ""
):Serializable