package com.example.umc.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.umc.R
import com.example.umc.databinding.ActivityEmotionBinding

class EmotionActivity : AppCompatActivity() {
    // 뷰 바인딩 변수 선언 (activity_emotion.xml이랑 연결해주는 역할을 수행
    private lateinit var binding: ActivityEmotionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 바인딩 초기화 후 레이아웃 붙이기
        binding = ActivityEmotionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 감정 이미지들에 클릭 이벤트 연결 (1주차 워크북)
        setEmotionClickListeners()
    }

    // 각 감정 우표 이미지 클릭 이벤트 실행 함수
    private fun setEmotionClickListeners() {
        // 행복 -> Toast 메시지 출력, 텍스트 색상 변경
        binding.ivHappy.setOnClickListener {
            showEmotion(R.string.toast_happy, binding.tvHappy, R.color.happyColor)
        }

        // 들뜸 -> Toast 메시지 출력, 텍스트 색상 변경
        binding.ivExcited.setOnClickListener {
            showEmotion(R.string.toast_excited, binding.tvExcited, R.color.excitedColor)
        }

        // 보통 -> Toast 메시지 출력, 텍스트 색상 변경
        binding.ivNormal.setOnClickListener {
            showEmotion(R.string.toast_normal, binding.tvNormal, R.color.normalColor)
        }

        // 불안 -> Toast 메시지 출력, 텍스트 색상 변경
        binding.ivAnxious.setOnClickListener {
            showEmotion(R.string.toast_anxious, binding.tvAnxious, R.color.anxiousColor)
        }

        // 속상 -> Toast 메시지 출력, 텍스트 색상 변경
        binding.ivSad.setOnClickListener {
            showEmotion(R.string.toast_sad, binding.tvSad, R.color.sadColor)
        }
    }

    // 감정 선택 시 공통 실행 함수
    // 위에 클릭 이벤트 작동 시 함수 적용
    private fun showEmotion(messageRes: Int, textView: android.widget.TextView, colorRes: Int) {
        // 메시지 보여주기
        Toast.makeText(this, getString(messageRes), Toast.LENGTH_SHORT).show()
        // 텍스트 색상 변경 (ContextCompat 함수는 구글링에서 찾은 함수, 색상 리소스를 가져오도록 도와줌)
        textView.setTextColor(ContextCompat.getColor(this, colorRes))
    }
}
