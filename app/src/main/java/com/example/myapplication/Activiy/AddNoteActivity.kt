package com.example.myapplication.Activiy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

import java.util.*

class AddNoteActivity : AppCompatActivity()
     {

    private lateinit var date: String
    private lateinit var time: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
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


