package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Note
import com.example.myapplication.R
import kotlinx.android.synthetic.main.item_note_recyclerview.view.*


class RecyclerAddNoteAdapter (private val data : List<Note>):
    RecyclerView.Adapter<RecyclerAddNoteAdapter.ShopViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ShopViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note_recyclerview,
                    parent,
                    false)
        )

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.setData(data[position])
    }

    override fun getItemCount() = data.size


    inner class ShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val date = itemView.txt_itemdate
        private val desc = itemView.txt_itemdesc
        private val title = itemView.txt_itemtitle

        fun setData(data : Note){

            date.text = data.date
            desc.text = data.desc
            title.text = data.titel

        }
    }
}