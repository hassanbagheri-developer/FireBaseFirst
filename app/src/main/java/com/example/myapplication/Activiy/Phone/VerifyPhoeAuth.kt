package com.example.myapplication.Activiy.Phone

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_verify_phoe_auth.*
import org.jetbrains.anko.toast
import java.util.*
import java.util.concurrent.TimeUnit

class VerifyPhoeAuth : AppCompatActivity() {

    private lateinit var firebaseAut: FirebaseAuth
    private lateinit var verificationId1: String
    private lateinit var phoneNumber: String
    private lateinit var timer: Timer
    private var timecount: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phoe_auth)

        firebaseAut = FirebaseAuth.getInstance()
        phoneNumber = intent.getStringExtra(CountyData.PHONE_KEY_EXTRA).toString()
        sendVerifivationCode(phoneNumber)
        getTime()






        btn_confirm.setOnClickListener {
            val code = edt_code.text.toString()
            if (code.isEmpty() || code.length > 6) {
                edt_code.setError("لطفا کد صحیح را وارد کنید")
                edt_code.requestFocus()
            } else {
                verfiyCode(code)


            }
        }
    }

    private fun verfiyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId1!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAut.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Log.e("hassan", "signInWithCredential:success")
                    toast("ثبت نام با موقعیت انجام شد.")


                    // save user in data base
                    val user = task.result?.user
                } else {

                    Log.e("hassan", "signInWithCredential:failure", task.exception)
                    toast(task.exception?.message.toString())

                }
            }
    }


    private fun sendVerifivationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAut)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)           // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            val code = credential.smsCode
            if (code != null) {
                edt_code.setText(code)
                verfiyCode(code)
            }
            Log.e("hassan", "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }


        override fun onVerificationFailed(e: FirebaseException) {
            toast(e.message.toString())
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {

            Log.e("hassan", "onCodeSent:$verificationId")


             verificationId1 = verificationId
//            val resendToken = token
        }


    }


    
    //regionset time
    fun getTime() {
        txt_timer.setTextColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.design_default_color_primary_dark
            )
        )
        timecount = 70000
        timer = Timer()
        timer.schedule(object : TimerTask() {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun run() {
                runOnUiThread {
                    timecount -= 1000
                    txt_timer.setText(getTimeStyle(timecount))
                    if (timecount == 0L) {
                        timer.cancel()
                        txt_timer.setText("00:00")
                        txt_resend.setVisibility(View.VISIBLE)
                        txt_resend.setOnClickListener(View.OnClickListener { finish() })
                    }
                }
            }
        }, 0, 1000)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getTimeStyle(timercount: Long): String? {
        var second = timercount / 1000
        val mint = second / 60
        second %= 60
        return String.format(
            Locale.forLanguageTag("fa"),
            "%2d",
            mint
        ) + ":" + String.format(Locale.forLanguageTag("fa"), "%2d", second)
    }

    //endregion

}