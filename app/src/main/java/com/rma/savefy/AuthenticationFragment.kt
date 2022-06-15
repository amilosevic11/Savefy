package com.rma.savefy

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rma.savefy.base.BaseFragment
import com.rma.savefy.databinding.FragmentAuthenticationBinding

class AuthenticationFragment : BaseFragment<FragmentAuthenticationBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAuthenticationBinding
        get() = FragmentAuthenticationBinding::inflate

    override fun setupUi() {
        Log.d("haha", "hehe")
    }
}