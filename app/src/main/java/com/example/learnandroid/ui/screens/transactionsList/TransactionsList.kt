package com.example.learnandroid.ui.screens.transactionsList

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.learnandroid.R
import com.example.learnandroid.ui.lists.transactions.TransactionsListAdapter
import com.example.learnandroid.ui.screens.login.LoginViewModel
import com.example.learnandroid.ui.screens.registration.RegistrationLiveDataModel
import com.example.learnandroid.ui.utils.baseui.BaseFragment
import kotlinx.android.synthetic.main.transactions_list_fragment.*

class TransactionsList : BaseFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        fun newInstance() = TransactionsList()
    }

    private val transactionsViewModel: TransactionsListViewModel
        get() { return viewModel as TransactionsListViewModel }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.transactions_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(TransactionsListViewModel::class.java)

        super.onActivityCreated(savedInstanceState)

        viewManager = LinearLayoutManager(this.activity)

        setNewTransactionButtonAction()
        initLiveData()

        transactionsViewModel.getTransactions()
    }

    private fun setNewTransactionButtonAction() {
        newTransactionButton?.setOnClickListener {
            transactionsViewModel.navigateToSelectUser()
        }
    }

    private fun initLiveData() {
        val dataObserver = Observer<TransactionsListLiveDataModel?> { dataModel ->
            setData(dataModel!!)
        }

        transactionsViewModel.liveDataModel.observe(viewLifecycleOwner, dataObserver)
    }

    private fun setData(dataModel: TransactionsListLiveDataModel) {
        viewAdapter = TransactionsListAdapter(dataModel.transactions.toTypedArray())

        recyclerView = view!!.findViewById<RecyclerView>(R.id.transactionsList).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

}
