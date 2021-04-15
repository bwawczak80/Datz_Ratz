package com.bwawczak.datz_ratz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val exampleList = generateDummyList(30)


        view.recyclerView.adapter = HomeFragmentRecyclerAdapter(exampleList)
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.setHasFixedSize(true)



        return view
    }

    private fun generateDummyList(size: Int): List<RecyclerViewData> {
        val list = ArrayList<RecyclerViewData>()

        for (i in 0 until size) {
            val item = RecyclerViewData(
                "Name $i", "Morph", "Weight", "Rodent", "date", "weight"
                , "rodent", "date", "weight", "rodent"
            )
            list += item
        }

        return list
    }


}