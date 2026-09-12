package com.navin.personalos.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LifeArea::class, Goal::class, Project::class, Milestone::class, Task::class, Reminder::class, RecurrenceRule::class, RecurrenceException::class, Habit::class, HabitEvent::class, Hobby::class, Skill::class, Session::class, JournalEntry::class, Idea::class, Memory::class, Chapter::class, ChapterItem::class, DailyReview::class, WeeklyReview::class, Tag::class, EntityLink::class, Attachment::class, TimelineEvent::class, EntityTagCrossRef::class, SearchDocument::class, SearchDocumentFts::class], version = 1, exportSchema = true)
@TypeConverters(EnumConverters::class)
abstract class PersonalDatabase : RoomDatabase() {
    abstract fun dao(): PersonalDao
}
