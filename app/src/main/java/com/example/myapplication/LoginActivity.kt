package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.email
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        firebaseAuth = FirebaseAuth.getInstance()


        btn_login.setOnClickListener {


            val email = edt_email.text.toString()
            val password = edt_password.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                toast("لظفا ایمیل و پسورد را وارد کنید ")
            } else
                checkLogin(email, password)


        }


    }


    fun checkLogin(email: String, pass: String) {

        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                toast("با موفقعیت وارد شدید")
                val getEmail = firebaseAuth.currentUser.email
                val getUid = firebaseAuth.currentUser.uid
            } else
                toast(it.exception?.message.toString())
        }

    }


}