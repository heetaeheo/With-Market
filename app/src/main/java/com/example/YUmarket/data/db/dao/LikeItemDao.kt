package com.example.YUmarket.data.db.dao

import androidx.room.*
import com.example.YUmarket.data.entity.room.LikeItemEntity


@Dao
interface LikeItemDao {
    // 최근에 추가한 항목이 위로 오도록
    @Query("select * from LikeItemEntity order by id desc")
    suspend fun getAll(): List<LikeItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: LikeItemEntity)

    @Delete
    suspend fun delete(item: LikeItemEntity)

    @Query("delete from LikeItemEntity")
    suspend fun deleteAll()
}