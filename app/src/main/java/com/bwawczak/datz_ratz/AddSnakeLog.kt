package com.bwawczak.datz_ratz

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bwawczak.datz_ratz.models.LogItem
import kotlinx.android.synthetic.main.activity_add_snake_log.*
import java.text.SimpleDateFormat
import java.util.*

class AddSnakeLog : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_snake_log)

        val logs = arrayOf<LogItem>()

        //create arrayAdapter for custom drop down menu

        val rodentList = resources.getStringArray(R.array.rodents)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, rodentList)
        this.autoCompleteTextViewLog.setAdapter(arrayAdapter)


        logBtn.setOnClickListener {
            //get user input data
            //verify and trim user data
            when {

                TextUtils.isEmpty(weightLogInput.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@AddSnakeLog,
                        "Please enter your snakes weight.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(autoCompleteTextViewLog.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this@AddSnakeLog,
                        "Please choose a rodent.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    //get date and time stamp
                    val datePattern ="dd/MM/yyyy"
                    val simpleDateFormat = SimpleDateFormat(datePattern)
                    val date = simpleDateFormat.format(Date())

                    val isInShed:Boolean = checkBox.isChecked


                    val logs = LogItem(
                        weightLogInput.text.toString().trim { it <= ' ' },
                        date,
                        autoCompleteTextViewLog.text.toString(),
                        isInShed

                    )
                    //transition back to homeFragment
                    val intent =
                        Intent(this@AddSnakeLog, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)


                }
            }

            //TODO update user in Firebase

        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    //TODO override onbackpressed()



}