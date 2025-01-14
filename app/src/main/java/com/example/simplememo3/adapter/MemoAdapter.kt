package com.example.simplememo3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simplememo3.databinding.ItemMemoBinding
import com.example.simplememo3.room.Memo

// onItemClick : 아이템 클릭 시 실행될 동작을 외부에서 전달 받음
class MemoAdapter(private val onItemClick: (Memo) -> Unit) :
    ListAdapter<Memo, MemoAdapter.MemoViewHolder>(DIFF_CALLBACK) {

    inner class MemoViewHolder(private val binding: ItemMemoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(memo: Memo) {
            binding.apply {
                this.memo = memo
                executePendingBindings() // 데이터 바인딩 후 즉시 UI 업데이트

                // 아이템 클릭 시 전달 받은 onItemClick을 실행하여 클릭된 아이템 전달
                root.setOnClickListener {
                    onItemClick(memo)
                }
            }
        }
    }

    companion object {
        // RecyclerView 성능 최적화를 위해 변경 사항만 업데이트
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Memo>() {
            override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}