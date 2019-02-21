package com.androidcourse.marvellisimo.activities.comics

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.adapters.comics.ComicsListAdapter
import com.androidcourse.marvellisimo.dto.DataHandler
import com.androidcourse.marvellisimo.services.comics.ComicsServiceHandler
import kotlinx.android.synthetic.main.activity_comics_list.*

class ComicsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comics_list)

        var comics = DataHandler.comics!!

        comics_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        comics_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ComicsListAdapter(comics, context)
        }

    }
}
