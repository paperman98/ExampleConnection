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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_detail_repository.*
import kotlinx.android.synthetic.main.item_loadmore.*
import okhttp3.*
import java.io.IOException
import java.io.Serializable
import java.net.URL





class MainActivity : AppCompatActivity(), ClickListener {
    lateinit var listRepo: ArrayList<Repo>
    private lateinit var adapterRepo: AdapterRepo
    private lateinit var viewModel:RepoViewModel
    private lateinit var mEditText: androidx.appcompat.widget.SearchView
    private lateinit var mListView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mEditText = findViewById(R.id.edtSearch)
        mListView = findViewById(R.id.lvRepository)
        listRepo= ArrayList<Repo>()
        viewModel = ViewModelProvider(this).get(RepoViewModel::class.java)
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
                        viewModel.getRepository(query,1)
                    }
                    mEditText.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        viewModel.listRepo.observe(this){
            setUpListView(it,viewModel.showLoadMore)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpListView(list: ArrayList<Repo>, showLoadMore: Boolean) {
        adapterRepo.listRepo = list
        adapterRepo.showLoadMore = showLoadMore
        adapterRepo.notifyDataSetChanged();
    }



    override fun onButtonClick() {
        viewModel.getRepository(mEditText.query.toString(),viewModel.numberPage)

    }

    override fun onItemClick(repo: Repo) {
        val intent = Intent( this, DetailRepository::class.java)
        intent.putExtra("Repository",repo)
        startActivity(intent)
    }


}
