package com.davedecastro.yonduandroidexam.data.db.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface BaseDao<MODEL> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MODEL)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: MODEL)

    @Delete
    suspend fun delete(item: MODEL)

    fun get(): List<MODEL>
    fun get(id: Long): MODEL
    fun getFlow(): Flow<List<MODEL>>
    fun getFlow(id: Long): Flow<MODEL>
    fun clear()
}