package com.example.learnandroid.ui.components.usersList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.learnandroid.R
import com.example.learnandroid.ui.lists.users.UsersListAdapter
import com.example.learnandroid.ui.utils.baseui.BaseFragment

class UsersList : BaseFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        fun newInstance() = UsersList()
    }

    val usersListViewModel: UsersListViewModel
        get() { return viewModel as UsersListViewModel }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(UsersListViewModel::class.java)
        super.onActivityCreated(savedInstanceState)

        viewManager = LinearLayoutManager(this.activity)
        initLiveData()
    }

    private fun initLiveData() {
        val dataObserver = Observer<UsersListLiveDataModel?> { dataModel ->
            setData(dataModel!!)
        }

        usersListViewModel.liveDataModel.observe(viewLifecycleOwner, dataObserver)
    }

    private fun setData(dataModel: UsersListLiveDataModel) {
        super.setOverlayVisiblity(dataModel.isLoading)

        viewAdapter = UsersListAdapter(dataModel.users)

        recyclerView = view!!.findViewById<RecyclerView>(R.id.usersList).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
