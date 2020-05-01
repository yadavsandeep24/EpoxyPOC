package com.zeptolearn.epoxypoc.di

import com.zeptolearn.epoxypoc.ui.main.PlaceholderFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributePlaceHolderFragment(): PlaceholderFragment
}
