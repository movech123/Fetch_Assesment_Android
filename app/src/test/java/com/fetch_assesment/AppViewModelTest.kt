package com.fetch_assesment

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


class AppViewModelTest {

    private val mockService: FetchAPIService = object : FetchAPIService {
        override suspend fun getItems(): List<Item> {
            return listOf(
                Item(id = 1, listId = 1, name = "Item 1"), // Assuming name matches id like the data
                Item(id = 2, listId = 1, name = "Item 2"),
                Item(id = 3, listId = 2, name = null), // Should be filtered out
                Item(id = 4, listId = 2, name = "Item 4")
            )
        }
    }

    private val viewModel = AppViewModel()

    @Test
    fun `example test`() = runTest {

        val result = viewModel.fetchItems(mockService)

        // Convert result to List<Pair<Int, List<Item>>>
        val resultAsList = result.map { it.key to it.value }

        // Assert: Verify grouped and sorted output
        val expected = listOf(
            1 to listOf(Item(id = 1, listId = 1, name = "Item 1"), Item(id = 2, listId = 1, name = "Item 2")),
            2 to listOf(Item(id = 4, listId = 2, name = "Item 4"))
        )

        assertEquals(expected, resultAsList)
    }
}
