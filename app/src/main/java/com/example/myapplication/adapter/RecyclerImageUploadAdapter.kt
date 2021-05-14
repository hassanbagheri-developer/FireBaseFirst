package com.example.myapplication.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Activiy.Storage.Upload
import com.example.myapplication.R
import kotlinx.android.synthetic.main.dialog_big_image.*
import kotlinx.android.synthetic.main.dialog_big_image.view.*
import kotlinx.android.synthetic.main.item_image_upload_recycler.view.*
import org.jetbrains.anko.toast


class RecyclerImageUploadAdapter(private val data: List<Upload>,val context :Context) :
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


        if (data.size == 0) {

            context.toast("مقدرای برای نمایش وجود ندارد.")

        }else{
            holder.setData(data[position])
        }
    }

    override fun getItemCount() = data.size


    inner class imgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//        private val ratingBar = itemView.ratingBar_shop_recycler
        private val title = itemView.item_txt_showTitle


        fun setData(data: Upload) {

//            txtName.text = data.title
            title.text= data.title
            itemView.item_txt_showDate.text="1399/12/19"

            // holder.TxtShowDateitem.setText(upload.getDate());

            Glide.with(context)
                .load(data.link)
                .placeholder(R.drawable.ic_cloud_upload_black_24dp)
                .into(itemView.item_img_image)

            itemView.item_img_fullimage.setOnClickListener {


                val dialog = Dialog(context)
//                val img = dialog.img_big_image

                dialog.setCancelable(true)
                dialog.setContentView(R.layout.dialog_big_image)
                Glide.with(context)
                    .load(data.link)
                    .placeholder(R.drawable.ic_cloud_upload_black_24dp)
                    .into(dialog.img_big_image)

                dialog.img_big_image.setOnClickListener {  }
                dialog.img_close.setOnClickListener { dialog.dismiss() }


                dialog.show()

            }
            
        }
    }

}