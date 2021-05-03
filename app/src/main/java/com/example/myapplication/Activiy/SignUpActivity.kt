package com.example.myapplication.Activiy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.Data.Users
import com.example.myapplication.Activiy.LoginActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SignUpActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("users")


        btn_signup.setOnClickListener {

            show_prgressBar()
            val email = edt_emails.text.toString()
            val username = edt_username.text.toString()
            val password = edt_pass.text.toString()

            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                hide_prgressBar()
                toast("لطفا تمامی فیلدها رو پرکنید ")

            } else {

                signUp(email, username, password)
                hide_prgressBar()
            }

        }


    }

    private fun signUp(email: String, username: String, password: String) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val firebaseUser = firebaseAuth.currentUser
                val uid = firebaseUser.uid

                // create Data Model

                val users= Users(username, email, password, "")
//                    users=Users(username,email,password,"")

                databaseReference.child(uid).setValue(users).addOnCompleteListener {
                    if(it.isSuccessful){
                        toast("کاربر " + username + "  با موفیعت ثبت نام شد")
                        startActivity<LoginActivity>()
                        finish()
                    }
                }

            }else
                toast(it.exception?.message.toString())
        }

    }

    fun show_prgressBar() {
        signup_progresBar.visibility = View.VISIBLE
    }

    fun hide_prgressBar() {
        signup_progresBar.visibility = View.INVISIBLE
    }
}
