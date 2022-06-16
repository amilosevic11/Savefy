package com.rma.savefy.ui.revenuesandexpenses

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.rma.savefy.base.BaseFragment
import com.rma.savefy.databinding.FragmentRevenuesAndExpensesBinding
import com.rma.savefy.helpers.initPopupMenu
import org.koin.androidx.viewmodel.ext.android.viewModel

class RevenuesAndExpensesFragment : BaseFragment<FragmentRevenuesAndExpensesBinding>(), PopupMenu.OnMenuItemClickListener {

    private val revenuesAndExpensesViewModel: RevenuesAndExpensesViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRevenuesAndExpensesBinding
        get() = FragmentRevenuesAndExpensesBinding::inflate

    override fun setupUi() {
        observeData()
        setOnClickListeners()
    }

    private fun observeData() {
        revenuesAndExpensesViewModel.isUserSignedOut.observe(viewLifecycleOwner) {
            navigateToAuthentication()
        }
    }

    private fun setOnClickListeners() {
        binding.sivMenu.setOnClickListener {
            initPopupMenu(it, requireContext(), this)
        }

        binding.sivAvatar.setOnClickListener {
            checkForPermissions()
        }
    }

    private fun checkForPermissions() {
        
    }

    private fun navigateToAuthentication() {
        findNavController().navigate(
            RevenuesAndExpensesFragmentDirections.actionRevenuesAndExpensesFragmentToAuthenticationFragment()
        )
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        revenuesAndExpensesViewModel.signOut()
        return true
    }
}