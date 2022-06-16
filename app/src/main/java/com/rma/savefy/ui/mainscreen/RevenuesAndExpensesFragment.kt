package com.rma.savefy.ui.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import com.rma.savefy.base.BaseFragment
import com.rma.savefy.databinding.FragmentRevenuesAndExpensesBinding

class RevenuesAndExpensesFragment : BaseFragment<FragmentRevenuesAndExpensesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRevenuesAndExpensesBinding
        get() = FragmentRevenuesAndExpensesBinding::inflate

    override fun setupUi() {

    }
}