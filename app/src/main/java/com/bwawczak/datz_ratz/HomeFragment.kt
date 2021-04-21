package com.bwawczak.datz_ratz

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val exampleList = 30.generateDummyList()

        view.recyclerView.adapter = HomeFragmentRecyclerAdapter(exampleList)
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.setHasFixedSize(true)

        database = FirebaseDatabase.getInstance()
        reference = database.getReference("users")

        //getData()

        return view
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

//    private fun getData(){
//        reference.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                Log.e("cancel", p0.toString())
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                var list = ArrayList<DatabaseModel>()
//                for (data in p0.children){
//                    var model = data.getValue(DatabaseModel::class.java)
//                    list.add(model as DatabaseModel)
//                }
//
//                if (list.size > 0 ) {
//                    val adapter = HomeFragmentRecyclerAdapter(list)
//                    recyclerView.adapter = adapter
//                }
//
//            }
//
//        })
//    }


}