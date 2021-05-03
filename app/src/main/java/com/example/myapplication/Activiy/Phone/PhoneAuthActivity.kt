package com.example.myapplication.Activiy.Phone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_phone_auth.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class PhoneAuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)

        spinner.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            CountyData.countryName
        )


        btn_login.setOnClickListener {

            val code = CountyData.countryCode[spinner.selectedItemPosition]

            val number = edt_number.text.toString()

            if (number.isEmpty() || number.length < 10) {
                toast("شماره تلفن معتبر نمی باشد!")
            } else {
                val phoneNumbre = "+" + code + number
                startActivity<VerifyPhoeAuth>(CountyData.PHONE_KEY_EXTRA to phoneNumbre)
            }

        }

    }
}

