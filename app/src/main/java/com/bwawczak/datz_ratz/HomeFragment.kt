package com.bwawczak.datz_ratz

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bwawczak.datz_ratz.firestore.FirestoreClass
import com.bwawczak.datz_ratz.models.Snake
import kotlinx.android.synthetic.main.fragment_home.view.*


class
HomeFragment : Fragment(), HomeFragmentRecyclerAdapter.OnItemClickListener {

    lateinit var adapter: HomeFragmentRecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        adapter = HomeFragmentRecyclerAdapter(arrayListOf(), this)

        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.setHasFixedSize(true)
        view.recyclerView.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        // read snakes from firestore
        FirestoreClass().listSnakes(
            FirestoreClass().getCurrentUserId(),
            object : FirestoreClass.Callback {
                override fun onSuccess(snakes: ArrayList<Snake>?) {
                    if (snakes != null) {
                        adapter.setItemList(snakes)

                    }
                    adapter.notifyDataSetChanged()
                }
            })
    }

    // Handles on click event for recyclerView
    override fun onItemClick(position: Int) {

        val intent =
            Intent(context, AddSnakeLog::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("index", position)
        intent.putExtra("snake", adapter.getItem(position))

        startActivity(intent)

    }


}