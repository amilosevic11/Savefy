package com.rma.savefy.di

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.rma.savefy.data.room.EmailsDatabase
import com.rma.savefy.helpers.DB_NAME
import com.rma.savefy.repos.EmailsRepository
import com.rma.savefy.repos.FirebaseCloudStorageRepository
import com.rma.savefy.repos.FirebaseAuthRepository
import com.rma.savefy.repos.FirestoreRepository
import com.rma.savefy.ui.authentication.AuthenticationViewModel
import com.rma.savefy.ui.registration.RegistrationViewModel
import com.rma.savefy.ui.mainscreen.MainFragmentViewModel
import com.rma.savefy.ui.revenueandexpense.RevenueAndExpenseViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val roomDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            EmailsDatabase::class.java,
            DB_NAME
        ).build()
    }
    single { get<EmailsDatabase>().emailsDao() }
}

val repositoryModule = module {
    single { FirebaseAuthRepository(FirebaseAuth.getInstance()) }
    single { EmailsRepository(get()) }
    single { FirebaseCloudStorageRepository(FirebaseStorage.getInstance()) }
    single { FirestoreRepository(FirebaseFirestore.getInstance()) }
}

val viewModelModule = module {
    viewModel { AuthenticationViewModel(get(), get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { MainFragmentViewModel(get(), get(), get()) }
    viewModel { RevenueAndExpenseViewModel(get()) }
}