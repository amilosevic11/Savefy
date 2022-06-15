package com.rma.savefy.ui.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rma.savefy.base.BaseFragment
import com.rma.savefy.databinding.FragmentRegistrationBinding

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegistrationBinding
        get() = FragmentRegistrationBinding::inflate

    override fun setupUi() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.sivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}