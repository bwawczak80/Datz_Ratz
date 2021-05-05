package com.bwawczak.datz_ratz

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bwawczak.datz_ratz.firestore.FirestoreClass
import com.bwawczak.datz_ratz.models.LogItem
import com.bwawczak.datz_ratz.models.Snake
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_add_snake.*
import kotlinx.android.synthetic.main.fragment_add_snake.view.*
import java.text.SimpleDateFormat
import java.util.*


class AddSnakeFragment : Fragment() {


    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // get current User
        val currentUser = FirestoreClass().getCurrentUserId()

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_snake, container, false)

        //create arrayAdapter for custom drop down menu
        val rodentList = resources.getStringArray(R.array.rodents)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, rodentList)
        view.autoCompleteTextView.setAdapter(arrayAdapter)

        var isInShed = false

        view.addBtn.setOnClickListener {

            when {

                TextUtils.isEmpty(nameInput.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        context,
                        "Please enter your snakes name or identifier.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                TextUtils.isEmpty(weightInput.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        context,
                        "Please enter your snakes weight.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                TextUtils.isEmpty(morphInput.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        context,
                        "Please enter your snakes morph.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                TextUtils.isEmpty(dateInput.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        context,
                        "Please enter when you snake last ate.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                TextUtils.isEmpty(autoCompleteTextView.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        context,
                        "Please enter your snakes last meal.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {



                    val snake = Snake(
                        view.nameInput.text.toString().trim { it <= ' ' },
                        view.morphInput.text.toString().trim { it <= ' ' },
                        arrayListOf<LogItem>()
                    )

                    val snakeLogItem = LogItem(
                        view.weightInput.text.toString().trim { it <= ' ' },
                        view.dateInput.text.toString().trim { it <= ' ' },
                        view.autoCompleteTextView.text.toString(),
                        isInShed


                    )

                    //TODO update Firebase
                    //TODO transition back to Home Fragment


                }
            }

        }

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("users")

        return view
    }


}