package com.fetch_assesment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


// Adapter to display the list of items through a recycler view
class ItemViewAdapter : RecyclerView.Adapter<ItemViewAdapter.GroupViewHolder>() {
    private var groups: List<Pair<Int, List<Item>>> = emptyList() // ListId is the key with a list of item names

    fun setData(newGroups: List<Map.Entry<Int, List<Item>>>) {
        groups = newGroups.map { it.key to it.value }
        notifyDataSetChanged() //  refresh UI
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val (listId, items) = groups[position]
        holder.bind(listId, items)
    }

    override fun getItemCount(): Int = groups.size

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val listIdHeader: TextView = itemView.findViewById(R.id.listIdHeader)
        private val itemsContainer: ViewGroup = itemView.findViewById(R.id.itemsContainer)

        fun bind(listId: Int, items: List<Item>) {
            listIdHeader.text = "List ID: $listId"
            itemsContainer.removeAllViews()

            items.forEach { item ->     // Display each item in a list view for the current listId
                val itemView = TextView(itemView.context).apply {
                    text = "  • ${item.name} (ID: ${item.id})"
                    setPadding(32, 8, 0, 8) // Format list structure with indentation
                }
                itemsContainer.addView(itemView)
            }
        }
    }
}