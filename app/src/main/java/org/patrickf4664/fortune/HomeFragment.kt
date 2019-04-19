package org.patrickf4664.fortune

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import org.patrickf4664.fortune.alarm.AlarmManager.Companion.setAlarm
import org.patrickf4664.fortune.favorites.FavoritesManager

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment(), MainActivity.ToolbarClickListener {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var quoteDsp : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        quoteDsp = view.findViewById(R.id.quote_dsp)
        quoteDsp.movementMethod = ScrollingMovementMethod()

        view.findViewById<Button>(R.id.reload_btn).setOnClickListener {
            loadQuote()
        }

        loadQuote()

        val parent = this.activity!! as MainActivity
        parent.setOnToolbarClickedListener(this)
    }

    //Called when the favorites item is clicked
    override fun favoritesClicked() {
        FavoritesManager.createFavorite(quoteDsp.text.toString())
        Toast.makeText(this.context!!, "Quote Saved", Toast.LENGTH_SHORT).show()
    }

    //Called when the share item is clicked
    override fun shareClicked() {
        val sender : Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, quoteDsp.text)
            type = "text/plain"
        }

        startActivity(Intent.createChooser(sender, "Share Quote"))
    }

    private fun loadQuote() {
        val quote = StorageManager.getQuote(this.context!!)
        if (quote.isEmpty()) {
            loadQuote()
        } else {
            quoteDsp.text = quote
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
