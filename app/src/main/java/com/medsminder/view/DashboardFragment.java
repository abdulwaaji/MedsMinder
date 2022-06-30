package com.medsminder.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.medsminder.MedicViewModel;
import com.medsminder.MedicsAdapter;
import com.medsminder.R;
import com.medsminder.model.Medic;

import java.util.List;


public class DashboardFragment extends Fragment {

    MedicsAdapter medicAdapter;
    private MedicViewModel medicViewModel;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        medicAdapter = new MedicsAdapter();

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_dashboard);
        recyclerView.setAdapter(medicAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton floatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);
        floatingActionButton.show();

        medicViewModel = new ViewModelProvider(this).get(MedicViewModel.class);
        medicViewModel.getMedicsList().observe(getViewLifecycleOwner(), new Observer<List<Medic>>() {
            @Override
            public void onChanged(List<Medic> medicList) {
                medicAdapter.submitList(medicList);
            }
        });
        return view;

    }


}