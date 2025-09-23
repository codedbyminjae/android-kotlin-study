package com.example.umc

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class DiaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 간단한 텍스트만 있는 UI
        return TextView(requireContext()).apply {
            text = "일기쓰기 프래그먼트"
            gravity = Gravity.CENTER
            textSize = 24f
        }
    }
}
