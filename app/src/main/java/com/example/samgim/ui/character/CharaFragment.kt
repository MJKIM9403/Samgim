package com.example.samgim.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.samgim.R
import com.example.samgim.databinding.FragmentHomeBinding

class CharaFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.character, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}