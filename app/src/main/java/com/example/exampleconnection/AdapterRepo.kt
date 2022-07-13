package com.example.exampleconnection

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface ClickListener {
    fun onItemClick(repo: Repo)
    fun onButtonClick()
}

class AdapterRepo(): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var mClickListen: ClickListener? = null
    var showLoadMore: Boolean = false
    var showLoading: Boolean = false

    var listRepo:ArrayList<Repo> = ArrayList<Repo>()

    inner class ViewHolderRepoItem(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvRepo: TextView = itemView.findViewById(R.id.tvRepo)
        val tvStar: TextView = itemView.findViewById(R.id.starRepo)
        val tvNumberRepo : TextView = itemView.findViewById(R.id.numberRepo)
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == listRepo.size) 1 else 0
    }

    inner class ViewHolderLoadMore(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val btnLoad: Button = itemView.findViewById(R.id.btnLoadMore)
        val pgLoading: ProgressBar = itemView.findViewById(R.id.pgLoading)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewRepo = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo, parent, false)
        val viewLoad =
            LayoutInflater.from(parent.context).inflate(R.layout.item_loadmore, parent, false)

        when (viewType) {
            0 -> return ViewHolderRepoItem(viewRepo)
            1 -> return ViewHolderLoadMore(viewLoad)
        }
        return ViewHolderRepoItem(viewRepo)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position == listRepo.size) {
            val holderLoadMore = holder as ViewHolderLoadMore

            if(showLoading){
                holder.pgLoading.visibility = View.VISIBLE
                holder.btnLoad.visibility = View.INVISIBLE

            }else{
                holder.pgLoading.visibility = View.INVISIBLE
                holder.btnLoad.visibility = View.VISIBLE

            }

            holderLoadMore.btnLoad.setOnClickListener {
                holder.btnLoad.visibility = View.INVISIBLE
                holder.pgLoading.visibility = View.VISIBLE
                mClickListen?.onButtonClick()
            }
        } else {
            val repo: Repo = listRepo[position]
            val holderRepoItem = holder as ViewHolderRepoItem
            holder.itemView.setOnClickListener {
                mClickListen?.onItemClick(repo)
            }
            holderRepoItem.tvNumberRepo.text = (position + 1).toString()
            holderRepoItem.tvRepo.text = repo.mName
            holderRepoItem.tvStar.text = repo.mStar + " ✬"
        }
    }

    override fun getItemCount(): Int {
        if (showLoadMore) {
            return listRepo.size+1
        } else {
            return listRepo.size
        }
    }
}