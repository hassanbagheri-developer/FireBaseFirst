package com.example.myapplication.Activiy

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Activiy.Storage.MainUploadActivity
import com.example.myapplication.Activiy.Storage.UploadImageActivity
import com.example.myapplication.Data.Helper
import com.example.myapplication.Data.Note
import com.example.myapplication.R
import com.example.myapplication.adapter.RecyclerAddNoteAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : AppCompatActivity(), Helper {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var Uid: String
    private lateinit var sharedPreferences: SharedPreferences
    private val notes = ArrayList<Note>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        islogin()
        databaseReference = FirebaseDatabase.getInstance().getReference("Note").child(Uid)
        initReclerView()
        getData()

        fab_addnote.setOnClickListener {
            startActivity<AddNoteActivity>()
        }

        btn_upload.setOnClickListener {
            startActivity<MainUploadActivity>()
        }


    }

    private fun initReclerView() {
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

    }

    //region select data az fire base
    fun getData() {
        notes.clear()
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                toast(error.message)
                Log.e("hassan", error.message)

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("hassan11", "test")
                Log.e("hassan", "$snapshot")
                val children = snapshot.children
                children.forEach() {

                    val note = it.getValue(Note::class.java)
                    notes.add(note!!)
                    recyclerview.adapter =
                        RecyclerAddNoteAdapter(notes, this@MainActivity, this@MainActivity)
                }
            }

        })


    }
    //endregion


    fun islogin() {
        sharedPreferences =
            getSharedPreferences(LoginActivity.IS_LOGIN_KEY, Context.MODE_PRIVATE)
        if (!sharedPreferences.contains(LoginActivity.UID_KEY)) {
            startActivity<LoginActivity>()
            finish()
        } else {
            Uid = sharedPreferences.getString(LoginActivity.UID_KEY, "").toString()
        }
    }

    override fun DeleteNote(note: Note) {

        databaseReference.child(note.id).removeValue().addOnCompleteListener {

            if (it.isSuccessful) {


                val dialog = Dialog(this@MainActivity)
                dialog.window
                    ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dialog.setContentView(R.layout.dialog_message_delete)
                dialog.show()

                getData()

            } else {
                Log.e("hasan", it.exception?.message.toString())
            }
        }
    }


    override fun EditNote(note: Note) {

        databaseReference.child(note.id).setValue(note).addOnCompleteListener {

            if (it.isSuccessful) {


                val dialog = Dialog(this@MainActivity)
                dialog.window
                    ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dialog.setContentView(R.layout.dialog_message_edit)
                dialog.show()

                getData()
            } else {
                Log.e("hasan", it.exception?.message.toString())
            }
        }
    }

}