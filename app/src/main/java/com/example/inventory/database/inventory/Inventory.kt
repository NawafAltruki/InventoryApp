package com.example.inventory.database.inventory

import android.content.ClipData
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity
data class Inventory (

    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @NonNull @ColumnInfo(name = "name") val name : String,
    @NonNull @ColumnInfo(name = "price") val price : Double,
    @NonNull @ColumnInfo(name = "quantity") val quantity : Int

)
fun Inventory.getFormattedPrice(): String =
    NumberFormat.getCurrencyInstance().format(price)