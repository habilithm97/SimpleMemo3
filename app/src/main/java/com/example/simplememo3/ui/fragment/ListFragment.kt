package com.example.simplememo3.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplememo3.R
import com.example.simplememo3.adapter.MemoAdapter
import com.example.simplememo3.databinding.FragmentListBinding
import com.example.simplememo3.viewmodel.MemoViewModel

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!! // 항상 null-safe한 접근 가능
    private val memoViewModel: MemoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 어댑터 생성 및 아이템 클릭 시 실행할 동작 정의
        val memoAdapter = MemoAdapter { memo ->
            val memoFragment = MemoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("memo", memo)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, memoFragment)
                .addToBackStack(null)
                .commit()
        }
        binding.apply {
            rv.apply {
                adapter = memoAdapter
                layoutManager = LinearLayoutManager(requireContext()).apply {
                    reverseLayout = true
                    stackFromEnd = true
                }
                setHasFixedSize(true) // 아이템 크기 고정 -> 성능 최적화
            }
            fab.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, MemoFragment())
                    .addToBackStack(null) // 백 스택에 추가
                    .commit()
            }
            memoViewModel.getAll.observe(viewLifecycleOwner) {
                // 어댑터에 새로운 리스트 제출
                memoAdapter.submitList(it) {
                    val itemCount = memoAdapter.itemCount
                    if (itemCount > 0) {
                        rv.smoothScrollToPosition(itemCount - 1)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null // 메모리 릭 방지
    }
}
/*
* _binding과 binding의 차이
 -_binding : 실제 바인딩 객체를 저장하는 Mutable 변수 (null 가능)
 -binding : _binding을 읽기 전용으로 접근하기 위한 Immutable 변수 (항상 null이 아니어야 하므로 !! 처리 )
 */