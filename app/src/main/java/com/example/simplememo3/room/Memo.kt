package com.example.simplememo3.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/* Entity
 -DB의 테이블을 나타내는 데이터 클래스 (테이블의 스키마 정의)
 -테이블 구조와 1:1 매핑
 */
@Entity(tableName = "memo_table")
data class Memo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "content") var content: String
)