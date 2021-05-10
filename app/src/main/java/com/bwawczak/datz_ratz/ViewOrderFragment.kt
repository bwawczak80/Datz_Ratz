package com.bwawczak.datz_ratz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwawczak.datz_ratz.firestore.FirestoreClass
import com.bwawczak.datz_ratz.models.LogItem
import kotlinx.android.synthetic.main.fragment_view_order.view.*
import java.util.*
import kotlin.collections.ArrayList


class ViewOrderFragment : Fragment() {

    lateinit var adapter: OrderFragmentRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_order, container, false)


        adapter = OrderFragmentRecyclerAdapter(arrayListOf())
        view.orderRecyclerView.layoutManager = LinearLayoutManager(context)
        view.orderRecyclerView.setHasFixedSize(true)
        view.orderRecyclerView.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        FirestoreClass().listRecentLogItems(FirestoreClass().getCurrentUserId(),
            object : FirestoreClass.RecentLogItemCallback {
                override fun onSuccess(logItems: ArrayList<LogItem>) {
                    Log.d("Firestore", "size = " + logItems.size)

                    val mealList: MutableList<String> = ArrayList()
                    var x = logItems.size
                    while (x != 0) {

                        mealList.add(logItems[x - 1].lastMeal)
                        x--
                    }

                    val list = ArrayList<RecyclerViewOrder>()


                    for(i in mealList.distinct()){

                        val item = RecyclerViewOrder(Collections.frequency(mealList, i).toString(),i, " ")
                        list += item

                    }
                    adapter.setItemList(list)
                    adapter.notifyDataSetChanged()
                }
            })

    }

//    private fun calculateTotals(mealList: MutableList<String>) {
//
//        for (item in mealList.distinct()) {
//            println(item + ": " + Collections.frequency(mealList, item))
//        }
//        val rodentArray = arrayOf(
//            "Pinky Mouse",
//            "Fuzzy Mouse",
//            "Hopper Mouse",
//            "Small Mouse",
//            "Medium Mouse",
//            "Large Mouse",
//            "Pinky ASF",
//            "Hopper ASF",
//            "Small ASF",
//            "Medium ASF",
//            "Large ASF",
//            "Jumbo ASF",
//            "Pinky Rat",
//            "Weaned Rat",
//            "Small Rat",
//            "Medium Rat",
//            "Large Rat",
//            "Jumbo Rat",
//            "None"
//        )
//        val priceArray = arrayOf(
//            .50,
//            .75,
//            1.00,
//            1.25,
//            1.50,
//            2.00,
//            .50,
//            1.00,
//            1.75,
//            2.50,
//            4.00,
//            6.00,
//            1.00,
//            2.00,
//            3.00,
//            4.00,
//            5.00,
//            7.00,
//            0
//        )
//    }

}





