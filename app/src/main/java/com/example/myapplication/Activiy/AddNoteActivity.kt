package com.example.myapplication.Activiy

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.EventLogTags
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Data.Note
import com.example.myapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_note.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var date: String
    private lateinit var time: String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var databaseReference: DatabaseReference
    private lateinit var Uid :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        islogin()
        databaseReference = FirebaseDatabase.getInstance().getReference("Note").child(Uid)

        btn_addnote.setOnClickListener {
            val date = "1399/12/19"
            val Title = edt_noteTitle.text.toString()
            val Description = edt_noteDesc.text.toString()

            if (!Title.isEmpty()) {
                if (!Description.isEmpty()) {
                    if (!date.isEmpty()) {
                        addNote(Title, Description, date)

                    } else
                        toast("لطفا زمان یاداشت رو وارد کنید")
                } else
                    toast("لطفا توضیحات رو یاداشت کنید")
            } else {
                toast("لطفا عنوان یاداشت را وارد کنید")
            }

        }


    }

    private fun addNote(title: String, description: String, date: String) {
        val key = databaseReference.push().key.toString()

        val note = Note(title, description, date)
        databaseReference.child(key).setValue(note).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity<MainActivity>()
                finish()
                toast("پیام با موفقعیت اضافه شد")
            } else {
                toast(it.exception?.message.toString())
            }
        }


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


//    fun persianCal() {
//        val calendar = Calendar.getInstance()
//
//        val timePickerDialog =
//            TimePickerDialog.newInstance(
//                this@AddNoteActivity,
//                calendar[Calendar.HOUR_OF_DAY],
//                calendar[Calendar.MINUTE],
//                true
//            )
//        timePickerDialog.show(fragmentManager, "TimePickDialog")
//        val persianCalendar = PersianCalendar()
//        val datePickerDialog =
//            DatePickerDialog.newInstance(
//                this@AddNoteActivity,
//                persianCalendar.persianYear,
//                persianCalendar.persianMonth,
//                persianCalendar.persianDay
//            )
//        val dates = arrayOfNulls<PersianCalendar>(13)
//        for (i in -6..6) {
//            val date = PersianCalendar()
//            date.add(PersianCalendar.MONTH, i)
//            dates[i + 6] = date
//        }
//        datePickerDialog.selectableDays = dates
//        datePickerDialog.show(fragmentManager, "TimePickDialog")
//    }


