package com.bwawczak.datz_ratz.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwawczak.datz_ratz.OrderFragmentRecyclerAdapter
import com.bwawczak.datz_ratz.R
import com.bwawczak.datz_ratz.RecyclerViewOrder
import com.bwawczak.datz_ratz.firestore.FirestoreClass
import com.bwawczak.datz_ratz.models.LogItem
import com.bwawczak.datz_ratz.models.User
import com.bwawczak.datz_ratz.utils.Constants
import kotlinx.android.synthetic.main.fragment_view_order.*
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

        adapter =
            OrderFragmentRecyclerAdapter(arrayListOf())
        view.orderRecyclerView.layoutManager = LinearLayoutManager(context)
        view.orderRecyclerView.setHasFixedSize(true)
        view.orderRecyclerView.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        FirestoreClass().listRecentLogItems(FirestoreClass().getCurrentUserId(),
            object : FirestoreClass.RecentLogItemCallback {
                override fun onSuccess(logItems: ArrayList<LogItem>, user: User) {
                    Log.d("Firestore", "size = " + logItems.size)


                    val mealList: MutableList<String> = ArrayList()
                    val totalPrice = calculateTotals(mealList)
                    println("----------------$totalPrice----------------")

                    var x = logItems.size
                    while (x != 0) {

                        mealList.add(logItems[x - 1].lastMeal)
                        x--
                    }

                    val list = ArrayList<RecyclerViewOrder>()




                    for (i in mealList.distinct()) {

                        val item =
                            RecyclerViewOrder(
                                Collections.frequency(
                                    mealList,
                                    i
                                ).toString(), i, " "
                            )
                        list += item

                    }
                    adapter.setItemList(list)
                    adapter.notifyDataSetChanged()

                    btn_send.setOnClickListener {
                        when {
                            TextUtils.isEmpty(puDayInput.text.toString().trim { it <= ' ' }) -> {
                                Toast.makeText(
                                    context,
                                    "Please enter a day to pickup.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            TextUtils.isEmpty(puTimeInput.text.toString().trim { it <= ' ' }) -> {
                                Toast.makeText(
                                    context,
                                    "Please enter a time to pickup.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            else -> {
                                val puDay = puDayInput.text.toString().trim { it <= ' ' }
                                val puTime = puTimeInput.text.toString().trim { it <= ' ' }
                                //pu = arrayOf(puDay,puTime)
                                val userFirst = user.firstName
                                val userLast = user.lastName
                                val userName = "$userFirst $userLast"
                                val userPhone = user.phone
                                val title = "Rodent Order for: $userName"
                                val message = StringBuilder()
                                message.appendln(title)
                                message.appendln(" ")
                                for (item in mealList.distinct()) {
                                    message.appendln(
                                        "$item: " + Collections.frequency(
                                            mealList,
                                            item
                                        )
                                    )
                                }
                                message.appendln(" ")
                                message.appendln(userPhone)
                                message.appendln(" ")
                                message.appendln("For pickup $puDay at $puTime")


                                val completeMsg = message.toString()


                                sendEmail(completeMsg)

                            }
                        }

                    }

                }
            })

    }

    private fun sendEmail(message: String) {
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailTo:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(Constants.EMAIL))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, "New Rodent Order")
        mIntent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            startActivity(Intent.createChooser(mIntent, "Choose email provider"))
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }

    }


    private fun calculateTotals(mealList: MutableList<String>): Int {

        for (item in mealList.distinct()) {
            println(item + ": " + Collections.frequency(mealList, item))
        }



        val rodentPrices = mapOf(
            "Pinky Mouse" to .50,
            "Fuzzy Mouse" to .75,
            "Hopper Mouse" to 1.00,
            "Small Mouse" to 1.25,
            "Medium Mouse" to 1.50,
            "Large Mouse" to 2.00,
            "Pinky ASF" to .50,
            "Hopper ASF" to 1.00,
            "Small ASF" to 1.75,
            "Medium ASF" to 2.50,
            "Large ASF" to 4.00,
            "Jumbo ASF" to 6.00,
            "Pinky Rat" to 1.00,
            "Weaned Rat" to 2.00,
            "Small Rat" to 3.00,
            "Medium Rat" to 4.00,
            "Large Rat" to 5.00,
            "Jumbo Rat" to 7.00,
            "None" to 0
        )
        var total = 0
        for (i in mealList){
            if (rodentPrices.containsKey(i))
                total += rodentPrices[i] as Int
        }

        println(total)



        return total
    }

}





