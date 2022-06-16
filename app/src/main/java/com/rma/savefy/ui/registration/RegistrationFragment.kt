package com.rma.savefy.ui.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rma.savefy.base.BaseFragment
import com.rma.savefy.databinding.FragmentRegistrationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>() {

    private val registrationViewModel: RegistrationViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegistrationBinding
        get() = FragmentRegistrationBinding::inflate

    override fun setupUi() {
        observeData()
        setOnClickListeners()
    }

    private fun observeData() {
        registrationViewModel.didCreateNewAccount.observe(viewLifecycleOwner) {
            if(it) {
                //TODO Show some message
                navigateToAuthentication()
            }
            else {
                //TODO Show some message
            }
        }
    }

    private fun setOnClickListeners() {
        binding.sivBack.setOnClickListener {
            navigateToAuthentication()
        }

        binding.btnRegister.setOnClickListener {
            registrationViewModel.createNewAccount(
                binding.textInputEditTextEmail.text.toString(),
                binding.textInputEditTextPassword.text.toString()
            )
        }
    }

    private fun navigateToAuthentication() {
        findNavController().navigateUp()
    }
}