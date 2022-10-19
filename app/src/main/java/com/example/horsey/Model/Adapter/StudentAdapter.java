package com.example.horsey.Model.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horsey.DataBase.Student;
import com.example.horsey.R;

import java.util.List;

public class StudentAdapter extends BaseAdapter<Student, StudentAdapter.StudentViewHolder>{

    public StudentAdapter(List<Student> list, Context context) {
        super(list, context);
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context.get()).inflate( R.layout.cell_student,parent,false);
        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student s = list.get(position);
        holder.bind(s);
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {

        private ImageView gender,avatar;
        private TextView name,birthDay;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View view) {
            gender = view.findViewById(R.id.student_gender);
            avatar = view.findViewById(R.id.student_avatar);
            name = view.findViewById(R.id.student_name);
            birthDay = view.findViewById(R.id.student_birthDay);
        }

        public void bind(Student student) {
            if (student.getGender() == 0) {
                gender.setImageResource(R.mipmap.male);
            } else {
                gender.setImageResource(R.mipmap.female);
            }

            name.setText(student.getName());
            birthDay.setText(student.getBirthDay());
        }
    }
}
