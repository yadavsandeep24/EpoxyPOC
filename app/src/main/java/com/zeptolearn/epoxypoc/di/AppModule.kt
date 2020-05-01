package com.zeptolearn.epoxypoc.di

import android.app.Application
import androidx.room.Room
import com.zeptolearn.epoxypoc.api.EpoxyPocService
import com.zeptolearn.epoxypoc.db.AppDatabase
import com.zeptolearn.epoxypoc.db.dao.ContainersDao
import com.zeptolearn.epoxypoc.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideEpoxyPocService(): EpoxyPocService {
        return Retrofit.Builder()
            .baseUrl("http://alrn.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(EpoxyPocService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideContainerDao(db: AppDatabase): ContainersDao {
        return db.getContainerDao()
    }
}
