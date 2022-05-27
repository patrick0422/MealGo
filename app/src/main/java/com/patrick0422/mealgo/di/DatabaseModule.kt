package com.patrick0422.mealgo.di

import android.content.Context
import androidx.room.Room
import com.patrick0422.mealgo.data.local.MealDatabase
import com.patrick0422.mealgo.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideMealDao(mealDatabase: MealDatabase) = mealDatabase.mealDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        MealDatabase::class.java,
        DATABASE_NAME
    ).build()
}