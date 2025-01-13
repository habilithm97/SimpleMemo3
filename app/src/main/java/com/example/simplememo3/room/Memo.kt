package com.example.simplememo3.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/* Entity
 -DB의 테이블을 나타내는 데이터 클래스 (테이블의 스키마 정의)
 -테이블 구조와 1:1 매핑
 */
@Parcelize // Parcelable 자동 구현
@Entity(tableName = "memos")
data class Memo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "content") var content: String
) : Parcelable // 컴포넌트 간에 데이터를 전달할 수 있도록 직렬화