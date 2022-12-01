package com.alenskaya.fakecalls.presentation

import android.app.AlarmManager
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.alenskaya.fakecalls.presentation.features.execution.CallsScheduler
import com.alenskaya.fakecalls.presentation.features.execution.CallsNotificationManagerBuilder
import com.alenskaya.fakecalls.presentation.navigation.ApplicationRouter
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

    internal companion object {

        @Provides
        @Singleton
        fun provideNotificationManager(@ApplicationContext context: Context): NotificationManagerCompat {
            return CallsNotificationManagerBuilder.build(context)
        }

        @Provides
        @Singleton
        fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
            return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        }

        @Provides
        @Singleton
        fun provideAlarmExecutor(
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
         * Provides notifier about changes in calls data.
         */
        @Singleton
        @Provides
        fun providesListOfCallsUpdatedNotifier() = CallsDataChangedNotifier()
    }
}
