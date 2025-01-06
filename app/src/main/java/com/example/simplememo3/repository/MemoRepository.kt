package com.example.simplememo3.repository

import com.example.simplememo3.room.Memo
import com.example.simplememo3.room.MemoDao
import kotlinx.coroutines.flow.Flow

/* Repository
 -데이터 관련 로직을 담당, 데이터 소스와의 상호작용을 캡슐화
 -비즈니스 로직의 분리 (Repository = 데이터 관련 로직, ViewModel = UI 관련 로직)
 -데이터 소스의 추상화 : ViewModel이 어떤 데이터 소스에서 데이터를 가져오는지 몰라도 되게 함
 */
class MemoRepository(private val memoDao: MemoDao) {
    suspend fun insertMemo(memo: Memo) {
        memoDao.insertMemo(memo)
    }
    fun getAll(): Flow<List<Memo>> {
        return memoDao.getAll()
    }
}