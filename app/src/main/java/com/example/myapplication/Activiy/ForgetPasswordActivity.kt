package com.example.myapplication.Activiy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_forget_password.edt_email
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.email
import org.jetbrains.anko.toast

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        firebaseAuth = FirebaseAuth.getInstance()

        btn_sendlink.setOnClickListener {

            if(edt_email.text.isEmpty()){

                toast("لطفا ایمیل خود را وارد کنید.")
            }else{
                show_prgressBar()
                forgetPassword(edt_email.text.toString())
            }
        }
    }

    private fun forgetPassword(email: String) {

        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {

            if (it.isSuccessful){
                hide_prgressBar()
                toast("لینک بازیابی پسورد به ایمیل " + email + "ارسال شد.")
        } else{
                hide_prgressBar()
                toast(it.exception?.message.toString())
            }
        }

    }


    fun show_prgressBar() {
        forgetpassword_progressBar.visibility = View.VISIBLE
    }

    fun hide_prgressBar() {
        forgetpassword_progressBar.visibility = View.INVISIBLE
    }
}