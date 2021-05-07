package com.example.myapplication.Activiy

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Data.Note
import com.example.myapplication.R
import com.example.myapplication.adapter.RecyclerAddNoteAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : AppCompatActivity() {

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


    }

    private fun initReclerView() {
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)

    }

    fun getData(){
        notes.clear()
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                toast(error.message.toString())
                Log.e("hassan",error.message)

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("hassan","test")
                Log.e("hassan","$snapshot")
                for (DataSnapshot recipeSnapshot: snapshot. ) {
                    val note = dataSnapshot1.getValue(Note::class.java)
                    Log.e("hassan","-----------------")
                    Log.e("hassan","$note")
                    notes.add(note!!)
                    recyclerview.adapter=RecyclerAddNoteAdapter(notes)
                }
            }

        })


    }

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

}