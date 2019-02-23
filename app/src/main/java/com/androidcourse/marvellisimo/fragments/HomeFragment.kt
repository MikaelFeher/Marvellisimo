package com.androidcourse.marvellisimo.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch

import com.androidcourse.marvellisimo.R
import com.androidcourse.marvellisimo.dto.DataHandler
import com.androidcourse.marvellisimo.helpers.FragmentHandler
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private var viewItem: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var input = et_search_input
        var rbCharacters = rb_search_type_characters
        var rbComics = rb_search_type_comics
        var radioGroup = rg_search_radio_button_group
        var btnSearch = btn_submit

        btnSearch.setOnClickListener {
//            search(input, switch)

            println("RadioGroup: ${radioGroup.checkedRadioButtonId}")


//            var context: FragmentHandler = it.context as FragmentHandler
//            if (switch.isActivated) {
//                context.setNextFragment(SearchResultFragment.create(true))
//
//            } else {
//                context.setNextFragment(SearchResultFragment.create(false))
//            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewItem = inflater.inflate(R.layout.fragment_home, container, false)
        return viewItem
    }

//    fun search(input: EditText) {
//        var inputValue = input.text
//        if () {
//            DataHandler.findCharacter(inputValue.toString())
//        } else {
//            DataHandler.findComic(inputValue.toString())
//        }
//    }
}