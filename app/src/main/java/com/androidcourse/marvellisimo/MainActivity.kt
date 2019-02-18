package com.androidcourse.marvellisimo

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.androidcourse.marvellisimo.activities.character.CharacterListActivity
import com.androidcourse.marvellisimo.activities.comics.ComicsListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

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
                val intent = Intent(applicationContext, CharacterListActivity::class.java)
                this.startActivity(intent)
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
