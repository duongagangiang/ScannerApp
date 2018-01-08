package com.example.admin.diemdanhsinhvien;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.diemdanhsinhvien.data.APIService;
import com.example.admin.diemdanhsinhvien.data.ApiUtils;
import com.example.admin.diemdanhsinhvien.model.MSG;
import com.example.admin.diemdanhsinhvien.model.Nguoidung;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangnhapActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_login;
    EditText edt_email,edt_pass;
    private APIService mApi;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public void setWidget(){
        btn_login=(Button)findViewById(R.id.btn_login);
        edt_email=(EditText)findViewById(R.id.edt_email);
        edt_pass=(EditText)findViewById(R.id.edt_password);
        mApi= ApiUtils.getAPIService();

    }
    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        setWidget();
        setEvents();
    }
    public void setEvents(){
        btn_login.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        if(checkNetworkConnection()) {
            login(edt_email.getText().toString(), edt_pass.getText().toString());
        }
        else{
            loginNoNetwork();
        }
    }
    public void loginNoNetwork(){
        preferences=getSharedPreferences("login",MODE_PRIVATE);
        String email=preferences.getString("email","");
        String password=preferences.getString("password","");
        if(email.equalsIgnoreCase(edt_email.getText().toString().trim()) && password.equalsIgnoreCase(edt_pass.getText().toString().trim()))
        {
            Intent intent=new Intent(DangnhapActivity.this,HomeActivity.class);
            intent.putExtra("EMAIL",edt_email.getText().toString().trim());
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Tài khoản không tồn tại",Toast.LENGTH_SHORT).show();
        }

    }
    public void login(final String email, final String password){

            mApi.login(email,password).enqueue(new Callback<MSG>() {
                @Override
                public void onResponse(Call<MSG> call, Response<MSG> response) {

                    if(response.body().getSuccess()==1){

                        Toast.makeText(getBaseContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                        preferences=getSharedPreferences("login",MODE_PRIVATE);
                        editor=preferences.edit();
                        editor.putString("email",email);
                        editor.putString("password",password);
                        editor.commit();
                        Intent intent=new Intent(DangnhapActivity.this,HomeActivity.class);
                        intent.putExtra("EMAIL",edt_email.getText().toString().trim());
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getBaseContext(),"Tài khoản hoặc mật khẩu sai",Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<MSG> call, Throwable t) {
                    Log.e("onFailure", t.toString());
                }
            });


    }
}
