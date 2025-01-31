package com.example.simplememo3.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simplememo3.R
import com.example.simplememo3.databinding.ItemMemoBinding
import com.example.simplememo3.room.Memo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// 아이템 실행 동작을 외부에서 전달 받음
class MemoAdapter(private val onItemClick: (Memo) -> Unit,
                  private val onItemLongClick: (Memo) -> Unit) :
    ListAdapter<Memo, MemoAdapter.MemoViewHolder>(DIFF_CALLBACK) {

    inner class MemoViewHolder(private val binding: ItemMemoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(memo: Memo) {
            binding.apply {
                // Memo 데이터를 각 뷰에 할당
                tvContent.text = memo.content
                tvCreateDate.text = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(Date(memo.createDate))

                root.setOnClickListener {
                    Log.d("MemoAdapter", "클릭한 메모 id: ${memo.id}, content: ${memo.content}")
                    onItemClick(memo)
                }
                root.setOnLongClickListener {
                    showPopupMenu(it, memo)
                    true
                }
            }
        }
        private fun showPopupMenu(view: View, memo: Memo) {
            PopupMenu(view.context, view).apply {
                menuInflater.inflate(R.menu.item_context_menu, menu)

                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.delete -> {
                            onItemLongClick(memo)
                            true
                        }
                        else -> false
                    }
                }
                show()
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