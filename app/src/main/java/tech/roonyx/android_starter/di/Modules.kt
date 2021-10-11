package tech.roonyx.android_starter.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tech.roonyx.android_starter.data.local.AppDatabase
import tech.roonyx.android_starter.data.remote.Api
import tech.roonyx.android_starter.data.remote.RetrofitFactory
import tech.roonyx.android_starter.repository.Repository
import tech.roonyx.android_starter.ui.main.MainViewModel
import tech.roonyx.android_starter.ui.paging.PagingViewModel

fun getAppModules(baseUrl: String) = listOf(
    networkModule(baseUrl),
    repositoryModule,
    interactorModule,
    viewModelModule,
    appModule
)

private val appModule = module {
    single { AppDatabase.create(androidContext()) }
}

private val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { PagingViewModel(get()) }
}

private val interactorModule = module {

}

private val repositoryModule = module {
    single { Repository(get()) }
}

private fun networkModule(baseUrl: String) = module {
    single { RetrofitFactory.create<Api>(baseUrl) }
}