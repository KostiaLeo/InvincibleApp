package com.lyft.android.interviewapp.ui.screens.event_details;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
        requireActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        binding = FragmentEventDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EventDetailsViewModel.class);
        viewModel.getUiStateLiveData().observe(getViewLifecycleOwner(), this::bindUiState);

        binding.registerButton.setOnClickListener(v -> viewModel.registerToEvent());
        binding.arrowIV.setOnClickListener(v -> Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigateUp());
    }

    private void bindUiState(PlaceDetailsUiState uiState) {

        if (uiState.isLoading()) {
            binding.progressBar.setVisibility(VISIBLE);
            binding.linearLayout2.setVisibility(View.INVISIBLE);
            binding.toolBar.setVisibility(View.INVISIBLE);
            binding.cardView.setVisibility(View.INVISIBLE);
        } else {
            setupStatusBarColor();
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.linearLayout2.setVisibility(VISIBLE);
            binding.toolBar.setVisibility(VISIBLE);
            binding.cardView.setVisibility(VISIBLE);
        }

        if (uiState.getDetails().isRegistered()) {
            binding.registerButton.setBackgroundResource(R.drawable.green_bg_rounded);
            binding.registerButton.setText("Registered");
        } else {
            binding.registerButton.setBackgroundResource(R.drawable.blue_bg_rounded);
            binding.registerButton.setText("Register");
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

    private void setupStatusBarColor() {
        View decorView = requireActivity().getWindow().getDecorView();
        int uiVisibility = decorView.getSystemUiVisibility();
        uiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        decorView.setSystemUiVisibility(uiVisibility);
        requireActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.blue));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}