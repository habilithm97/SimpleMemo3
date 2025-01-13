package com.example.simplememo3.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/* DAO (Data Access Object)
 -DB 작업을 정의하는 인터페이스
 -데이터 작업과 SQL 쿼리를 간단하게 정의
 -Room이 DAO를 기반으로 쿼리의 구현체 자동 생성
 */
@Dao
interface MemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo: Memo)

    @Update
    suspend fun updateMemo(memo: Memo)

    @Query("select * from memos order by id")
    fun getAll(): Flow<List<Memo>>
}
/*
* suspend
 -시간이 오래 걸릴 수 있는 작업 (Room DB) -> 비동기 처리
 -일시 중단 가능 -> UI 스레드 차단x

* Flow
 -비동기 데이터 스트림
 -순차적 데이터 업데이트
 -Cold 스트림 : 데이터를 요청하기 전까지 실행 안 함
 -Backpressure : 데이터 방출 속도 조절
 (LiveData -> UI와의 강한 결합)
 */