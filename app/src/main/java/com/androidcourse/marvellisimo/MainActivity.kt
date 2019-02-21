package com.androidcourse.marvellisimo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.androidcourse.marvellisimo.activities.character.CharacterListActivity
import com.androidcourse.marvellisimo.activities.comics.ComicsListActivity
import com.androidcourse.marvellisimo.dto.DataHandler
import com.androidcourse.marvellisimo.fragments.CharacterListFragment
import com.androidcourse.marvellisimo.services.character.CharacterServiceHandler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_character_list.*

class MainActivity : AppCompatActivity()  {
    private var characterListFragment : Fragment = CharacterListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "This will be a search function...", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        DataHandler.initializeData()

//        characterListFragment = CharacterListFragment()
    }

    private fun initializeData() {
        CharacterServiceHandler.getAllCharacters()
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
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, characterListFragment)
                    .addToBackStack(characterListFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()


//                val intent = Intent(applicationContext, CharacterListActivity::class.java)
//                this.startActivity(intent)
                return true
            }
            R.id.action_comics -> {
                val intent = Intent(applicationContext, ComicsListActivity::class.java)
                this.startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
