package com.navin.personalos.core.database

import androidx.room.*

@Dao
interface PersonalDao {
    @Query("SELECT * FROM LifeArea ORDER BY createdAt DESC") fun observeLifeArea(): kotlinx.coroutines.flow.Flow<List<LifeArea>>
    @Query("SELECT * FROM LifeArea") suspend fun allLifeArea(): List<LifeArea>
    @Query("SELECT * FROM LifeArea WHERE id = :id") suspend fun getLifeArea(id: String): LifeArea?
    @Upsert suspend fun put(value: LifeArea)
    @Query("DELETE FROM LifeArea WHERE id = :id") suspend fun deleteLifeArea(id: String)
    @Query("SELECT * FROM Goal ORDER BY createdAt DESC") fun observeGoal(): kotlinx.coroutines.flow.Flow<List<Goal>>
    @Query("SELECT * FROM Goal") suspend fun allGoal(): List<Goal>
    @Query("SELECT * FROM Goal WHERE id = :id") suspend fun getGoal(id: String): Goal?
    @Upsert suspend fun put(value: Goal)
    @Query("DELETE FROM Goal WHERE id = :id") suspend fun deleteGoal(id: String)
    @Query("SELECT * FROM Project ORDER BY createdAt DESC") fun observeProject(): kotlinx.coroutines.flow.Flow<List<Project>>
    @Query("SELECT * FROM Project") suspend fun allProject(): List<Project>
    @Query("SELECT * FROM Project WHERE id = :id") suspend fun getProject(id: String): Project?
    @Upsert suspend fun put(value: Project)
    @Query("DELETE FROM Project WHERE id = :id") suspend fun deleteProject(id: String)
    @Query("SELECT * FROM Milestone ORDER BY createdAt DESC") fun observeMilestone(): kotlinx.coroutines.flow.Flow<List<Milestone>>
    @Query("SELECT * FROM Milestone") suspend fun allMilestone(): List<Milestone>
    @Query("SELECT * FROM Milestone WHERE id = :id") suspend fun getMilestone(id: String): Milestone?
    @Upsert suspend fun put(value: Milestone)
    @Query("DELETE FROM Milestone WHERE id = :id") suspend fun deleteMilestone(id: String)
    @Query("SELECT * FROM Task ORDER BY createdAt DESC") fun observeTask(): kotlinx.coroutines.flow.Flow<List<Task>>
    @Query("SELECT * FROM Task") suspend fun allTask(): List<Task>
    @Query("SELECT * FROM Task WHERE id = :id") suspend fun getTask(id: String): Task?
    @Upsert suspend fun put(value: Task)
    @Query("DELETE FROM Task WHERE id = :id") suspend fun deleteTask(id: String)
    @Query("SELECT * FROM Reminder ORDER BY createdAt DESC") fun observeReminder(): kotlinx.coroutines.flow.Flow<List<Reminder>>
    @Query("SELECT * FROM Reminder") suspend fun allReminder(): List<Reminder>
    @Query("SELECT * FROM Reminder WHERE id = :id") suspend fun getReminder(id: String): Reminder?
    @Upsert suspend fun put(value: Reminder)
    @Query("DELETE FROM Reminder WHERE id = :id") suspend fun deleteReminder(id: String)
    @Query("SELECT * FROM RecurrenceRule ORDER BY createdAt DESC") fun observeRecurrenceRule(): kotlinx.coroutines.flow.Flow<List<RecurrenceRule>>
    @Query("SELECT * FROM RecurrenceRule") suspend fun allRecurrenceRule(): List<RecurrenceRule>
    @Query("SELECT * FROM RecurrenceRule WHERE id = :id") suspend fun getRecurrenceRule(id: String): RecurrenceRule?
    @Upsert suspend fun put(value: RecurrenceRule)
    @Query("DELETE FROM RecurrenceRule WHERE id = :id") suspend fun deleteRecurrenceRule(id: String)
    @Query("SELECT * FROM RecurrenceException ORDER BY createdAt DESC") fun observeRecurrenceException(): kotlinx.coroutines.flow.Flow<List<RecurrenceException>>
    @Query("SELECT * FROM RecurrenceException") suspend fun allRecurrenceException(): List<RecurrenceException>
    @Query("SELECT * FROM RecurrenceException WHERE id = :id") suspend fun getRecurrenceException(id: String): RecurrenceException?
    @Upsert suspend fun put(value: RecurrenceException)
    @Query("DELETE FROM RecurrenceException WHERE id = :id") suspend fun deleteRecurrenceException(id: String)
    @Query("SELECT * FROM Habit ORDER BY createdAt DESC") fun observeHabit(): kotlinx.coroutines.flow.Flow<List<Habit>>
    @Query("SELECT * FROM Habit") suspend fun allHabit(): List<Habit>
    @Query("SELECT * FROM Habit WHERE id = :id") suspend fun getHabit(id: String): Habit?
    @Upsert suspend fun put(value: Habit)
    @Query("DELETE FROM Habit WHERE id = :id") suspend fun deleteHabit(id: String)
    @Query("SELECT * FROM HabitEvent ORDER BY createdAt DESC") fun observeHabitEvent(): kotlinx.coroutines.flow.Flow<List<HabitEvent>>
    @Query("SELECT * FROM HabitEvent") suspend fun allHabitEvent(): List<HabitEvent>
    @Query("SELECT * FROM HabitEvent WHERE id = :id") suspend fun getHabitEvent(id: String): HabitEvent?
    @Upsert suspend fun put(value: HabitEvent)
    @Query("DELETE FROM HabitEvent WHERE id = :id") suspend fun deleteHabitEvent(id: String)
    @Query("SELECT * FROM Hobby ORDER BY createdAt DESC") fun observeHobby(): kotlinx.coroutines.flow.Flow<List<Hobby>>
    @Query("SELECT * FROM Hobby") suspend fun allHobby(): List<Hobby>
    @Query("SELECT * FROM Hobby WHERE id = :id") suspend fun getHobby(id: String): Hobby?
    @Upsert suspend fun put(value: Hobby)
    @Query("DELETE FROM Hobby WHERE id = :id") suspend fun deleteHobby(id: String)
    @Query("SELECT * FROM Skill ORDER BY createdAt DESC") fun observeSkill(): kotlinx.coroutines.flow.Flow<List<Skill>>
    @Query("SELECT * FROM Skill") suspend fun allSkill(): List<Skill>
    @Query("SELECT * FROM Skill WHERE id = :id") suspend fun getSkill(id: String): Skill?
    @Upsert suspend fun put(value: Skill)
    @Query("DELETE FROM Skill WHERE id = :id") suspend fun deleteSkill(id: String)
    @Query("SELECT * FROM Session ORDER BY createdAt DESC") fun observeSession(): kotlinx.coroutines.flow.Flow<List<Session>>
    @Query("SELECT * FROM Session") suspend fun allSession(): List<Session>
    @Query("SELECT * FROM Session WHERE id = :id") suspend fun getSession(id: String): Session?
    @Upsert suspend fun put(value: Session)
    @Query("DELETE FROM Session WHERE id = :id") suspend fun deleteSession(id: String)
    @Query("SELECT * FROM JournalEntry ORDER BY createdAt DESC") fun observeJournalEntry(): kotlinx.coroutines.flow.Flow<List<JournalEntry>>
    @Query("SELECT * FROM JournalEntry") suspend fun allJournalEntry(): List<JournalEntry>
    @Query("SELECT * FROM JournalEntry WHERE id = :id") suspend fun getJournalEntry(id: String): JournalEntry?
    @Upsert suspend fun put(value: JournalEntry)
    @Query("DELETE FROM JournalEntry WHERE id = :id") suspend fun deleteJournalEntry(id: String)
    @Query("SELECT * FROM Idea ORDER BY createdAt DESC") fun observeIdea(): kotlinx.coroutines.flow.Flow<List<Idea>>
    @Query("SELECT * FROM Idea") suspend fun allIdea(): List<Idea>
    @Query("SELECT * FROM Idea WHERE id = :id") suspend fun getIdea(id: String): Idea?
    @Upsert suspend fun put(value: Idea)
    @Query("DELETE FROM Idea WHERE id = :id") suspend fun deleteIdea(id: String)
    @Query("SELECT * FROM Memory ORDER BY createdAt DESC") fun observeMemory(): kotlinx.coroutines.flow.Flow<List<Memory>>
    @Query("SELECT * FROM Memory") suspend fun allMemory(): List<Memory>
    @Query("SELECT * FROM Memory WHERE id = :id") suspend fun getMemory(id: String): Memory?
    @Upsert suspend fun put(value: Memory)
    @Query("DELETE FROM Memory WHERE id = :id") suspend fun deleteMemory(id: String)
    @Query("SELECT * FROM Chapter ORDER BY createdAt DESC") fun observeChapter(): kotlinx.coroutines.flow.Flow<List<Chapter>>
    @Query("SELECT * FROM Chapter") suspend fun allChapter(): List<Chapter>
    @Query("SELECT * FROM Chapter WHERE id = :id") suspend fun getChapter(id: String): Chapter?
    @Upsert suspend fun put(value: Chapter)
    @Query("DELETE FROM Chapter WHERE id = :id") suspend fun deleteChapter(id: String)
    @Query("SELECT * FROM ChapterItem ORDER BY createdAt DESC") fun observeChapterItem(): kotlinx.coroutines.flow.Flow<List<ChapterItem>>
    @Query("SELECT * FROM ChapterItem") suspend fun allChapterItem(): List<ChapterItem>
    @Query("SELECT * FROM ChapterItem WHERE id = :id") suspend fun getChapterItem(id: String): ChapterItem?
    @Upsert suspend fun put(value: ChapterItem)
    @Query("DELETE FROM ChapterItem WHERE id = :id") suspend fun deleteChapterItem(id: String)
    @Query("SELECT * FROM DailyReview ORDER BY createdAt DESC") fun observeDailyReview(): kotlinx.coroutines.flow.Flow<List<DailyReview>>
    @Query("SELECT * FROM DailyReview") suspend fun allDailyReview(): List<DailyReview>
    @Query("SELECT * FROM DailyReview WHERE id = :id") suspend fun getDailyReview(id: String): DailyReview?
    @Upsert suspend fun put(value: DailyReview)
    @Query("DELETE FROM DailyReview WHERE id = :id") suspend fun deleteDailyReview(id: String)
    @Query("SELECT * FROM WeeklyReview ORDER BY createdAt DESC") fun observeWeeklyReview(): kotlinx.coroutines.flow.Flow<List<WeeklyReview>>
    @Query("SELECT * FROM WeeklyReview") suspend fun allWeeklyReview(): List<WeeklyReview>
    @Query("SELECT * FROM WeeklyReview WHERE id = :id") suspend fun getWeeklyReview(id: String): WeeklyReview?
    @Upsert suspend fun put(value: WeeklyReview)
    @Query("DELETE FROM WeeklyReview WHERE id = :id") suspend fun deleteWeeklyReview(id: String)
    @Query("SELECT * FROM Tag ORDER BY createdAt DESC") fun observeTag(): kotlinx.coroutines.flow.Flow<List<Tag>>
    @Query("SELECT * FROM Tag") suspend fun allTag(): List<Tag>
    @Query("SELECT * FROM Tag WHERE id = :id") suspend fun getTag(id: String): Tag?
    @Upsert suspend fun put(value: Tag)
    @Query("DELETE FROM Tag WHERE id = :id") suspend fun deleteTag(id: String)
    @Query("SELECT * FROM EntityLink ORDER BY createdAt DESC") fun observeEntityLink(): kotlinx.coroutines.flow.Flow<List<EntityLink>>
    @Query("SELECT * FROM EntityLink") suspend fun allEntityLink(): List<EntityLink>
    @Query("SELECT * FROM EntityLink WHERE id = :id") suspend fun getEntityLink(id: String): EntityLink?
    @Upsert suspend fun put(value: EntityLink)
    @Query("DELETE FROM EntityLink WHERE id = :id") suspend fun deleteEntityLink(id: String)
    @Query("SELECT * FROM Attachment ORDER BY createdAt DESC") fun observeAttachment(): kotlinx.coroutines.flow.Flow<List<Attachment>>
    @Query("SELECT * FROM Attachment") suspend fun allAttachment(): List<Attachment>
    @Query("SELECT * FROM Attachment WHERE id = :id") suspend fun getAttachment(id: String): Attachment?
    @Upsert suspend fun put(value: Attachment)
    @Query("DELETE FROM Attachment WHERE id = :id") suspend fun deleteAttachment(id: String)
    @Query("SELECT * FROM TimelineEvent ORDER BY createdAt DESC") fun observeTimelineEvent(): kotlinx.coroutines.flow.Flow<List<TimelineEvent>>
    @Query("SELECT * FROM TimelineEvent") suspend fun allTimelineEvent(): List<TimelineEvent>
    @Query("SELECT * FROM TimelineEvent WHERE id = :id") suspend fun getTimelineEvent(id: String): TimelineEvent?
    @Upsert suspend fun put(value: TimelineEvent)
    @Query("DELETE FROM TimelineEvent WHERE id = :id") suspend fun deleteTimelineEvent(id: String)
    @Upsert suspend fun put(value: EntityTagCrossRef)
    @Upsert suspend fun put(value: SearchDocument)
    @Query("SELECT * FROM SearchDocument WHERE entityType = :type AND entityId = :id") suspend fun document(type: EntityType, id: String): SearchDocument?
    @Query("DELETE FROM SearchDocument WHERE entityType = :type AND entityId = :id") suspend fun removeDocument(type: EntityType, id: String)
    @Query("SELECT SearchDocument.* FROM SearchDocument JOIN SearchDocumentFts ON SearchDocument.rowId = SearchDocumentFts.rowid WHERE SearchDocumentFts MATCH :query ORDER BY SearchDocument.updatedAt DESC LIMIT :limit OFFSET :offset") suspend fun search(query: String, limit: Int = 100, offset: Int = 0): List<SearchDocument>
    @Query("DELETE FROM EntityTagCrossRef WHERE entityType = :type AND entityId = :id") suspend fun removeTags(type: EntityType, id: String)
    @Query("SELECT * FROM EntityTagCrossRef") suspend fun allTags(): List<EntityTagCrossRef>
    @Query("DELETE FROM SearchDocument") suspend fun clearIndex()
    @Query("SELECT * FROM EntityTagCrossRef") fun observeTagLinks(): kotlinx.coroutines.flow.Flow<List<EntityTagCrossRef>>
    @Delete suspend fun remove(value: EntityTagCrossRef)

}
