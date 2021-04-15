package com.bwawczak.datz_ratz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_view_order.view.*


class ViewOrderFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_view_order, container, false)

        val myList = generateDummyList(20)

        view.orderRecyclerView.adapter = OrderFragmentRecyclerAdapter(myList)
        view.orderRecyclerView.layoutManager = LinearLayoutManager(context)
        view.orderRecyclerView.setHasFixedSize(true)





        return view
    }

    private fun generateDummyList(size: Int): List<RecyclerViewOrder> {

        val list = ArrayList<RecyclerViewOrder>()

        for (i in 0 until size) {
            val item = RecyclerViewOrder("$i", "Small ASF", "$1.75")
            list += item
        }
        return list
    }


}