package com.example.myapplication.Activiy

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.view.View
import com.example.myapplication.Activiy.Phone.PhoneAuthActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main_sign_up.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private val IS_LOGIN_KEY = "is_login_key"
        private val UID_KEY = "uid_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences(IS_LOGIN_KEY, Context.MODE_PRIVATE)

        if(sharedPreferences.contains(UID_KEY)){
            startActivity<MainActivity>()
            finish()
        }

        btn_login.setOnClickListener {

            show_prgressBar()
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            Log.e("hassan", email + "      " + password)

            if (email.isEmpty() || password.isEmpty()) {
                hide_prgressBar()
                toast("لظفا ایمیل و پسورد را وارد کنید ")

            } else {
                hide_prgressBar()
                checkLogin(email, password)
            }

        }

        txt_mainsignin.setOnClickListener {
            startActivity<MainSignUpActivity>()
        }

        txt_forgetpassword.setOnClickListener {
            startActivity<ForgetPasswordActivity>()
        }

        txt_phoneauth.setOnClickListener {
            startActivity<PhoneAuthActivity>()
        }


    }


    fun checkLogin(email: String, pass: String) {

        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                toast("با موفقعیت وارد شدید")
//                val getEmail = firebaseAuth.currentUser.email
                val uid = firebaseAuth.currentUser.uid
                handlerlogin(uid)
                startActivity<MainActivity>()
            } else
                toast(it.exception?.message.toString())
        }

    }

    private fun handlerlogin(uid: String) {

        val editor = sharedPreferences.edit()
        editor.putString(UID_KEY,uid)
        editor.commit()

    }

    fun show_prgressBar() {
        login_progressBar.visibility = View.VISIBLE
    }

    fun hide_prgressBar() {
        login_progressBar.visibility = View.INVISIBLE
    }


}