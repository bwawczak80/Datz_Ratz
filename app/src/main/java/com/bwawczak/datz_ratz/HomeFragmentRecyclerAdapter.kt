package com.bwawczak.datz_ratz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bwawczak.datz_ratz.models.Snake
import kotlinx.android.synthetic.main.home_fragment_item.view.*

class HomeFragmentRecyclerAdapter(private var itemList: ArrayList<Snake>,
                                  private val listener: OnItemClickListener) :
    RecyclerView.Adapter<HomeFragmentRecyclerAdapter.HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.home_fragment_item,
            parent, false
        )

        return HomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.textNameItem.text = currentItem.snakeName
        holder.textMorphItem.text = currentItem.morph

       // holder.textWeightItem.text = ""
        //holder.textRodentItem.text = ""


        // get max 2 log item
        val logs = currentItem.logs

        if (logs.size >= 2) {
            holder.weightOneItem.text = logs[logs.size-1].weight
            holder.dateOneItem.text = logs[logs.size-1].mealDate
            holder.rodentOneItem.text = logs[logs.size-1].lastMeal

            holder.weightTwoItem.text = logs[logs.size-2].weight
            holder.dateTwoItem.text = logs[logs.size-2].mealDate
            holder.rodentTwoItem.text = logs[logs.size-2].lastMeal
        }
        else if (logs.size == 1) {
            holder.weightOneItem.text = logs[logs.size-1].weight
            holder.dateOneItem.text = logs[logs.size-1].mealDate
            holder.rodentOneItem.text = logs[logs.size-1].lastMeal

            holder.weightTwoItem.text = "empty"
            holder.dateTwoItem.text = ""
            holder.rodentTwoItem.text = ""
        }
        else {
            holder.weightOneItem.text = "empty"
            holder.dateOneItem.text = ""
            holder.rodentOneItem.text = ""

            holder.weightTwoItem.text = "empty"
            holder.dateTwoItem.text = ""
            holder.rodentTwoItem.text = ""
        }


    }

    override fun getItemCount() = itemList.size

    public fun setItemList(items: ArrayList<Snake>) {
        itemList = items
    }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textNameItem: TextView = itemView.text_name
        val textMorphItem: TextView = itemView.text_morph

        //val textWeightItem: TextView = itemView.text_weight
        //val textRodentItem: TextView = itemView.text_rodent

        val weightOneItem: TextView = itemView.weight_one
        val weightTwoItem: TextView = itemView.weight_two
        val dateOneItem: TextView = itemView.date_one
        val dateTwoItem: TextView = itemView.date_two
        val rodentOneItem: TextView = itemView.rodent_one
        val rodentTwoItem: TextView = itemView.rodent_two

        init {
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            val position:Int = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }

        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}