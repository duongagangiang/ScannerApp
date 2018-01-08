package com.example.admin.diemdanhsinhvien.data;

import com.example.admin.diemdanhsinhvien.model.MSG;
import com.example.admin.diemdanhsinhvien.model.Nguoidung;
import com.example.admin.diemdanhsinhvien.model.Student;
import com.example.admin.diemdanhsinhvien.model.Sukien;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Admin on 12/19/2017.
 */

public interface APIService {
//    @GET("/student/api.php")
//    Call<List<Student>> getStudents();
//    // GET student by id student from server
//// Server return json object
//    @GET("/student/api.php")
//    Call<List<Student>> getStudent(@Query("id") String id);
//    // POST student from client to server
//    // Server return string
//    @FormUrlEncoded
//    @POST("/student/api.php")
//    Call<ResponseBody> insertStudent(@Field("name") String name, @Field("age") int age, @Field("nclass") String nclass);
    //login
    @FormUrlEncoded
    @POST("/webservice/nguoidung/login.php")
    Call<MSG> login(@Field("email") String email, @Field("password") String password);

    //Sự kiện
    @GET("/webservice/sukien/danhsach_json.php")
    Call<List<Sukien>> getEvents();
    // GET student by id student from server
// Server return json object
    @GET("/webservice/sukien/danhsach_json.php")
    Call<List<Sukien>> getEvent(@Query("id") String id);
    // POST student from client to server
    // Server return string
    @FormUrlEncoded
    @POST("/webservice/sukien/danhsach_json.php")
    Call<ResponseBody> insertEvents(@Field("tensukien") String tensukien, @Field("thoigian") String thoigian, @Field("diadiem") String diadiem, @Field("soluong") int soluong);
    //Quan ly danh sach sinh vien
    @FormUrlEncoded
    @POST("/webservice/danhsachsinhvien/danhsachsv_json.php")
    Call<List<Student>> getStudents(@Field("sukien") int idSukien);

    @GET("/webservice/nguoidung/users_json.php")
    Call<List<Nguoidung>> getUsers();

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseBody> deleteEvent(@Field("id") int id);
}
