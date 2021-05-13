package com.example.myapplication.Activiy.Storage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_main_upload.*
import org.jetbrains.anko.startActivity


class MainUploadActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_upload)

        fab_addimage.setOnClickListener {
            startActivity<UploadImageActivity>()
        }


    }
}