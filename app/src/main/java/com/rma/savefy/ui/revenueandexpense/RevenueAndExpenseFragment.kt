package com.rma.savefy.ui.revenueandexpense

import android.view.LayoutInflater
import android.view.ViewGroup
import com.rma.savefy.base.BaseFragment
import com.rma.savefy.databinding.FragmentRevenueAndExpenseBinding

class RevenueAndExpenseFragment: BaseFragment<FragmentRevenueAndExpenseBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRevenueAndExpenseBinding
        get() = FragmentRevenueAndExpenseBinding::inflate

    override fun setupUi() {
        TODO("Not yet implemented")
    }
}