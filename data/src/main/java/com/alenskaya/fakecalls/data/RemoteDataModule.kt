package com.alenskaya.fakecalls.data

import com.alenskaya.fakecalls.data.remote.FakeContactApiRepository
import com.alenskaya.fakecalls.data.remote.contacts.api.FakeContactApi
import com.alenskaya.fakecalls.domain.contacts.FakeContactRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
private annotation class BaseUrl

/**
 * DI for remote data sources
 */
@Module
@InstallIn(SingletonComponent::class)
internal abstract class RemoteDataModule {

    @Binds
    @Singleton
    abstract fun fakeContactsRepository(impl: FakeContactApiRepository): FakeContactRepository

    companion object {
        @Provides
        @Singleton
        fun provideApi(retrofit: Retrofit): FakeContactApi =
            retrofit.create(FakeContactApi::class.java)

        @Provides
        @Singleton
        fun provideRetrofit(
            moshi: Moshi,
            okHttpClient: OkHttpClient,
            @BaseUrl
            baseUrl: String
        ): Retrofit = Retrofit.Builder().apply {
            addConverterFactory(MoshiConverterFactory.create(moshi))
            client(okHttpClient)
            baseUrl(baseUrl)
        }.build()

        @Provides
        @Singleton
        fun provideMoshi(): Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
        }.build()

        @Provides
        @BaseUrl
        fun provideBaseUrl() = "https://randomuser.me/api/"
    }
}
