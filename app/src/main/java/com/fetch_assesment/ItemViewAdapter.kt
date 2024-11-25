package com.fetch_assesment

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.widget.LinearLayout
import android.view.ViewGroup.MarginLayoutParams

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
            listIdHeader.textSize = 20f
            itemsContainer.removeAllViews()

            // Format and add child items
            items.forEach { item ->
                val itemCard = CardView(itemView.context).apply {
                    val params = MarginLayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 12, 12, 12) // Set margins here
                    layoutParams = params

                    setCardBackgroundColor(Color.LTGRAY)
                    radius = 32f
                    cardElevation = 4f
                }

                val itemContent = TextView(itemView.context).apply {
                    text = "ID: ${item.id}\nName: ${item.name}"
                    val spannable = SpannableString(text).apply {
                        setSpan(StyleSpan(Typeface.BOLD), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Bold "ID:"
                        setSpan(StyleSpan(Typeface.BOLD), text.indexOf("Name:"), text.indexOf("Name:") + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // Bold "Name:"
                    }
                    // Set the spannable string as add padding and styling
                    this.text = spannable
                    textSize = 14f
                    setPadding(12,12, 16, 12)
                    gravity = Gravity.CENTER_VERTICAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        150
                    )
                }

                itemCard.addView(itemContent)
                itemsContainer.addView(itemCard)
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
