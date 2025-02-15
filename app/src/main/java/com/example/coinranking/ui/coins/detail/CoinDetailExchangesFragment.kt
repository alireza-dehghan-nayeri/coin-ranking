package com.example.coinranking.ui.coins.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinranking.core.BaseFragment
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Exchange
import com.example.coinranking.databinding.FragmentCoinDetailExchangesBinding
import com.example.coinranking.ui.common.ExchangeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailExchangesFragment : BaseFragment() {

    private val adapter = ExchangeAdapter {
        navigateToExchangeDetailFragment(it)
    }

    private lateinit var binding: FragmentCoinDetailExchangesBinding
    private val coinDetailExchangesViewModel by viewModels<CoinDetailExchangesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCoinDetailExchangesBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coinDetailExchangesViewModel.refresh()
        initViews()
        observe()

    }

    private fun initViews() {
        binding.rvExchanges.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CoinDetailExchangesFragment.adapter
        }
    }

    private fun observe() {
        coinDetailExchangesViewModel.coinExchangesResource.observe(viewLifecycleOwner, { resource ->
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

    private fun navigateToExchangeDetailFragment(exchange: Exchange) {
        val action =
            CoinDetailFragmentDirections
                .actionCoinDetailFragmentToExchangeDetailFragment(
                    exchange
                )
        navController.navigate(action)
    }

}