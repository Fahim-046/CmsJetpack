package com.example.cmsjetpack.di

import com.example.cmsjetpack.network.ApiClient
import com.example.cmsjetpack.network.api.UserInterface
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return ApiClient.retrofitInstance(moshi)
    }

    @Provides
    @Singleton
    fun provideUserApiInterface(retrofit: Retrofit): UserInterface {
        return retrofit.create(UserInterface::class.java)
    }
}
