package com.gs.rm98703_98780.util

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.gs.rm98703_98780.R
import com.gs.rm98703_98780.model.ItemModel


class ItemsAdapter(private val onItemRemoved: (ItemModel) -> Unit) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private var items = listOf<ItemModel>()

    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val eventLocation = view.findViewById<TextView>(R.id.viewLocal)
        val eventType = view.findViewById<TextView>(R.id.viewTipo)
        val eventImpact = view.findViewById<TextView>(R.id.viewImpacto)
        val eventDate = view.findViewById<TextView>(R.id.viewData)
        val eventAffected = view.findViewById<TextView>(R.id.viewAfetados)
        val button = view.findViewById<ImageButton>(R.id.imageButton)

        fun bind(item: ItemModel) {
            eventLocation.text = item.local
            eventType.text = item.tipo
            eventImpact.text = item.impacto
            eventDate.text = item.data
            eventAffected.text = item.afetados.toString()

            button.setOnClickListener {
                onItemRemoved(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateItems(newItems: List<ItemModel>) {
        items = newItems
        notifyDataSetChanged()

    }

}