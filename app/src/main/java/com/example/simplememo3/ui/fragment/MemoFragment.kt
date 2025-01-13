package com.example.simplememo3.ui.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.example.simplememo3.databinding.FragmentMemoBinding
import com.example.simplememo3.room.Memo
import com.example.simplememo3.ui.activity.MainActivity
import com.example.simplememo3.viewmodel.MemoViewModel

class MemoFragment : Fragment() {
    private var _binding: FragmentMemoBinding? = null
    private val binding get() = _binding!! // 항상 null-safe한 접근 가능
    private val memoViewModel: MemoViewModel by viewModels()

    private var previousMemo: Memo? = null // 받아온 메모 저장

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // MainActivity 툴바에 업 버튼 활성화
        (activity as? MainActivity)?.showUpButton(true)

        // 받아온 메모를 previousMemo에 저장
        previousMemo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("memo", Memo::class.java)
        } else {
            @Suppress("DEPRECATION") // API 33 미만 호환성 처리
            arguments?.getParcelable("memo")
        }
        if (previousMemo != null) {
            binding.edtMemo.setText(previousMemo!!.content)
        }
    }

    override fun onResume() {
        super.onResume()

        binding.edtMemo.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.edtMemo, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onPause() {
        super.onPause()

        val currentMemo = binding.edtMemo.text.toString()

        // 메모가 비어있지 않고, 이전 메모와 다를 경우에만 처리
        if (currentMemo.isNotBlank() && currentMemo != previousMemo?.content) {
            if (previousMemo != null) { // 수정 모드
                updateMemo(currentMemo)
            } else { // 추가 모드
                newMemo(currentMemo)
            }
        }
    }

    private fun newMemo(memoStr: String) {
        val memo = Memo(content = memoStr)
        memoViewModel.insertMemo(memo)
    }

    private fun updateMemo(memoStr: String) {
        // 이전 메모를 복사하여 새로운 객체 생성
        val updatedMemo = previousMemo?.copy(content = memoStr)
        if (updatedMemo != null) {
            memoViewModel.updateMemo(updatedMemo)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        (activity as? MainActivity)?.showUpButton(false)
        _binding = null // 메모리 릭 방지
    }
}