package com.bwawczak.datz_ratz

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btn_fpw.setOnClickListener {
            val email: String = login_email.text.toString().trim { it <= ' ' }
            if (email.isEmpty()) {
                Toast.makeText(
                    this@ForgotPasswordActivity,
                    "Please enter your email.", Toast.LENGTH_LONG
                ).show()
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                "A link has been sent to your email.", Toast.LENGTH_LONG
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                task.exception!!.message.toString(), Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }

        }
    }
}