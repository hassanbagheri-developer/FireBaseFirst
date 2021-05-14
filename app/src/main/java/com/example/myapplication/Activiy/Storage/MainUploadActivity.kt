package com.example.myapplication.Activiy.Storage

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Activiy.LoginActivity
import com.example.myapplication.Data.Note
import com.example.myapplication.R
import com.example.myapplication.adapter.RecyclerAddNoteAdapter
import com.example.myapplication.adapter.RecyclerImageUploadAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_upload.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.*


class MainUploadActivity : AppCompatActivity() {


    private lateinit var databaseReference: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var Uid: String
//    private lateinit var progresDialog: Dialog
    private val uploads = ArrayList<Upload>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_upload)

        initProgressDialog()

        databaseReference =
            FirebaseDatabase.getInstance().getReference(UploadImageActivity.DATABSE_PATH_UPLAOD)
        islogin()
        initRecyclerView()
        getData()

        fab_addimage.setOnClickListener {
            startActivity<UploadImageActivity>()
        }


    }

    private fun getData() {
        uploads?.clear()
//        progresDialog.show()
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
//                progresDialog.dismiss()
                toast(error.message)

            }

            override fun onDataChange(snapshot: DataSnapshot) {
//                progresDialog.dismiss()
                val children = snapshot.children
                children.forEach() {

                    val upload = it.getValue(Upload::class.java)
                    uploads.add(upload!!)
                    recylcerview_uploadimage.adapter =
                        RecyclerImageUploadAdapter(uploads, this@MainUploadActivity)
                }
            }
        })

    }

    fun initRecyclerView() {
        recylcerview_uploadimage.setLayoutManager(LinearLayoutManager(this))
        recylcerview_uploadimage.setHasFixedSize(true)
    }


    fun initProgressDialog() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("در حال ارتباط با سرور... لطفا منتظر بمانید")
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