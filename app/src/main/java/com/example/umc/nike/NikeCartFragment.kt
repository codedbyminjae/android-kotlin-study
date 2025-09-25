package com.example.umc.nike

import android.os.Bundle
import com.example.umc.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc.databinding.FragmentNikeCartBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class NikeCartFragment : Fragment() {

    private var _binding: FragmentNikeCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNikeCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOrder.setOnClickListener {
            // 구매하기 탭으로 전환
            val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.nike_bottom_nav)
            bottomNav?.selectedItemId = R.id.menu_shop
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
