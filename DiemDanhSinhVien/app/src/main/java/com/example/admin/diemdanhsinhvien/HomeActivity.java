package com.example.admin.diemdanhsinhvien;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.diemdanhsinhvien.adapter.SukienAdapter;
import com.example.admin.diemdanhsinhvien.custom.SimpleDividerItemDecoration;
import com.example.admin.diemdanhsinhvien.data.APIService;
import com.example.admin.diemdanhsinhvien.data.ApiUtils;
import com.example.admin.diemdanhsinhvien.model.Sukien;
import com.example.admin.diemdanhsinhvien.scanner.ScannerActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private APIService mAPIService1;
    RecyclerView recyclerView;
    SukienAdapter adapter;
    List<Sukien> sukienList=new ArrayList<>();
    NavigationView navigationView;
    TextView txt_myemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAPIService1= ApiUtils.getAPIService();
        recyclerView=(RecyclerView)findViewById(R.id.rec_danhsachsukien);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(HomeActivity.this, ScannerActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
         txt_myemail=header.findViewById(R.id.txt_myemail);
        txt_myemail.setText(getIntent().getStringExtra("EMAIL"));
    }
    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
    @Override
    public void recreate() {
        super.recreate();
        if(checkNetworkConnection()){
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkNetworkConnection()){
            navigationView.getMenu().findItem(R.id.nav_create_events).setEnabled(true);
        }else{
            navigationView.getMenu().findItem(R.id.nav_create_events).setEnabled(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllEvents();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_create_events) {

            // Handle the camera action
            taoSuKien();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //Tao sự kiện
    private void taoSuKien() {
        startActivityForResult(new Intent(HomeActivity.this,TaosukienActivity.class),1);
    }

    private void getAllEvents() {

        mAPIService1.getEvents().enqueue(new Callback<List<Sukien>>() {
            @Override
            public void onResponse(Call<List<Sukien>> call, Response<List<Sukien>> response) {
                try {
                    String result = "";
                    sukienList.clear();
                    for (int i = 0; i < response.body().size(); i++) {
//                        result += "ID: " + response.body().get(i).getId()
//                                + "\nSukien: " + response.body().get(i).getTensukien()
//                                + "\nThoigian: " + response.body().get(i).getThoigian()
//                                + "\nDiadiem: " + response.body().get(i).getDiadiem()
//                                + "\nSoluong: " + response.body().get(i).getSoluong() + "\n\n";
                        int id=response.body().get(i).getId();
                        String tensukien=response.body().get(i).getTensukien();
                        String thoigian=response.body().get(i).getThoigian();
                        String diadiem=response.body().get(i).getDiadiem();
                        int soluong=response.body().get(i).getSoluong();
                        sukienList.add(new Sukien(id,tensukien,thoigian,diadiem,soluong));
                    }

                     adapter=new SukienAdapter(sukienList,getApplicationContext());
                     recyclerView.setAdapter(adapter);
                     Log.d("data2",""+sukienList);
                } catch (Exception e) {
                    Log.d("onResponse", "Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Sukien>> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    private void getEventById() {

    }
    private void insertEvents(String tensukien, String thoigian, String diadiem, int soluong) {
        mAPIService1.insertEvents(tensukien, thoigian, diadiem,soluong).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    String status = response.body().string().toString().trim();
                    Log.e("response", status);
                    if (status.length() > 0) {
                        getAllEvents();
                        Toast.makeText(getBaseContext(), status, Toast.LENGTH_LONG).show();
                    } else {
                       adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Log.e("onResponse", "Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode== Activity.RESULT_OK){
            String tensukien=data.getStringExtra("TEN_SUKIEN");
            String thoigian=data.getStringExtra("THOIGIAN");
            String diadiem=data.getStringExtra("DIADIEM");
            int soluong=data.getIntExtra("SOLUONG",0);
            insertEvents(tensukien,thoigian,diadiem,soluong);
            Log.d("data",tensukien+"\n"+thoigian+"\n"+diadiem+"\n"+soluong+"\n");
        }
    }

}
