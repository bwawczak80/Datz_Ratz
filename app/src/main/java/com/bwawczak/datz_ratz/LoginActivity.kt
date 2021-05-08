package com.bwawczak.datz_ratz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_email.setText("brian@user.com")
        login_password.setText("password")
        txt_register.setOnClickListener {

            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))

        }

        btn_login.setOnClickListener {
            when {


                TextUtils.isEmpty(login_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter an email address.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(login_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter a valid password.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {

                    val eMail: String = login_email.text.toString().trim { it <= ' ' }
                    val pWord: String = login_password.text.toString().trim { it <= ' ' }

                    // Create an instance and register with email and password
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(eMail, pWord)
                        .addOnCompleteListener { task ->

                            // if the registration is successful
                            if (task.isSuccessful) {


                                Toast.makeText(
                                    this@LoginActivity, "You are logged in.",
                                    Toast.LENGTH_LONG
                                ).show()

                                val intent =
                                    Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra(
                                    "user_id",
                                    FirebaseAuth.getInstance().currentUser!!.uid
                                )
                                intent.putExtra("email_id", eMail)

                                startActivity(intent)
                                finish()
                            } else {
                                // If the login is not successful...
                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }

            }

        }

        forgot_password.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

}