package com.example.exampleconnection

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import com.bumptech.glide.Glide
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class DetailRepository : AppCompatActivity() {
    private var mAuthor:String = ""
    private var mLanguage:String=""
    private var mWatcher:String=""
    private var mDate:String=""
    private var mUrl:String=""
    private var mAvatar:String=""
    private var mDes:String=""

    private lateinit var tvAuthor:TextView
    private lateinit var imgAvatar:ImageView
    private lateinit var tvLanguage:TextView
    private lateinit var tvWatcher:TextView
    private lateinit var tvDate:TextView
    private lateinit var btnGoVisit:Button
    private lateinit var tvDes:TextView
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
        val bundle = intent.extras
        if (bundle!=null){
            mAuthor = bundle.getString("tvName", "Tentacgia")
            mLanguage = bundle.getString("tvLanguage", "java")
            mWatcher = bundle.getString("tvWatch", "3")
            mDate = bundle.getString("tvDate", "20.05.2020")
            mUrl = bundle.getString("tvUrl", "https")
            mAvatar = bundle.getString("imgAvatar", "https")
            mDes = bundle.getString("tvDescription", "hello world")
        }
        initVariable()


    }

    private fun initVariable() {
        tvAuthor.text = mAuthor
        tvLanguage.text = mLanguage
        tvDate.text = mDate
        tvWatcher.text = mWatcher
        tvDes.text = mDes
        Log.e("XXX", "initVariable: "+mAvatar )
        btnGoVisit.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(mUrl)
            startActivity(intent)
        }

        Glide.with(this).load(mAvatar).into(imgAvatar)
    }


    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            val myBitmap = BitmapFactory.decodeStream(input)
            myBitmap
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }




}
