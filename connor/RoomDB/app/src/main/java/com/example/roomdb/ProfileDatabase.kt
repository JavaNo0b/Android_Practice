package com.example.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Profile::class], version = 1)
abstract class ProfileDatabase: RoomDatabase() {
    abstract fun profileDao(): ProfileDao

    companion object {  //싱글톤 패턴

        private var instance: ProfileDatabase? = null

        @Synchronized   //실행 순서를 만들어준다
        fun getInstance(context: Context): ProfileDatabase? {
            if(instance == null){
                synchronized(ProfileDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProfileDatabase::class.java,
                        "user-database"   //다른 DB와 이름이 겹치지 않게 주의
                    ).build()
                }
            }
            return instance
        }
    }
}
