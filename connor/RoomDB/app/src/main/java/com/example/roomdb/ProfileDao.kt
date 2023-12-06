package com.example.roomdb

import androidx.room.*

@Dao
interface ProfileDao {
    @Insert
    fun insert(profile: Profile)
    @Update
    fun update(profile: Profile)
    @Delete
    fun delete(profile: Profile)
    @Query("SELECT * FROM Profile")  //테이블 모든 값 가져오기
    fun getAll(): List<Profile>
//    @Query("DELETE FROM Profile WHERE name = :name") //'name'인 유저 삭제
//    fun deleteUserByName(name: String)
}