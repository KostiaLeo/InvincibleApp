package com.lyft.android.interviewapp.ui.screens.event_details;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lyft.android.interviewapp.databinding.FragmentEventDetailsBinding;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EventDetailsFragment extends Fragment {

    private EventDetailsViewModel eventDetailsViewModel;

    private FragmentEventDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = FragmentEventDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventDetailsViewModel = new ViewModelProvider(this).get(EventDetailsViewModel.class);
        eventDetailsViewModel.getUiStateLiveData().observe(getViewLifecycleOwner(), this::bindUiState);
    }

    private void bindUiState(PlaceDetailsUiState uiState) {
        binding.details.setText(uiState.getName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}