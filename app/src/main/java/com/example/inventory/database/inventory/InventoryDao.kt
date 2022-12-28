package com.example.inventory.database.inventory

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Inventory)

    @Update
    suspend fun update(item: Inventory)

    @Delete
    suspend fun delete(item: Inventory)

    @Query("SELECT * from inventory ORDER BY name ASC")
    fun getItems(): Flow<List<Inventory>>

    @Query("SELECT * from inventory WHERE id = :id")
    fun getItem(id:Int): Flow<Inventory>


}