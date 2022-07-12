package com.example.exampleconnection

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_detail_repository.*
import kotlinx.android.synthetic.main.item_loadmore.*
import okhttp3.*
import java.io.IOException
import java.net.URL


class Owner(
    @SerializedName("avatar_url")
    val mImageAvatar: String? = "",
)

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

)

data class RepoResult(
    val items: ArrayList<Repo>
)


class MainActivity : AppCompatActivity(), ClickListener {
    private val logTag = "MainActivity"
    private val url = "https://api.github.com/search/repositories?q="
    private var client = OkHttpClient()
    lateinit var listRepo: ArrayList<Repo>
    private lateinit var adapterRepo: AdapterRepo
    var numberPage:Int = 1
    val perPage = 30
    private lateinit var mEditText: androidx.appcompat.widget.SearchView
    private lateinit var mListView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mEditText = findViewById(R.id.edtSearch)
        mListView = findViewById(R.id.lvRepository)
        listRepo = ArrayList<Repo>()
        adapterRepo = AdapterRepo()
        mListView.adapter = adapterRepo
        mListView.layoutManager = LinearLayoutManager(this)
        adapterRepo.mClickListen = this
    }


    override fun onResume() {
        super.onResume()

        mEditText.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (query.isNotEmpty()) {
                        listRepo.clear()
                        getRepository(query,1)
                    }
                    mEditText.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpListView(list: ArrayList<Repo>, showLoadMore: Boolean) {
        adapterRepo.listRepo = list
        adapterRepo.showLoadMore = showLoadMore
        adapterRepo.notifyDataSetChanged();
    }

    private fun getRepository(query: String, page:Int ) {
        val request = Request.Builder()
            .url(URL("$url$query&page=$page&per_page=$perPage"))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw Exception("loi0")
                    val gson = Gson()
                    val data = gson.fromJson(response.body?.string(), RepoResult::class.java)
                    for (repo in data.items) {
                        listRepo.add(repo)
                    }
                    ++numberPage
                    adapterRepo.showLoading = false
                    runOnUiThread {
                        if (data.items.size == perPage){
                            setUpListView(listRepo,true)
                        } else {
                            setUpListView(listRepo, false)
                        }
                    }
                }
            }

        }
        )
    }

    override fun onButtonClick() {
        adapterRepo.showLoading = true
        getRepository(mEditText.query.toString(),numberPage)

    }

    override fun onItemClick(repo: Repo) {
        val intent = Intent( this,DetailRepository::class.java)
        val bundle = Bundle()
        bundle.putString("tvName",repo.mName)
        bundle.putString("tvLanguage",repo.mLanguage)
        bundle.putString("tvWatch",repo.mWatcher)
        bundle.putString("imgAvatar",repo.mOwner.mImageAvatar)
        bundle.putString("tvDate",repo.mCreateDate)
        bundle.putString("tvUrl",repo.mGitUrl)
        bundle.putString("tvDescription",repo.mDescription)
        intent.putExtras(bundle)
        startActivity(intent)
    }


}
