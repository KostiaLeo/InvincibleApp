package com.lyft.android.interviewapp.ui.screens.event_details;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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

        binding.registerButton.setOnClickListener(v -> viewModel.registerToEvent());
    }

    private void bindUiState(PlaceDetailsUiState uiState) {

        if (uiState.isLoading()){
            binding.progressBar.setVisibility(VISIBLE);
            binding.group.setVisibility(GONE);
        }else {
            binding.progressBar.setVisibility(GONE);
            binding.group.setVisibility(VISIBLE);
        }

        if (uiState.getDetails().isRegistered()){
            binding.registerButton.setBackgroundColor(getResources().getColor(R.color.green));
        } else {
            binding.registerButton.setBackgroundColor(getResources().getColor(R.color.blue));
        }
        binding.eventIconIV.setImageResource(uiState.getDetails().getIconResourceId());
        binding.nameToolBarTV.setText(uiState.getDetails().getOrganizer());
        binding.dateTV.setText(uiState.getDetails().getDateTime());
        binding.pointsTV.setText(uiState.getDetails().getGamePoints());
        binding.typeOfWork.setText(uiState.getDetails().getName());
        binding.placeTV.setText(uiState.getDetails().getLocation());
        binding.numberOfWorkersTV.setText(uiState.getDetails().getVolunteersCount());
        binding.donationTV.setText(uiState.getDetails().getDonationsCount());
        binding.organizerDescriptionTV.setText(uiState.getDetails().getOrganizer());
        binding.descriptionTV.setText(uiState.getDetails().getDescription());
        binding.dutiesTV.setText(uiState.getDetails().getDuties());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}