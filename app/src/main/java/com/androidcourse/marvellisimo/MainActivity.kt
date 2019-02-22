package com.androidcourse.marvellisimo

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.androidcourse.marvellisimo.activities.comics.ComicsListActivity
import com.androidcourse.marvellisimo.dto.DataHandler
import com.androidcourse.marvellisimo.fragments.characters.CharacterListFragment
import com.androidcourse.marvellisimo.fragments.comics.ComicsListFragment
import com.androidcourse.marvellisimo.helpers.FragmentHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FragmentHandler {

    private var characterListFragment: Fragment =
        CharacterListFragment()
    private var comicsListFragment: Fragment = ComicsListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Initialize data...
        DataHandler.initializeData()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "This will be a search function...", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_characters -> {
                return setFragment(characterListFragment)
            }
            R.id.action_comics -> {
                return setFragment(comicsListFragment)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setNextFragment(fragment: Fragment) {
        setFragment(fragment)
    }

    private fun setFragment(fragment: Fragment): Boolean {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(fragment.toString())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
        return true
    }


}
