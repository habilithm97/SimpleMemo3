package com.example.simplememo3.ui.activity

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simplememo3.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
    }

    private fun initView() {
        binding.apply {
            val items = listOf("잠금 비밀번호 설정 및 변경")
            val adapter = ArrayAdapter(this@SettingActivity, R.layout.simple_list_item_1, items)
            listView.apply {
                this.adapter = adapter
                setOnItemClickListener { _, _, _, _ ->
                    val intent = Intent(this@SettingActivity, PasswordActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}