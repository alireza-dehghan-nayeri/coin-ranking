package com.example.coinranking.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinranking.core.BaseFragment
import com.example.coinranking.data.model.Bookmark
import com.example.coinranking.data.model.CoinAndBookmark
import com.example.coinranking.databinding.FragmentBookmarksBinding
import com.example.coinranking.ui.common.CoinAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment : BaseFragment() {

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

    private lateinit var binding: FragmentBookmarksBinding
    private val bookmarksViewModel by viewModels<BookmarksViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBookmarksBinding.inflate(
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

    }

    private fun initViews() {
        binding.rvBookmarks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@BookmarksFragment.adapter
        }

    }

    private fun observe() {
        bookmarksViewModel.bookmarks.observe(viewLifecycleOwner, { items ->
            adapter.submitList(items)
        })
    }

    private fun navigateToDetailFragment(coin: CoinAndBookmark) {
        val action =
            BookmarksFragmentDirections
                .actionBookmarksFragmentToCoinDetailFragment(coin)
        navController.navigate(action)
    }

    private fun coinItemBookmarkClickHandel(
        coin: CoinAndBookmark,
        position: Int
    ) {

        if (coin.bookmark != null) {

            bookmarksViewModel.removeCoinFromBookmarks(Bookmark(coin.coin.uuid))
        } else {
            bookmarksViewModel.addCoinToBookmarks(Bookmark(coin.coin.uuid))

        }
        adapter.notifyItemChanged(position)
    }

    private fun isCoinBookmarked(coin: CoinAndBookmark): Boolean {
        return coin.bookmark != null
    }

}