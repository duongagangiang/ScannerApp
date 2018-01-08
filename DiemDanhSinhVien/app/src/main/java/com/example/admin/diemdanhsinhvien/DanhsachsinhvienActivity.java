package com.example.admin.diemdanhsinhvien;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.admin.diemdanhsinhvien.adapter.CustomAdapter;
import com.example.admin.diemdanhsinhvien.adapter.SukienAdapter;
import com.example.admin.diemdanhsinhvien.custom.SimpleDividerItemDecoration;
import com.example.admin.diemdanhsinhvien.data.APIService;
import com.example.admin.diemdanhsinhvien.data.ApiUtils;
import com.example.admin.diemdanhsinhvien.data.DatabaseHelper;
import com.example.admin.diemdanhsinhvien.data.NetworkStateChecker;
import com.example.admin.diemdanhsinhvien.model.Name;
import com.example.admin.diemdanhsinhvien.model.Student;
import com.example.admin.diemdanhsinhvien.model.Sukien;
import com.example.admin.diemdanhsinhvien.scanner.ScannerActivity;
import com.example.admin.diemdanhsinhvien.scanner.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachsinhvienActivity extends AppCompatActivity {
    public static final String URL_SAVE_NAME = "http://quanlysukien.atwebpages.com/webservice/danhsachsinhvien/update_danhsachsinhvien.php";
    public static final String URL_GET_USERS = "http://quanlysukien.atwebpages.com/webservice/danhsachsinhvien/update_danhsachsinhvien.php";
    //database helper object
    private DatabaseHelper db;

    //View objects
    private Button buttonSave;
    private EditText editTextMSSV,editTextIDSUKIEN;
    private ListView listViewNames;

    //List to store all the names
    private List<Name> names;

    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    public static final int REQ_CODE = 1996;
    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCAST = "com.example.admin.diemdanhsinhvien";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;

    //adapterobject for list view
   // private NameAdapter nameAdapter;

    List<Student> students;
    FloatingActionButton fab_scanner;
    APIService mApi;
    CustomAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachsinhvien);
        recyclerView=(RecyclerView)findViewById(R.id.rec_danhsachsinhvien);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mApi= ApiUtils.getAPIService();
        fab_scanner=(FloatingActionButton)findViewById(R.id.fab_scanner);
        fab_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(DanhsachsinhvienActivity.this, ScannerActivity.class),REQ_CODE);
            }
        });
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //loading the names again
                loadNames();
            }
        };
        db = new DatabaseHelper(this);
        names = new ArrayList<>();
        registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));
        registerReceiver(new NetworkStateChecker(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }
    private void loadNames() {
        names.clear();
        Cursor cursor = db.getNames();
        if (cursor.moveToFirst()) {
            do {
                Name name = new Name(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_MSSV)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_IDSUKIEN)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_STATUS))
                );
                names.add(name);
            } while (cursor.moveToNext());
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        students=new ArrayList<>();
        Sukien sukien=(Sukien)getIntent().getSerializableExtra("SUKIEN");
        int idSukien=sukien.getId();
        getAllStudents(idSukien);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==REQ_CODE){

               saveNameToServer(data);
            }
        }
    }
    /*
    * this method will simply refresh the list
    * */
//    private void refreshList() {
//        nameAdapter.notifyDataSetChanged();
//    }

    /*
    * this method is saving the name to ther server
    * */
    private void saveNameToServer(Intent data) {
        Sukien sukien=(Sukien)getIntent().getSerializableExtra("SUKIEN");
        final String idSukien=String.valueOf(sukien.getId());
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Name...");
        progressDialog.show();

        final String mssv = data.getStringExtra("MSSV").trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SAVE_NAME,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                //if there is a success
                                //storing the name to sqlite with status synced
                                saveNameToLocalStorage(mssv,idSukien, NAME_SYNCED_WITH_SERVER);
                            } else {
                                //if there is some error
                                //saving the name to sqlite with status unsynced
                                saveNameToLocalStorage(mssv,idSukien, NAME_NOT_SYNCED_WITH_SERVER);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        //on error storing the name to sqlite with status unsynced
                        saveNameToLocalStorage(mssv,idSukien, NAME_NOT_SYNCED_WITH_SERVER);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mssv", mssv);
                params.put("idSuKien", String.valueOf(idSukien));
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    //saving the name to local storage
    private void saveNameToLocalStorage(String mssv,String idSuKien, int status) {
        editTextMSSV.setText("");
        editTextIDSUKIEN.setText("");
        db.addName(mssv,idSuKien, status);
        Name n = new Name(mssv,idSuKien, status);
        names.add(n);
    }
    public void getAllStudents(int idSukien){

        mApi.getStudents(idSukien).enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                try {
                    students.clear();
                    for (int i = 0; i < response.body().size(); i++) {
                        int id=response.body().get(i).getId();
                        String hoten=response.body().get(i).getHoten();
                        String lop=response.body().get(i).getLop();
                        String mssv=response.body().get(i).getMssv();
                        int diemdanh=response.body().get(i).getDiemdanh();
                        students.add(new Student(id,mssv,hoten,lop,diemdanh));

                    }
                    adapter=new CustomAdapter(getApplicationContext(),students);
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Log.d("onResponse", "Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }
}
