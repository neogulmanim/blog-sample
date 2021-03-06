package net.dudmy.itemtouchhelper

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_artist.view.*

class MainAdapter(
        private val items: MutableList<Artist>,
        private val listener: ItemDragListener
) : RecyclerView.Adapter<MainAdapter.ViewHolder>(), ItemActionListener {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_artist, parent, false)
        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun onItemMoved(from: Int, to: Int) {
        if (from == to) {
            return
        }

        val fromItem = items.removeAt(from)
        items.add(to, fromItem)
        notifyItemMoved(from, to)
    }

    override fun onItemSwiped(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    class ViewHolder(itemView: View, listener: ItemDragListener) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.drag_handle.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    listener.onStartDrag(this)
                }
                false
            }
        }

        fun bind(item: Artist) {
            itemView.name.text = item.name
            itemView.agency.text = item.agency
            itemView.debut.text = item.debut
        }
    }
}