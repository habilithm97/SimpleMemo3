package com.example.simplememo3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplememo3.repository.MemoRepository
import com.example.simplememo3.room.Memo
import com.example.simplememo3.room.MemoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/* AAC ViewModel을 이용한 MVVM 패턴
* MVVM
 -데이터 관련 로직과 UI 관련 로직을 분리
 -UI는 ViewModel을 통해 데이터를 관찰하며 변경 사항을 자동으로 업데이트

* AAC ViewModel
 -viewModelScope를 통해 비동기 작업을 안전하게 처리
 -UI 관련 데이터를 생명주기와 독립적으로 안전하게 관리
 */

// Room DB 인스턴스 생성 시 Application Context 필요
class MemoViewModel(application: Application) : AndroidViewModel(application) {
    private val memoRepository: MemoRepository
    val getAll: LiveData<List<Memo>>

    init {
        val memoDao = MemoDatabase.getInstance(application).memoDao()
        memoRepository = MemoRepository(memoDao)
        // 비동기 데이터를 UI에서 안전하게 관찰할 수 있도록 LiveData로 변환
        getAll = memoRepository.getAll().asLiveData()
    }

    fun insertMemo(memo: Memo) {
        viewModelScope.launch(Dispatchers.IO) {
            memoRepository.insertMemo(memo)
        }
    }
}
/*
* viewModelScope : ViewModel의 생명주기 내에서 안전하게 작업 실행
* Dispatchers.IO : 입출력 작업에 최적화된 스레드에서 작업 실행
 */