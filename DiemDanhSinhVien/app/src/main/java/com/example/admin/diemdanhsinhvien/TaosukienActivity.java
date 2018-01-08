package com.example.admin.diemdanhsinhvien;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.diemdanhsinhvien.fragment.DatePickerFragment;
import com.example.admin.diemdanhsinhvien.fragment.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaosukienActivity extends AppCompatActivity implements  TimePickerFragment.TimeDialogListener,DatePickerFragment.DateDialogListener {
     EditText edt_tensukien, edt_diadiem, edt_soluong;
     TextView txtEventDate,txtEventTime;
     Button btnDatePicker,btnTimepicker,btnThemsukien;
    String ngay;
    String thoigian;
    String time;
    String diadiem="";
    String tensukien="";
    String soluong="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taosukien);
        edt_tensukien = (EditText)findViewById(R.id.edt_Tensukien);
        txtEventDate=(TextView) findViewById(R.id.txt_ngay);
        txtEventTime=(TextView)findViewById(R.id.txt_gio);
        btnDatePicker=(Button)findViewById(R.id.datepicker);
        btnTimepicker=(Button)findViewById(R.id.timepicker);
        edt_diadiem = (EditText)findViewById(R.id.edt_Diadiem);
        edt_soluong = (EditText)findViewById(R.id.edt_Soluong);
        btnThemsukien=(Button)findViewById(R.id.btn_themsukien);

        tensukien = edt_tensukien.getText().toString();
        diadiem = edt_diadiem.getText().toString();
        soluong = edt_soluong.getText().toString().trim();
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment dialog=new DatePickerFragment();
                dialog.show(getSupportFragmentManager(),"DateDialog");
            }
        });
        btnTimepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment dialog=new TimePickerFragment();
                dialog.show(getSupportFragmentManager(),"TimeDialog");
            }
        });

        btnThemsukien.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if ("".equals(edt_tensukien.getText().toString().trim()) ||
                   "".equals(edt_diadiem.getText().toString().trim()) ||
                   "".equals(edt_soluong.getText().toString().trim())) {
                   Toast.makeText(TaosukienActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
               }else {
                   Intent intent = new Intent();
                   intent.putExtra("TEN_SUKIEN", edt_tensukien.getText().toString().trim());
                   intent.putExtra("THOIGIAN", thoigian+" "+ngay);
                   intent.putExtra("DIADIEM", edt_diadiem.getText().toString().trim());
                   intent.putExtra("SOLUONG", Integer.parseInt(edt_soluong.getText().toString().trim()));
                   setResult(Activity.RESULT_OK, intent);
                   finish();
               }
           }
       });

    }
    @Override
    public String onFinishDialog(String time) {
        txtEventTime.setText(time);
        thoigian=time;
        return thoigian;
    }

    @Override
    public String onFinishDialog(Date date) {
        txtEventDate.setText(formatDate(date));
        ngay=formatDate(date);
        return formatDate(date);
    }
    public String formatDate(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        String chuoingay=sdf.format(date);
        return chuoingay;
    }


}
