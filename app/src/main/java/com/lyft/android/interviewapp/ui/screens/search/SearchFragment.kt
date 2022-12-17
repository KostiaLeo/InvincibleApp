package com.lyft.android.interviewapp.ui.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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