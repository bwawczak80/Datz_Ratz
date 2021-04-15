package com.bwawczak.datz_ratz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderFragmentRecyclerAdapter(private val itemList: List<RecyclerViewOrder>) :
    RecyclerView.Adapter<OrderFragmentRecyclerAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderFragmentRecyclerAdapter.OrderViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.order_fragment_item, parent, false)

        return OrderViewHolder(itemView)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(
        holder: OrderFragmentRecyclerAdapter.OrderViewHolder,
        position: Int
    ) {
        val currentItem = itemList[position]

        holder.qtyTxt.text = currentItem.txtQty
        holder.rodentTxt.text = currentItem.txtRodent
        holder.priceTxt.text = currentItem.txtPrice
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val qtyTxt: TextView = itemView.findViewById(R.id.txt_qty)
        val rodentTxt: TextView = itemView.findViewById(R.id.txt_rodent)
        val priceTxt: TextView = itemView.findViewById(R.id.txt_price)
    }
}