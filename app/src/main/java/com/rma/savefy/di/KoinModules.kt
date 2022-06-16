package com.rma.savefy.di

import com.google.firebase.auth.FirebaseAuth
import com.rma.savefy.repos.FirebaseAuthRepository
import com.rma.savefy.ui.authentication.AuthenticationViewModel
import com.rma.savefy.ui.registration.RegistrationViewModel
import com.rma.savefy.ui.revenuesandexpenses.RevenuesAndExpensesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single { FirebaseAuthRepository(FirebaseAuth.getInstance()) }
}

val viewModelModule = module {
    viewModel { AuthenticationViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { RevenuesAndExpensesViewModel(get()) }
}