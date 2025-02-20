package com.example.simplememo3.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import com.example.simplememo3.databinding.FragmentPasswordBinding

class PasswordFragment : Fragment() {
    private var _binding: FragmentPasswordBinding? = null
    private val binding get() = _binding!! // 항상 null-safe한 접근 가능

    private val memoId: Long by lazy {
        // IllegalArgumentException : arguments가 null이거나, MEMO_ID라는 키로 값을 찾을 수 없는 경우
        arguments?.getLong(MEMO_ID) ?: throw IllegalArgumentException("Memo ID is required")
    }
    private val inputPassword = StringBuilder()

    private lateinit var dots: List<View>

    // 클래스 동반 객체
    // 객체 내의 속성/메서드는 클래스 이름을 통해 바로 접근 가능
    companion object {
        private const val MEMO_ID = "memo_id"

        fun newInstance(memoId: Long) = PasswordFragment().apply {
            arguments = Bundle().apply {
                putLong(MEMO_ID, memoId)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            keypad.children.forEach { view ->
                if (view is Button) {
                    view.setOnClickListener {
                        onKeyPress(view.text.toString()) // 버튼의 텍스트(숫자)를 전달
                    }
                }
            }
            btn.setOnClickListener {
                parentFragmentManager.popBackStack() // 뒤로 가기
            }
            dots = listOf(dot1, dot2, dot3, dot4)
        }
    }

    private fun onKeyPress(number: String) {
        if (inputPassword.length < 4) {
            inputPassword.append(number)
            updatePasswordDots() // UI 갱신
        }
        if (inputPassword.length == 4) {
            handlePassword(inputPassword.toString()) // 비밀번호 처리
        }
    }

    private fun updatePasswordDots() {
        // 입력된 비밀번호의 길이보다 작은 인덱스의 dot들은 선택됨
        dots.forEachIndexed { index, view ->
            view.isSelected = index < inputPassword.length
        }
    }

    private fun handlePassword(password: String) {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null // 메모리 릭 방지
    }
}