package org.patrickf4664.fortune.viewer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import org.patrickf4664.fortune.R
import org.patrickf4664.fortune.StorageManager

class QuoteListViewer : AppCompatActivity(), QuoteAdapter.DeleteEventListener {

    var fileName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote_list_viewer)

        //Get the sent text
        fileName = intent.extras.getString("file_name")

        //Setup the toolbar
        val toolbar = findViewById<Toolbar>(R.id.qviewer_toolbar)
        toolbar.title = fileName
        setSupportActionBar(toolbar)

        //Setup our recycler view
        val quoteListView = findViewById<RecyclerView>(R.id.quotes_view)

        val layoutManager = LinearLayoutManager(this)
        quoteListView.layoutManager = layoutManager

        val data = StorageManager.loadFromFile(this, fileName)
        val adapter = QuoteAdapter(data)
        adapter.deleteEventListener = this
        quoteListView.adapter = adapter

        val divider = DividerItemDecoration(quoteListView.context, layoutManager.orientation)
        quoteListView.addItemDecoration(divider)
    }

    override fun onDelete() {
    }
}
