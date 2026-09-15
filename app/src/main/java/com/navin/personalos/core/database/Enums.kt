package com.navin.personalos.core.database

import androidx.room.TypeConverter

enum class LifeAreaStatus { ACTIVE, ARCHIVED }
enum class GoalStatus { ACTIVE, PAUSED, ACHIEVED, ARCHIVED }
enum class ProjectStatus { ACTIVE, PAUSED, COMPLETED, ARCHIVED }
enum class MilestoneStatus { OPEN, COMPLETED, SKIPPED }
enum class TaskStatus { OPEN, COMPLETED, CANCELLED }
enum class Priority { LOW, NORMAL, HIGH, URGENT }
enum class OwnerType { TASK, HABIT, ROUTINE, JOURNAL_PROMPT, STANDALONE }
enum class ReminderState { SCHEDULED, DELIVERED, CANCELLED, SKIPPED }
enum class DeliveryState { PENDING, SCHEDULED, NOTIFICATIONS_BLOCKED, EXACT_ACCESS_BLOCKED, CHANNEL_BLOCKED, EXPIRED, ERROR }
enum class Frequency { DAILY, WEEKLY, MONTHLY, YEARLY }
enum class ExceptionType { SKIP, OVERRIDE }
enum class ActiveStatus { ACTIVE, PAUSED, ARCHIVED }
enum class TargetType { CHECK, COUNT, DURATION }
enum class HabitEventState { COMPLETED, SKIPPED, PARTIAL }
enum class MeasurementType { NONE, COUNT, DURATION, NUMBER, PERCENT_EXPLICIT }
enum class ReflectionType { FREE, DAILY, EVENING, WEEKLY_LINKED }
enum class IdeaStatus { INBOX, ACTIVE, ARCHIVED, CONVERTED }
enum class ChapterStatus { ACTIVE, COMPLETED, ARCHIVED }
enum class StorageMode { EXTERNAL_URI, PRIVATE_COPY }
enum class EntityType { LIFE_AREA, GOAL, PROJECT, MILESTONE, TASK, REMINDER, HABIT, HOBBY, SKILL, SESSION, JOURNAL, IDEA, MEMORY, CHAPTER, DAILY_REVIEW, WEEKLY_REVIEW }

class EnumConverters {
    @TypeConverter fun lifeAreaStatusToText(value: LifeAreaStatus): String = value.name
    @TypeConverter fun textToLifeAreaStatus(value: String): LifeAreaStatus = LifeAreaStatus.valueOf(value)
    @TypeConverter fun goalStatusToText(value: GoalStatus): String = value.name
    @TypeConverter fun textToGoalStatus(value: String): GoalStatus = GoalStatus.valueOf(value)
    @TypeConverter fun projectStatusToText(value: ProjectStatus): String = value.name
    @TypeConverter fun textToProjectStatus(value: String): ProjectStatus = ProjectStatus.valueOf(value)
    @TypeConverter fun milestoneStatusToText(value: MilestoneStatus): String = value.name
    @TypeConverter fun textToMilestoneStatus(value: String): MilestoneStatus = MilestoneStatus.valueOf(value)
    @TypeConverter fun taskStatusToText(value: TaskStatus): String = value.name
    @TypeConverter fun textToTaskStatus(value: String): TaskStatus = TaskStatus.valueOf(value)
    @TypeConverter fun priorityToText(value: Priority): String = value.name
    @TypeConverter fun textToPriority(value: String): Priority = Priority.valueOf(value)
    @TypeConverter fun ownerTypeToText(value: OwnerType): String = value.name
    @TypeConverter fun textToOwnerType(value: String): OwnerType = OwnerType.valueOf(value)
    @TypeConverter fun reminderStateToText(value: ReminderState): String = value.name
    @TypeConverter fun textToReminderState(value: String): ReminderState = ReminderState.valueOf(value)
    @TypeConverter fun deliveryStateToText(value: DeliveryState): String = value.name
    @TypeConverter fun textToDeliveryState(value: String): DeliveryState = DeliveryState.valueOf(value)
    @TypeConverter fun frequencyToText(value: Frequency): String = value.name
    @TypeConverter fun textToFrequency(value: String): Frequency = Frequency.valueOf(value)
    @TypeConverter fun exceptionTypeToText(value: ExceptionType): String = value.name
    @TypeConverter fun textToExceptionType(value: String): ExceptionType = ExceptionType.valueOf(value)
    @TypeConverter fun activeStatusToText(value: ActiveStatus): String = value.name
    @TypeConverter fun textToActiveStatus(value: String): ActiveStatus = ActiveStatus.valueOf(value)
    @TypeConverter fun targetTypeToText(value: TargetType): String = value.name
    @TypeConverter fun textToTargetType(value: String): TargetType = TargetType.valueOf(value)
    @TypeConverter fun habitEventStateToText(value: HabitEventState): String = value.name
    @TypeConverter fun textToHabitEventState(value: String): HabitEventState = HabitEventState.valueOf(value)
    @TypeConverter fun measurementTypeToText(value: MeasurementType): String = value.name
    @TypeConverter fun textToMeasurementType(value: String): MeasurementType = MeasurementType.valueOf(value)
    @TypeConverter fun reflectionTypeToText(value: ReflectionType): String = value.name
    @TypeConverter fun textToReflectionType(value: String): ReflectionType = ReflectionType.valueOf(value)
    @TypeConverter fun ideaStatusToText(value: IdeaStatus): String = value.name
    @TypeConverter fun textToIdeaStatus(value: String): IdeaStatus = IdeaStatus.valueOf(value)
    @TypeConverter fun chapterStatusToText(value: ChapterStatus): String = value.name
    @TypeConverter fun textToChapterStatus(value: String): ChapterStatus = ChapterStatus.valueOf(value)
    @TypeConverter fun storageModeToText(value: StorageMode): String = value.name
    @TypeConverter fun textToStorageMode(value: String): StorageMode = StorageMode.valueOf(value)
    @TypeConverter fun entityTypeToText(value: EntityType): String = value.name
    @TypeConverter fun textToEntityType(value: String): EntityType = EntityType.valueOf(value)
}
