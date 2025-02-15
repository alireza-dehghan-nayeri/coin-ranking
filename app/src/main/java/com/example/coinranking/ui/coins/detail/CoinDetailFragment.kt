package com.example.coinranking.ui.coins.detail

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.coinranking.R
import com.example.coinranking.core.BaseFragment
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.databinding.FragmentCoinDetailBinding
import com.example.coinranking.ui.common.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentCoinDetailBinding
    private val args by navArgs<CoinDetailFragmentArgs>()
    private val coinDetailViewModel by viewModels<CoinDetailViewModel>()
    private lateinit var coin: CoinAndBookmark

    override fun onResume() {
        super.onResume()
        val times = resources.getStringArray(R.array.times)
        val timeArrayAdapter = ArrayAdapter(requireContext(), R.layout.item_time_drop_down, times)
        binding.avTimeFilterDropDown.apply {
            setAdapter(timeArrayAdapter)
        }

        val prices = resources.getStringArray(R.array.prices)
        val priceArrayAdapter = ArrayAdapter(requireContext(), R.layout.item_time_drop_down, prices)
        binding.avPriceTypeDropDown.setAdapter(priceArrayAdapter)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coin = args.coin
        observe()
        bind()
        initViews()
        setupTabLayoutWithViewPager()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item: MenuItem = menu.findItem(
            R.id.searchFragment
        )
        item.isVisible = false
    }

    private fun observe() {

        coinDetailViewModel.coinResource.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Error -> {
                    println("error")
                }
                is Resource.Success -> {
                    println("sucsses")
                    coin = resource.data!!
                    bind()
                    binding.notifyChange()
                }

                is Resource.Loading -> {
                    println("loading")
                }
            }
        })
    }

    private fun bind() {
        binding.coin = coin.coin
    }

    private fun initViews() {

        setIvBookmarkImageResource()

        binding.ivBookmark.setOnClickListener {
            coinBookmarkClickHandel(coin)

        }
    }

    private fun coinBookmarkClickHandel(
        coin: CoinAndBookmark
    ) {

        if (isCoinBookmarked(coin)) {

            coinDetailViewModel.removeCoinFromBookmarks(Bookmark(coin.coin.uuid))
            binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_empty)
        } else {

            coinDetailViewModel.addCoinToBookmarks(Bookmark(coin.coin.uuid))
            binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_filled)

        }
    }

    private fun isCoinBookmarked(coin: CoinAndBookmark): Boolean {
        return coin.bookmark != null
    }

    private fun setupTabLayoutWithViewPager() {
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        adapter.apply {
            addFragment(
                createViewPagerFragment(CoinDetailOverviewFragment()),
                "Overview"
            )
            addFragment(
                createViewPagerFragment(CoinDetailExchangesFragment()),
                "Exchanges"
            )
        }

        with(binding) {
            viewPager.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = adapter.getItemTitle(position)

            }.attach()
        }

    }

    private fun createViewPagerFragment(fragment: Fragment): Fragment {
        val bundle = Bundle()
        bundle.putParcelable("cryptocurrency", coin)
        fragment.arguments = bundle
        return fragment
    }

    private fun setIvBookmarkImageResource() {
        if (isCoinBookmarked(coin)) {
            binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_filled)
        } else {
            binding.ivBookmark.setImageResource(R.drawable.ic_bookmark_empty)
        }
    }
}