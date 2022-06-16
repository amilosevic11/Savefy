package com.rma.savefy.ui.authentication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.rma.savefy.base.BaseFragment
import com.rma.savefy.databinding.FragmentAuthenticationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthenticationFragment : BaseFragment<FragmentAuthenticationBinding>() {

    private val authenticationViewModel: AuthenticationViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAuthenticationBinding
        get() = FragmentAuthenticationBinding::inflate

    override fun setupUi() {
        checkIsAlreadySignedIn()
        observeData()
        setOnClickListeners()
    }

    private fun checkIsAlreadySignedIn() {
        authenticationViewModel.isUserAlreadySignedIn()
    }

    private fun observeData() {
        authenticationViewModel.isUserSignedIn.observe(viewLifecycleOwner) {
            shouldShowProgressDialog(shouldShowProgress = false)
            if (it) {
                navigateToMainScreen()
            }
        }
    }

    private fun setOnClickListeners() {
        binding.mtvRegisterNow.setOnClickListener {
            navigateToRegistration()
        }

        binding.btnLogin.setOnClickListener {
            shouldShowProgressDialog(shouldShowProgress = true)
            authenticationViewModel.signIn(
                binding.textInputEditTextEmail.text.toString(),
                binding.textInputEditTextPassword.text.toString()
            )
        }
    }

    private fun navigateToMainScreen() {
        findNavController().navigate(
            AuthenticationFragmentDirections.actionAuthenticationFragmentToRevenuesAndExpensesFragment()
        )
    }

    private fun navigateToRegistration() {
        findNavController().navigate(
            AuthenticationFragmentDirections.actionAuthenticationFragmentToRegistrationFragment()
        )
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