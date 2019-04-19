package org.patrickf4664.fortune.viewer

import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.*
import android.widget.TextView

import org.patrickf4664.fortune.R
import org.patrickf4664.fortune.favorites.FavoritesManager

class QuoteAdapter(private val data: ArrayList<String>) : RecyclerView.Adapter<QuoteAdapter.SLViewHolder>() {

    var clickable = false
    lateinit var deleteEventListener: DeleteEventListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SLViewHolder {
        val textView = LayoutInflater.from(p0.context).inflate(R.layout.quote_list_row, p0, false) as View
        return SLViewHolder(textView, clickable)
    }

    override fun onBindViewHolder(p0: SLViewHolder, p1: Int) {
        p0.textView.text = data.get(p1)
        p0.fileNo = p1+1
        p0.deleteEventListener = deleteEventListener
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class SLViewHolder : RecyclerView.ViewHolder, View.OnCreateContextMenuListener {

        var parent : View
        var textView : TextView
        var fileNo = 1

        lateinit var deleteEventListener: DeleteEventListener

        constructor(row: View, clickable: Boolean) : super(row) {
            row.setOnCreateContextMenuListener(this)
            parent = row
            textView = row.findViewById(R.id.quote_list_dsp)

            if (clickable) {
                row.isClickable = true
                row.isFocusable = true

                val outVal = TypedValue()
                row.context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outVal, true)
                row.setBackgroundResource(outVal.resourceId)
            }
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            val deleteItem = menu!!.add(0, 1, 1, "Delete")
            deleteItem.setOnMenuItemClickListener(contextMenuEvent)
        }

        val contextMenuEvent = MenuItem.OnMenuItemClickListener {
            val id = it.itemId
            if (id != 1) {
                true
            }

            FavoritesManager.rmFile(fileNo)
            deleteEventListener.onDelete()

            true
        }
    }

    interface DeleteEventListener {
        fun onDelete()
    }
}