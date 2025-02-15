package com.example.coinranking.ui.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import com.example.coinranking.R
import com.example.coinranking.core.BaseFragment
import com.example.coinranking.databinding.FragmentSearchBinding
import com.example.coinranking.ui.common.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : BaseFragment() {

    private lateinit var binding: FragmentSearchBinding

    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // needed to be true to hide search icon in the tool bar
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupTabLayoutWithViewPager()

        initViews()

    }

    private fun initViews() {
        binding.textField.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    search(v.text.toString())
                    true
                }
                else -> false
            }
        }
    }

    private fun search(searchQuery: String?) {
        searchViewModel.search(searchQuery)
        binding.textField.text?.clear()
        closeSoftKeyboard(requireContext(), binding.textField)

    }

    // hide search icon
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
                SearchCoinsFragment(),
                "Cryptocurrencies"
            )
            addFragment(
                SearchExchangesFragment(),
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

    private fun closeSoftKeyboard(context: Context, v: View) {
        val iMm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        iMm.hideSoftInputFromWindow(v.windowToken, 0)
        v.clearFocus()
    }
}