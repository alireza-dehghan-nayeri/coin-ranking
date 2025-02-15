package com.example.coinranking.ui.coins.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinranking.R
import com.example.coinranking.core.BaseFragment
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.databinding.FragmentCoinsBinding
import com.example.coinranking.ui.common.CoinAdapter
import com.example.coinranking.ui.entry.InteractionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinsFragment : BaseFragment() {

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

    private lateinit var binding: FragmentCoinsBinding

    private val interactionViewModel by activityViewModels<InteractionViewModel>()

    private val coinsViewModel by viewModels<CoinsViewModel>()


    private var priceSorted = false
    private var marketCapSorted = true
    private var isDesc = true


    override fun onResume() {
        super.onResume()
        val times = resources.getStringArray(R.array.times)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_time_drop_down, times)
        binding.avTimeFilterDropDown.apply {
            setAdapter(arrayAdapter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToSplashFragmentIfNeeded()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentCoinsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coinsViewModel.refresh()
        initViews()
        observe()
    }

    private fun initViews() {

        binding.chipPriceFilter.isCloseIconVisible = false
        binding.chipMarketCapFilter.isCloseIconVisible = true

        binding.rvCoins.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CoinsFragment.adapter
        }

        binding.chipPriceFilter.setOnClickListener {
            if (priceSorted) {

                if (isDesc) {
                    isDesc = false
                    binding.chipPriceFilter.setCloseIconResource(R.drawable.ic_up_arrow)
                    coinsViewModel.setSortArguments(orderDirection = "asc")

                } else {
                    isDesc = true
                    binding.chipPriceFilter.setCloseIconResource(R.drawable.ic_down_arrow)
                    coinsViewModel.setSortArguments(orderDirection = "desc")
                }

            } else {
                priceSorted = true
                marketCapSorted = false
                binding.chipMarketCapFilter.isCloseIconVisible = false
                if (isDesc) {
                    binding.chipPriceFilter.setCloseIconResource(R.drawable.ic_down_arrow)
                } else {
                    binding.chipPriceFilter.setCloseIconResource(R.drawable.ic_up_arrow)
                }
                binding.chipPriceFilter.isCloseIconVisible = true
                coinsViewModel.setSortArguments(orderBy = "price")

            }
            coinsViewModel.refresh()
        }

        binding.chipMarketCapFilter.setOnClickListener {
            if (marketCapSorted) {

                if (isDesc) {
                    isDesc = false
                    binding.chipMarketCapFilter.setCloseIconResource(R.drawable.ic_up_arrow)
                    coinsViewModel.setSortArguments(orderDirection = "asc")

                } else {
                    isDesc = true
                    binding.chipMarketCapFilter.setCloseIconResource(R.drawable.ic_down_arrow)
                    coinsViewModel.setSortArguments(orderDirection = "desc")
                }

            } else {
                marketCapSorted = true
                priceSorted = false
                binding.chipPriceFilter.isCloseIconVisible = false
                if (isDesc) {
                    binding.chipMarketCapFilter.setCloseIconResource(R.drawable.ic_down_arrow)
                } else {
                    binding.chipMarketCapFilter.setCloseIconResource(R.drawable.ic_up_arrow)
                }
                binding.chipMarketCapFilter.isCloseIconVisible = true
                coinsViewModel.setSortArguments(orderBy = "marketCap")

            }
            coinsViewModel.refresh()
        }

        binding.srCoin.setOnRefreshListener {
            Toast.makeText(context, "refresh list", Toast.LENGTH_SHORT).show()
            coinsViewModel.refresh()
            refresh()
        }

        binding.avTimeFilterDropDown.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    coinsViewModel.setSortArguments(timePeriod = "3h")
                }
                1 -> {
                    coinsViewModel.setSortArguments(timePeriod = "24h")
                }
                2 -> {
                    coinsViewModel.setSortArguments(timePeriod = "7d")
                }
                3 -> {
                    coinsViewModel.setSortArguments(timePeriod = "30d")
                }
                4 -> {
                    coinsViewModel.setSortArguments(timePeriod = "3m")
                }
                5 -> {
                    coinsViewModel.setSortArguments(timePeriod = "1y")
                }
                6 -> {
                    coinsViewModel.setSortArguments(timePeriod = "3y")
                }
                7 -> {
                    coinsViewModel.setSortArguments(timePeriod = "5y")
                }
            }
        }
    }

    private fun observe() {
        coinsViewModel.coinsResource.observe(viewLifecycleOwner, { resource ->
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
            binding.rvCoins.scrollToPosition(0)
        })

    }

    private fun navigateToSplashFragmentIfNeeded() {
        if (!interactionViewModel.splashSeen) {
            interactionViewModel.setSplashSeen()
            navController.navigate(R.id.action_coinsFragment_to_splashFragment)
        }
    }

    private fun navigateToDetailFragment(coin: CoinAndBookmark) {
        val action =
            CoinsFragmentDirections
                .actionCoinsFragmentToCoinDetailFragment(
                    coin
                )
        navController.navigate(action)
    }

    private fun coinItemBookmarkClickHandel(
        coin: CoinAndBookmark,
        position: Int
    ) {
        if (coin.bookmark != null) {
            coinsViewModel.removeCoinFromBookmarks(Bookmark(coin.coin.uuid))
        } else {
            coinsViewModel.addCoinToBookmarks(Bookmark(coin.coin.uuid))
        }
        adapter.notifyItemChanged(position)
    }

    private fun isCoinBookmarked(coin: CoinAndBookmark): Boolean {
        return coin.bookmark != null
    }

    private fun refresh() {
        binding.srCoin.isRefreshing = false
    }

}