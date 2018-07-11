package com.binwin.studio.roomkotlindatabase.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.binwin.studio.roomkotlindatabase.model.Data
import com.binwin.studio.roomkotlindatabase.R
import kotlinx.android.synthetic.main.custom_contact.view.*

class RecyclerAdapter(val deleteclickListener: OnDeleteClick, val items: List<Data>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_contact, parent, false))
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items.get(position))
    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Data) {
            itemView.ids.text = item.id.toString()
            itemView.names.text = item.name
            itemView.ages.text = item.age.toString()
            itemView.delete.setOnClickListener {deleteclickListener.onDeleteClick(items,adapterPosition)
//                Toast.makeText(itemView.context,item.name+" deleted",Toast.LENGTH_SHORT).show()
             }
        }
    }

    interface OnDeleteClick {
        fun onDeleteClick(items:List<Data>, pos: Int)
    }
}