package com.example.coinranking.ui.coins.detail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.coinranking.core.Resource
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.databinding.FragmentCoinDetailOverviewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailOverviewFragment : Fragment() {

    private lateinit var binding: FragmentCoinDetailOverviewBinding
    private val coinDetailViewModel by viewModels<CoinDetailViewModel>()
    private lateinit var coin: CoinAndBookmark

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            coin = requireArguments().getParcelable("cryptocurrency")!!

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCoinDetailOverviewBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        coinDetailViewModel.getDetail(coin.coin.uuid)
        bind()
        initViews()

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
                    initViews()
                }

                is Resource.Loading -> {
                    println("loading")
                }
            }
        })
    }

    private fun initViews() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.tvCoinDescription1.text = Html.fromHtml(
                binding.coin?.description ?: "Nothing to show",
                Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            binding.tvCoinDescription1.text = Html.fromHtml(binding.coin?.description)
        }
    }

    private fun bind() {
        binding.coin = coin.coin
    }
}