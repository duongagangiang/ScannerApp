package com.example.admin.diemdanhsinhvien.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.diemdanhsinhvien.DanhsachsinhvienActivity;
import com.example.admin.diemdanhsinhvien.R;
import com.example.admin.diemdanhsinhvien.data.APIService;
import com.example.admin.diemdanhsinhvien.data.ApiUtils;
import com.example.admin.diemdanhsinhvien.model.Student;
import com.example.admin.diemdanhsinhvien.model.Sukien;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 12/25/2017.
 */

public class SukienAdapter extends RecyclerView.Adapter<SukienAdapter.MyViewHolder> {
    List<Sukien> listSukien;
    Context context;

    public SukienAdapter(List<Sukien> listSukien, Context context) {
        this.listSukien = listSukien;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_rec_sukien,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       holder.txt_tensukien.setText(listSukien.get(position).getTensukien());
       holder.txt_thoigian.setText(listSukien.get(position).getThoigian());
       holder.txt_diadiem.setText(listSukien.get(position).getDiadiem());
    }

    @Override
    public int getItemCount() {
        return listSukien.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_tensukien,txt_thoigian,txt_diadiem;
        ImageView img_editsukien;
        public MyViewHolder(View itemView) {
            super(itemView);
            txt_tensukien=(TextView)itemView.findViewById(R.id.txt_tensukien);
            txt_thoigian=(TextView)itemView.findViewById(R.id.txt_thoigian);
            txt_diadiem=(TextView)itemView.findViewById(R.id.txt_diadiem);
            img_editsukien=(ImageView)itemView.findViewById(R.id.img_editsukien);
            itemView.setOnClickListener(this);
            img_editsukien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION && view.getId()==R.id.img_editsukien) {
                        APIService apiService= ApiUtils.getAPIService();
                        apiService.deleteEvent(listSukien.get(pos).getId()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String status="";
                                    
                                } catch (Exception e) {
                                    Log.d("onResponse", "Error");
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }
            });
        }

        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            if(pos!=RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, DanhsachsinhvienActivity.class);
                intent.putExtra("SUKIEN", listSukien.get(pos));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }

    }
}
