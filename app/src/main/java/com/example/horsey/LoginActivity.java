package com.example.horsey;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.horsey.Activity.ClassActivity;
import com.example.horsey.DataBase.Student;
import com.example.horsey.ViewModel.StudentViewModel;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private Button signupButton;
    private Button loginButton;
    private View decorView;
    private EditText userName, passWord;
    private StudentViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signupButton = findViewById(R.id.signupButton);
        loginButton = findViewById(R.id.loginButton);
        userName = findViewById(R.id.edit1);
        passWord = findViewById(R.id.edit2);
        viewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        decorView = getWindow().getDecorView();
        UIShrink();
        init();
    }

    private void UIShrink() {
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }


    private void init() {
        signupButton.setOnClickListener(
                this::toRegister
        );

        loginButton.setOnClickListener(
                view -> toLogin()
        );
    }

    public void toLogin() {
        String name = userName.getText().toString(), pass = passWord.getText().toString();
        viewModel.getStudentByName(name).observe(this, new Observer<Student>() {
            @Override
            public void onChanged(Student student) {
                if(student != null && student.getPassword().equals(pass)) {
                    Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                    startActivity(intent);
                    //finish();
                } else {
                    Log.d("输入的账号密码",name + "," + pass);
                    //Log.d("Student", student.getName() + student.getPassword());
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void toRegister(View view) {
        //Intent intent = new Intent(this, RegisterActivity.class);
        Intent intent = new Intent(this, ClassActivity.class);
        startActivity(intent);
        //finish();
    }

}