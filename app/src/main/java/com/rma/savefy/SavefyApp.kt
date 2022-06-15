package com.rma.savefy

import android.app.Application
import com.rma.savefy.di.repositoryModule
import com.rma.savefy.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SavefyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SavefyApp)
            modules(listOf(
                repositoryModule,
                viewModelModule
            ))
        }
    }
}