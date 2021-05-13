package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activiy.Storage.Upload
import com.example.myapplication.R


class RecyclerImageUploadAdapter(private val data: List<Upload>) :
    RecyclerView.Adapter<RecyclerImageUploadAdapter.imgViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        imgViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_image_upload_recycler,
                    parent,
                    false
                )
        )

    override fun onBindViewHolder(holder: imgViewHolder, position: Int) {
        holder.setData(data[position])

        if (data.size == 0) {

//            Toast.makeText(this@RecyclerImageUploadAdapter, "مقدرای برای نمایش وجود ندارد.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = data.size


    inner class imgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ratingBar = itemView.ratingBar_shop_recycler

        fun setData(data: Upload) {

            txtName.text = data.title
            
        }
    }

}