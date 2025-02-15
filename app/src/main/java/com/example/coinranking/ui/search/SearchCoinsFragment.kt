package com.example.coinranking.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinranking.core.BaseFragment
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.databinding.FragmentSearchCoinsBinding
import com.example.coinranking.ui.common.CoinAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchCoinsFragment : BaseFragment() {

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

    private lateinit var binding: FragmentSearchCoinsBinding

    private val searchViewModel: SearchViewModel by activityViewModels()


    override fun onResume() {
        super.onResume()
        binding.rvCoins.scrollToPosition(0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchCoinsBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe()
        searchViewModel.refresh()
    }

    private fun initViews() {

        binding.rvCoins.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchCoinsFragment.adapter
        }

    }

    private fun observe() {
        searchViewModel.coinsSearchResultList.observe(
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
                binding.rvCoins.scrollToPosition(0)
            })
    }


    private fun navigateToDetailFragment(coin: CoinAndBookmark) {
        val action =
            SearchFragmentDirections
                .actionSearchFragmentToCoinDetailFragment(
                    coin
                )
        navController.navigate(action)
    }

    private fun coinItemBookmarkClickHandel(
        coin: CoinAndBookmark,
        position: Int
    ) {

        if (coin.bookmark != null) {
            searchViewModel.removeCoinFromBookmarks(Bookmark(coin.coin.uuid))
        } else {
            searchViewModel.addCoinToBookmarks(Bookmark(coin.coin.uuid))

        }
        adapter.notifyItemChanged(position)
    }

    private fun isCoinBookmarked(coin: CoinAndBookmark): Boolean {
        return coin.bookmark != null
    }
}