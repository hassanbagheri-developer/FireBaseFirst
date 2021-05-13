package com.example.myapplication.Activiy.Storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.Activiy.Storage.UploadImageActivity.Companion.DATABSE_PATH_UPLAOD
import com.example.myapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main_upload.*
import kotlinx.android.synthetic.main.activity_upload_image.*
import org.jetbrains.anko.startActivity

class UploadImageActivity : AppCompatActivity() {

    companion object{
        val STORAGE_PATH_UPLAOD = "Uploads/"
        val DATABSE_PATH_UPLAOD = "Upload"
        val IMAGE_REQUEST = 234
    }

    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)


        databaseReference= FirebaseDatabase.getInstance().getReference(DATABSE_PATH_UPLAOD)
        storageRef = FirebaseStorage.getInstance().getReference(STORAGE_PATH_UPLAOD)

        edt_imgTitle.visibility = View.INVISIBLE
        txt_shownameimage.visibility= View.INVISIBLE

        fab_addimage.setOnClickListener {

            startActivity<UploadImageActivity>()
        }
    }
}