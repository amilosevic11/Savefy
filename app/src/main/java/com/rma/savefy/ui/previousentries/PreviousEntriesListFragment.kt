package com.rma.savefy.ui.previousentries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rma.savefy.base.BaseFragment
import com.rma.savefy.databinding.FragmentPreviousEntriesListBinding
import com.rma.savefy.models.Results
import com.rma.savefy.ui.mainscreen.MainFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PreviousEntriesListFragment : BaseFragment<FragmentPreviousEntriesListBinding>(), ResultsRecyclerAdapter.OnItemClickListener {

    private val mainFragmentViewModel: MainFragmentViewModel by viewModel()
    private lateinit var recyclerAdapter: ResultsRecyclerAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPreviousEntriesListBinding
        get() = FragmentPreviousEntriesListBinding::inflate

    override fun setupUi() {
        shouldShowProgressDialog(true)
        setupRecyclerView()
        observeData()
        getAllData()
        setOnClickListeners()
    }

    private fun observeData() {
        mainFragmentViewModel.didFetchAllData.observe(viewLifecycleOwner) {
            recyclerAdapter.addData(it)
        }
        shouldShowProgressDialog(false)
    }

    private fun getAllData() {
        mainFragmentViewModel.getAllDataAsList()
    }

    private fun setOnClickListeners() {
        binding.sivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        recyclerAdapter = ResultsRecyclerAdapter(this)
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }
    }

    override fun onItemClick(result: Results) {

    }

    private fun shouldShowProgressDialog(shouldShowProgress: Boolean) {
        if(shouldShowProgress) {
            binding.progressDialog.progressBarBg.visibility = View.VISIBLE
        }
        else {
            binding.progressDialog.progressBarBg.visibility = View.GONE
        }
    }
}