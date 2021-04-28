package com.bwawczak.datz_ratz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bwawczak.datz_ratz.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener {
            when {

                TextUtils.isEmpty(reg_first_name.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter your first name.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(reg_last_name.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter your last name.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(reg_email.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter an email address.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(reg_password.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please enter a valid password.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {

                    val fName: String = reg_first_name.text.toString().trim { it <= ' ' }
                    val lName: String = reg_last_name.text.toString().trim { it <= ' ' }
                    val eMail: String = reg_email.text.toString().trim { it <= ' ' }
                    val pWord: String = reg_password.text.toString().trim { it <= ' ' }

                    // Create an instance and register with email and password
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(eMail, pWord)
                        .addOnCompleteListener { task ->

                            // if the registration is successful
                            if (task.isSuccessful) {

                                //Firebase registered user
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                val user = User()

                                Toast.makeText(
                                    this@RegisterActivity, "You have been successfully registered.",
                                    Toast.LENGTH_LONG
                                ).show()

                                /** Here the user is registered and automatically signed in, so
                                 * we send them to the main screen with the user id and email that they
                                 * registered with.
                                 */

                                val intent =
                                    Intent(this@RegisterActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", eMail)
                                intent.putExtra("first_name", fName)
                                intent.putExtra("last_name", lName)
                                startActivity(intent)
                                finish()
                            } else {
                                // If the registration is not successful...
                                Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }

            }
        }

        txt_login.setOnClickListener {


            onBackPressed()

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