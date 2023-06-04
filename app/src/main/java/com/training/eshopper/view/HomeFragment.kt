package com.training.eshopper.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.training.eshopper.data.model.DataState
import com.training.eshopper.databinding.FragmentHomeBinding
import com.training.eshopper.view.adapter.ProductAdapter
import com.training.eshopper.view.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.getAllProducts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun setupRecyclerView() {
        adapter = ProductAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@HomeFragment.adapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeProductsState()
        setupRecyclerView()
    }

    private fun observeProductsState() {
        mainViewModel.productsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Success -> adapter.submitList(state.data)
                is DataState.Error -> showError(state.message.toString())
                else -> {}
            }
        }
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }
}