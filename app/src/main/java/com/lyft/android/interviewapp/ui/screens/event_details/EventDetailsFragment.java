package com.lyft.android.interviewapp.ui.screens.event_details;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lyft.android.interviewapp.R;
import com.lyft.android.interviewapp.databinding.FragmentEventDetailsBinding;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EventDetailsFragment extends Fragment {

    private EventDetailsViewModel viewModel;

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
        viewModel = new ViewModelProvider(this).get(EventDetailsViewModel.class);
        viewModel.getUiStateLiveData().observe(getViewLifecycleOwner(), this::bindUiState);

        binding.details.setOnClickListener(v -> viewModel.registerToEvent());
    }

    private void bindUiState(PlaceDetailsUiState uiState) {
        binding.image.setImageResource(uiState.getDetails().getIconResourceId());
        binding.details.setText(uiState.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}