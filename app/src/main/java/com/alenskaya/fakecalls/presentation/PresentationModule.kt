package com.alenskaya.fakecalls.presentation

import android.app.AlarmManager
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.alenskaya.fakecalls.presentation.execution.CallsScheduler
import com.alenskaya.fakecalls.presentation.execution.CallsNotificationManagerBuilder
import com.alenskaya.fakecalls.presentation.execution.ExecutionStrings
import com.alenskaya.fakecalls.presentation.execution.ExecutionStringsImpl
import com.alenskaya.fakecalls.presentation.main.calls.CallsStrings
import com.alenskaya.fakecalls.presentation.main.calls.CallsStringsImpl
import com.alenskaya.fakecalls.presentation.main.create.CreateStrings
import com.alenskaya.fakecalls.presentation.main.create.CreateStringsImpl
import com.alenskaya.fakecalls.presentation.main.home.HomeStrings
import com.alenskaya.fakecalls.presentation.main.home.HomeStringsImpl
import com.alenskaya.fakecalls.presentation.main.phonebook.PhonebookStrings
import com.alenskaya.fakecalls.presentation.main.phonebook.PhonebookStringsImpl
import com.alenskaya.fakecalls.presentation.navigation.ApplicationRouter
import com.alenskaya.fakecalls.presentation.phonebook.PhonebookContactsProvider
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PresentationModule {

    @Binds
    @Singleton
    abstract fun appStrings(impl: AppStringsImpl): AppStrings

    @Binds
    @Singleton
    abstract fun homeStrings(impl: HomeStringsImpl): HomeStrings

    @Binds
    @Singleton
    abstract fun createStrings(impl: CreateStringsImpl): CreateStrings

    @Binds
    @Singleton
    abstract fun phonebookStrings(impl: PhonebookStringsImpl): PhonebookStrings

    @Binds
    @Singleton
    abstract fun callStrings(impl: CallsStringsImpl): CallsStrings

    @Binds
    @Singleton
    abstract fun executionStrings(impl: ExecutionStringsImpl): ExecutionStrings

    internal companion object {

        /**
         * Provides firebase remote config object.
         * Is used for getting feature flags.
         */
        @Provides
        @Singleton
        fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
            return Firebase.remoteConfig.apply {
                setConfigSettingsAsync(remoteConfigSettings {
                    minimumFetchIntervalInSeconds = 3600
                })
            }
        }

        /**
         * Provides firebase analytics service.
         * Is used to send analytics events.
         */
        @Provides
        @Singleton
        fun provideFirebaseAnalytics() = Firebase.analytics

        /**
         * Provides calls notification manager.
         */
        @Provides
        @Singleton
        fun provideNotificationManager(@ApplicationContext context: Context): NotificationManagerCompat {
            return CallsNotificationManagerBuilder.build(context)
        }

        /**
         * Provides system alarm manager.
         */
        @Provides
        @Singleton
        fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
            return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        }

        /**
         * Provides calls scheduler.
         */
        @Provides
        @Singleton
        fun provideCallsScheduler(
            @ApplicationContext context: Context,
            alarmManager: AlarmManager
        ): CallsScheduler {
            return CallsScheduler(context, alarmManager)
        }

        /**
         * Provides application coil image loader, which caches images.
         */
        @Provides
        @Singleton
        fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
            return ImageLoader.Builder(context)
                .memoryCache {
                    MemoryCache.Builder(context)
                        .maxSizePercent(0.25)
                        .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(context.cacheDir.resolve("images_cache"))
                        .maxSizePercent(0.02)
                        .build()
                }
                .build()
        }

        @Provides
        @Singleton
        fun providePhonebookContactsRetriever(@ApplicationContext context: Context): PhonebookContactsProvider {
            return PhonebookContactsProvider(context.contentResolver)
        }

        /**
         * Provides coroutine scope for Application router.
         */
        @Singleton
        @Provides
        fun providesCoroutineScope(): CoroutineScope {
            return CoroutineScope(SupervisorJob() + Dispatchers.Default)
        }

        /**
         * Provides application router.
         */
        @Singleton
        @Provides
        fun providesApplicationRouter(coroutineScope: CoroutineScope) =
            ApplicationRouter(coroutineScope)

        /**
         * Provides dialogs displayer.
         */
        @Singleton
        @Provides
        fun providesDialogsDisplayer(coroutineScope: CoroutineScope) =
            DialogsDisplayer(coroutineScope)

        /**
         * Provides notifications permission manager.
         */
        @Provides
        @Singleton
        fun provideNotificationsPermissionManager(coroutineScope: CoroutineScope) =
            NotificationPermissionManager(coroutineScope)
    }
}
