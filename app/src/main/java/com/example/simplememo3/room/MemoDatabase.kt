package com.example.simplememo3.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// DB 구현체 자동 생성 (추상 클래스로 선언)
@Database(entities = [Memo::class], version = 1)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao

    // DB 인스턴스를 앱 전체에서 관리하기 위한 싱글톤 패턴
    companion object {
        @Volatile // 여러 스레드에서 최신 값을 읽을 수 있도록 보장 (일관성 유지)
        private var INSTANCE: MemoDatabase? = null

        fun getInstance(context: Context) : MemoDatabase {
            // synchronized : 여러 스레드가 동시에 인스턴스를 생성하지 못하도록 동기화
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemoDatabase::class.java, "Memo.db")
                    .build()
                INSTANCE = instance // 새로 생성한 instance를 INSTANCE에 저장
                instance
            }
        }
    }
}