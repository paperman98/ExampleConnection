package com.example.exampleconnection

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.io.IOException
import java.io.InputStream
import java.io.Serializable
import java.net.HttpURLConnection
import java.net.URL


class DetailRepository : AppCompatActivity() {

    private lateinit var tvAuthor:TextView
    private lateinit var imgAvatar:ImageView
    private lateinit var tvLanguage:TextView
    private lateinit var tvWatcher:TextView
    private lateinit var tvDate:TextView
    private lateinit var btnGoVisit:Button
    private lateinit var tvDes:TextView
    private var repo: Repo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_repository)
        initLayout()
        getBundle()
    }

    private fun initLayout() {
        tvAuthor = findViewById(R.id.tvAuthor)
        imgAvatar = findViewById(R.id.imgSource)
        tvLanguage = findViewById(R.id.tvLanguage)
        tvWatcher = findViewById(R.id.tvWatch)
        tvDate = findViewById(R.id.tvCreate)
        tvDes = findViewById(R.id.tvDes)
        btnGoVisit = findViewById(R.id.btnGo)
    }


    private fun getBundle(){
        val intent = getIntent()
        val repository = intent.getSerializableExtra("Repository")
        if(repository != null){
            repo = repository as Repo
            initVariable()
        }
    }
    private fun initVariable() {
        tvAuthor.text = repo?.mName
        tvLanguage.text = repo?.mLanguage
        tvDate.text = repo?.mCreateDate
        tvWatcher.text = repo?.mWatcher
        tvDes.text = repo?.mDescription
        btnGoVisit.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(repo?.mGitUrl)
            startActivity(intent)
        }

        Glide.with(this).load(repo?.mOwner?.mImageAvatar).into(imgAvatar)
    }


}
