package com.example.coinranking.ui.exchanges.detail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.Exchange
import com.example.coinranking.databinding.FragmentExchangeDetailOverviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeDetailOverviewFragment : Fragment() {

    private lateinit var binding: FragmentExchangeDetailOverviewBinding
    private val exchangeDetailViewModel by viewModels<ExchangeDetailViewModel>()
    private lateinit var exchange: Exchange


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            exchange = requireArguments().getParcelable("exchange")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExchangeDetailOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        exchangeDetailViewModel.getDetail(exchange.uuid)
        bind()
        initViews()
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
                    initViews()
                }

                is Resource.Loading -> {
                }
            }
        })
    }

    private fun bind() {
        binding.exchange = exchange
    }

    private fun initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.tvExchangeDescription2.text = Html.fromHtml(
                binding.exchange?.description ?: "Nothing to show",
                Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            binding.tvExchangeDescription2.text = Html.fromHtml(binding.exchange?.description)
        }
    }
}