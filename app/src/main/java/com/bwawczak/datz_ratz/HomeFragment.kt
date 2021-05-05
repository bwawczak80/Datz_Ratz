package com.bwawczak.datz_ratz

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_home.view.*


class
HomeFragment : Fragment(), HomeFragmentRecyclerAdapter.OnItemClickListener {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val exampleList = 30.generateDummyList()

        view.recyclerView.adapter = HomeFragmentRecyclerAdapter(exampleList, this)
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.setHasFixedSize(true)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("users")

        return view
    }

    // Handles on click event for recyclerView
    override fun onItemClick(position: Int) {
        //Toast.makeText(context,"Item $position clicked",Toast.LENGTH_SHORT).show()

        val intent =
            Intent(context, AddSnakeLog::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }

    private fun Int.generateDummyList(): List<RecyclerViewData> {
        val list = ArrayList<RecyclerViewData>()

        for (i in 0 until this) {
            val item = RecyclerViewData(
                "Name $i", "Morph", "Weight", "Rodent", "date", "weight"
                , "rodent", "date", "weight", "rodent"
            )
            list += item
        }

        return list
    }

}