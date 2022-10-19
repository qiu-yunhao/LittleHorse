package com.example.horsey.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.horsey.DataBase.Student;
import com.example.horsey.R;
import com.example.horsey.ViewModel.StudentViewModel;

import java.util.Objects;

public class RegisterFragment extends BaseFragment {

    private EditText username;
    private EditText password;
    private EditText birthday;
    private boolean isClick;
    private int gender;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        initView(v);
        return v;
    }

    @Override
    public void initView(View v) {
        isClick = false;
        username = v.findViewById(R.id.register_username);
        password = v.findViewById(R.id.register_password);
        birthday = v.findViewById(R.id.register_birthDay);
        AppCompatButton button = v.findViewById(R.id.register_login);
        RadioButton male = v.findViewById(R.id.gender_male);
        RadioButton female = v.findViewById(R.id.gender_female);
        male.setOnClickListener(this::radioGroupOnClick);
        female.setOnClickListener(this::radioGroupOnClick);
        StudentViewModel viewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);
        button.setOnClickListener(view -> {
            String userName = username.getText().toString(), passWord = password.getText().toString(), birthDay = birthday.getText().toString();
            if (isFinish())
                viewModel.getStudentByName(userName).observe(requireActivity(), student -> {
                    if (student != null) {
                        Toast.makeText(requireActivity(), "该账号已经被注册了", Toast.LENGTH_SHORT).show();
                    } else {
                        viewModel.insertNewStudent(new Student(userName, passWord,gender,birthDay));
                    }
                });
        });
    }

    private boolean isFinish() {
        return username.getText().length() > 0 && password.getText().length() > 0 && birthday.getText().length() > 0 && isClick;
    }

    public void radioGroupOnClick(View v) {
        isClick = ((RadioButton) v).isChecked();
        if(isClick) {
            switch (v.getId()) {
                case R.id.gender_male:
                    gender = 0;
                    break;
                case R.id.gender_female:
                    gender = 1;
                    break;
            }
        }
    }
}
