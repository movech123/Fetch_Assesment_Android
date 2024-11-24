package com.fetch_assesment

import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {


    suspend fun fetchItems(fetchService: FetchAPIService = RetroFitClient.fetchService): List<Map.Entry<Int, List<Item>>>{
        val response = fetchService.getItems()

        // Filter out items with null or blank names
        val filteredItems = response.filter { !it.name.isNullOrBlank() }

        // Group by listId and sort each group by name with natural number sorting
        return filteredItems
            .groupBy { it.listId }
            .mapValues { (_, items) ->
                items.sortedBy { it.id }  // Sort by id instead of parsing name (Found this was easier since the id matches the item name)
            }
            .entries
            .sortedBy { it.key }
    }
}