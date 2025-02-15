package com.example.coinranking.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinranking.core.BaseFragment
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Exchange
import com.example.coinranking.databinding.FragmentSearchExchangesBinding
import com.example.coinranking.ui.common.ExchangeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchExchangesFragment : BaseFragment() {

    private var adapter = ExchangeAdapter {
        navigateToDetailFragment(it)
    }

    private lateinit var binding: FragmentSearchExchangesBinding

    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchExchangesBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.rvExchanges.scrollToPosition(0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe()

    }

    private fun initViews() {
        binding.rvExchanges.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchExchangesFragment.adapter
        }
    }

    private fun observe() {
        searchViewModel.exchangesSearchResultList.observe(
            requireParentFragment().viewLifecycleOwner,
            { resource ->
                when (resource) {
                    is Resource.Error -> {
                        adapter.submitList(resource.data.orEmpty())
                    }

                    is Resource.Success -> {
                        adapter.submitList(resource.data.orEmpty())
                    }

                    is Resource.Loading -> {
                        adapter.submitList(resource.data.orEmpty())
                    }
                }
            })
    }

    private fun navigateToDetailFragment(exchange: Exchange) {
        val action =
            SearchFragmentDirections
                .actionSearchFragmentToExchangeDetailFragment(
                    exchange
                )
        navController.navigate(action)
    }
}