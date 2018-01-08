package com.example.admin.diemdanhsinhvien.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.diemdanhsinhvien.data.DbQuanlysukien;
import com.example.admin.diemdanhsinhvien.model.Contact;
import com.example.admin.diemdanhsinhvien.R;
import com.example.admin.diemdanhsinhvien.model.Student;

import java.util.List;

/**
 * Created by Admin on 12/12/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    List<Student> students;
    public CustomAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=(LayoutInflater.from(context)).inflate(R.layout.item_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txt_mssv.setText(students.get(position).getMssv());
        holder.txt_stt.setText(String.valueOf(position+1));
        holder.txt_hoten.setText(students.get(position).getHoten());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_stt,txt_mssv,txt_hoten;
        public MyViewHolder(View itemView) {
            super(itemView);
            txt_stt=(TextView)itemView.findViewById(R.id.txt_stt);
            txt_mssv=(TextView)itemView.findViewById(R.id.txt_mssv);
            txt_hoten=(TextView)itemView.findViewById(R.id.txt_hoten);
        }
    }
}
