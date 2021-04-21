package com.bwawczak.datz_ratz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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

            val nameHolder = view.nameInput.text.toString().trim()
            val weightHolder = view.weightInput.text.toString().trim()
            val morphHolder = view.morphInput.text.toString().trim()

            // TODO calendar input
            val dateHolder = view.dateInput.text.toString().trim()
            val rodentHolder = view.autoCompleteTextView.text.toString().trim()

            writeToDB(nameHolder, weightHolder, morphHolder, dateHolder, rodentHolder)


        }

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("users")

        return view
    }

    private fun writeToDB(
        name: String,
        weight: String,
        morph: String,
        date: String,
        rodent: String
    ) {

        if (name.isNotEmpty() && weight.isNotEmpty() && morph.isNotEmpty() && date.isNotEmpty()
            && rodent.isNotEmpty()
        ) {
            val model = DatabaseModel(name, weight, morph, date, rodent)
            val id = reference.push().key

            //send the data to Firebase
            reference.child(id!!).setValue(model)

            Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "All Fields Required", Toast.LENGTH_LONG).show()
        }

    }
}