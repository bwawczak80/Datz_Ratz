package com.bwawczak.datz_ratz

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bwawczak.datz_ratz.firestore.FirestoreClass
import com.bwawczak.datz_ratz.models.LogItem
import com.bwawczak.datz_ratz.models.Snakes
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

//            val nameHolder = view.nameInput.text.toString().trim()
//            val weightHolder = view.weightInput.text.toString().trim()
//            val morphHolder = view.morphInput.text.toString().trim()
//            val dateHolder = view.dateInput.text.toString().trim()
//            val rodentHolder = view.autoCompleteTextView.text.toString().trim()

                    val snake = Snakes(
                        view.nameInput.text.toString().trim { it <= ' ' },
                        view.morphInput.text.toString().trim { it <= ' ' },
                        view.dateInput.text.toString().trim { it <= ' ' }

                    )

                    val firstLog = LogItem(
                        view.weightInput.text.toString().trim { it <= ' ' },
                        view.autoCompleteTextView.text.toString()
                    )


                }
            }
            //writeToDB(nameHolder, weightHolder, morphHolder, dateHolder, rodentHolder)

          //  FirestoreClass().registerUser(this, user)

        }

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("users")

        return view
    }

//    private fun writeToDB(
//
//
//        name: String,
//        weight: String,
//        morph: String,
//        date: String,
//        rodent: String
//
//    ) {
//
//
//
//        if (name.isNotEmpty() && weight.isNotEmpty() && morph.isNotEmpty() && date.isNotEmpty()
//            && rodent.isNotEmpty()
//        ) {
//            val model = DatabaseModel(name, weight, morph, date, rodent)
//            val id = reference.push().key
//
//            //send the data to Firebase
//            reference.child(id!!).setValue(model)
//
//            Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show()
//        } else {
//            Toast.makeText(context, "All Fields Required", Toast.LENGTH_LONG).show()
//        }
//
//    }
}