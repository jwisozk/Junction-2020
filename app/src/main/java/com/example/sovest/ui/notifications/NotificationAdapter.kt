package com.example.sovest.ui.notifications

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sovest.R
import com.example.sovest.ui.notifications.Cell
import kotlinx.android.synthetic.main.cell.view.*

//import kotlinx.android.synthetic.main.trainee_fragment.view.*

class NotificationAdapter(
    var cellList: List<String>,
    private val inflater: LayoutInflater
) : RecyclerView.Adapter<ViewHolder>() {

//    fun submitList(newCellList: List<Cell>) {
//        cellList = newCellList
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.cell, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(cellList[position])

    override fun getItemCount(): Int = cellList.size
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(cell: String) {
        itemView.notifyCell.text = cell
        Log.d(this.toString(), cell)
    }
}