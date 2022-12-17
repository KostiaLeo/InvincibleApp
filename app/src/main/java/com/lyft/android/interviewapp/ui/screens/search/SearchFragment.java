package com.lyft.android.interviewapp.ui.screens.search;

import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import com.lyft.android.interviewapp.R;
import com.lyft.android.interviewapp.data.repository.models.ShortEventUiModel;
import com.lyft.android.interviewapp.databinding.FragmentSearchBinding;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    private FragmentSearchBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.getUiStateLiveData().observe(getViewLifecycleOwner(), this::bindUiState);
    }

    private void bindUiState(SearchUiState uiState) {
        binding.getRoot().removeAllViews();

        for (ShortEventUiModel event : uiState.getEvents()) {
            TextView textView = new TextView(requireContext());
            textView.setText(event.toString());
            textView.setOnClickListener(v -> navigateToEventDetails(event.getId()));
            binding.getRoot().addView(textView);
        }
    }

    private void navigateToEventDetails(String eventId) {
        Navigation
                .findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(SearchFragmentDirections.actionSearchFragmentToEventDetailsFragment(eventId));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}