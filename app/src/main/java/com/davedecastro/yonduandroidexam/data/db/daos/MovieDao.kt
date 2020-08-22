package com.davedecastro.yonduandroidexam.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.davedecastro.yonduandroidexam.data.db.entities.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie): Long

    @Query(
        """
            SELECT *
            FROM movies
            LIMIT 1
        """
    )
    fun get(): LiveData<Movie>
}