package com.rma.savefy.ui.authentication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rma.savefy.base.BaseFragment
import com.rma.savefy.databinding.FragmentAuthenticationBinding

class AuthenticationFragment : BaseFragment<FragmentAuthenticationBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAuthenticationBinding
        get() = FragmentAuthenticationBinding::inflate

    override fun setupUi() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.mtvRegisterNow.setOnClickListener {
            navigateToRegistration()
        }
    }

    private fun navigateToRegistration() {
        findNavController().navigate(AuthenticationFragmentDirections.actionAuthenticationFragmentToRegistrationFragment())
    }
}