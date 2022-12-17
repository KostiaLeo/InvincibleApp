package com.lyft.android.interviewapp.ui.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lyft.android.interviewapp.R
import com.lyft.android.interviewapp.ui.screens.search.content.SearchContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        requireActivity().window.statusBarColor = resources.getColor(R.color.light_blue)
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        return ComposeView(requireContext()).apply {
            setContent {
                SearchContent(
                    viewModel = viewModel,
                    onNavigateToEventDetails = ::navigateToEventDetails
                )
            }
        }
    }

    private fun navigateToEventDetails(id: String) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToEventDetailsFragment(id)
        )
    }
}