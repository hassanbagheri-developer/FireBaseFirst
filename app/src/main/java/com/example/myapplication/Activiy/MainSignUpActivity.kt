package com.example.myapplication.Activiy

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.Data.Users
import com.example.myapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main_sign_up.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainSignUpActivity : AppCompatActivity() {

    val GOOGLE_CODE_REQEST = 101;

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_sign_up)
        sharedPreferences = getSharedPreferences(LoginActivity.IS_LOGIN_KEY, Context.MODE_PRIVATE)


        configGoogle()

        btn_googleaauth.setOnClickListener {
            signInGoogle();
        }


        btn_EmailAut.setOnClickListener {
            startActivity<SignUpActivity>()
        }
    }

    //region google login

    private fun configGoogle() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("users")

    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.e("hassan", "signInWithCredential:success")
                    val firebaseUser = firebaseAuth.currentUser

                    val Uid = firebaseUser.uid
                    val name = firebaseUser.displayName
                    val email = firebaseUser.email

                    val users = Users(name, email, "", "")
                    databaseReference.child(Uid).setValue(users).addOnCompleteListener {
                        if (task.isSuccessful) {
                            toast("همگام سازی با موفعیت انجام شد")
                            val uid = firebaseAuth.currentUser.uid
                            val editor = sharedPreferences.edit()
                            editor.putString(LoginActivity.UID_KEY,uid)
                            editor.commit()
                            startActivity<MainActivity>()
                            finish()

                        } else {
                            toast(task.exception?.message.toString())
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    toast("هنگام سازی با خطا مواجه شد")
                    Log.e("hassan", "signInWithCredential:failure", task.exception)
                }
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_CODE_REQEST) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                Log.e("hassan", "test" )
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                Log.e("hassan", "firebaseAuthWithGoogle:" )
                firebaseAuthWithGoogle(account!!)
            } catch (e :ApiException ) {
                // Google Sign In failed, update UI appropriately
                Log.e("hassan","aaa", e)
            }




        }
    }

    fun signInGoogle() {
        intent = googleSignInClient.signInIntent
        startActivityForResult(intent, GOOGLE_CODE_REQEST)

    }

    //endregion


}