package com.example.simplememo3.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.simplememo3.R
import com.example.simplememo3.databinding.ActivityMainBinding
import com.example.simplememo3.ui.fragment.ListFragment
import com.example.simplememo3.ui.fragment.MemoFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var sortOrder = true

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

        if (savedInstanceState == null) {
            replaceFragment(ListFragment())
        }
    }

    private fun initView() {
        binding.apply {
            setSupportActionBar(toolbar)
            setOnBackPressedCallback()
        }
    }

    // 업 버튼 클릭 시 동작
    override fun onSupportNavigateUp(): Boolean {
        // 백 스택에 프래그먼트가 있을 경우 최상위 프래그먼트 제거
        return if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            true
        } else {
            super.onSupportNavigateUp() // 기본 동작 (상위 화면으로 이동)
        }
    }

    // 백 버튼 클릭 시 동작
    private fun setOnBackPressedCallback() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 백 스택에 프래그먼트가 있을 경우 최상위 프래그먼트 제거
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    isEnabled = false // 현재 콜백 비활성화
                    onBackPressedDispatcher.onBackPressed() // 기본 동작 (현재 화면 제거)
                }
            }
        })
    }

    // MemoFragment에서만 업 버튼이 보이게 설정
    fun showUpButton(show: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(show)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        // 현재 활성화된 프래그먼트에 따라 메뉴를 동적으로 변경
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)

        if (currentFragment is ListFragment) {
            menu.clear()
            menuInflater.inflate(R.menu.list_fragment_options, menu)

            val createDateItem = menu.findItem(R.id.createDate)
            val updateDateItem = menu.findItem(R.id.updateDate)

            if (sortOrder) {
                createDateItem.setIcon(R.drawable.check)
                updateDateItem.setIcon(null)
            } else {
                createDateItem.setIcon(null)
                updateDateItem.setIcon(R.drawable.check)
            }
        } else if (currentFragment is MemoFragment) {
            menu.clear()
            menuInflater.inflate(R.menu.memo_fragment_options, menu)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.createDate -> {
                if (!sortOrder) {
                    sortOrder = true
                    invalidateOptionsMenu()
                }
                true
            }
            R.id.updateDate -> {
                if (sortOrder) {
                    sortOrder = false
                    invalidateOptionsMenu()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}