package com.example.fetchchallenge.features.userfeed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchchallenge.databinding.FragmentUserFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFeedFragment : Fragment() {

    private val viewModel: UserFeedViewModel by viewModels()
    private var _binding: FragmentUserFeedBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserFeedBinding.inflate(inflater, container, false)
        // vertical is default
        binding.userFeedRv.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if(it)
                binding.progressbar.visibility = View.VISIBLE
            else
                binding.progressbar.visibility = View.GONE
        }

        viewModel.fetchUsers()
        viewModel.userFeedData.observe(viewLifecycleOwner) {
            binding.userFeedRv.adapter = UserFeedAdapter(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}