package com.k.sekiro.taskmanagementapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.*
import com.k.sekiro.taskmanagementapp.task_management_feature.domain.model.*
import com.k.sekiro.taskmanagementapp.task_management_feature.presentation.tasks_screen.component.*
import org.junit.*

class TaskCarTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Test
    fun title_is_displayed() {
        val task = Task.DEFAULT_TASK
        composeTestRule.setContent {
            TaskCard(task = task)
        }

        composeTestRule.onNodeWithText(task.title).assertIsDisplayed()

    }

}