package com.zephysus.core.task

import com.zephysus.core.model.QuoteTask
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Singleton

@Singleton
interface QuoteTaskManager {
    /**
     * Schedules a task for syncing quotes.
     *
     * @return Unique work ID
     */
    fun syncNotes(): UUID

    /**
     * Schedules a [com.zephysus.core.model.QuoteTask] task
     *
     * @return Unique work ID
     */
    fun scheduleTask(quoteTask: QuoteTask): UUID

    /**
     * Retrieves the state of a task
     *
     * @param taskId Unique work ID
     * @return Nullable (in case task not exists) task state
     */
    fun getTaskState(taskId: UUID): TaskState?

    /**
     * Returns Flowable task state of a specific task
     *
     * @param taskId Unique work ID
     * @return Flow of task state
     */
    fun observeTask(taskId: UUID): Flow<TaskState>

    /**
     * Aborts/Stops all scheduled (ongoing) tasks
     */
    fun abortAllTasks()

    /**
     * Generates task ID from quote ID
     */
    fun getTaskIdFromNoteId(quoteId: String) = quoteId
}
