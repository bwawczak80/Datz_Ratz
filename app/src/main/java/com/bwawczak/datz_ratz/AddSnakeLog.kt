package com.bwawczak.datz_ratz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bwawczak.datz_ratz.models.LogItem
import kotlinx.android.synthetic.main.activity_add_snake_log.*
import kotlinx.android.synthetic.main.activity_add_snake_log.autoCompleteTextView
import kotlinx.android.synthetic.main.fragment_add_snake.*
import java.text.SimpleDateFormat
import java.util.*

class AddSnakeLog : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_snake_log)

        val logs = arrayOf<LogItem>()

        logBtn.setOnClickListener {
            //get user input data
            //verify and trim user data
            when {

                TextUtils.isEmpty(weightInput.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@AddSnakeLog,
                        "Please enter your snakes weight.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(autoCompleteTextView.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@AddSnakeLog,
                        "Please choose a rodent.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    //get date and time stamp
                    val datePattern ="dd-MM-yyyy"
                    val simpleDateFormat = SimpleDateFormat(datePattern)
                    val date = simpleDateFormat.format(Date())


                    val logs = LogItem(
                        weightInput.text.toString().trim { it <= ' ' },
                        date,
                        autoCompleteTextView.text.toString()
                    )
                    //transition back to homeFragment
                    val intent =
                        Intent(this@AddSnakeLog, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    finish()


                }
            }

            //TODO update user in Firebase

        }

    }



}