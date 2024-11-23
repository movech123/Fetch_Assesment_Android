package com.fetch_assesment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter to display the list of items through a recycler view
class ItemViewAdapter : RecyclerView.Adapter<ItemViewAdapter.GroupViewHolder>() {
    private var groups: List<Pair<Int, List<Item>>> = emptyList() // ListId is the key with a list of item names
    private val expandedPositions = mutableSetOf<Int>() // Track expanded positions

    fun setData(newGroups: List<Map.Entry<Int, List<Item>>>) {
        groups = newGroups.map { it.key to it.value }
        notifyDataSetChanged() // Refresh UI
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val (listId, items) = groups[position]
        val isExpanded = expandedPositions.contains(position)
        holder.bind(listId, items, isExpanded)

        holder.itemView.setOnClickListener {
            if (isExpanded) {
                expandedPositions.remove(position) // Expanding a collapsed item
            } else {
                expandedPositions.add(position)
            }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = groups.size

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val listIdHeader: TextView = itemView.findViewById(R.id.itemTitle)
        private val itemsContainer: ViewGroup = itemView.findViewById(R.id.itemDetails)
        private val dropdownIndicator: ImageView = itemView.findViewById(R.id.dropdownIndicator)

        fun bind(listId: Int, items: List<Item>, isExpanded: Boolean) {
            listIdHeader.text = "List ID: $listId"
            itemsContainer.removeAllViews()

            // Format and add child items
            items.forEach { item ->
                val itemView = TextView(itemView.context).apply {
                    text = "  • ${item.name} (ID: ${item.id})"
                    textSize = 16f
                    setPadding(32, 8, 0, 8)
                }
                itemsContainer.addView(itemView)
            }

            // Toggle visibility of item details based on expanded state
            itemsContainer.visibility = if (isExpanded) {
                View.VISIBLE
            } else View.GONE

            // Rotate the dropdown indicator once expanded or collapsed
            dropdownIndicator.rotation = if (isExpanded) {
                180f
            } else {
                0f
            }
        }
    }
}
