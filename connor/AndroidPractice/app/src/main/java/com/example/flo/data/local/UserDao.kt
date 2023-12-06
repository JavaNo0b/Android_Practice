package com.example.flo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.flo.data.entities.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM UserTable")
    fun getUsers() : List<User>

    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password")
    fun getUser(email:String, password:String) : User? //해당 이메일과 비밀번호의 데이터가 없을 수도 있으므로 ?가 붙는다.
}