package com.rma.savefy.ui.revenueandexpense

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.rma.savefy.R
import com.rma.savefy.base.BaseFragment
import com.rma.savefy.databinding.FragmentRevenueAndExpenseBinding
import com.rma.savefy.helpers.initPopupMenu
import org.koin.androidx.viewmodel.ext.android.viewModel

class RevenueAndExpenseFragment: BaseFragment<FragmentRevenueAndExpenseBinding>(), PopupMenu.OnMenuItemClickListener {

    private val revenueAndExpenseViewModel: RevenueAndExpenseViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRevenueAndExpenseBinding
        get() = FragmentRevenueAndExpenseBinding::inflate

    override fun setupUi() {
        observeData()
        setOnClickListeners()
    }

    private fun observeData() {
        revenueAndExpenseViewModel.didUploadData.observe(viewLifecycleOwner) {
            shouldShowProgressDialog(false)
            if(it) {
                findNavController().navigateUp()
            }
        }
    }

    private fun setOnClickListeners() {
        binding.mbtnAdd.setOnClickListener {
            revenueAndExpenseViewModel.addRevenueOrExpense(
                type = binding.textInputEditTextType.text.toString(),
                amount = binding.textInputEditTextAmount.text.toString(),
                description = binding.textInputEditTextDescription.text.toString()
            )
            shouldShowProgressDialog(true)
        }

        binding.textInputEditTextDescription.setOnClickListener {
            initPopupMenu(requireContext(), it, R.menu.revenues_expenses, this)
        }

        binding.sivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun shouldShowProgressDialog(shouldShowProgress: Boolean) {
        if(shouldShowProgress) {
            binding.progressDialog.progressBarBg.visibility = View.VISIBLE
        }
        else {
            binding.progressDialog.progressBarBg.visibility = View.GONE
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.revenue -> binding.textInputEditTextDescription.setText(getString(R.string.revenue))
            else -> binding.textInputEditTextDescription.setText(getString(R.string.expense))
        }
        return true
    }
}