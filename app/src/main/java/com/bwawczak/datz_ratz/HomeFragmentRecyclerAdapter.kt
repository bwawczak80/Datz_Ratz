package com.bwawczak.datz_ratz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_fragment_item.view.*

class HomeFragmentRecyclerAdapter(private val itemList: List<RecyclerViewData>,
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

        holder.textNameItem.text = currentItem.textName
        holder.textMorphItem.text = currentItem.textMorph
        holder.textWeightItem.text = currentItem.textWeight
        holder.textRodentItem.text = currentItem.textRodent
        holder.weightOneItem.text = currentItem.weightOne
        holder.weightTwoItem.text = currentItem.weightTwo
        holder.dateOneItem.text = currentItem.dateOne
        holder.dateTwoItem.text = currentItem.dateTwo
        holder.rodentOneItem.text = currentItem.rodentOne
        holder.rodentTwoItem.text = currentItem.rodentTwo
    }

    override fun getItemCount() = itemList.size


    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textNameItem: TextView = itemView.text_name
        val textMorphItem: TextView = itemView.text_morph
        val textWeightItem: TextView = itemView.text_weight
        val textRodentItem: TextView = itemView.text_rodent
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