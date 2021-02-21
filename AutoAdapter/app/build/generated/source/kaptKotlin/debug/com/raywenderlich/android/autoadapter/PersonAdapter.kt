package com.raywenderlich.android.autoadapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import kotlin.Int
import kotlin.collections.List

class PersonAdapter(
  private val items: List<Person>
) : Adapter<PersonAdapter.ViewHolder>() {
  override fun getItemCount(): Int = items.size

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonAdapter.ViewHolder {
    val view = android.view.LayoutInflater.from(parent.context).inflate(2131361822, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(viewHolder: PersonAdapter.ViewHolder, position: Int) {
    viewHolder.bind(items[position])
  }

  class ViewHolder(
    itemView: View
  ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    fun bind(item: Person) {
      itemView.findViewById<TextView>(2131165330).text = item.name
      itemView.findViewById<TextView>(2131165248).text = item.address
    }
  }
}
