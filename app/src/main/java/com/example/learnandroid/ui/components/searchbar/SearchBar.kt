package com.example.learnandroid.ui.components.searchbar

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.learnandroid.R
import com.example.learnandroid.ui.utils.baseui.BaseFragment
import kotlinx.android.synthetic.main.search_bar_fragment.*

class SearchBar : BaseFragment() {

    companion object {
        fun newInstance() = SearchBar()
    }

    val searchBarViewModel: SearchBarViewModel
        get() { return viewModel as SearchBarViewModel }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_bar_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(SearchBarViewModel::class.java)
        super.onActivityCreated(savedInstanceState)

        setSearchKeyTextListener()
    }

    private fun setSearchKeyTextListener() {
        searchKeyText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                searchBarViewModel.postKey(s.toString())
            }
        })
    }
}
