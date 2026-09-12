package com.navin.personalos.core.database

import androidx.room.*
import java.util.UUID

@Entity(tableName = "LifeArea", indices = [Index("status")])
data class LifeArea(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String? = null,
    val iconKey: String? = null,
    val accentKey: String? = null,
    val status: LifeAreaStatus = LifeAreaStatus.ACTIVE,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "Goal", indices = [Index("status"), Index("targetDate"), Index("lifeAreaId")], foreignKeys = [ForeignKey(entity = LifeArea::class, parentColumns = ["id"], childColumns = ["lifeAreaId"], onDelete = ForeignKey.SET_NULL)])
data class Goal(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val whyItMatters: String? = null,
    val successDefinition: String? = null,
    val status: GoalStatus = GoalStatus.ACTIVE,
    val targetDate: String? = null,
    val measurementType: MeasurementType = MeasurementType.NONE,
    val currentValue: Double? = null,
    val targetValue: Double? = null,
    val unit: String? = null,
    val lifeAreaId: String? = null,
    val achievedAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "Project", indices = [Index("status"), Index("lifeAreaId"), Index("goalId"), Index("targetDate")], foreignKeys = [ForeignKey(entity = LifeArea::class, parentColumns = ["id"], childColumns = ["lifeAreaId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = Goal::class, parentColumns = ["id"], childColumns = ["goalId"], onDelete = ForeignKey.SET_NULL)])
data class Project(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String? = null,
    val status: ProjectStatus = ProjectStatus.ACTIVE,
    val lifeAreaId: String? = null,
    val goalId: String? = null,
    val targetDate: String? = null,
    val completedAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "Milestone", indices = [Index("projectId"), Index("goalId"), Index("targetDate"), Index("status")], foreignKeys = [ForeignKey(entity = Project::class, parentColumns = ["id"], childColumns = ["projectId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = Goal::class, parentColumns = ["id"], childColumns = ["goalId"], onDelete = ForeignKey.SET_NULL)])
data class Milestone(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val projectId: String? = null,
    val goalId: String? = null,
    val orderIndex: Int = 0,
    val targetDate: String? = null,
    val status: MilestoneStatus = MilestoneStatus.OPEN,
    val completedAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "Task", indices = [Index("status"), Index("dueAt"), Index("dueLocalDate"), Index("projectId"), Index("goalId"), Index("lifeAreaId"), Index("parentTaskId"), Index("recurrenceRuleId"), Index("pinnedFocus")], foreignKeys = [ForeignKey(entity = Project::class, parentColumns = ["id"], childColumns = ["projectId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = Goal::class, parentColumns = ["id"], childColumns = ["goalId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = LifeArea::class, parentColumns = ["id"], childColumns = ["lifeAreaId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = Task::class, parentColumns = ["id"], childColumns = ["parentTaskId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = RecurrenceRule::class, parentColumns = ["id"], childColumns = ["recurrenceRuleId"], onDelete = ForeignKey.SET_NULL)])
data class Task(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val notes: String? = null,
    val status: TaskStatus = TaskStatus.OPEN,
    val priority: Priority = Priority.NORMAL,
    val dueAt: Long? = null,
    val dueLocalDate: String? = null,
    val projectId: String? = null,
    val goalId: String? = null,
    val lifeAreaId: String? = null,
    val parentTaskId: String? = null,
    val recurrenceRuleId: String? = null,
    val pinnedFocus: Boolean = false,
    val orderIndex: Int? = null,
    val completedAt: Long? = null,
    val occurrenceLocalDate: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "Reminder", indices = [Index("triggerAt"), Index("recurrenceRuleId"), Index("state"), Index(value = ["ownerType", "ownerId"])], foreignKeys = [ForeignKey(entity = RecurrenceRule::class, parentColumns = ["id"], childColumns = ["recurrenceRuleId"], onDelete = ForeignKey.SET_NULL)])
data class Reminder(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val ownerType: OwnerType = OwnerType.STANDALONE,
    val ownerId: String? = null,
    val title: String,
    val triggerAt: Long? = null,
    val localTimeMinutes: Int? = null,
    val recurrenceRuleId: String? = null,
    val state: ReminderState = ReminderState.SCHEDULED,
    val deliveryState: DeliveryState = DeliveryState.PENDING,
    val exactRequired: Boolean = true,
    val deliveredAt: Long? = null,
    val scheduledAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "RecurrenceRule", indices = [Index("startLocalDate")])
data class RecurrenceRule(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val frequency: Frequency = Frequency.DAILY,
    val interval: Int = 1,
    val weekdaysMask: Int? = null,
    val dayOfMonth: Int? = null,
    val monthOfYear: Int? = null,
    val localTimeMinutes: Int? = null,
    val startLocalDate: String,
    val endLocalDate: String? = null,
    val occurrenceCountLimit: Int? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "RecurrenceException", indices = [Index("recurrenceRuleId"), Index(value = ["recurrenceRuleId", "occurrenceLocalDate"], unique = true)], foreignKeys = [ForeignKey(entity = RecurrenceRule::class, parentColumns = ["id"], childColumns = ["recurrenceRuleId"], onDelete = ForeignKey.CASCADE)])
data class RecurrenceException(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val recurrenceRuleId: String,
    val occurrenceLocalDate: String,
    val type: ExceptionType = ExceptionType.SKIP,
    val overrideDueAt: Long? = null,
    val overrideTitle: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "Habit", indices = [Index("status"), Index("scheduleRuleId"), Index("goalId"), Index("lifeAreaId")], foreignKeys = [ForeignKey(entity = RecurrenceRule::class, parentColumns = ["id"], childColumns = ["scheduleRuleId"], onDelete = ForeignKey.RESTRICT), ForeignKey(entity = Goal::class, parentColumns = ["id"], childColumns = ["goalId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = LifeArea::class, parentColumns = ["id"], childColumns = ["lifeAreaId"], onDelete = ForeignKey.SET_NULL)])
data class Habit(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String? = null,
    val status: ActiveStatus = ActiveStatus.ACTIVE,
    val scheduleRuleId: String,
    val targetType: TargetType = TargetType.CHECK,
    val targetValue: Double? = null,
    val unit: String? = null,
    val preferredTimeMinutes: Int? = null,
    val goalId: String? = null,
    val lifeAreaId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "HabitEvent", indices = [Index("habitId"), Index("localDate"), Index("state"), Index(value = ["habitId", "localDate"], unique = true)], foreignKeys = [ForeignKey(entity = Habit::class, parentColumns = ["id"], childColumns = ["habitId"], onDelete = ForeignKey.CASCADE)])
data class HabitEvent(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val habitId: String,
    val localDate: String,
    val state: HabitEventState = HabitEventState.COMPLETED,
    val value: Double? = null,
    val note: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "Hobby", indices = [Index("status"), Index("lifeAreaId")], foreignKeys = [ForeignKey(entity = LifeArea::class, parentColumns = ["id"], childColumns = ["lifeAreaId"], onDelete = ForeignKey.SET_NULL)])
data class Hobby(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String? = null,
    val status: ActiveStatus = ActiveStatus.ACTIVE,
    val lifeAreaId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "Skill", indices = [Index("status"), Index("hobbyId"), Index("lifeAreaId")], foreignKeys = [ForeignKey(entity = Hobby::class, parentColumns = ["id"], childColumns = ["hobbyId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = LifeArea::class, parentColumns = ["id"], childColumns = ["lifeAreaId"], onDelete = ForeignKey.SET_NULL)])
data class Skill(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String? = null,
    val status: ActiveStatus = ActiveStatus.ACTIVE,
    val hobbyId: String? = null,
    val lifeAreaId: String? = null,
    val currentFocus: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "Session", indices = [Index("occurredAt"), Index("localDate"), Index("hobbyId"), Index("skillId"), Index("projectId"), Index("lifeAreaId")], foreignKeys = [ForeignKey(entity = Hobby::class, parentColumns = ["id"], childColumns = ["hobbyId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = Skill::class, parentColumns = ["id"], childColumns = ["skillId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = Project::class, parentColumns = ["id"], childColumns = ["projectId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = LifeArea::class, parentColumns = ["id"], childColumns = ["lifeAreaId"], onDelete = ForeignKey.SET_NULL)])
data class Session(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val occurredAt: Long,
    val localDate: String,
    val durationMinutes: Int? = null,
    val notes: String? = null,
    val hobbyId: String? = null,
    val skillId: String? = null,
    val projectId: String? = null,
    val lifeAreaId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "JournalEntry", indices = [Index("entryLocalDate"), Index("occurredAt"), Index("lifeAreaId"), Index("projectId"), Index("goalId")], foreignKeys = [ForeignKey(entity = LifeArea::class, parentColumns = ["id"], childColumns = ["lifeAreaId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = Project::class, parentColumns = ["id"], childColumns = ["projectId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = Goal::class, parentColumns = ["id"], childColumns = ["goalId"], onDelete = ForeignKey.SET_NULL)])
data class JournalEntry(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String? = null,
    val body: String,
    val entryLocalDate: String,
    val occurredAt: Long,
    val reflectionType: ReflectionType = ReflectionType.FREE,
    val lifeAreaId: String? = null,
    val projectId: String? = null,
    val goalId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "Idea", indices = [Index("status"), Index("projectId"), Index("lifeAreaId")], foreignKeys = [ForeignKey(entity = Project::class, parentColumns = ["id"], childColumns = ["projectId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = LifeArea::class, parentColumns = ["id"], childColumns = ["lifeAreaId"], onDelete = ForeignKey.SET_NULL)])
data class Idea(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String? = null,
    val body: String,
    val status: IdeaStatus = IdeaStatus.INBOX,
    val projectId: String? = null,
    val lifeAreaId: String? = null,
    val convertedToType: EntityType? = null,
    val convertedToId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "Memory", indices = [Index("memoryLocalDate"), Index("occurredAt"), Index("lifeAreaId"), Index("projectId")], foreignKeys = [ForeignKey(entity = LifeArea::class, parentColumns = ["id"], childColumns = ["lifeAreaId"], onDelete = ForeignKey.SET_NULL), ForeignKey(entity = Project::class, parentColumns = ["id"], childColumns = ["projectId"], onDelete = ForeignKey.SET_NULL)])
data class Memory(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val body: String? = null,
    val memoryLocalDate: String,
    val occurredAt: Long? = null,
    val sourceType: EntityType? = null,
    val sourceId: String? = null,
    val lifeAreaId: String? = null,
    val projectId: String? = null,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "Chapter", indices = [Index("startLocalDate"), Index("status"), Index("lifeAreaId")], foreignKeys = [ForeignKey(entity = LifeArea::class, parentColumns = ["id"], childColumns = ["lifeAreaId"], onDelete = ForeignKey.SET_NULL)])
data class Chapter(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String? = null,
    val startLocalDate: String,
    val endLocalDate: String? = null,
    val status: ChapterStatus = ChapterStatus.ACTIVE,
    val lifeAreaId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "ChapterItem", indices = [Index("chapterId"), Index(value = ["chapterId", "entityType", "entityId"], unique = true)], foreignKeys = [ForeignKey(entity = Chapter::class, parentColumns = ["id"], childColumns = ["chapterId"], onDelete = ForeignKey.CASCADE)])
data class ChapterItem(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val chapterId: String,
    val entityType: EntityType,
    val entityId: String,
    val orderIndex: Int? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "DailyReview", indices = [Index("localDate"), Index(value = ["localDate"], unique = true)])
data class DailyReview(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val localDate: String,
    val reflectionBody: String? = null,
    val highlight: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "WeeklyReview", indices = [Index(value = ["periodStartLocalDate"], unique = true)])
data class WeeklyReview(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val periodStartLocalDate: String,
    val periodEndLocalDate: String,
    val reflectionBody: String? = null,
    val highlight: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "Tag", indices = [Index(value = ["normalizedName"], unique = true)])
data class Tag(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val normalizedName: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "EntityLink", indices = [Index(value = ["fromType", "fromId"])])
data class EntityLink(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val fromType: EntityType,
    val fromId: String,
    val toType: EntityType,
    val toId: String,
    val relationType: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "Attachment", indices = [Index(value = ["ownerType", "ownerId"])])
data class Attachment(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val ownerType: EntityType,
    val ownerId: String,
    val uri: String,
    val storageMode: StorageMode = StorageMode.EXTERNAL_URI,
    val mimeType: String,
    val displayName: String? = null,
    val sizeBytes: Long? = null,
    val checksum: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(tableName = "TimelineEvent", indices = [Index("occurredAt"), Index("localDate"), Index(value = ["sourceType", "sourceId"])])
data class TimelineEvent(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val eventType: String,
    val occurredAt: Long,
    val localDate: String,
    val sourceType: EntityType? = null,
    val sourceId: String? = null,
    val titleSnapshot: String? = null,
    val metadataJson: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = createdAt
)

@Entity(primaryKeys = ["tagId", "entityType", "entityId"], indices = [Index("tagId"), Index(value = ["entityType", "entityId"])], foreignKeys = [ForeignKey(entity = Tag::class, parentColumns = ["id"], childColumns = ["tagId"], onDelete = ForeignKey.CASCADE)])
data class EntityTagCrossRef(val tagId: String, val entityType: EntityType, val entityId: String)

@Entity(indices = [Index(value = ["entityType", "entityId"], unique = true)])
data class SearchDocument(@PrimaryKey(autoGenerate = true) val rowId: Long = 0, val entityType: EntityType, val entityId: String, val title: String?, val body: String?, val keywords: String?, val updatedAt: Long)
@Fts4(contentEntity = SearchDocument::class)
@Entity
data class SearchDocumentFts(val title: String?, val body: String?, val keywords: String?)
