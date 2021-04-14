package com.bwawczak.datz_ratz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.dropdown_item.view.*
import kotlinx.android.synthetic.main.fragment_add_snake.view.*


class AddSnakeFragment : Fragment() {

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_add_snake, container, false)

        val rodentList = resources.getStringArray(R.array.rodents)
        val arrayAdapter = ArrayAdapter(requireContext(), R. layout.dropdown_item, rodentList)
        view.autoCompleteTextView.setAdapter(arrayAdapter)

        var nameHolder = " "
        var weightHolder = " "
        var morphHolder = " "
        var dateHolder = " "
        var rodentHolder = " "


        view.addBtn.setOnClickListener {

            nameHolder = view.nameInput.text.toString()
            weightHolder = view.weightInput.text.toString()
            morphHolder = view.morphInput.text.toString()
            dateHolder = view.dateInput.text.toString()
            rodentHolder = view.autoCompleteTextView.text.toString()



            writeToDB(nameHolder, weightHolder, morphHolder, dateHolder, rodentHolder)

        }

        return view
    }


    //TODO
    private fun writeToDB(
        name: String,
        weight: String,
        morph: String,
        date: String,
        rodent: String
    ) {



    }
}