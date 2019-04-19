package org.patrickf4664.fortune

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

import org.patrickf4664.fortune.favorites.FavoritesManager

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //Our click interface for the toolbar
    //This is used to pass toolbar click events back to the home fragment
    interface ToolbarClickListener {
        fun favoritesClicked()
        fun shareClicked()
    }

    //Our fragments
    private lateinit var homeFragment: HomeFragment
    private lateinit var viewAllFragment: ViewAllFragment
    private lateinit var favoritesFragment: FavoritesFragment
    private lateinit var settingsFragment: SettingsFragment

    //Other variables
    private var showToolbarItems = true
    private lateinit var toolbarClickListener: ToolbarClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupDrawer()

        //Load the first quote
        StorageManager.loadAllQuotes(this)
        FavoritesManager.initFolders(this)

        //Load fragments
        initFragments()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (showToolbarItems) {
            menuInflater.inflate(R.menu.main_toolbar, menu)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_fav -> {
                toolbarClickListener.favoritesClicked()
                return true
            }

            R.id.action_share -> {
                toolbarClickListener.shareClicked()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        showFragment(item.itemId)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    //Used to set our click listener
    fun setOnToolbarClickedListener(listener: ToolbarClickListener) {
        toolbarClickListener = listener
    }

    //Hide/view fragments
    private fun showFragment(id: Int) {
        var ft = supportFragmentManager.beginTransaction()

        ft.hide(homeFragment)
        ft.hide(viewAllFragment)
        ft.hide(favoritesFragment)
        ft.hide(settingsFragment)
        showToolbarItems = false

        if (id == R.id.nav_home) {
            ft.show(homeFragment)
            showToolbarItems = true
        } else if (id == R.id.nav_view_all) {
            ft.show(viewAllFragment)
        } else if (id == R.id.nav_favorites) {
            favoritesFragment.loadList()
            ft.show(favoritesFragment)
        } else if (id == R.id.nav_settings) {
            ft.show(settingsFragment)
        }

        ft.commit()
        setupDrawer()
    }

    //Sets up all our fragments
    private fun initFragments() {
        homeFragment = HomeFragment.newInstance("","")
        viewAllFragment = ViewAllFragment.newInstance("","")
        favoritesFragment = FavoritesFragment.newInstance()
        settingsFragment = SettingsFragment.newInstance("","")

        var ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.fragment_view, homeFragment)
        ft.add(R.id.fragment_view, viewAllFragment)
        ft.add(R.id.fragment_view, favoritesFragment)
        ft.add(R.id.fragment_view, settingsFragment)
        ft.hide(viewAllFragment)
        ft.hide(favoritesFragment)
        ft.hide(settingsFragment)
        ft.commit()
    }

    private fun setupDrawer() {
        //Setup the toolbar
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        //Handles the navigation drawer
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }
}
