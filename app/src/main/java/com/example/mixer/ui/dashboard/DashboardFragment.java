package com.example.mixer.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mixer.MainActivity;
import com.example.mixer.dashboard;
import com.example.mixer.databinding.FragmentDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;

//Nothing to see here
public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private FirebaseAuth mAuth;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        final TextView textView = binding.textDashboard;
        textView.setText(mAuth.getCurrentUser().getEmail());
        //dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent logout;

        logout = new Intent(view.getContext(), MainActivity.class);
        startActivity(logout);
    }
}