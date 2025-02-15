package com.example.coinranking.ui.exchanges.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.coinranking.R
import com.example.coinranking.core.BaseFragment
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Exchange
import com.example.coinranking.databinding.FragmentExchangeDetailBinding
import com.example.coinranking.ui.common.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentExchangeDetailBinding
    private val args by navArgs<ExchangeDetailFragmentArgs>()
    private val exchangeDetailViewModel by viewModels<ExchangeDetailViewModel>()
    private lateinit var exchange: Exchange

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExchangeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exchange = args.exchange
        observe()
        bind()
        setupTabLayoutWithViewPager()
    }

    private fun observe() {
        exchangeDetailViewModel.exchangeResource.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Error -> {
                }

                is Resource.Success -> {
                    exchange = resource.data!!
                    bind()
                    binding.notifyChange()
                }

                is Resource.Loading -> {
                }
            }
        })
    }

    private fun bind() {

        binding.exchange = exchange
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item: MenuItem = menu.findItem(
            R.id.searchFragment
        )
        item.isVisible = false
    }

    private fun setupTabLayoutWithViewPager() {
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        adapter.apply {
            addFragment(
                createViewPagerFragment(ExchangeDetailOverviewFragment()),
                "Overview"
            )
            addFragment(
                createViewPagerFragment(ExchangeDetailCoinsFragment()),
                "Cryptocurrencies"
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
        bundle.putParcelable("exchange", exchange)
        fragment.arguments = bundle
        return fragment
    }
}