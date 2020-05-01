package com.zeptolearn.epoxypoc.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zeptolearn.epoxypoc.ui.main.PageViewModel
import com.zeptolearn.epoxypoc.viewmodel.EpoxyPocViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(PageViewModel::class)
    abstract fun bindPageViewModel(pageViewModel: PageViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: EpoxyPocViewModelFactory): ViewModelProvider.Factory
}
