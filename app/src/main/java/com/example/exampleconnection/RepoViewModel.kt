package com.example.exampleconnection

import android.annotation.SuppressLint
import android.provider.Settings
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.util.ArrayList

class RepoViewModel : ViewModel() {
    private var client = OkHttpClient()
    private lateinit var adapterRepo: AdapterRepo
    private val url = "https://api.github.com/search/repositories?q="
    private val perPage = 30
    var numberPage:Int = 1
    var showLoadMore: Boolean = false
    var listRepository = ArrayList<Repo>()



    var listRepo = MutableLiveData<ArrayList<Repo>>()

    fun getRepository(query: String, page:Int ) {
        val request = Request.Builder()
            .url(URL("$url$query&page=$page&per_page=$perPage"))
            .build()

        client.newCall(request).enqueue(object : Callback {
            @SuppressLint("NotifyDataSetChanged")
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw Exception("loi0")
                    val gson = Gson()
                    val data = gson.fromJson(response.body?.string(), RepoResult::class.java)
                    for (repo in data.items) {
                        listRepository.add(repo)
                    }

                    listRepo.postValue(listRepository)
                    ++numberPage

//                    adapterRepo.showLoading = false

                    showLoadMore = data.items.size == perPage

                }
            }

        }
        )
    }
}