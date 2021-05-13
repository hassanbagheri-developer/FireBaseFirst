package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Data.Helper
import com.example.myapplication.Data.Note
import com.example.myapplication.R
import kotlinx.android.synthetic.main.dialog_edit_note.view.*
import kotlinx.android.synthetic.main.item_note_recyclerview.view.*
import java.text.SimpleDateFormat
import java.util.*


class RecyclerAddNoteAdapter(
    private val data: List<Note>,
    val context: Context,
    val helper: Helper
) :
    RecyclerView.Adapter<RecyclerAddNoteAdapter.noteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        noteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_note_recyclerview,
                    parent,
                    false
                )
        )

    override fun onBindViewHolder(holder: noteViewHolder, position: Int) {
        holder.setData(data[position])
    }

    override fun getItemCount() = data.size


    inner class noteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val date = itemView.txt_itemdate
        private val desc = itemView.txt_itemdesc
        private val title = itemView.txt_itemtitle

        private val deletnote = itemView.img_delete
        private val editnote = itemView.img_edit


        fun setData(data: Note) {

            date.text = data.date
            desc.text = data.desc
            title.text = data.titel

            deletnote.setOnClickListener {

                showDialogDelete(data)

            }

            editnote.setOnClickListener {

                showDialogEdit(data)
            }

        }

    }

    private fun showDialogDelete(data: Note) {
        val alertdialog = AlertDialog.Builder(
            ContextThemeWrapper(
                context,
                R.style.AlertDialogCustom
            )
        )

        alertdialog.setTitle("حذف یادداشت" + data.titel)
        alertdialog.setMessage("آیا مایل به حذف این یادداشت می باشید ؟")
        alertdialog.setCancelable(true)
        alertdialog.setPositiveButton("آره") {
                dialog, which -> helper.DeleteNote(data) }
        alertdialog.setNegativeButton(
            "نه!"
        ) { dialog, which -> dialog.cancel() }

        val alert = alertdialog.create()
        alert.show()
    }

    @SuppressLint("SimpleDateFormat", "WeekBasedYear")
    private fun showDialogEdit(note: Note) {

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_note, null)

        val dialog = AlertDialog.Builder(context, R.color.colorTransparent)
            .setView(view)

        dialog.show()

        val edittitle = view.edt_dialog_noteTitle
        val editdesc = view.edt_dialog_noteDesc
        val txtshowdate = view.txt_dialog_noteDate
        val editconfirm = view.btn_editconfirm


        edittitle.setText(note.titel)
        editdesc.setText(note.desc)
        txtshowdate.setText(note.date)

        editconfirm.setOnClickListener {

            val simpleDateFormat = SimpleDateFormat("dd-MM-YYYY  HH:MM")
            val calndar = Calendar.getInstance()

            val noteTitle = edittitle.text.toString()
            val noteDes = editdesc.text.toString()
            val noteId = note.id
            val notedate = simpleDateFormat.format(calndar.time)

            val note = Note(notedate, noteTitle, noteDes, noteId)
            helper.EditNote(note)


        }

    }

}