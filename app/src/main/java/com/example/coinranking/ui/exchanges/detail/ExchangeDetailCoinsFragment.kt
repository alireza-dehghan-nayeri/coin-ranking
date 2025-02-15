package com.example.coinranking.ui.exchanges.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinranking.core.BaseFragment
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.databinding.FragmentExchangeDetailCoinsBinding
import com.example.coinranking.ui.common.CoinAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeDetailCoinsFragment : BaseFragment() {

    private val adapter = CoinAdapter(
        {
            navigateToDetailFragment(it)
        },
        { coin, position ->
            coinItemBookmarkClickHandel(coin, position)
        }
    ) { coin ->
        isCoinBookmarked(coin)
    }

    private lateinit var binding: FragmentExchangeDetailCoinsBinding
    private val exchangeDetailCoinsViewModel by viewModels<ExchangeDetailCoinsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExchangeDetailCoinsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exchangeDetailCoinsViewModel.refresh()
        initViews()
        observe()
    }

    private fun initViews() {
        binding.rvCoins.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ExchangeDetailCoinsFragment.adapter
        }
    }

    private fun observe() {
        exchangeDetailCoinsViewModel.exchangeCoinsResource.observe(viewLifecycleOwner, { resource ->
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

    private fun navigateToDetailFragment(coin: CoinAndBookmark) {
        val action =
            ExchangeDetailFragmentDirections
                .actionExchangeDetailFragmentToCoinDetailFragment(
                    coin
                )
        navController.navigate(action)
    }

    private fun coinItemBookmarkClickHandel(
        coin: CoinAndBookmark,
        position: Int
    ) {

        if (coin.bookmark != null) {
            exchangeDetailCoinsViewModel.removeCoinFromBookmarks(Bookmark(coin.coin.uuid))
        } else {
            exchangeDetailCoinsViewModel.addCoinToBookmarks(Bookmark(coin.coin.uuid))
        }
        adapter.notifyItemChanged(position)
    }

    private fun isCoinBookmarked(coin: CoinAndBookmark): Boolean {
        return coin.bookmark != null
    }
}