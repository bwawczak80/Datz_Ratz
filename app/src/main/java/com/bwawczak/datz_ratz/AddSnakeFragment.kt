package com.bwawczak.datz_ratz

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bwawczak.datz_ratz.models.LogItem
import com.bwawczak.datz_ratz.models.Snake
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_add_snake.*
import kotlinx.android.synthetic.main.fragment_add_snake.view.*


class AddSnakeFragment : Fragment() {


    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_snake, container, false)

        //create arrayAdapter for custom drop down menu
        val rodentList = resources.getStringArray(R.array.rodents)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, rodentList)
        view.autoCompleteTextView.setAdapter(arrayAdapter)

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
                        view.dateInput.text.toString().trim { it <= ' ' },
                        arrayListOf<LogItem>()
                    )

                    val firstLog = LogItem(
                        view.weightInput.text.toString().trim { it <= ' ' },
                        view.autoCompleteTextView.text.toString()
                    )


                }
            }

        }

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("users")

        return view
    }


}