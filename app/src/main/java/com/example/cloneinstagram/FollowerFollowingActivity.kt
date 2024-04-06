package com.example.cloneinstagram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cloneinstagram.databinding.ActivityFollowerFollowingBinding
import com.google.android.material.tabs.TabLayoutMediator

class FollowerFollowingActivity : AppCompatActivity() {

    lateinit var binding : ActivityFollowerFollowingBinding
    private val tapList = arrayListOf("100 팔로워", "100 팔로잉", "구독")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowerFollowingBinding.inflate(layoutInflater)

        //TabLayout, ViewPager2 생성
        initView()

        binding.tvFollowerEditName.text = intent.getStringExtra("key")

        //이전 버튼을 눌렀을 때 원래의 엑티비티로 돌아감
        binding.ivPrevious.setOnClickListener {
            finish()
        }

        setContentView(binding.root)
    }

    private fun initView() {
        binding.vpFollower.adapter = FollowerFollowingTabLayoutVPAdapter(this)
        TabLayoutMediator(binding.tlFollower, binding.vpFollower){ tab, position ->
            tab.text = tapList[position]
        }.attach()
    }
}