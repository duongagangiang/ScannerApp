package com.example.admin.diemdanhsinhvien.data;

/**
 * Created by Admin on 12/19/2017.
 */

public class ApiUtils {
    //public static final String BASE_URL = "http://10.0.2.2:81";
    public static final String BASE_URL = "http://quanlysukien.atwebpages.com";
    private ApiUtils() {
    }
    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
