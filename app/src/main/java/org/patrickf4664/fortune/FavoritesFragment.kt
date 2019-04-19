package org.patrickf4664.fortune


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast

import org.patrickf4664.fortune.favorites.FavoritesManager
import org.patrickf4664.fortune.viewer.QuoteAdapter

class FavoritesFragment : Fragment(), QuoteAdapter.DeleteEventListener {

    private lateinit var listView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = view.findViewById(R.id.favorites_list)

        val layoutManager = LinearLayoutManager(this.context!!)
        listView.layoutManager = layoutManager

        loadList()

        val divider = DividerItemDecoration(listView.context, layoutManager.orientation)
        listView.addItemDecoration(divider)
    }

    override fun onDelete() {
        loadList()
    }

    fun loadList() {
        val data = FavoritesManager.loadFavQuotes()
        val listAdapter = QuoteAdapter(data)
        listAdapter.clickable = true
        listAdapter.deleteEventListener = this
        listView.adapter = listAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FavoritesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
