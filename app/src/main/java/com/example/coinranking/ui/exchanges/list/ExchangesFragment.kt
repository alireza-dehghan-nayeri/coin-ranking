package com.example.coinranking.ui.exchanges.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinranking.core.BaseFragment
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Exchange
import com.example.coinranking.databinding.FragmentExchangesBinding
import com.example.coinranking.ui.common.ExchangeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangesFragment : BaseFragment() {

    private lateinit var binding: FragmentExchangesBinding

    private val exchangesViewModel by viewModels<ExchangesViewModel>()

    private var adapter = ExchangeAdapter {
        navigateToDetailFragment(it)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExchangesBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exchangesViewModel.refresh()
        initViews()
        observe()

    }

    private fun initViews() {
        binding.rvExchanges.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ExchangesFragment.adapter
        }

        binding.srCoin.setOnRefreshListener {
            Toast.makeText(context, "refresh list", Toast.LENGTH_SHORT).show()
            exchangesViewModel.refresh()
            refresh()
        }
    }

    private fun observe() {

        exchangesViewModel.exchangesResource.observe(viewLifecycleOwner, { resource ->
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
            ExchangesFragmentDirections
                .actionExchangesFragmentToExchangeDetailFragment(
                    exchange
                )
        navController.navigate(action)
    }

    private fun refresh() {
        binding.srCoin.isRefreshing = false
    }
}
