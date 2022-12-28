package com.example.inventory.viewmodel

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.inventory.database.inventory.Inventory
import com.example.inventory.database.inventory.InventoryDao
import kotlinx.coroutines.launch

class InventoryViewModel (private val inventoryDao: InventoryDao) : ViewModel() {

    val allItems: LiveData<List<Inventory>> = inventoryDao.getItems().asLiveData()

    private fun insertItem(item: Inventory) {
        viewModelScope.launch {
            inventoryDao.insert(item)
        }
    }

    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Inventory {
        return Inventory(
            name = itemName,
            price = itemPrice.toDouble(),
            quantity = itemCount.toInt()
        )
    }

    fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
        insertItem(newItem)
    }

    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }

    fun retrieveItem(id: Int): LiveData<Inventory> {
        return inventoryDao.getItem(id).asLiveData()
    }

    private fun updateItem(item: Inventory) {
        viewModelScope.launch {
            inventoryDao.update(item)
        }
    }

    fun sellItem(item: Inventory) {
        if (item.quantity > 0) {
            // Decrease the quantity by 1
            val newItem = item.copy(quantity = item.quantity - 1)
            updateItem(newItem)
        }
    }

    fun isStockAvailable(item: Inventory): Boolean {
        return (item.quantity > 0)
    }

    fun deleteItem(item: Inventory) {
        viewModelScope.launch {
            inventoryDao.delete(item)
        }
    }

    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ): Inventory {
        return Inventory(
            id = itemId,
            name = itemName,
            price = itemPrice.toDouble(),
            quantity = itemCount.toInt()
        )
    }

    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice, itemCount)
        updateItem(updatedItem)
    }
}