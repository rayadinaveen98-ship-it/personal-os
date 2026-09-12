package com.navin.personalos

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.navin.personalos.core.database.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.Clock
import javax.inject.Singleton

@HiltAndroidApp
class PersonalApplication : Application()

@Module
@InstallIn(SingletonComponent::class)
object PersonalModule {
    @Provides @Singleton fun database(@ApplicationContext context: Context): PersonalDatabase =
        Room.databaseBuilder(context, PersonalDatabase::class.java, "personal-os.db").build()
    @Provides fun dao(db: PersonalDatabase): PersonalDao = db.dao()
    @Provides fun clock(): Clock = Clock.systemDefaultZone()
}
