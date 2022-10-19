package com.example.horsey.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.horsey.DataBase.Student;
import com.example.horsey.Model.Adapter.StudentAdapter;
import com.example.horsey.R;
import com.example.horsey.ViewModel.StudentViewModel;

import java.util.List;

public class StudentFragment extends BaseFragment {

    private StudentAdapter adapter;

    public StudentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_student, container, false);
        initView(v);
        return v;
    }

    @Override
    public void initView(View v) {
        StudentViewModel viewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);
        RecyclerView recyclerView = v.findViewById(R.id.student_recyclerView);
        LiveData<List<Student>> students = viewModel.getAllStudent();
        students.observe(requireActivity(), students1 -> {
            adapter = new StudentAdapter(students1,requireContext());
            recyclerView.setAdapter(adapter);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false));

    }
}